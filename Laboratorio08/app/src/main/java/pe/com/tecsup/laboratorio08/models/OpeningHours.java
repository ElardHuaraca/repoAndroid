
package pe.com.tecsup.laboratorio08.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class OpeningHours {

    private Boolean openNow;

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("openNow", openNow).toString();
    }

}
