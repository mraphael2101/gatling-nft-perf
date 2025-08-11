package computerdatabase;

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

    private final ScenarioBuilder scn = scenario("AceToysSimulation")
            .exec(
                    http("AceToys Home")
                            .get("/")
                             // Save CSRF token value in a var for later use
                            .check(css("#_csrf", "content").saveAs("csrfToken"))
            )
            .pause(1)
            .exec(
                    http("Category All")
                            .get("/category/all/")
            )
            .pause(1)
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
            .pause(1)
            .exec(
                    http("View Cart")
                            .get("/cart/view")
            )
            .pause(6)
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
            .pause(4)
            .exec(
                    http("Checkout")
                            .get("/cart/checkout")
            )
            .pause(3)
            .exec(
                    http("Logout")
                            .post("/logout")
                            .formParam("_csrf", "#{csrfTokenLoggedIn}")
            );

    {
        setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }
}
