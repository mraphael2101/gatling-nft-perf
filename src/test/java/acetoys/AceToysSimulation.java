package acetoys;

import acetoys.pageobjects.*;
import acetoys.session.UserSession;
import acetoys.simulation.UserJourney;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class AceToysSimulation extends Simulation {

    private final HttpProtocolBuilder httpProtocol =
            http
                    .baseUrl("https://acetoys.uk")
                    .inferHtmlResources(
                            AllowList(),
                            DenyList(
                                    ".*\\.(js|css|gif|jpeg|jpg|ico|woff|woff2|t|o|ttf|png)",
                                    ".*detectportal\\.firefox\\.com.*")
                    )
                    .acceptEncodingHeader("gzip, deflate")
                    .silentResources(); // reduce report noise from static assets

//    private final ScenarioBuilder scn = scenario("AceToysSimulation")
//            .exec(UserSession.initSession)
//            // Feed credentials so ${username} and ${password} exist in the session
//            .feed(csv("data/user_credentials.csv").circular())
//            .exec(StaticPages.homePage) // References to a static page object
//            .pause(2)
//            .exec(UserSession.increaseItemsInBasket)
//            .exec(ProductCategoriesPage.browseProductListByCategoryAcrossPages)
//            .pause(2)
//            .exec(ProductCategoriesPage.addItem1ToCart)
//            .pause(3)
//            .exec(ProductDetailsPage.loadProductDetailsPage_ColouredBuildingBlocks)
//            .pause(2)
//            .exec(ProductCategoriesPage.addItem5ToCart)
//            .pause(2)
//            .exec(ShoppingCartPage.viewCart)
//            .pause(2)
//            .exec(ShoppingCartPage.increaseQuantityInCart)
//            .pause(2)
//            .exec(
//                    // This is how we access the session API
//                    session -> {
//                        System.out.println("Session: " + session);
//                        System.out.println("csrfTokenLoggedIn value is: " + session.getString("csrfTokenLoggedIn"));
//                        return session;
//                    }
//            )
//            .pause(3)
//            .exec(ShoppingCartPage.checkoutCart)
//            .pause(2)
//            .exec(Customer.logout);

    private final ScenarioBuilder scn = scenario("AceToysSimulation")
            .exec(UserJourney.browseStore);



    {
        setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }
}
