package uk.co.supermarket.promotions.impl;

import uk.co.supermarket.model.Cart;
import uk.co.supermarket.model.CartEntry;
import uk.co.supermarket.model.Product;
import uk.co.supermarket.model.PromotionTypeEnum;
import uk.co.supermarket.promotions.AbstractPromotion;

import java.util.ArrayList;
import java.util.List;

public class BuyNItemsXGetKItemsYFreePromotion extends AbstractPromotion {

    public static final PromotionTypeEnum promotionType = PromotionTypeEnum.BUY_N_ITEMS_X_GET_K_ITEMS_Y_FREE;

    private int minimumNumberOfProductsForPromotion;
    private String freeProductForPromotion;
    private int numberOfFreeProducts;

    public BuyNItemsXGetKItemsYFreePromotion(int minimumNumberOfProductsForPromotion, String freeProductForPromotion, int numberOfFreeProducts) {
        this.minimumNumberOfProductsForPromotion = minimumNumberOfProductsForPromotion;
        this.freeProductForPromotion = freeProductForPromotion;
        this.numberOfFreeProducts = numberOfFreeProducts;
    }

    @Override
    public double calculatePriceWithPromotion(List<CartEntry> cartEntries) {
        double price = 0;

        if (cartEntries == null || cartEntries.isEmpty()) {
            return price;
        }

        int totalNumberOfFreeProducts = 0;

        //We assume that we can't more then one CartEntry with the same product
        CartEntry potentialFreeProduct = null;

        for (CartEntry cartEntry : cartEntries) {
            Product product = cartEntry.getProduct();

            if (product.getName().equals(freeProductForPromotion)) {
                potentialFreeProduct = cartEntry;
                continue;
            }

            int quantity = cartEntry.getQuantity();

            Double basePrice = product.getBasePrice();
            price += basePrice * quantity;

            if (quantity >= minimumNumberOfProductsForPromotion) {
                int numberOfProductGroupsWithPromotion = quantity / minimumNumberOfProductsForPromotion;
                totalNumberOfFreeProducts += numberOfProductGroupsWithPromotion * numberOfFreeProducts;
            }
        }

        if (potentialFreeProduct != null) {
            double quantity = potentialFreeProduct.getQuantity();
            if (totalNumberOfFreeProducts < quantity) {
                price += (quantity - totalNumberOfFreeProducts) * potentialFreeProduct.getProduct().getBasePrice();
            }
        }

        return price;
    }

    // For this promotion we want process products applicable to this promotion plus products, which potential can be free
    @Override
    public void applyPromotion(Cart cart, List<CartEntry> entriesToProcess) {

        if (cart == null || entriesToProcess == null) {
            // todo: log error
            return;
        }

        double price = cart.getTotalPrice();

        List<CartEntry> entriesForPromotion = new ArrayList<>();

        for (CartEntry entry : entriesToProcess) {
            Product product = entry.getProduct();
            if (product.getPromotion() == getPromotionType() || product.getName().equals(freeProductForPromotion)) {
                entriesForPromotion.add(entry);
            }
        }

        for (CartEntry entryToRemove : entriesForPromotion) {
            entriesToProcess.remove(entryToRemove);
        }

        cart.setTotalPrice(price + calculatePriceWithPromotion(entriesForPromotion));
    }

    @Override
    public PromotionTypeEnum getPromotionType() {
        return promotionType;
    }
}
