package computerdatabase;

import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import io.gatling.javaapi.core.Simulation;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class AceToysSimulation extends Simulation {

    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://acetoys.uk")
//    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*", ".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*", ".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*", ".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*", ".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*", ".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*", ".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*", ".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*", ".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*"))
            .inferHtmlResources(AllowList(), DenyList(".*\\.(js|css|gif|jpeg|jpg|ico|woff|woff2|t|o|ttf|png)", ".*detectportal\\.firefox\\.com.*"))
            .acceptEncodingHeader("gzip, deflate");

    private String uri08 = "https://acetoys.uk";

    private ScenarioBuilder scn = scenario("AceToysSimulation")
            .exec(
                    http("AceToysSimulation_74:GET_https://acetoys.uk/")
                            .get(uri08 + "/")
            )
            .pause(1)
            .exec(
                    http("AceToysSimulation_84:GET_https://acetoys.uk/category/all/")
                            .get(uri08 + "/category/all/")
            )
            .pause(1)
            .exec(
                    http("AceToysSimulation_85:GET_https://acetoys.uk/cart/add/1")
                            .get(uri08 + "/cart/add/1")
            )
            .pause(3)
            .exec(
                    http("AceToysSimulation_86:GET_https://acetoys.uk/product/coloured-building-blocks")
                            .get(uri08 + "/product/coloured-building-blocks")
            )
            .pause(2)
            .exec(
                    http("AceToysSimulation_87:GET_https://acetoys.uk/cart/add/5")
                            .get(uri08 + "/cart/add/5")
            )
            .pause(1)
            .exec(
                    http("AceToysSimulation_88:GET_https://acetoys.uk/cart/view")
                            .get(uri08 + "/cart/view")
                            .resources()
            )
            .pause(6)
            .exec(
                    http("AceToysSimulation_90:POST_https://acetoys.uk/login")
                            .post(uri08 + "/login")
                            .formParam("_csrf", "e0ce2c20-ad0f-4778-a8d0-189a2d4e9b18")
                            .formParam("username", "user1")
                            .formParam("password", "pass")
                            .resources()
            )
            .pause(4)
            .exec(
                    http("AceToysSimulation_94:GET_https://acetoys.uk/cart/checkout")
                            .get(uri08 + "/cart/checkout")
            )
            .pause(3)
            .exec(
                    http("AceToysSimulation_95:POST_https://acetoys.uk/logout")
                            .post(uri08 + "/logout")
                            .formParam("_csrf", "0cc2a74f-6fce-47f4-be65-9fb9dea62df2")
            )
            .exec();

    {
        setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }
}
