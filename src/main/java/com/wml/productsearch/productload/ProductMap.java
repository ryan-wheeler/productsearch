package com.wml.productsearch.productload;

import com.wml.productsearch.product.Product;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * ProductMap is a holder for Products with methods for grouping and retrieving by keyword
 */
public class ProductMap {
    private static final String WHITESPACE = "\\s+";
    private final Map<String, List<Product>> productsByKeyword = new HashMap<>();
    private final List<Product> products = new ArrayList<>();
    private final Predicate<String> keywordFilter;

    public ProductMap(Predicate<String> keywordFilter) {
        this.keywordFilter = keywordFilter;
    }

    /**
     * add adds a list of products to productsByKeyword
     * @param products
     */
    public void add(final List<Product> products) {
        if (Objects.nonNull(products) && !products.isEmpty()) {
            products.stream().forEach(this::add);
        }
    }

    /**
     * add adds a single product to productsByKeyword
     * @param product
     */
    public void add(final Product product) {
        if (Objects.nonNull(product)) {
            products.add(product);
            String productName = product.getProductName();
            if (Objects.nonNull(productName)) {
                Arrays.stream(productName.toLowerCase().split(WHITESPACE))
                        .filter(keywordFilter)
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .forEach(s -> {
                            List<Product> products = productsByKeyword.get(s);
                            if (Objects.isNull(products)) {
                                products = new ArrayList<>();
                                productsByKeyword.put(s, products);
                            }
                            products.add(product);
                        });

            }
        }
    }

    /**
     * findProducts returns a list of distinct products which match the words in a query
     * returning all items if the query is null or empty and
     * no items if no matches are found
     * @param query
     * @return
     */
    public List<Product> findProducts(final String query) {
        List<Product> matches = products;
        if (Objects.nonNull(query) && !query.isEmpty()) {
            matches = Arrays.stream(query.toLowerCase().split(WHITESPACE))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(s -> productsByKeyword.get(s))
                    .filter(Objects::nonNull)
                    .flatMap(Collection::stream)
                    .distinct()
                    .collect(Collectors.toList());
        }
        return matches;
    }

    /**
     * getSize returns the size of productsByKeyword
     * @return
     */
    public int getSize() {
        return productsByKeyword.size();
    }

    /**
     * getter for products
     *
     * @return java.util.List<com.wml.productsearch.product.Product> products
     */
    public List<Product> getProducts() {
        return products;
    }
}
