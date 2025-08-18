package acetoys.simulation;

import io.gatling.javaapi.core.PopulationBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;

//TODO: See https://docs.gatling.io/concepts/injection/#open-model for more details

/**
 * Load injection models used in this class:
 * - Open model: controls arrival rate (users/sec). If the system slows, active users can accumulate.
 * - Closed model: controls concurrent users. If the system slows, throughput drops but concurrency stays capped.
 * This class demonstrates both:
 * - instantUsers, rampUsers, complexInjection: open-model injections that shape arrivals.
 * - closedModel: a closed-model injection that fixes and then ramps concurrency.
 */

public class TestPopulator {

    /**
     * Open model: waits 5s, then injects 10 users immediately in a single burst.
     * Useful for quick smoke checks and validating scenario wiring under a short spike.
     */
    public static PopulationBuilder instantUsers =
            TestScenario.defaultLoadTest
                    .injectOpen(
                            nothingFor(5),
                            atOnceUsers(10)
                    );

    /**
     * Open model: waits 5s, then injects 10 users linearly over the 20s.
     * Useful to observe system behavior as the arrival rate grows gradually.
     */
    public static PopulationBuilder rampUsers =
            TestScenario.defaultLoadTest
                    .injectOpen(
                            nothingFor(5),
                            rampUsers(10).during(20)
                    );

    /**
     * Open model with two phases and jittered start times:
     * - First 20s: ~10 users per second (with slight randomness).
     * - Next 20s: smoothly ramps from ~10 to ~20 users per second (also randomized).
     * Randomization avoids perfectly even spacing between user starts.
     */
    public static PopulationBuilder complexInjection =
            TestScenario.defaultLoadTest
                    .injectOpen(
                            constantUsersPerSec(10).during(20).randomized(),
                            rampUsersPerSec(10).to(20).during(20).randomized()
                    );


    /**
     * Closed model: holds 10 concurrent users for 20s, then ramps concurrency from 10 to 20 over the 20s.
     * Throughput adapts to system speed (concurrency is capped), making it useful for steady-state capacity tests.
     */
    public static PopulationBuilder closedModel =
            TestScenario.highPurchaseLoadTest
                    .injectClosed(
                            constantConcurrentUsers(10).during(20),
                            rampConcurrentUsers(10).to(20).during(20)
                    );


}
