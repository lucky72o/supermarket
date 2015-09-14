package uk.co.supermarket.service.impl;

import org.junit.Before;
import org.junit.Test;
import uk.co.supermarket.model.Cart;
import uk.co.supermarket.model.CartEntry;
import uk.co.supermarket.model.Product;
import uk.co.supermarket.model.PromotionTypeEnum;
import uk.co.supermarket.promotions.Promotion;
import uk.co.supermarket.service.CalculationService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class CalculationServiceImplTest {

    private List<CartEntry> cartEntries = new ArrayList<>();

    CalculationService calculationService;

    Set<Promotion> promotions = new HashSet<>();

    private Product product1;
    private Product product2;

    @Before
    public void setUp() {

        calculationService = new CalculationServiceImpl();

        double basePrice1 = 10;
        product1 = new Product("DM401", basePrice1);
        double basePrice2 = 12;
        product2 = new Product("DT5201", basePrice2);

        double specialPrice1Product1 = 9;
        product1.getPriceMap().put(PromotionTypeEnum.BUY_TWO_FOR_SPECIAL_PRICE, specialPrice1Product1);
        double specialPrice1Product2 = 11;
        product2.getPriceMap().put(PromotionTypeEnum.BUY_TWO_FOR_SPECIAL_PRICE, specialPrice1Product2);

        double specialPrice2Product1 = 8;
        product1.getPriceMap().put(PromotionTypeEnum.BUY_THREE_IN_SET_WITH_SPECIAL_PRICE, specialPrice2Product1);
        double specialPrice2Product2 = 10;
        product2.getPriceMap().put(PromotionTypeEnum.BUY_THREE_IN_SET_WITH_SPECIAL_PRICE, specialPrice2Product2);
    }

    @Test
    public void whenPromotionsAreEmpty_thanRegularPriceForAllProducts() {
        Cart cart = new Cart();

        cartEntries.add(new CartEntry(product1, 3));
        cartEntries.add(new CartEntry(product2, 4));

        cart.setTotalPrice(0.0);
        cart.getEntries().addAll(cartEntries);

        calculationService.calculateTheCart(cart, promotions);

        double expectedPrice = 3 * product1.getBasePrice() + 4 * product2.getBasePrice();

        assertEquals(expectedPrice, cart.getTotalPrice(), 0.0);
    }

}