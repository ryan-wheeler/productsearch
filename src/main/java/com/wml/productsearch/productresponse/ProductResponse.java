package com.wml.productsearch.productresponse;

import com.wml.productsearch.product.Product;

import java.util.List;

public class ProductResponse {
    private List<Product> products;
    private long totalProducts;
    private long pageNumber;
    private int pageSize;
    private int statusCode;

    /**
     * getter for products
     *
     * @return java.util.List<com.wml.productsearch.product.Product> products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * setter for products
     *
     * @param products
     **/
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * getter for totalProducts
     *
     * @return long totalProducts
     */
    public long getTotalProducts() {
        return totalProducts;
    }

    /**
     * setter for totalProducts
     *
     * @param totalProducts
     **/
    public void setTotalProducts(long totalProducts) {
        this.totalProducts = totalProducts;
    }

    /**
     * getter for pageNumber
     *
     * @return long pageNumber
     */
    public long getPageNumber() {
        return pageNumber;
    }

    /**
     * setter for pageNumber
     *
     * @param pageNumber
     **/
    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * getter for pageSize
     *
     * @return int pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * setter for pageSize
     *
     * @param pageSize
     **/
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * getter for statusCode
     *
     * @return int statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * setter for statusCode
     *
     * @param statusCode
     **/
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
