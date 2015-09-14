package uk.co.supermarket.promotions.impl;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import uk.co.supermarket.model.CartEntry;
import uk.co.supermarket.model.Product;
import uk.co.supermarket.model.PromotionTypeEnum;
import uk.co.supermarket.promotions.Promotion;

import java.util.ArrayList;
import java.util.List;

public class BuyThreePayForTwoPromotionTest {

    public static final double basePrice = 10;

    private Promotion promotion;
    private Product product;
    private List<CartEntry> cartEntries;

    @Before
    public void setUp() {
        promotion = new BuyThreePayForTwoPromotion();
        product = new Product("DM401", basePrice);
        product.setPromotion(PromotionTypeEnum.BUY_THREE_PAY_FOR_TWO);
        cartEntries = new ArrayList<>();
    }

    @Test
    public void whenLessQuantityThenThree_thanNormalPrice() {
        int quantity = 1;
        CartEntry cartEntry = new CartEntry(product, quantity);
        cartEntries.add(cartEntry);

        double price = promotion.calculatePriceWithPromotion(cartEntries);

        assertEquals(quantity * basePrice, price, 0.0);
    }

    @Test
    public void whenTreeProduct_thanPriceForTwo() {
        int quantity = 3;
        CartEntry cartEntry = new CartEntry(product, quantity);
        cartEntries.add(cartEntry);

        double price = promotion.calculatePriceWithPromotion(cartEntries);

        assertEquals(2 * basePrice, price, 0.0);
    }

    @Test
    public void whenThreeProductsOneType_andTreeProductsAnotherType_thanPriceForTwoAndTwo() {
        Double basePrice2 = 20.0;
        Product product2 = new Product("DT5201", basePrice2);

        int quantity = 3;
        CartEntry cartEntry1 = new CartEntry(product, quantity);
        CartEntry cartEntry2 = new CartEntry(product2, quantity);
        cartEntries.add(cartEntry1);
        cartEntries.add(cartEntry2);

        double price = promotion.calculatePriceWithPromotion(cartEntries);

        assertEquals(2 * basePrice + 2 * basePrice2, price, 0.0);
    }

    @Test
    public void whenFiveProduct_thanPriceForFour() {
        int quantity = 5;
        CartEntry cartEntry = new CartEntry(product, quantity);
        cartEntries.add(cartEntry);

        double price = promotion.calculatePriceWithPromotion(cartEntries);

        assertEquals(4 * basePrice, price, 0.0);
    }

    @Test
    public void whenZeroQuantity_thanZeroPrice() {
        int quantity = 0;
        CartEntry cartEntry = new CartEntry(product, quantity);
        cartEntries.add(cartEntry);

        double price = promotion.calculatePriceWithPromotion(cartEntries);

        assertEquals(0.0, price, 0.0);
    }

    @Test
    public void whenCartEntriesIsEmpty_thanZeroPrice() {
        double price = promotion.calculatePriceWithPromotion(null);

        assertEquals(0.0, price, 0.0);
    }
}