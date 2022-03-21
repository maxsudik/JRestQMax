package lecture_2_simplePostRequest;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public final class PostRequestDemoTest {

    //Passing JSON body as String --> Not Recommended
    //Easy to copy/paste --> can be used quickly check the behaviour
    //Not recommended for larger or dynamic JSON
    @Test
    public void simplePostTest() {
        Response response = given()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"username\" : \"admin\",\n" +
                        "    \"password\" : \"password123\"\n" +
                        "}")
                .log()
                .all()
                .post("https://restful-booker.herokuapp.com/auth");

        response.prettyPrint();
        System.out.println(response.getStatusCode());

        response = given()
                .header("Content-Type", ContentType.JSON)//Using enum for Header Content Type
                .body("{\n" +
                        "    \"username\" : \"admin\",\n" +
                        "    \"password\" : \"password123\"\n" +
                        "}")
                .log()
                .all()
                .post("https://restful-booker.herokuapp.com/auth");

        response.prettyPrint();
        System.out.println(response.getStatusCode());
    }

    //Pass JSON from external file --> /src/test/resources/simpleTest.json5
    //You cannot get the request content from the file and print it to the console
    //You can use this approach only for static data
    @Test
    public void postTestReadFromExternalFile1() {
        Response response = given()
                .header("Content-Type", ContentType.JSON)
                .log()
                .all()
                .body(new File(System.getProperty("user.dir") + "/src/test/resources/simpleTest.json5"))
                .post("https://restful-booker.herokuapp.com/auth");

        response.prettyPrint();
    }

    //Read JSON from external file and convert to a String --> /src/test/resources/simpleTest.json5
    //Logs the request body into the console
    //Change few parameters in the request
    //Not suitable for request having a lot of dynamic parameters
    @Test
    public void postTestReadFromExternalFile2() throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/src/test/resources/simpleTest.json5"));
        String requestBody = new String(bytes);

        Response response = given()
                .header("Content-Type", ContentType.JSON)
                .log()
                .all()
                .body(requestBody)
                .post("https://restful-booker.herokuapp.com/auth");

        response.prettyPrint();

        String changedRequestBody = requestBody.replace("password123", "password1234");//Invalid password
        response = given()
                .header("Content-Type", ContentType.JSON)
                .log()
                .all()
                .body(changedRequestBody)
                .post("https://restful-booker.herokuapp.com/auth");

        response.prettyPrint();

        String fakedRequestBody = requestBody.replace("123", String.valueOf(new Faker().number().numberBetween(100, 1000)));//Invalid password
        response = given()
                .header("Content-Type", ContentType.JSON)
                .log()
                .all()
                .body(fakedRequestBody)
                .post("https://restful-booker.herokuapp.com/auth");

        response.prettyPrint();
    }

    //Using Map from java
    //{ } --> Map Interface
    //[ ] --> List Interface
    //Serialization achieved by <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
    @Test
    public void postTestUsingHashMap() {
        Map<String, Object> body = new HashMap<>();
        body.put("username", "admin");
        body.put("password", "password123");

        Response response = given()
                .header("Content-Type", ContentType.JSON)
                .log()
                .all()
                .body(body)
                .post("https://restful-booker.herokuapp.com/auth");

        response.prettyPrint();
    }

    //Using Map and List from java
    //{ } --> Map Interface
    //[ ] --> List Interface
    //Serialization achieved by jackson-databind dependency
    //Cons - verbose, not suitable for big json files, generic types should be mentioned
    @Test
    public void postTestUsingComplexJson() {
        Map<String, Object> body = new HashMap<>();
        body.put("firstname", "Jim");
        body.put("lastname", "Brown");
        body.put("totalprice", 111);
        body.put("depositpaid", true);

        HashMap<String, Object> bookingdates = new HashMap<>();
        bookingdates.put("checkin", "2018-01-01");
        bookingdates.put("checkout", "2019-01-01");
        body.put("bookingdates", bookingdates);

        List<String> additionalneeds = new ArrayList<>();
        additionalneeds.add("Breakfast");
        additionalneeds.add("Lunch");
        additionalneeds.add("Dinner");
        body.put("additionalneeds", additionalneeds);


        Response response = given()
                .header("Content-Type", ContentType.JSON)
                .log()
                .all()
                .body(body)
                .post("https://restful-booker.herokuapp.com/booking");

        response.prettyPrint();
    }

    //Using external JSON library
    //Having some collections that can solve problems we had while using Map and List
    //{ } --> JsonObject using <!-- https://mvnrepository.com/artifact/org.json/json -->
    //[ ] --> JsonArray using <!-- https://mvnrepository.com/artifact/org.json/json -->
    @Test
    public void postTestUsingJsonLibrary() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstname", "Jim");
        jsonObject.put("lastname", "Brown");
        jsonObject.put("totalprice", 111);
        jsonObject.put("depositpaid", true);

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2018-01-01");
        bookingdates.put("checkout", "2019-01-01");
        jsonObject.put("bookingdates", bookingdates);

        JSONArray additionalneeds = new JSONArray();
        additionalneeds.put("Breakfast");
        additionalneeds.put("Lunch");
        additionalneeds.put("Dinner");
        jsonObject.put("additionalneeds", additionalneeds);
        jsonObject.accumulate("additionalneeds", "Parking");//for adding additional values
        jsonObject.putOpt("additionalneeds", null);//adds value only if it is not null
        jsonObject.putOnce("depositpaid", false);//checks if the key with values already present in the request body

        Response response = given()
                .header("Content-Type", ContentType.JSON)
                .log()
                .all()
                .body(jsonObject.toString())//or .body(jsonObject.toMap())
                .post("https://restful-booker.herokuapp.com/booking");

        response.prettyPrint();
    }
}
