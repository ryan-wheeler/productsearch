package com.wml.productsearch.productload.integration;

import com.wml.productsearch.product.Product;
import com.wml.productsearch.productservice.ProductService;
import com.wml.productsearch.search.SearchCriteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IntegrationTest {
    private static final ScheduledExecutorService scheduled = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    private ProductService productService;

    @Test
    public void testProductCount() {
        waitIfNotInitialized();
    }

    /**
     * while the product service is loading products, wait one second then try again
     */
    private void waitIfNotInitialized() {
        if (productService.initialized()) {
            List<Product> allProducts = productService.search(new SearchCriteria());
            assertEquals(224, allProducts.size());
        } else {
            scheduled.schedule(this::waitIfNotInitialized, 1, TimeUnit.SECONDS);
        }
    }

}
