package uk.co.supermarket.promotions.impl;

import org.junit.Before;
import org.junit.Test;
import uk.co.supermarket.model.CartEntry;
import uk.co.supermarket.model.Product;
import uk.co.supermarket.model.PromotionTypeEnum;
import uk.co.supermarket.promotions.Promotion;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BuyNItemsXGetKItemsYFreePromotionTest {

    public static final PromotionTypeEnum promotionType = PromotionTypeEnum.BUY_N_ITEMS_X_GET_K_ITEMS_Y_FREE;

    public static final int MINIMUM_NUMBER_OF_PRODUCTS_FOR_PROMOTION = 4;

    public static final double basePrice1 = 10;
    public static final double basePrice2 = 20;

    private Promotion promotion;
    private Product product1;
    private Product product2;
    private List<CartEntry> cartEntries;

    @Before
    public void setUp() {
        promotion = new BuyNItemsXGetKItemsYFreePromotion(MINIMUM_NUMBER_OF_PRODUCTS_FOR_PROMOTION, "DT5201", 1);
        product1 = new Product("DM401", basePrice1);
        product2 = new Product("DT5201", basePrice2);

        product1.setPromotion(promotionType);

        cartEntries = new ArrayList<>();
    }

    @Test
    public void whenLessQuantityThenThree_thanNoFreeProducts() {
        int quantity1 = 2;
        int quantity2 = 1;

        CartEntry cartEntry1 = new CartEntry(product1, quantity1);
        CartEntry cartEntry2 = new CartEntry(product2, quantity2);
        cartEntries.add(cartEntry1);
        cartEntries.add(cartEntry2);

        double price = promotion.calculatePriceWithPromotion(cartEntries);

        assertEquals(quantity1 * basePrice1 + quantity2 * basePrice2, price, 0.0);
    }


    @Test
    public void whenEnoughQuantity_thanFreeProduct() {
        int quantity1 = 4;
        int quantity2 = 1;

        CartEntry cartEntry1 = new CartEntry(product1, quantity1);
        CartEntry cartEntry2 = new CartEntry(product2, quantity2);
        cartEntries.add(cartEntry1);
        cartEntries.add(cartEntry2);

        double price = promotion.calculatePriceWithPromotion(cartEntries);

        assertEquals(quantity1 * basePrice1, price, 0.0);
    }

    @Test
    public void whenEnoughQuantityForOneFreeProduct_thanSecondProductIsNotFree() {
        int quantity1 = 4;
        int quantity2 = 2;

        CartEntry cartEntry1 = new CartEntry(product1, quantity1);
        CartEntry cartEntry2 = new CartEntry(product2, quantity2);
        cartEntries.add(cartEntry1);
        cartEntries.add(cartEntry2);

        double price = promotion.calculatePriceWithPromotion(cartEntries);

        assertEquals(quantity1 * basePrice1 + (quantity2 - 1) * basePrice2, price, 0.0);
    }

    @Test
    public void whenZeroQuantity_thanZeroPrice() {
        int quantity1 = 0;
        int quantity2 = 0;

        CartEntry cartEntry1 = new CartEntry(product1, quantity1);
        CartEntry cartEntry2 = new CartEntry(product2, quantity2);
        cartEntries.add(cartEntry1);
        cartEntries.add(cartEntry2);

        double price = promotion.calculatePriceWithPromotion(cartEntries);

        assertEquals(0.0, price, 0.0);
    }

    @Test
    public void whenCartEntriesIsEmpty_thanZeroPrice() {
        double price = promotion.calculatePriceWithPromotion(null);

        assertEquals(0.0, price, 0.0);
    }

}