package uk.co.supermarket.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Product {

    public Product(String name, Double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
    }

    private String name;
    private Double basePrice;
    private PromotionTypeEnum promotion;
    private Map<PromotionTypeEnum, Double> priceMap = new HashMap<PromotionTypeEnum, Double>();

    public Map<PromotionTypeEnum, Double> getPriceMap() {
        return priceMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public PromotionTypeEnum getPromotion() {
        return promotion;
    }

    public void setPromotion(PromotionTypeEnum promotion) {
        this.promotion = promotion;
    }
}
