package acetoys.pageobjects;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class StaticPages {

    /**
     * The purpose of `ChainBuilder` in Gatling is to define a sequence of actions or requests that form a reusable
     * part of a Gatling scenario. This is particularly useful for structuring and reusing common sequences of
     * HTTP calls or actions across multiple scenarios, improving both readability and maintainability of the
     * performance test script
     */
    public static ChainBuilder homePage =
            exec(
                    http("Load AceToys Home Page")
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
            );

}
