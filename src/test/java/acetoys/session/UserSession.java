package acetoys.session;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class UserSession {

    public static ChainBuilder initSession =
            // Clears all HTTP cookies stored for the current virtual user
            exec(flushCookieJar())
                    .exec(session -> session.set("customerLoggedIn", false));
}
