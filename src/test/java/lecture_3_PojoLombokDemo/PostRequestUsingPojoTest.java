package lecture_3_PojoLombokDemo;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lecture_3_booking.Booking;
import lecture_3_booking.BookingDates;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public final class PostRequestUsingPojoTest {

    //{ } --> Class
    //[ ] --> List<T>
    //Online Generator JSON --> POJO https://www.jsonschema2pojo.org
    @Test
    public void pojoTest() {

        BookingDates bookingdates = new BookingDates("2018-01-01", "2019-01-01");
        List<String> additionalneeds = new ArrayList<>(Arrays.asList("Breakfast", "Lunch", "Dinner"));
        Booking booking = new Booking("John", "Doe", 300, true, bookingdates, additionalneeds);
        Response response = given()
                .header("Content-Type", ContentType.JSON)
                .log()
                .all()
                .body(booking)
                .post("https://restful-booker.herokuapp.com/booking");

        response.prettyPrint();
    }
}
