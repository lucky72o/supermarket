package uk.co.supermarket.promotions.impl;

import org.junit.Before;
import org.junit.Test;
import uk.co.supermarket.model.CartEntry;
import uk.co.supermarket.model.Product;
import uk.co.supermarket.model.PromotionTypeEnum;
import uk.co.supermarket.promotions.Promotion;
import uk.co.supermarket.promotions.impl.BuyThreeInSetWithSpecialPricePromotion;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BuyThreeInSetWithSpecialPricePromotionTest {
    public static final PromotionTypeEnum promotionType = PromotionTypeEnum.BUY_THREE_IN_SET_WITH_SPECIAL_PRICE;

    public static final int MINIMUM_NUMBER_OF_PRODUCTS_FOR_PROMOTION = 3;

    public static final double basePrice1 = 10;
    public static final double basePrice2 = 20;

    public static final double specialPrice1 = 8;
    public static final double specialPrice2 = 16;

    private Promotion promotion;
    private Product product1;
    private Product product2;
    private List<CartEntry> cartEntries;

    @Before
    public void setUp() {
        promotion = new BuyThreeInSetWithSpecialPricePromotion();
        product1 = new Product("DM401", basePrice1);
        product2 = new Product("DT5201", basePrice2);

        product1.getPriceMap().put(promotionType, specialPrice1);
        product2.getPriceMap().put(promotionType, specialPrice2);

        product1.setPromotion(PromotionTypeEnum.BUY_THREE_IN_SET_WITH_SPECIAL_PRICE);
        product2.setPromotion(PromotionTypeEnum.BUY_THREE_IN_SET_WITH_SPECIAL_PRICE);

        cartEntries = new ArrayList<>();
    }

    @Test
    public void whenLessQuantityThenThree_thanNormalPrice() {
        int quantity1 = 1;
        int quantity2 = 1;

        CartEntry cartEntry1 = new CartEntry(product1, quantity1);
        CartEntry cartEntry2 = new CartEntry(product2, quantity2);
        cartEntries.add(cartEntry1);
        cartEntries.add(cartEntry2);

        double price = promotion.calculatePriceWithPromotion(cartEntries);

        assertEquals(quantity1 * basePrice1 + quantity2 * basePrice2, price, 0.0);
    }


    @Test
    public void whenTreeProduct_thanSpecialPrice() {
        int quantity1 = 1;
        int quantity2 = 2;

        CartEntry cartEntry1 = new CartEntry(product1, quantity1);
        CartEntry cartEntry2 = new CartEntry(product2, quantity2);
        cartEntries.add(cartEntry1);
        cartEntries.add(cartEntry2);

        double expectedPrice = quantity1 * specialPrice1 + quantity2 * specialPrice2;

        double price = promotion.calculatePriceWithPromotion(cartEntries);

        assertEquals(expectedPrice, price, 0.0);
    }


    @Test
    public void whenFiveProduct_thanThreeProductsWithSpecialPrice_andTwoProductsWithNormalPrice() {
        int quantity1 = 2;
        int quantity2 = 3;

        CartEntry cartEntry1 = new CartEntry(product1, quantity1);
        CartEntry cartEntry2 = new CartEntry(product2, quantity2);
        cartEntries.add(cartEntry1);
        cartEntries.add(cartEntry2);

        double price = promotion.calculatePriceWithPromotion(cartEntries);

        int numberOfProductWithPromotionSecondCartEntry = MINIMUM_NUMBER_OF_PRODUCTS_FOR_PROMOTION - quantity1;

        double expectedPrice = quantity1 * specialPrice1 + numberOfProductWithPromotionSecondCartEntry * specialPrice2 + (quantity2 - numberOfProductWithPromotionSecondCartEntry) * basePrice2;

        assertEquals(expectedPrice, price, 0.0);
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