package camp.abc.com.campignapp.object;

public class Campign {

    String campignId, adSpace, campignDate, campignHour;
    String campignSeconds, campignLength;

    public Campign(String campignId, String adSpace, String campignDate, String campignHour, String campignSeconds, String campignLength) {

        this.campignId = campignId;
        this.adSpace = adSpace;
        this.campignDate = campignDate;
        this.campignHour = campignHour;
        this.campignSeconds = campignSeconds;
        this.campignLength = campignLength;

    }

    public String getId() {
        return campignId;
    }

    public String getAdSpace() {
        return adSpace;
    }

    public String getDate() {
        return campignDate;
    }

    public String getHour() {
        return campignHour;
    }

    public String getSeconds() {
        return campignSeconds;
    }

    public String getLength() {
        return campignLength;
    }

    public String getCampignId() {
        return campignId;
    }
}
