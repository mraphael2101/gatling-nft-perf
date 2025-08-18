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

// Run mode 1 - Uncomment this block to run the simulation
//    private final HttpProtocolBuilder httpProtocol =
//            http
//                    .baseUrl("https://acetoys.uk")
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
            .baseUrl("https://acetoys.uk")
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

        setUp(
                TestPopulator.complexInjection.protocols(httpProtocolBuilder)
        );
    }
}
