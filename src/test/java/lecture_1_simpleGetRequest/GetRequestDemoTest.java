package lecture_1_simpleGetRequest;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public final class GetRequestDemoTest {

    @Test
    public void getTest() {
        //Verify response code
        given().get("https://restful-booker.herokuapp.com/booking").then().statusCode(200);

        //Print data from the response
        Response response = given().get("https://restful-booker.herokuapp.com/booking");
        System.out.println(response.getStatusCode());
        System.out.println(response.getTime());
        System.out.println(response.getTimeIn(TimeUnit.SECONDS));

        response.prettyPrint();

        System.out.println(response.headers());

        System.out.println("*************************************");

        Headers headers = response.getHeaders();
        for (Header header : headers) {
            System.out.println(header.getName());
            System.out.println(header.getValue());
        }
    }
}
