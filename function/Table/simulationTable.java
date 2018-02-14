package function.Table;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by BELYANIN on 06.02.2018.
 */
public class simulationTable {
    private final StringProperty kks;
    private final StringProperty marker;
    private final StringProperty comment;

    public simulationTable(String kks, String marker, String comment) {
        this.kks = new SimpleStringProperty(kks);
        this.marker = new SimpleStringProperty(marker);
        this.comment = new SimpleStringProperty(comment);
    }

    public String getKks() {
        return kks.get();
    }

    public String getMarker() {
        return marker.get();
    }

    public String getComment() {
        return comment.get();
    }

    public void setKks(String kks) {
        this.kks.set(kks);
    }

    public void setMarker(String marker) {
        this.marker.set(marker);
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public StringProperty kksProperty() {
        return kks;
    }

    public StringProperty markerProperty() {
        return marker;
    }

    public StringProperty commentProperty() {
        return comment;
    }
}
