
package pe.com.tecsup.laboratorio08.models;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ResponsePlace {

    private List<Object> htmlAttributions = null;
    private String nextPageToken;
    private List<Result> results = null;
    private String status;

    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("htmlAttributions", htmlAttributions).append("nextPageToken", nextPageToken).append("results", results).append("status", status).toString();
    }

}
