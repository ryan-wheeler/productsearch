package com.wml.productsearch.config;

import com.wml.productsearch.productcontroller.ProductController;
import com.wml.productsearch.productload.ProductLoader;
import com.wml.productsearch.productservice.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ProductLoaderConfig is configuration for the Product search stack
 */
@Configuration
@EnableScheduling
public class ProductLoaderConfig {
    private static final Logger log = LoggerFactory.getLogger(ProductLoaderConfig.class);
    private static final String PROPERTY_HOSTNAME = "hostname";
    private static final String PROPERTY_STOPWORDS_LOCATION = "stopwords";
    private static final String PROPERTY_PAGE_SIZE = "pagesize";
    private static final long DEFAULT_PAGE_SIZE = 30;
    private final ProductLoader productLoader;
    private final ProductService productService;
    private final ProductController productController;

    public ProductLoaderConfig(final Environment environment, final ResourceLoader resourceLoader) {
        final String hostname = environment.getRequiredProperty(PROPERTY_HOSTNAME);
        final String stopwordsLocation = environment.getProperty(PROPERTY_STOPWORDS_LOCATION);
        final long pageSize = environment.getProperty(PROPERTY_PAGE_SIZE, Long.class, DEFAULT_PAGE_SIZE);
        List<String> stopwords = Collections.emptyList();
        if (Objects.nonNull(stopwordsLocation) && !stopwordsLocation.isEmpty()) {
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(resourceLoader.getResource(stopwordsLocation).getInputStream()));
                stopwords = reader.lines()
                        .filter(Objects::nonNull)
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
            } catch (IOException e) {
                log.error("Error loading stopwords", e);
            }
        }
        productLoader = new ProductLoader(stopwords, hostname, pageSize);
        productService = new ProductService(productLoader);
        productController = new ProductController(productService);
    }

    /**
     * productLoader adds an instance of ProductLoader to the application context
     * @return
     */
    @Bean
    public ProductLoader productLoader() {
        return productLoader;
    }

    /**
     * productService adds an instance of ProductService to the application context
     * @return
     */
    @Bean
    public ProductService productService() {
        return productService;
    }

}
