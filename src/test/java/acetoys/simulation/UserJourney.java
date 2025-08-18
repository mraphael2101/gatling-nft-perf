package acetoys.simulation;

import acetoys.pageobjects.*;
import acetoys.session.UserSession;
import io.gatling.javaapi.core.ChainBuilder;

import java.time.Duration;

import static acetoys.session.UserSession.initSession;
import static io.gatling.javaapi.core.CoreDsl.*;

public class UserJourney {

    private static final Duration LOW_PAUSE = Duration.ofMillis(1000);
    private static final Duration HIGH_PAUSE = Duration.ofMillis(3000);

    public static ChainBuilder browseStore =
            exec(initSession)
                    /* Groups let you wrap several actions into a named “transaction” so Gatling reports
                       aggregate KPIs (min/avg/max/percentiles) for the whole block, not just per-request.

                    Optional: group static pages for clarity */
                    .group("Static pages").on(
                            exec(StaticPages.homePage)
                                    .pause(HIGH_PAUSE)
                                    .exec(StaticPages.getInTouch)
                                    .pause(LOW_PAUSE, HIGH_PAUSE)
                                    .exec(StaticPages.ourStory)
                                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    )
                    /* Using repeat with a counter and group("Browse pass #{pass}") labels each iteration as a
                       separate group in the report (e.g., pass 0/1/2), while keeping individual request names
                       stable for trend comparisons across runs.

                       Three browsing passes, each in its own group */
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

    public static ChainBuilder firstUserJourney =
            exec(UserSession.initSession)
                    // Feed credentials so ${username} and ${password} exist in the session
                    .feed(csv("data/user_credentials.csv").circular())
                    .exec(StaticPages.homePage) // References to a static page object
                    .pause(2)
                    .exec(UserSession.increaseItemsInBasket)
                    .exec(ProductCategoriesPage.browseProductListByCategoryAcrossPages)
                    .pause(2)
                    .exec(ProductCategoriesPage.addItem1ToCart)
                    .pause(3)
                    .exec(ProductDetailsPage.loadProductDetailsPage_ColouredBuildingBlocks)
                    .pause(2)
                    .exec(ProductCategoriesPage.addItem5ToCart)
                    .pause(2)
                    .exec(ShoppingCartPage.viewCart)
                    .pause(2)
                    .exec(ShoppingCartPage.increaseQuantityInCart)
                    .pause(2)
                    .exec(
                            // This is how we access the session API
                            session -> {
                                System.out.println("Session: " + session);
                                System.out.println("csrfTokenLoggedIn value is: " + session.getString("csrfTokenLoggedIn"));
                                return session;
                            }
                    )
                    .pause(3)
                    .exec(ShoppingCartPage.checkoutCart)
                    .pause(2)
                    .exec(Customer.logout);
}
