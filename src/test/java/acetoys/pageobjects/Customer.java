package acetoys.pageobjects;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.Choice;
import io.gatling.javaapi.core.FeederBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class Customer {

    private static final FeederBuilder<String> userCredentialsFeeder =
            csv("data/user_credentials.csv").circular();

    public static ChainBuilder login =
            exec(
                    http("Login")
                            .post("/login")
                            .formParam("_csrf", "#{csrfToken}")
                            .formParam("username", "#{username}")
                            .formParam("password", "#{password}")
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
