package lecture_3_booking;

import java.util.List;

public final class Booking {

    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private BookingDates bookingdates;
    private List<String> additionalneeds;

    public Booking(String firstname, String lastname, int totalprice, boolean depositpaid, BookingDates bookingdates, List<String> additionalneeds) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.totalprice = totalprice;
        this.depositpaid = depositpaid;
        this.bookingdates = bookingdates;
        this.additionalneeds = additionalneeds;
    }

    public List<String> getAdditionalneeds() {
        return additionalneeds;
    }

    public Booking setAdditionalneeds(List<String> additionalneeds) {
        this.additionalneeds = additionalneeds;
        return this;
    }

    public BookingDates getBookingdates() {
        return bookingdates;
    }

    public Booking setBookingdates(BookingDates bookingdates) {
        this.bookingdates = bookingdates;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public Booking setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public Booking setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public Booking setTotalprice(int totalprice) {
        this.totalprice = totalprice;
        return this;
    }

    public boolean isDepositpaid() {
        return depositpaid;
    }

    public Booking setDepositpaid(boolean depositpaid) {
        this.depositpaid = depositpaid;
        return this;
    }
}
