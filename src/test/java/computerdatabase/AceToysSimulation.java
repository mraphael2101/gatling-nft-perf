package computerdatabase;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

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

    private final ScenarioBuilder scn = scenario("AceToysSimulation")
            .exec(
                    http("AceToys Home")
                            .get("/")
                            // .check(status().is(200)) // 200 is checked by Gatling implicitly, no need to write it
                            .check(status().not(404), status().not(405))
                            // regex looks for an exact phrase & //d looks for a digit with 7 digits to follow
                            // .check(regex("a substring that exists in the markup \\d{7}"))
                            // Check the HTML markup to see if it contains the below title
                            .check(substring("<title>Ace Toys Online Shop</title>"))
                            // Check the HTML markup for an element with an id of _csrf, and extract the value from content
                            // Save CSRF token value in a var for later use
                            .check(css("#_csrf", "content").saveAs("csrfToken"))
            )
            .pause(2)
            .exec(
                    http("Category All")
                            .get("/category/all/")
            )
            .pause(2)
            .exec(
                    http("Add Item 1")
                            .get("/cart/add/1")
            )
            .pause(3)
            .exec(
                    http("Product: coloured-building-blocks")
                            .get("/product/coloured-building-blocks")
            )
            .pause(2)
            .exec(
                    http("Add Item 5")
                            .get("/cart/add/5")
            )
            .pause(2)
            .exec(
                    http("View Cart")
                            .get("/cart/view")
            )
            .pause(3)
            .exec(
                    http("Login")
                            .post("/login")
                            .formParam("_csrf", "#{csrfToken}")
                            .formParam("username", "user1")
                            .formParam("password", "pass")
                            // Re-save the CSRF token at this point as the value changes
                            .check(css("#_csrf", "content").saveAs("csrfTokenLoggedIn"))

            )
            .exec(
                    // This is how we access the session API
                    session -> {
                        System.out.println("Session: " + session);
                        System.out.println("csrfTokenLoggedIn value is: " + session.getString("csrfTokenLoggedIn"));
                        return session;
                    }
            )
            .pause(3)
            .exec(
                    http("Checkout")
                            .get("/cart/checkout")
            )
            .pause(2)
            .exec(
                    http("Logout")
                            .post("/logout")
                            .formParam("_csrf", "#{csrfTokenLoggedIn}")
            );

    {
        setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }
}
