package uk.co.supermarket.promotions;

import uk.co.supermarket.model.Cart;
import uk.co.supermarket.model.CartEntry;
import uk.co.supermarket.model.PromotionTypeEnum;

import java.util.List;

public interface Promotion {

    double calculatePriceWithPromotion(List<CartEntry> cartEntries);
    void applyPromotion(Cart cart, List<CartEntry> entriesToProcess);
    PromotionTypeEnum getPromotionType();
}
