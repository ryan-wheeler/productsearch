package com.wml.productsearch.search;

/**
 * SearchCriteria represents the attributes that products can be searched and filtered by
 */
public class SearchCriteria {
    private String search;
    private Double minPrice;
    private Double maxPrice;
    private Double minReviewRating;
    private Double maxReviewRating;
    private Integer minReviewCount;
    private Integer maxReviewCount;
    private Boolean inStock;

    public SearchCriteria() {
    }

    public SearchCriteria(
            final String search,
            final Double minPrice,
            final Double maxPrice,
            final Double minReviewRating,
            final Double maxReviewRating,
            final Integer minReviewCount,
            final Integer maxReviewCount,
            final Boolean inStock
    ) {
        this.search = search;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minReviewRating = minReviewRating;
        this.maxReviewRating = maxReviewRating;
        this.minReviewCount = minReviewCount;
        this.maxReviewCount = maxReviewCount;
        this.inStock = inStock;
    }

    /**
     * getter for search
     *
     * @return java.lang.String search
     */
    public String getSearch() {
        return search;
    }

    /**
     * setter for search
     *
     * @param search
     **/
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * getter for minPrice
     *
     * @return java.lang.Double minPrice
     */
    public Double getMinPrice() {
        return minPrice;
    }

    /**
     * setter for minPrice
     *
     * @param minPrice
     **/
    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    /**
     * getter for maxPrice
     *
     * @return java.lang.Double maxPrice
     */
    public Double getMaxPrice() {
        return maxPrice;
    }

    /**
     * setter for maxPrice
     *
     * @param maxPrice
     **/
    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    /**
     * getter for minReviewRating
     *
     * @return java.lang.Double minReviewRating
     */
    public Double getMinReviewRating() {
        return minReviewRating;
    }

    /**
     * setter for minReviewRating
     *
     * @param minReviewRating
     **/
    public void setMinReviewRating(Double minReviewRating) {
        this.minReviewRating = minReviewRating;
    }

    /**
     * getter for maxReviewRating
     *
     * @return java.lang.Double maxReviewRating
     */
    public Double getMaxReviewRating() {
        return maxReviewRating;
    }

    /**
     * setter for maxReviewRating
     *
     * @param maxReviewRating
     **/
    public void setMaxReviewRating(Double maxReviewRating) {
        this.maxReviewRating = maxReviewRating;
    }

    /**
     * getter for minReviewCount
     *
     * @return java.lang.Integer minReviewCount
     */
    public Integer getMinReviewCount() {
        return minReviewCount;
    }

    /**
     * setter for minReviewCount
     *
     * @param minReviewCount
     **/
    public void setMinReviewCount(Integer minReviewCount) {
        this.minReviewCount = minReviewCount;
    }

    /**
     * getter for maxReviewCount
     *
     * @return java.lang.Integer maxReviewCount
     */
    public Integer getMaxReviewCount() {
        return maxReviewCount;
    }

    /**
     * setter for maxReviewCount
     *
     * @param maxReviewCount
     **/
    public void setMaxReviewCount(Integer maxReviewCount) {
        this.maxReviewCount = maxReviewCount;
    }

    /**
     * getter for inStock
     *
     * @return java.lang.Boolean inStock
     */
    public Boolean getInStock() {
        return inStock;
    }

    /**
     * setter for inStock
     *
     * @param inStock
     **/
    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }
}
