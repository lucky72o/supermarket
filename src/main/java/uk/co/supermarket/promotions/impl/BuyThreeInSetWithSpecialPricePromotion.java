package uk.co.supermarket.promotions.impl;

import uk.co.supermarket.model.CartEntry;
import uk.co.supermarket.model.Product;
import uk.co.supermarket.model.PromotionTypeEnum;
import uk.co.supermarket.promotions.AbstractPromotion;

import java.util.List;

public class BuyThreeInSetWithSpecialPricePromotion extends AbstractPromotion {

    public static final PromotionTypeEnum promotionType = PromotionTypeEnum.BUY_THREE_IN_SET_WITH_SPECIAL_PRICE;

    public static final int MINIMUM_NUMBER_OF_PRODUCTS_FOR_PROMOTION = 3;

    public double calculatePriceWithPromotion(List<CartEntry> cartEntries) {
        double price = 0;

        if (cartEntries == null || cartEntries.isEmpty()) {
            return price;
        }

        int totalQuantity = 0;

        for (CartEntry cartEntry : cartEntries) {
            totalQuantity += cartEntry.getQuantity();
        }

        if (totalQuantity < MINIMUM_NUMBER_OF_PRODUCTS_FOR_PROMOTION) {
            for (CartEntry cartEntry : cartEntries) {
                price += cartEntry.getProduct().getBasePrice() * cartEntry.getQuantity();
            }
            return price;
        }

        int numberOfProductsWithPromotion = (totalQuantity / MINIMUM_NUMBER_OF_PRODUCTS_FOR_PROMOTION) * MINIMUM_NUMBER_OF_PRODUCTS_FOR_PROMOTION;

        int numberOfProducts = 0;

        for (CartEntry cartEntry : cartEntries) {
            int quantity = cartEntry.getQuantity();
            Product product = cartEntry.getProduct();
            Double promotionPrice = product.getPriceMap().get(promotionType);

            if (numberOfProductsWithPromotion >= numberOfProducts + quantity) {
                // Fow this task we assume that promotion price is always present in products price map
                price += promotionPrice * quantity;
                numberOfProducts += quantity;
            } else {
                int lastProductsWithPromotion = numberOfProductsWithPromotion - numberOfProducts;
                int productsWithoutPromotion = quantity - lastProductsWithPromotion;

                price += promotionPrice * lastProductsWithPromotion + product.getBasePrice() * productsWithoutPromotion;
            }
        }

        return price;
    }

    @Override
    public PromotionTypeEnum getPromotionType() {
        return promotionType;
    }
}
