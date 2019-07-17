package com.wml.productsearch.productload;

import com.wml.productsearch.product.Product;
import com.wml.productsearch.productresponse.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ProductLoader loads products on application start, and at configured intervals thereafter
 */
public class ProductLoader {
    private static final Logger LOG = LoggerFactory.getLogger(ProductLoader.class);
    private static final String URL_TEMPLATE = "https://{host}/walmartproducts/{pageNumber}/{pageSize}";
    private static final String ERROR_MESSAGE_FIRST_ATTEMPT = "Error loading page %d on first attempt";
    private static final String ERROR_MESSAGE_FINAL_ATTEMPT = "Error loading page %d on final attempt";
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final Semaphore SEMAPHORE = new Semaphore(1);
    private final List<String> stopWords;
    private final String hostname;
    private long pageSize = 30;
    private ProductMap productMap;

    public ProductLoader(final List<String> stopWords, final String hostname, final long pageSize) {
        this.stopWords = stopWords;
        this.hostname = hostname;
        this.pageSize = pageSize;
    }

    /**
     * loadProducts generates a new ProductMap by loading all products from the product service
     */
    @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds:300000}")
    public void loadProducts() {
        final ProductResponse firstPage = getPage(1);
        long totalProducts = firstPage.getTotalProducts();
        long totalPages = 0;
        if (totalProducts > 0 && totalProducts > firstPage.getPageSize()) {
            totalPages = (long) Math.ceil( (double) totalProducts / pageSize );
        }
        if (totalPages > 1) {
            ProductMap productMap = new ProductMap(this::notStopword);
            productMap.add(firstPage.getProducts());
            List<Integer> failedPages = populateProductMap(
                    Stream.iterate(2, i -> i + 1).limit(totalPages),
                    productMap,
                    ERROR_MESSAGE_FIRST_ATTEMPT
            );
            if (failedPages.size() > 0) {
                populateProductMap(
                        failedPages.stream(),
                        productMap,
                        ERROR_MESSAGE_FINAL_ATTEMPT
                );
            }
            setProductMap(productMap);
        }
    }

    /**
     * populateProductMap populates the provided ProductMap by loading the pages in the provided Stream<Integer>
     * @param pages
     * @param productMap
     * @param messageTemplate
     * @return
     */
    private List<Integer> populateProductMap(
            final Stream<Integer> pages,
            final ProductMap productMap,
            final String messageTemplate
    ) {
        return pages
                .map(pageNumber -> {
                    ProductResponse response = null;
                    try {
                        response = getPage(pageNumber);
                        productMap.add(response.getProducts());
                    } catch (Exception e) {
                        LOG.error(String.format("Error loading page %d on first attempt", pageNumber), e);
                    }
                    return Objects.isNull(response) ? new Integer(pageNumber) : null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * getPage makes a request for a single page of Products
     * @param pageNumber
     * @return
     */
    private ProductResponse getPage(final long pageNumber) {
        return REST_TEMPLATE.exchange(
                URL_TEMPLATE,
                HttpMethod.GET,
                null,
                ProductResponse.class,
                hostname,
                pageNumber,
                pageSize
        ).getBody();
    }

    /**
     * searchProducts finds products matching a query
     * @param query
     * @return
     */
    public List<Product> searchProducts(final String query) {
        if (Objects.isNull(getProductMap())) {
            return Collections.emptyList();
        } else {
            return getProductMap().findProducts(query);
        }
    }

    /**
     * gets all products
     * @return
     */
    public List<Product> getProducts() {
        return getProductMap().getProducts();
    }

    /**
     * getter for productMap
     *
     * @return com.wml.productsearch.productload.ProductMap productMap
     */
    public ProductMap getProductMap() {
        try {
            SEMAPHORE.acquire();
        } catch (InterruptedException e) {
            LOG.error("Error acquiring semaphore");
        } finally {
            SEMAPHORE.release();
        }
        return productMap;
    }

    /**
     * setter for productMap
     *
     * @param productMap
     **/
    public void setProductMap(ProductMap productMap) {
        try {
            SEMAPHORE.acquire();
        } catch (InterruptedException e) {
            LOG.error("Error acquiring semaphore");
        } finally {
            SEMAPHORE.release();
        }
        this.productMap = productMap;
    }

    /**
     * notStopword validates that a word is not in the list of stopwords
     * @param word
     * @return
     */
    public boolean notStopword(final String word) {
        return Objects.nonNull(stopWords) && !stopWords.contains(word);
    }

    /**
     * indicates that products are loaded
     * @return
     */
    public boolean initialized() {
        return Objects.nonNull(getProductMap());
    }
}
