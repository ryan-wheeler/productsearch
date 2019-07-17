package com.wml.productsearch.product;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * DTO for Product
 */
public class Product {
    @JsonIgnore
    private static final String NOT_DIGIT_OR_PERIOD = "[^\\.\\d]";
    private String productId;
    private String productName;
    private String shortDescription;
    private String longDescription;
    private String price;
    @JsonIgnore
    private Double priceNumeric;
    private String productImage;
    private double reviewRating;
    private int reviewCount;
    private boolean inStock;

    /**
     * getter for productId
     *
     * @return java.lang.String productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * setter for productId
     *
     * @param productId
     **/
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * getter for productName
     *
     * @return java.lang.String productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * setter for productName
     *
     * @param productName
     **/
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * getter for shortDescription
     *
     * @return java.lang.String shortDescription
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * setter for shortDescription
     *
     * @param shortDescription
     **/
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * getter for longDescription
     *
     * @return java.lang.String longDescription
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * setter for longDescription
     *
     * @param longDescription
     **/
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    /**
     * getter for price
     *
     * @return java.lang.String price
     */
    public String getPrice() {
        return price;
    }

    /**
     * setter for price
     *
     * @param price
     **/
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * getter for priceNumeric
     *
     * @return double priceNumeric
     */
    public double getPriceNumeric() {
        if (Objects.isNull(priceNumeric) && Objects.nonNull(price)) {
            String priceCleaned = price.replaceAll(NOT_DIGIT_OR_PERIOD, "");
            priceNumeric = Double.parseDouble(priceCleaned);
        }
        return Objects.isNull(priceNumeric) ? 0 : priceNumeric.doubleValue();
    }

    /**
     * getter for productImage
     *
     * @return java.lang.String productImage
     */
    public String getProductImage() {
        return productImage;
    }

    /**
     * setter for productImage
     *
     * @param productImage
     **/
    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    /**
     * getter for reviewRating
     *
     * @return double reviewRating
     */
    public double getReviewRating() {
        return reviewRating;
    }

    /**
     * setter for reviewRating
     *
     * @param reviewRating
     **/
    public void setReviewRating(double reviewRating) {
        this.reviewRating = reviewRating;
    }

    /**
     * getter for reviewCount
     *
     * @return int reviewCount
     */
    public int getReviewCount() {
        return reviewCount;
    }

    /**
     * setter for reviewCount
     *
     * @param reviewCount
     **/
    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    /**
     * getter for inStock
     *
     * @return boolean inStock
     */
    public boolean isInStock() {
        return inStock;
    }

    /**
     * setter for inStock
     *
     * @param inStock
     **/
    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }
}
