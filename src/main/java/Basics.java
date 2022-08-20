import files.ReusableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.json.Json;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class Basics {
    public static void main(String[] args) {
        // validate if add PLACE API is working as epected
// principles of rest assured:
        // given - all input details

        // when - submit specific API
        //-post details or put details in when
        //  -resource and httpmethod will always go under when method


        // then method -validate the response
        // -what api got generated here
        // first set base url
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123").body(payload.addplace()).
                // for addplace we add directly add jsonfile then read it as bytes then convert bytes to string
                        // body(new String(Files.readAllBytes(Paths.get("//Users/mohammedrahman/Downloads//addplace.json")))).
                when().post("maps/api/place/add/json")
                //sumbit resource value to when and next we must validate
                .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();

        System.out.println(response);

        JsonPath js = new JsonPath(response);
        String placeID = js.getString("place_id");
        System.out.println(placeID);
        System.out.println("firstplace ID is: " + placeID);
        //update place
        String newAddress = "701 Summer Walk, africa";

        given().log().all().queryParam("key", "qaclick123").body("{\n" +
                "\"place_id\":\"" + placeID + "\",\n" +
                "\"address\":\"" + newAddress + "\",\n" +
                "\"key\":\"qaclick123\"\n" +
                "}").

                when().put("maps/api/place/update/json").
                then().assertThat().log().all().statusCode(200).body("msg",
                equalTo("Address successfully updated"));
        //query common for put place

        // now we must verify
        // need validation
        // use get place API
        //get place

        String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
                .queryParam("place_id", placeID)
                .when().get("maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200)
                .extract().response().asString();
        System.out.println("this is getplace:  " + getPlaceResponse);

        JsonPath js1= ReusableMethods.rawtoJSON(getPlaceResponse);
        // converts raw string to JSON file

        String actualAddress = js1.getString("address");
        // from json WE EXTRACT the address
        System.out.println(actualAddress);
        //junit /testng
        Assert.assertEquals(actualAddress, newAddress);

        // NOTICE: using static it allows us to import and use
        // that method outside of the class using name of class.method



        //get methods only part of body //no body sent

        //next lecture we will try to update place and getplace using this PlaceID variable.
        //extracted and placed inside variable


        // can change status code expected value and see if validation pass or fails
        //first validation check status code

        // note up to .header method: no errors so validations are GOOD
        // next tank: add place > update place with new address > get place to validate
        // new address is present in response
        //   this is end to end
        // 1. add place 2. put place and update. 3. get place to retrive added inntiallty and verify


    }
    @Test
    public void test(){
        System.out.println(payload.CoursePrice());
        Assert.assertFalse(true);
    }
}
