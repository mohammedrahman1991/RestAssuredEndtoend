package files;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJSON {
    @Test(dataProvider ="BooksData")
    public void addBook(String isbn, String aisle){
        RestAssured.baseURI= "https://rahulshettyacademy.com/Library/Addbook.php";
        String response = given().header("Content-Type","application/json").
                body(payload.addBook(isbn,aisle)).
                when()
                .post("Library/Addbook.php  ").
                then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath js = ReusableMethods.rawtoJSON(response);
                String iD = js.get("ID");
        System.out.println(iD);


    }

    @DataProvider(name="BooksData")
    public Object[][] getData(){

        //array is a collection of elementals
        // multi dimensional array = it is a collection of arrays]
        //so we can create object and initializze it
        return new Object[][] {{"iou89", "23455"}, {"a33r", "4463"},{"okmfet3","4675"}};
    }





}
