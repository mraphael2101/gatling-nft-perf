package acetoys.pageobjects;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class ProductDetailsPage {

// Static implementation of the page object
//    public static ChainBuilder loadProductDetailsPage_ColouredBuildingBlocks =
//            exec(
//                    http("Load Product Details Page - Product: coloured-building-blocks")
//                            .get("/product/coloured-building-blocks")
//                            .check(
//                                    css("#ProductDescription")
//                                            .is("Selection of building blocks that come in a wide range of shapes and colours")
//                            )
//                             .check(substring("You have <span>1</span> products in your Basket"))
//            );

    private static final FeederBuilder<Object> productFeeder =
            jsonFile("data/product_details.json").random();

    public static ChainBuilder loadProductDetailsPage_ColouredBuildingBlocks =
            feed(productFeeder).
                    exec(
                            // Reference product_details.json file for the attribute names
                            http("Load Product Details Page - Product: #{name}")
                                    .get("/product/#{slug}")
                                    .check(
                                            css("#ProductDescription")
                                                    /* isEL method checks the value of the dynamic description
                                                    attribute from the feeder/json file */
                                                    .isEL("#{description}")
                                    )
                                    .check(substring("You have <span>1</span> products in your Basket"))
                    );
}
