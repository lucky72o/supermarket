package uk.co.supermarket.promotions.impl;

import uk.co.supermarket.model.CartEntry;
import uk.co.supermarket.model.PromotionTypeEnum;
import uk.co.supermarket.promotions.AbstractPromotion;

import java.util.ArrayList;
import java.util.List;

public class BuyThreePayForTwoPromotion extends AbstractPromotion {

    public static final PromotionTypeEnum promotionType = PromotionTypeEnum.BUY_THREE_PAY_FOR_TWO;
    public static final int MINIMUM_NUMBER_OF_PRODUCTS_FOR_PROMOTION = 3;

    @Override
    public double calculatePriceWithPromotion(List<CartEntry> cartEntries) {
        double price = 0;

        if (cartEntries == null || cartEntries.isEmpty()) {
            return price;
        }

        for (CartEntry cartEntry : cartEntries) {
            int quantity = cartEntry.getQuantity();

            Double basePrice = cartEntry.getProduct().getBasePrice();

            if (quantity < MINIMUM_NUMBER_OF_PRODUCTS_FOR_PROMOTION) {
                // Promotion is not applicable
                price += basePrice * quantity;
                continue;
            }

            int numberOfProductGroupsWithPromotion = quantity / MINIMUM_NUMBER_OF_PRODUCTS_FOR_PROMOTION;
            int numberOfProductWithoutPromotions = quantity % MINIMUM_NUMBER_OF_PRODUCTS_FOR_PROMOTION;

            double priceForProductsWithPromotion = basePrice * numberOfProductGroupsWithPromotion * 2;
            double priceForProductsWithoutPromotion = basePrice * numberOfProductWithoutPromotions;

            price += priceForProductsWithoutPromotion + priceForProductsWithPromotion;
        }

        return price;
    }

    @Override
    public PromotionTypeEnum getPromotionType() {
        return promotionType;
    }
}
