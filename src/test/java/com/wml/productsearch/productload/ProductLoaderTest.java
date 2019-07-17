package com.wml.productsearch.productload;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wml.productsearch.product.Product;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static org.junit.Assert.*;

public class ProductLoaderTest {
    private static final String HOSTNAME = "mobile-tha-server.firebaseapp.com";
    private static final int PAGE_SIZE = 30;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Predicate<String> LENGTH_OVER_ONE = s -> Objects.nonNull(s) && s.length() > 1;
    private ProductLoader productLoader;

    @Before
    public void setUp() throws Exception {
        productLoader = new ProductLoader(new ArrayList<>(), HOSTNAME, PAGE_SIZE);
    }

    @Test
    public void testQuery() {
        ProductMap productMap = new ProductMap(LENGTH_OVER_ONE);
        Product product1 = new Product();
        product1.setProductName("Test A Product One");
        Product product2 = new Product();
        product2.setProductName("Test A Product Two");
        productMap.add(Arrays.asList(product1, product2));

        List<Product> allProducts = productMap.findProducts(null);
        assertEquals(2, allProducts.size());

        List<Product> matchesTest = productMap.findProducts("test");
        assertEquals(2, matchesTest.size());

        List<Product> matchesOne = productMap.findProducts("one");
        assertEquals(product1, matchesOne.get(0));


        List<Product> matchesTwo = productMap.findProducts("two");
        assertEquals(product2, matchesTwo.get(0));

        List<Product> matchesA = productMap.findProducts("a");
        assertEquals(0, matchesA.size());
    }

}