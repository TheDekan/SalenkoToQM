package com.salenko.model;

public class Deal {

    private Long id;

    private Product product;

    private Double productCount;

    public Deal() {}

    public Deal(Product product, Double productCount) {
        this.product = product;
        this.productCount = productCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getProductCount() {
        return productCount;
    }

    public void setProductCount(Double productCount) {
        this.productCount = Math.ceil(productCount * 1000) / 1000;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Deal tb = (Deal) o;

        return id.equals(tb.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
