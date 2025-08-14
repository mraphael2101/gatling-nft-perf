package acetoys.simulation;

import acetoys.pageobjects.*;
import io.gatling.javaapi.core.ChainBuilder;

import java.time.Duration;

import static acetoys.session.UserSession.initSession;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.group;

public class UserJourney {

    private static final Duration LOW_PAUSE = Duration.ofMillis(1000);
    private static final Duration HIGH_PAUSE = Duration.ofMillis(3000);

    public static ChainBuilder browseStore =
            exec(initSession)
                    // Optional: group static pages for clarity
                    .group("Static pages").on(
                            exec(StaticPages.homePage)
                                    .pause(HIGH_PAUSE)
                                    .exec(StaticPages.getInTouch)
                                    .pause(LOW_PAUSE, HIGH_PAUSE)
                                    .exec(StaticPages.ourStory)
                                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    )
                    // Three browsing passes, each in its own group
                    .repeat(3, "pass").on(
                            group("Browse pass #{pass}").on(
                                    exec(ProductCategoriesPage.browseProductListByCategory)
                                            .pause(LOW_PAUSE, HIGH_PAUSE)
                                            .exec(ProductCategoriesPage.browseProductListByCategoryAcrossPages)
                                            .pause(LOW_PAUSE, HIGH_PAUSE)
                            )
                    );


    public static ChainBuilder abandonBasket =
            exec(StaticPages.homePage)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(ProductCategoriesPage.browseProductListByCategory)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(ProductDetailsPage.loadProductDetailsPage_ColouredBuildingBlocks)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(ProductDetailsPage.addCurrentProductToCart);

    public static ChainBuilder completePurchase =
            exec(StaticPages.homePage)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(ProductCategoriesPage.browseProductListByCategory)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(ProductDetailsPage.loadProductDetailsPage_ColouredBuildingBlocks)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(ProductDetailsPage.addCurrentProductToCart)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(ShoppingCartPage.increaseQuantityInCart)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(ShoppingCartPage.checkoutCart)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Customer.logout);
}
