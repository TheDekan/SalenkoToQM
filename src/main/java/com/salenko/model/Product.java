package com.salenko.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "productName")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "calc_type")
    private Boolean calculationType = false;

    @Column(name = "action_valid")
    private Boolean actionValid = false;

    @Column(name = "action_count")
    private Integer actionCount;

    @Column(name = "action_price")
    private Double actionPrice;

    @Column(name = "gift")
    private Boolean gift = false;

    @Column(name = "gift_name")
    private String giftName;

    @Column(name = "gift_count")
    private Integer giftCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getCalculationType() {
        return calculationType;
    }

    public void setCalculationType(Boolean calculationType) {
        this.calculationType = calculationType;
    }

    public Boolean getActionValid() {
        return actionValid;
    }

    public void setActionValid(Boolean actionValid) {
        this.actionValid = actionValid;
    }

    public Integer getActionCount() {
        return actionCount;
    }

    public void setActionCount(Integer actionCount) {
        this.actionCount = actionCount;
    }

    public Double getActionPrice() {
        return actionPrice;
    }

    public void setActionPrice(Double actionPrice) {
        this.actionPrice = actionPrice;
    }

    public Boolean getGift() {
        return gift;
    }

    public void setGift(Boolean gift) {
        this.gift = gift;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public Integer getGiftCount() {
        return giftCount;
    }

    public void setGiftCount(Integer giftCount) {
        this.giftCount = giftCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Product tb = (Product) o;

        return id.equals(tb.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
