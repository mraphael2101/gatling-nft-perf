package acetoys.pageobjects;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.css;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.http.HttpDsl.http;

public class ProductCategoriesPage {

    public static ChainBuilder browseProductListByCategory =
            exec(
                    http("Category All")
                            .get("/category/all/")
            );

    public static ChainBuilder addItem1ToCart =
            exec(
                    http("Add Item 1")
                            .get("/cart/add/1")
            );

    public static ChainBuilder addItem5ToCart =
            exec(
                    http("Add Item 5")
                            .get("/cart/add/5")
                            // Example of how to check DOM element value
                            // .check(css(".page-item.active").is("2"))
            );

    public static ChainBuilder addColouredBuildingBlocksToCart =
            exec(
                    http("Product: coloured-building-blocks")
                            .get("/product/coloured-building-blocks")
            );

    public static ChainBuilder viewCart =
            exec(
                    http("View Cart")
                            .get("/cart/view")
            );
}
