package camp.abc.com.campignapp.object;

public class Content {

    String campignId,  adSpace,  contentId,  contentURL,  contentType,  contentLength,  contentDesc;

    public Content(String campignId, String adSpace, String contentId, String contentURL, String contentType, String contentLength, String contentDesc) {

        this.campignId = campignId;
        this.adSpace = adSpace;
        this.contentId = contentId;
        this.contentURL = contentURL;
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.contentDesc = contentDesc;

    }

    public String getAdSpace() {
        return adSpace;
    }

    public String getCampignId() {
        return campignId;
    }

    public String getContentId() {
        return contentId;
    }

    public String getContentURL() {
        return contentURL;
    }

    public String getContentType() {
        return contentType;
    }

    public String getContentLength() {
        return contentLength;
    }

    public String getContentDesc() {
        return contentDesc;
    }
}
