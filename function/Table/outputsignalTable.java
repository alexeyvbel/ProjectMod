package function.Table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by BELYANIN on 01.02.2018.
 */
public class outputsignalTable {

    private final IntegerProperty abonent_id;
    private final StringProperty page_kks;
    private final StringProperty page_no;
    private final StringProperty signalcode;
    private final StringProperty telegram;
    private final StringProperty pageright;
    private final StringProperty pageright_no;
    private final StringProperty block;
    private final StringProperty techname;
    private final StringProperty marker;

    public outputsignalTable(int abonent_id, String page_kks, String page_no, String signalcode, String telegram, String pageright, String pageright_no, String block, String techname, String marker){
        this.abonent_id = new SimpleIntegerProperty(abonent_id);
        this.page_kks = new SimpleStringProperty(page_kks);
        this.page_no = new SimpleStringProperty(page_no);
        this.signalcode = new SimpleStringProperty(signalcode);
        this.telegram = new SimpleStringProperty(telegram);
        this.pageright = new SimpleStringProperty(pageright);
        this.pageright_no = new SimpleStringProperty(pageright_no);
        this.block = new SimpleStringProperty(block);
        this.techname = new SimpleStringProperty(techname);
        this.marker = new SimpleStringProperty(marker);
    }

    public int getAbonent_id() {
        return abonent_id.get();
    }

    public String getPage_kks() {
        return page_kks.get();
    }

    public String getPage_no() {
        return page_no.get();
    }

    public String getTelegram() {
        return telegram.get();
    }

    public String getSignalcode() {
        return signalcode.get();
    }

    public String getBlock() {
        return block.get();
    }

    public String getMarker() {
        return marker.get();
    }

    public String getTechname() {
        return techname.get();
    }

    public String getPageright() {
        return pageright.get();
    }

    public String getPageright_no() {
        return pageright_no.get();
    }

    public void setPage_kks(String page_kks) {
        this.page_kks.set(page_kks);
    }

    public void setPage_no(String page_no) {
        this.page_no.set(page_no);
    }

    public void setAbonent_id(int abonent_id) {
        this.abonent_id.set(abonent_id);
    }

    public void setSignalcode(String signalcode) {
        this.signalcode.set(signalcode);
    }

    public void setBlock(String block) {
        this.block.set(block);
    }

    public void setPageright(String pageright) {
        this.pageright.set(pageright);
    }

    public void setTelegram(String telegram) {
        this.telegram.set(telegram);
    }

    public void setMarker(String marker) {
        this.marker.set(marker);
    }

    public void setPageright_no(String pageright_no) {
        this.pageright_no.set(pageright_no);
    }

    public void setTechname(String techname) {
        this.techname.set(techname);
    }

    public IntegerProperty abonent_idProperty() {
        return abonent_id;
    }

    public StringProperty page_kksProperty() {
        return page_kks;
    }

    public StringProperty page_noProperty() {
        return page_no;
    }

    public StringProperty signalcodeProperty() {
        return signalcode;
    }

    public StringProperty telegramProperty() {
        return telegram;
    }

    public StringProperty blockProperty() {
        return block;
    }

    public StringProperty markerProperty() {
        return marker;
    }

    public StringProperty technameProperty() {
        return techname;
    }

    public StringProperty pagerightProperty() {
        return pageright;
    }

    public StringProperty pageright_noProperty() {
        return pageright_no;
    }
}
