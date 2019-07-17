package com.wml.productsearch.productservice;

import com.wml.productsearch.product.Product;
import com.wml.productsearch.productload.ProductLoader;
import com.wml.productsearch.search.SearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ProductService provides methods for searching and filtering Products
 */
public class ProductService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);
    private final ProductLoader productLoader;

    public ProductService(final ProductLoader productLoader) {
        this.productLoader = productLoader;
    }

    /**
     * indicates that the ProductService is ready to service requests
     * @return
     */
    public boolean initialized() {
        return productLoader.initialized();
    }

    /**
     * search finds matching products if a search string is provided and returns
     * all products otherwise, filtered by any provided criteria
     * @param searchCriteria
     * @return
     */
    public List<Product> search(final SearchCriteria searchCriteria) {
        List<Product> products = Collections.emptyList();
        if (Objects.nonNull(searchCriteria) &&
                Objects.nonNull(searchCriteria.getSearch()) &&
                !searchCriteria.getSearch().isEmpty()) {
                products = productLoader.searchProducts(searchCriteria.getSearch());
        } else {
            products = productLoader.getProducts();
        }
        return products.stream()
                .filter(product -> minPrice(searchCriteria, product))
                .filter(product -> maxPrice(searchCriteria, product))
                .filter(product -> minReviewRating(searchCriteria, product))
                .filter(product -> maxReviewRating(searchCriteria, product))
                .filter(product -> minReviewCount(searchCriteria, product))
                .filter(product -> maxReviewCount(searchCriteria, product))
                .filter(product -> inStock(searchCriteria, product))
                .collect(Collectors.toList());
    }

    private boolean minPrice(final SearchCriteria searchCriteria, final Product product) {
        return Objects.nonNull(searchCriteria) && (Objects.isNull(searchCriteria.getMinPrice()) ||
                product.getPriceNumeric() >= searchCriteria.getMinPrice());
    }

    private boolean maxPrice(final SearchCriteria searchCriteria, final Product product) {
        return Objects.isNull(searchCriteria.getMaxPrice()) ||
                product.getPriceNumeric() <= searchCriteria.getMaxPrice();
    }

    private boolean minReviewRating(final SearchCriteria searchCriteria, final Product product) {
        return Objects.isNull(searchCriteria.getMinReviewRating()) ||
                product.getReviewRating() <= searchCriteria.getMinReviewRating();
    }

    private boolean maxReviewRating(final SearchCriteria searchCriteria, final Product product) {
        return Objects.isNull(searchCriteria.getMaxReviewRating()) ||
                product.getReviewRating() <= searchCriteria.getMaxReviewRating();
    }

    private boolean minReviewCount(final SearchCriteria searchCriteria, final Product product) {
        return Objects.isNull(searchCriteria.getMinReviewCount()) ||
                product.getReviewCount() >= searchCriteria.getMinReviewCount();
    }

    private boolean maxReviewCount(final SearchCriteria searchCriteria, final Product product) {
        return Objects.isNull(searchCriteria.getMaxReviewCount()) ||
                product.getReviewCount() <= searchCriteria.getMaxReviewCount();
    }

    private boolean inStock(final SearchCriteria searchCriteria, final Product product) {
        return Objects.isNull(searchCriteria.getInStock()) ||
                product.isInStock() == searchCriteria.getInStock();
    }

}
