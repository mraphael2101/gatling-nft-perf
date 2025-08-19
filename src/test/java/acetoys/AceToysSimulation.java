package acetoys;

import acetoys.pageobjects.*;
import acetoys.session.UserSession;
import acetoys.simulation.TestPopulator;
import acetoys.simulation.TestScenario;
import acetoys.simulation.UserJourney;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class AceToysSimulation extends Simulation {

    private static final String TEST_TYPE = System.getProperty("TEST_TYPE", "INSTANT_USERS");
    private static final String DOMAIN = "https://acetoys.uk";

// Run mode 1 - Uncomment this block to run the simulation
//    private final HttpProtocolBuilder httpProtocol =
//            http
//                    .baseUrl(DOMAIN)
//                    .inferHtmlResources(
//                            AllowList(),
//                            DenyList(
//                                    ".*\\.(js|css|gif|jpeg|jpg|ico|woff|woff2|t|o|ttf|png)",
//                                    ".*detectportal\\.firefox\\.com.*")
//                    )
//                    .acceptEncodingHeader("gzip, deflate")
//                    .silentResources(); // reduce report noise from static assets
//    private final ScenarioBuilder scn = scenario("AceToysSimulation")
//            .exec(UserJourney.firstScenario);
//            .exec(UserJourney.browseStore);
//            .exec(UserJourney.abandonBasket);
//            .exec(UserJourney.completePurchase);
//    {
//        setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
//    }

    private HttpProtocolBuilder httpProtocolBuilder = http
            .baseUrl(DOMAIN)
                    .inferHtmlResources(
                            AllowList(),
                            DenyList(
                                    ".*\\.(js|css|gif|jpeg|jpg|ico|woff|woff2|t|o|ttf|png)",
                                    ".*detectportal\\.firefox\\.com.*")
                    )
                    .acceptEncodingHeader("gzip, deflate")
            .acceptLanguageHeader("en-GB,en;q=0.9");
    {

// Run mode 2 - Uncomment this block to run the simulation
//        setUp(
//                TestScenario.defaultLoadTest
//                        .injectOpen(atOnceUsers(10)).protocols(httpProtocolBuilder)
//        );

// Run mode 3 - Uncomment this block to run the simulation
//        setUp(
//                TestScenario.highPurchaseLoadTest
//                        .injectOpen(atOnceUsers(10)).protocols(httpProtocolBuilder)
//        );

        // Make execution mode for the framework dynamic like this to allow for easy switching between different test types
        switch (TEST_TYPE) {
            case "INSTANT_USERS":
                setUp(TestPopulator.instantUsers).protocols(httpProtocolBuilder);
                break;
            case "RAMP_USERS":
                setUp(TestPopulator.rampUsers).protocols(httpProtocolBuilder);
                break;
            case "COMPLEX_INJECTION":
                setUp(TestPopulator.complexInjection).protocols(httpProtocolBuilder);
                break;
            case "CLOSED_MODEL":
                setUp(TestPopulator.closedModel).protocols(httpProtocolBuilder);
                break;
            default:
                setUp(TestPopulator.instantUsers).protocols(httpProtocolBuilder);
        }
    }
}
