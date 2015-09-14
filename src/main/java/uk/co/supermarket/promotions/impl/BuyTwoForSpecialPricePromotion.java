package uk.co.supermarket.promotions.impl;

import uk.co.supermarket.model.CartEntry;
import uk.co.supermarket.model.Product;
import uk.co.supermarket.model.PromotionTypeEnum;
import uk.co.supermarket.promotions.AbstractPromotion;

import java.util.List;

public class BuyTwoForSpecialPricePromotion extends AbstractPromotion {

    public static final PromotionTypeEnum promotionType = PromotionTypeEnum.BUY_TWO_FOR_SPECIAL_PRICE;
    public static final int MINIMUM_NUMBER_OF_PRODUCTS_FOR_PROMOTION = 2;


    @Override
    public double calculatePriceWithPromotion(List<CartEntry> cartEntries) {
        double price = 0;

        if (cartEntries == null || cartEntries.isEmpty()) {
            return price;
        }

        for (CartEntry cartEntry : cartEntries) {
            int quantity = cartEntry.getQuantity();

            Product product = cartEntry.getProduct();
            Double basePrice = product.getBasePrice();

            if (quantity < MINIMUM_NUMBER_OF_PRODUCTS_FOR_PROMOTION) {
                // Promotion is not applicable
                price += basePrice * quantity;
                continue;
            }

            // Fow this task we assume that promotion price is always present in products price map
            Double specialPrice = product.getPriceMap().get(promotionType);

            int numberOfProductsWithPromotion = quantity / MINIMUM_NUMBER_OF_PRODUCTS_FOR_PROMOTION * 2;
            int numberOfProductWithoutPromotions = quantity % MINIMUM_NUMBER_OF_PRODUCTS_FOR_PROMOTION;

            double priceForProductsWithPromotion = specialPrice * numberOfProductsWithPromotion;
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
