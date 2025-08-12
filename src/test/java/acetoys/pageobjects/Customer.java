package acetoys.pageobjects;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.Choice;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class Customer {
    public static ChainBuilder login =
            exec(
                    http("Login")
                            .post("/login")
                            .formParam("_csrf", "#{csrfToken}")
                            .formParam("username", "user1")
                            .formParam("password", "pass")
                            // Re-save the CSRF token at this point as the value changes
                            .check(css("#_csrf", "content").saveAs("csrfTokenLoggedIn"))
            );

    public static ChainBuilder logout =
            // 10% chance to log out
            randomSwitch().on(
                    Choice.withWeight(10, exec(
                            http("Logout")
                                    .post("/logout")
                                    .formParam("_csrf", "#{csrfTokenLoggedIn}")
                                    /* Verify the DOM element which has an attribute named class=btn btn-secondary
                                    contains the text "Logout" in an anchor*/
                                    .check(css("a.btn.btn-secondary").is("Logout"))
                    ))
            );
}
