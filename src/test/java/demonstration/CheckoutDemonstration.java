package demonstration;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.supermarket.model.Cart;
import uk.co.supermarket.model.CartEntry;
import uk.co.supermarket.model.Product;
import uk.co.supermarket.model.PromotionTypeEnum;
import uk.co.supermarket.promotions.Promotion;
import uk.co.supermarket.promotions.impl.BuyNItemsXGetKItemsYFreePromotion;
import uk.co.supermarket.promotions.impl.BuyThreeInSetWithSpecialPricePromotion;
import uk.co.supermarket.promotions.impl.BuyThreePayForTwoPromotion;
import uk.co.supermarket.promotions.impl.BuyTwoForSpecialPricePromotion;
import uk.co.supermarket.service.CalculationService;
import uk.co.supermarket.service.impl.CalculationServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CheckoutDemonstration {

    private List<CartEntry> cartEntries = new ArrayList<>();

    CalculationService calculationService;

    Set<Promotion> promotions = new HashSet<>();

    private Product product1;
    private Product product2;
    private Product product3;
    private Product product4;
    private Product product5;

    @Before
    public void setUp() {

        calculationService = new CalculationServiceImpl();

        Promotion promotion1 = new BuyThreePayForTwoPromotion();
        Promotion promotion2 = new BuyTwoForSpecialPricePromotion();
        Promotion promotion3 = new BuyNItemsXGetKItemsYFreePromotion(3, "KT501", 1);     // For three products in this promotion you can get one "KT501" fo free
        Promotion promotion4 = new BuyThreeInSetWithSpecialPricePromotion();

        promotions.add(promotion1);
        promotions.add(promotion2);
        promotions.add(promotion3);
        promotions.add(promotion4);

        double basePrice1 = 10;
        product1 = new Product("DM401", basePrice1);
        double basePrice2 = 12;
        product2 = new Product("DT5201", basePrice2);
        double basePrice3 = 14;
        product3 = new Product("CS5981", basePrice3);
        double basePrice4 = 16;
        product4 = new Product("ML521", basePrice4);
        double basePrice5 = 20;
        product5 = new Product("KT501", basePrice5);

        double specialPrice1Product1 = 9;
        product1.getPriceMap().put(PromotionTypeEnum.BUY_TWO_FOR_SPECIAL_PRICE, specialPrice1Product1);
        double specialPrice1Product2 = 11;
        product2.getPriceMap().put(PromotionTypeEnum.BUY_TWO_FOR_SPECIAL_PRICE, specialPrice1Product2);
        double specialPrice1Product3 = 13;
        product3.getPriceMap().put(PromotionTypeEnum.BUY_TWO_FOR_SPECIAL_PRICE, specialPrice1Product3);
        double specialPrice1Product4 = 11;
        product4.getPriceMap().put(PromotionTypeEnum.BUY_TWO_FOR_SPECIAL_PRICE, specialPrice1Product4);
        double specialPrice1Product5 = 15;
        product5.getPriceMap().put(PromotionTypeEnum.BUY_TWO_FOR_SPECIAL_PRICE, specialPrice1Product5);

        double specialPrice2Product1 = 8;
        product1.getPriceMap().put(PromotionTypeEnum.BUY_THREE_IN_SET_WITH_SPECIAL_PRICE, specialPrice2Product1);
        double specialPrice2Product2 = 10;
        product2.getPriceMap().put(PromotionTypeEnum.BUY_THREE_IN_SET_WITH_SPECIAL_PRICE, specialPrice2Product2);
        double specialPrice2Product3 = 12;
        product3.getPriceMap().put(PromotionTypeEnum.BUY_THREE_IN_SET_WITH_SPECIAL_PRICE, specialPrice2Product3);
        double specialPrice2Product4 = 12;
        product4.getPriceMap().put(PromotionTypeEnum.BUY_THREE_IN_SET_WITH_SPECIAL_PRICE, specialPrice2Product4);
        double specialPrice2Product5 = 18;
        product5.getPriceMap().put(PromotionTypeEnum.BUY_THREE_IN_SET_WITH_SPECIAL_PRICE, specialPrice2Product5);
    }


    // BuyThreePayForTwoPromotion 3 products (qty: 2, 3, 4)  2 normal products (qty: 2, 3)
    @Test
    public void testCheckout1() {

        product1.setPromotion(PromotionTypeEnum.BUY_THREE_PAY_FOR_TWO);
        product2.setPromotion(PromotionTypeEnum.BUY_THREE_PAY_FOR_TWO);
        product3.setPromotion(PromotionTypeEnum.BUY_THREE_PAY_FOR_TWO);

        cartEntries.add(new CartEntry(product1, 2));
        cartEntries.add(new CartEntry(product2, 3));
        cartEntries.add(new CartEntry(product3, 4));
        cartEntries.add(new CartEntry(product4, 2));
        cartEntries.add(new CartEntry(product5, 3));

        Cart cart = new Cart();
        cart.setTotalPrice(0.0);
        cart.getEntries().addAll(cartEntries);

        calculationService.calculateTheCart(cart, promotions);

        double price1 = 2 * product1.getBasePrice();
        double price2 = 2 * product2.getBasePrice();
        double price3 = 3 * product3.getBasePrice();
        double price4 = 2 * product4.getBasePrice();
        double price5 = 3 * product5.getBasePrice();

        double expectedPrice = price1 + price2 + price3 + price4 + price5;

        Assert.assertEquals(expectedPrice, cart.getTotalPrice(), 0.0);

        printReceipt(cart);
    }

    // BuyTwoForSpecialPricePromotion 2 products (qty: 2, 3)  2 normal products (qty: 4, 2)
    @Test
    public void testCheckout2() {

        product1.setPromotion(PromotionTypeEnum.BUY_TWO_FOR_SPECIAL_PRICE);
        product2.setPromotion(PromotionTypeEnum.BUY_TWO_FOR_SPECIAL_PRICE);

        cartEntries.add(new CartEntry(product1, 2));
        cartEntries.add(new CartEntry(product2, 3));
        cartEntries.add(new CartEntry(product3, 4));
        cartEntries.add(new CartEntry(product4, 2));

        Cart cart = new Cart();
        cart.setTotalPrice(0.0);
        cart.getEntries().addAll(cartEntries);

        calculationService.calculateTheCart(cart, promotions);

        double price1 = 2 * product1.getPriceMap().get(PromotionTypeEnum.BUY_TWO_FOR_SPECIAL_PRICE);
        double price2 = 2 * product2.getPriceMap().get(PromotionTypeEnum.BUY_TWO_FOR_SPECIAL_PRICE) + product2.getBasePrice() * 1;
        double price3 = 4 * product3.getBasePrice();
        double price4 = 2 * product4.getBasePrice();

        double expectedPrice = price1 + price2 + price3 + price4;

        Assert.assertEquals(expectedPrice, cart.getTotalPrice(), 0.0);

        printReceipt(cart);
    }

    // BuyTwoForSpecialPricePromotion 2 products (qty: 2, 3), BuyThreePayForTwoPromotion 1 product (qty: 3), 2 normal products (qty: 4, 2)
    @Test
    public void testCheckout3() {

        product1.setPromotion(PromotionTypeEnum.BUY_TWO_FOR_SPECIAL_PRICE);
        product2.setPromotion(PromotionTypeEnum.BUY_TWO_FOR_SPECIAL_PRICE);
        product3.setPromotion(PromotionTypeEnum.BUY_THREE_PAY_FOR_TWO);

        cartEntries.add(new CartEntry(product1, 2));
        cartEntries.add(new CartEntry(product2, 3));
        cartEntries.add(new CartEntry(product3, 3));
        cartEntries.add(new CartEntry(product4, 4));
        cartEntries.add(new CartEntry(product5, 2));

        Cart cart = new Cart();
        cart.setTotalPrice(0.0);
        cart.getEntries().addAll(cartEntries);

        calculationService.calculateTheCart(cart, promotions);

        double price1 = 2 * product1.getPriceMap().get(PromotionTypeEnum.BUY_TWO_FOR_SPECIAL_PRICE);
        double price2 = 2 * product2.getPriceMap().get(PromotionTypeEnum.BUY_TWO_FOR_SPECIAL_PRICE) + product2.getBasePrice() * 1;
        double price3 = 2 * product3.getBasePrice();
        double price4 = 4 * product4.getBasePrice();
        double price5 = 2 * product5.getBasePrice();

        double expectedPrice = price1 + price2 + price3 + price4 + price5;

        Assert.assertEquals(expectedPrice, cart.getTotalPrice(), 0.0);

        printReceipt(cart);
    }

    // BuyNItemsXGetKItemsYFreePromotion N = 3 K = 1 1 product (qty: 4), BuyThreePayForTwoPromotion 2 products (qty: 3, 2), 2 normal products (qty: 4, 2)
    @Test
    public void testCheckout4() {

        product1.setPromotion(PromotionTypeEnum.BUY_N_ITEMS_X_GET_K_ITEMS_Y_FREE);
        product2.setPromotion(PromotionTypeEnum.BUY_THREE_PAY_FOR_TWO);
        product3.setPromotion(PromotionTypeEnum.BUY_THREE_PAY_FOR_TWO);

        cartEntries.add(new CartEntry(product1, 4));
        cartEntries.add(new CartEntry(product2, 3));
        cartEntries.add(new CartEntry(product3, 2));
        cartEntries.add(new CartEntry(product4, 4));
        cartEntries.add(new CartEntry(product5, 2));               // potential free product

        Cart cart = new Cart();
        cart.setTotalPrice(0.0);
        cart.getEntries().addAll(cartEntries);

        calculationService.calculateTheCart(cart, promotions);

        double price1 = 4 * product1.getBasePrice();
        double price2 = 2 * product2.getBasePrice();
        double price3 = 2 * product3.getBasePrice();
        double price4 = 4 * product4.getBasePrice();
        double price5 = 1 * product5.getBasePrice();

        double expectedPrice = price1 + price2 + price3 + price4 + price5;

        Assert.assertEquals(expectedPrice, cart.getTotalPrice(), 0.0);

        printReceipt(cart);
    }

    // BuyNItemsXGetKItemsYFreePromotion N = 3 K = 1 1 product (qty: 4), BuyThreePayForTwoPromotion 1 product (qty: 6), BuyThreeInSetWithSpecialPricePromotion 2 products (qty: 4, 2), 1 normal products (qty: 1)
    @Test
    public void testCheckout5() {

        product1.setPromotion(PromotionTypeEnum.BUY_N_ITEMS_X_GET_K_ITEMS_Y_FREE);
        product2.setPromotion(PromotionTypeEnum.BUY_THREE_PAY_FOR_TWO);
        product3.setPromotion(PromotionTypeEnum.BUY_THREE_IN_SET_WITH_SPECIAL_PRICE);
        product4.setPromotion(PromotionTypeEnum.BUY_THREE_IN_SET_WITH_SPECIAL_PRICE);

        cartEntries.add(new CartEntry(product1, 4));
        cartEntries.add(new CartEntry(product2, 6));
        cartEntries.add(new CartEntry(product3, 4));
        cartEntries.add(new CartEntry(product4, 2));
        cartEntries.add(new CartEntry(product5, 1));               // potential free product

        Cart cart = new Cart();
        cart.setTotalPrice(0.0);
        cart.getEntries().addAll(cartEntries);

        calculationService.calculateTheCart(cart, promotions);

        double price1 = 4 * product1.getBasePrice();
        double price2 = 4 * product2.getBasePrice();
        double price3 = 4 * product3.getPriceMap().get(PromotionTypeEnum.BUY_THREE_IN_SET_WITH_SPECIAL_PRICE);
        double price4 = 2 * product4.getPriceMap().get(PromotionTypeEnum.BUY_THREE_IN_SET_WITH_SPECIAL_PRICE);
        double price5 = 0 * product5.getBasePrice();

        double expectedPrice = price1 + price2 + price3 + price4 + price5;

        Assert.assertEquals(expectedPrice, cart.getTotalPrice(), 0.0);

        printReceipt(cart);
    }

    private void printReceipt(Cart cart) {
        System.out.println("Receipt:");
        System.out.println();

        for (CartEntry cartEntry : cart.getEntries()) {
            Product product = cartEntry.getProduct();
            System.out.println(product.getName() + ": " + product.getBasePrice() + " x " + cartEntry.getQuantity());
        }

        System.out.println();
        System.out.println("Total price: " + cart.getTotalPrice());
    }
}
