package uk.co.supermarket.model;

public class CartEntry {

    public CartEntry(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    private Product product;
    private int quantity;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
