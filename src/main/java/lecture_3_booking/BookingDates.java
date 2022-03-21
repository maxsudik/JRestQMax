package lecture_3_booking;

public final class BookingDates {

    private String checkin;
    private String checkout;

    public BookingDates(String checkin, String checkout) {
        this.checkin = checkin;
        this.checkout = checkout;
    }

    public String getCheckin() {
        return checkin;
    }

    public BookingDates setCheckin(String checkin) {
        this.checkin = checkin;
        return this;
    }

    public String getCheckout() {
        return checkout;
    }

    public BookingDates setCheckout(String checkout) {
        this.checkout = checkout;
        return this;
    }
}
