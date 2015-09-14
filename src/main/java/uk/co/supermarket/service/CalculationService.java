package uk.co.supermarket.service;

import uk.co.supermarket.model.Cart;
import uk.co.supermarket.promotions.Promotion;

import java.util.Set;

public interface CalculationService {
    /**
     * This method applies promotions to the card and returns total price after
     */
    void calculateTheCart(Cart cart, Set<Promotion> promotions);
}
