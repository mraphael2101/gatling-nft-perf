package acetoys.simulation;

import io.gatling.javaapi.core.Choice;
import io.gatling.javaapi.core.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;

public class TestScenario {

    public static ScenarioBuilder defaultLoadTest =
            scenario("Default Load Test")
                    .during(30) // 30 seconds
                    .on(
                            randomSwitch()
                                    .on(    // 60% of the time
                                            Choice.withWeight(60, exec(UserJourney.browseStore)),
                                            // 30% of the time
                                            Choice.withWeight(30, exec(UserJourney.abandonBasket)),
                                            // 10% of the time
                                            Choice.withWeight(10, exec(UserJourney.completePurchase))
                                    )
                    );

    public static ScenarioBuilder highPurchaseLoadTest =
            scenario("High Purchase Load Test")
                    .during(30) // 30 seconds
                    .on(
                            randomSwitch()
                                    .on(    // 30% of the time
                                            Choice.withWeight(30, exec(UserJourney.browseStore)),
                                            // 30% of the time
                                            Choice.withWeight(30, exec(UserJourney.abandonBasket)),
                                            // 40% of the time
                                            Choice.withWeight(40, exec(UserJourney.completePurchase))
                                    )
                    );

}
