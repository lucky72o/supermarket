package uk.co.supermarket.service.impl;

import uk.co.supermarket.model.Cart;
import uk.co.supermarket.model.CartEntry;
import uk.co.supermarket.promotions.Promotion;
import uk.co.supermarket.service.CalculationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CalculationServiceImpl implements CalculationService {

    @Override
    public void calculateTheCart(Cart cart, Set<Promotion> promotions) {
        List<CartEntry> entriesToProcess = new ArrayList<>(cart.getEntries());

        if (promotions != null) {
            for (Promotion promotion : promotions) {
                if (entriesToProcess.isEmpty()) {
                    break;
                }
                promotion.applyPromotion(cart, entriesToProcess);
            }
        }

        double priceForProductsWithoutPromotion = 0.0;
        for (CartEntry cartEntry : entriesToProcess) {
            priceForProductsWithoutPromotion += cartEntry.getProduct().getBasePrice() * cartEntry.getQuantity();
        }

        cart.setTotalPrice(cart.getTotalPrice() + priceForProductsWithoutPromotion);
    }

}
