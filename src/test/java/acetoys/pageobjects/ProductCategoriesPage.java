package acetoys.pageobjects;

import acetoys.session.UserSession;
import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class ProductCategoriesPage {

    public static ChainBuilder browseProductListByCategory =
            exec(
                    http("Category All")
                            .get("/category/all/")
            );

    // Pragmatic HTML pagination: hit the known URL pattern and iterate a bounded number of times.
    public static ChainBuilder browseProductListByCategoryAcrossPages =
            exec(s -> s.set("productListPageNumber", 1)) // Reset page number each time this chain is invoked

                    .repeat("#{maxPages}", "i").on(
                            exec(
                                    http("Category All page #{productListPageNumber}")
                                            .get("/category/all?page=#{productListPageNumber}")
                                    // Optionally assert a very stable marker if available, otherwise omit checks.
                                    // .check(substring("<title>").exists())
                            )
                                    .exec(s -> s.set("productListPageNumber", s.getInt("productListPageNumber") + 1))
                                    .pause(2)
                    );


    public static ChainBuilder addItem1ToCart =
            exec(UserSession.increaseItemsInBasket)
                    .exec(UserSession.increaseSessionBasketPriceTotal)
                    .exec(
                            http("Add Item 1")
                                    .get("/cart/add/1")
//                                    .check(substring("You have <span>#{itemsInBasket}</span> products in your Basket"))
                    );

    public static ChainBuilder addItem5ToCart =
            exec(
                    http("Add Item 5")
                            .get("/cart/add/5")
                            // Example of how to check DOM element value
                            // .check(css(".page-item.active").is("2"))
            );
}
