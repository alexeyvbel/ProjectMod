package function.Table;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by BELYANIN on 02.02.2018.
 */
public class connectionTable {

    private final StringProperty cabinet;
    private final StringProperty kks;
    private final StringProperty page_no;
    private final StringProperty page_esg;
    private final StringProperty page_nobi;
    private final StringProperty value;
    private final StringProperty addr;
    private final StringProperty het;

    public connectionTable(String cabinet, String kks, String page_no, String page_esg, String page_nobi, String value, String addr, String het) {
        this.cabinet = new SimpleStringProperty(cabinet);
        this.kks = new SimpleStringProperty(kks);
        this.page_no = new SimpleStringProperty(page_no);
        this.page_esg = new SimpleStringProperty(page_esg);
        this.page_nobi = new SimpleStringProperty(page_nobi);
        this.value = new SimpleStringProperty(value);
        this.addr = new SimpleStringProperty(addr);
        this.het = new SimpleStringProperty(het);

    }

    public String getCabinet() {
        return cabinet.get();
    }

    public String getKks() {
        return kks.get();
    }

    public String getPage_no() {
        return page_no.get();
    }

    public String getPage_esg() {
        return page_esg.get();
    }

    public String getPage_nobi() {
        return page_nobi.get();
    }

    public String getValue() {
        return value.get();
    }

    public String getAddr() {
        return addr.get();
    }

    public String getHet() {
        return het.get();
    }

    public void setCabinet(String cabinet) {
        this.cabinet.set(cabinet);
    }

    public void setKks(String kks) {
        this.kks.set(kks);
    }

    public void setPage_no(String page_no) {
        this.page_no.set(page_no);
    }

    public void setPage_esg(String page_esg) {
        this.page_esg.set(page_esg);
    }

    public void setPage_nobi(String page_nobi) {
        this.page_nobi.set(page_nobi);
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public void setAddr(String addr) {
        this.addr.set(addr);
    }

    public void setHet(String het) {
        this.het.set(het);
    }

    public StringProperty cabinetProperty() {
        return cabinet;
    }

    public StringProperty kksProperty() {
        return kks;
    }

    public StringProperty page_noProperty() {
        return page_no;
    }

    public StringProperty page_esgProperty() {
        return page_esg;
    }

    public StringProperty page_nobiProperty() {
        return page_nobi;
    }

    public StringProperty valueProperty() {
        return value;
    }

    public StringProperty addrProperty() {
        return addr;
    }

    public StringProperty hetProperty() {
        return het;
    }
}
