package com.wml.productsearch.productcontroller;

import com.wml.productsearch.product.Product;
import com.wml.productsearch.productservice.ProductService;
import com.wml.productsearch.search.SearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ProductController handles http requests for Products
 */
@RestController
@RequestMapping(path = "api/product")
public class ProductController {
    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * searches for and filters Product using the provided criteria
     * @param search
     * @param minPrice
     * @param maxPrice
     * @param minReviewRating
     * @param maxReviewRating
     * @param minReviewCount
     * @param maxReviewCount
     * @param inStock
     * @return
     */
    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public List<Product> search(
            @RequestParam(value = "search", required = false) final String search,
            @RequestParam(value = "minPrice", required = false) final Double minPrice,
            @RequestParam(value = "maxPrice", required = false) final Double maxPrice,
            @RequestParam(value = "minReviewRating", required = false) final Double minReviewRating,
            @RequestParam(value = "maxReviewRating", required = false) final Double maxReviewRating,
            @RequestParam(value = "minReviewCount", required = false) final Integer minReviewCount,
            @RequestParam(value = "maxReviewCount", required = false) final Integer maxReviewCount,
            @RequestParam(value = "inStock", required = false) final Boolean inStock
    ) {
        return productService.search(new SearchCriteria(
                search,
                minPrice,
                maxPrice,
                minReviewRating,
                maxReviewRating,
                minReviewCount,
                maxReviewCount,
                inStock
        ));
    }

    /**
     * initialized indicates that this instances is ready to service requests
     * @return
     */
    @RequestMapping(path = "/initialized", method = RequestMethod.GET)
    public boolean initialized() {
        return productService.initialized();
    }
}
