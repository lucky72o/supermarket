package uk.co.supermarket.promotions;

import uk.co.supermarket.model.Cart;
import uk.co.supermarket.model.CartEntry;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPromotion implements Promotion {

    @Override
    public void applyPromotion(Cart cart, List<CartEntry> entriesToProcess) {

        if (cart == null || entriesToProcess == null) {
            // todo: log error
            return;
        }

        double price = cart.getTotalPrice();

        List<CartEntry> entriesForPromotion = new ArrayList<>();

        for (CartEntry entry : entriesToProcess) {
            if (entry.getProduct().getPromotion() == getPromotionType()) {
                entriesForPromotion.add(entry);
            }
        }

        for (CartEntry entryToRemove : entriesForPromotion) {
            entriesToProcess.remove(entryToRemove);
        }

        cart.setTotalPrice(price + calculatePriceWithPromotion(entriesForPromotion));
    }
}
