package camp.abc.com.campignapp.object;

public class Compigncontent {

    String campignId, adSpace, campignDate, campignHour;
    Integer campignSeconds, campignLength;
    String contentId, contentURL, contentType, contentLength, contentDesc;


    public String getCampignId() {
        return campignId;
    }

    public void setCampignId(String campignId) {
        this.campignId = campignId;
    }

    public String getAdSpace() {
        return adSpace;
    }

    public void setAdSpace(String adSpace) {
        this.adSpace = adSpace;
    }

    public String getCampignDate() {
        return campignDate;
    }

    public void setCampignDate(String campignDate) {
        this.campignDate = campignDate;
    }

    public String getCampignHour() {
        return campignHour;
    }

    public void setCampignHour(String campignHour) {
        this.campignHour = campignHour;
    }

    public Integer getCampignSeconds() {
        return campignSeconds;
    }

    public void setCampignSeconds(Integer campignSeconds) {
        this.campignSeconds = campignSeconds;
    }

    public Integer getCampignLength() {
        return campignLength;
    }

    public void setCampignLength(Integer campignLength) {
        this.campignLength = campignLength;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentURL() {
        return contentURL;
    }

    public void setContentURL(String contentURL) {
        this.contentURL = contentURL;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentLength() {
        return contentLength;
    }

    public void setContentLength(String contentLength) {
        this.contentLength = contentLength;
    }

    public String getContentDesc() {
        return contentDesc;
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc;
    }


    @Override
    public String toString() {
        return "Compigncontent{" +
                "campignId='" + campignId + '\'' +
                ", adSpace='" + adSpace + '\'' +
                ", campignDate='" + campignDate + '\'' +
                ", campignHour='" + campignHour + '\'' +
                ", campignSeconds=" + campignSeconds +
                ", campignLength=" + campignLength +
                ", contentId='" + contentId + '\'' +
                ", contentURL='" + contentURL + '\'' +
                ", contentType='" + contentType + '\'' +
                ", contentLength='" + contentLength + '\'' +
                ", contentDesc='" + contentDesc + '\'' +
                '}';
    }
}
