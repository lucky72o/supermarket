package uk.co.supermarket.model;

import java.util.LinkedList;
import java.util.List;

public class Cart {
    private Double totalPrice;
    private List<CartEntry> entries = new LinkedList<CartEntry>();

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartEntry> getEntries() {
        return entries;
    }
}
