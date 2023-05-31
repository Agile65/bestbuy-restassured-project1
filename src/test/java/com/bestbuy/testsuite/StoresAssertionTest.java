package com.bestbuy.testsuite;

import com.bestbuy.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.collection.IsMapContaining;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasEntry;


public class StoresAssertionTest extends TestBase {

    static ValidatableResponse response;

    //1. Verify the if the total is equal to 1561
    @Test
    public void test001() {
        response = given()
                .when()
                .get("/stores")
                .then().statusCode(200);
        response.log().all();
        response.body("total", equalTo(1561));// same as assert use response.body here
    }

    //2. Verify the if the stores of limit is equal to 10
    @Test
    public void test002() {
        response = given()
                .when()
                .get("/stores")
                .then().statusCode(200);
        response.body("limit", equalTo(10));

    }

    //3. Check the single ‘Name’ in the Array list (Inver Grove Heights)
    @Test
    public void test003() {
        response = given()
                .when()
                .get("/stores")
                .then().statusCode(200);
        response.body("data.name", hasItem("Inver Grove Heights"));

    }

    //4. Check the multiple ‘Names’ in the ArrayList (Roseville, Burnsville, Maplewood)
    @Test
    public void test004() {
        response = given()
                .when()
                .get("/stores")
                .then().statusCode(200);
        response.body("data.name", hasItems("Roseville", "Burnsville", "Maplewood"));
    }

    //5. Verify the storied=7 inside storeservices of the third store of second services
    @Test
    public void test005() {
        response = given()
                .when()
                .get("/stores")
                .then().statusCode(200);

        //data[2].services[1].storeservices.storeId
        response.body("data[2].services[1].storeservices", hasEntry("storeId", 7));
    }

    //6. Check hash map values ‘createdAt’ inside storeservices map where store name = Roseville
    @Test
    public void test006() {
        response = given()
                .when()
                .get("/stores")
                .then().statusCode(200);
        response.body("data.findAll{it.name == 'Roseville'}", hasItem(IsMapContaining.hasEntry("createdAt", "2016-11-17T17:57:05.853Z")));
    }

    //7. Verify the state = MN of forth store
    @Test
    public void test007() {
        response = given()
                .when()
                .get("/stores")
                .then().statusCode(200);
        response.body("data[3]", hasEntry("state", "MN"));
    }

    //8. Verify the store name = Rochester of 9th store
    @Test
    public void test008() {
        response = given()
                .when()
                .get("/stores")
                .then().statusCode(200);
        response.body("data[8]", hasEntry("name", "Rochester"));
    }

    //9. Verify the storeId = 11 for the 6th store
    @Test
    public void test009() {
        response = given()
                .when()
                .get("/stores")
                .then().statusCode(200);
        //.data[5].services[6].storeservices.storeId//found json path on website
        response.body("data[5].services[6].storeservices", hasEntry("storeId", 11));
    }

    //10. Verify the serviceId = 4 for the 7th store of forth service
    @Test
    public void test010() {
        response = given()
                .when()
                .get("/stores")
                .then().statusCode(200);
        response.body("data[6].services[3].storeservices", hasEntry("serviceId", 4));
    }
}
