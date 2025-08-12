package acetoys.pageobjects;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class ProductDetailsPage {

    public static ChainBuilder loadProductDetailsPage_ColouredBuildingBlocks =
            exec(
                    http("Product: coloured-building-blocks")
                            .get("/product/coloured-building-blocks")
                            .check(
                                    css("#ProductDescription")
                                            .is("Selection of building blocks that come in a wide range of shapes and colours")
                            )
                             .check(substring("You have <span>1</span> products in your Basket"))
            );

}
