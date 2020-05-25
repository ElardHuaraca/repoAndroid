
package pe.com.tecsup.laboratorio08.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Geometry {

    private Location location;
    private Viewport viewport;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("location", location).append("viewport", viewport).toString();
    }

}
