package table;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public  class Table_Releases {
    private String Releases_table_user;
    private String Releases_table_obj_name;
    private String Releases_table_obj_addrss;
    private String Releases_table_version;
    private String Releases_table_version_name;
    private String Releases_table_version_desc;
    private String Releases_table_date;
//    private TableView<?> Releases_table;

    public void setReleases_table_user(String Releases_table_user) {
        this.Releases_table_user = Releases_table_user;
    }
    public void setReleases_table_obj_name(String Releases_table_obj_name) {
        this.Releases_table_obj_name = Releases_table_obj_name;
    }
    public void setReleases_table_obj_addrss(String Releases_table_obj_addrss) {
        this.Releases_table_obj_addrss = Releases_table_obj_addrss;
    }
    public void setReleases_table_version(String Releases_table_version) {
        this.Releases_table_version = Releases_table_version;
    }
    public void setReleases_table_version_name(String Releases_table_version_name) {
        this.Releases_table_version_name = Releases_table_version_name;
    }
    public void setReleases_table_version_desc(String Releases_table_version_desc) {
        this.Releases_table_version_desc = Releases_table_version_desc;
    }
    public void setReleases_table_date(String Releases_table_date) {
        this.Releases_table_date = Releases_table_date;
    }

    public String getReleases_table_user() {

        return Releases_table_user;
    }
    public String getReleases_table_obj_name() {

        return Releases_table_obj_name;
    }
    public String getReleases_table_obj_addrss() {

        return Releases_table_obj_addrss;
    }
    public String getReleases_table_version() {

        return Releases_table_version;
    }

    public String getReleases_table_version_name() {

        return Releases_table_version_name;
    }
    public String getReleases_table_version_desc() {

        return Releases_table_version_desc;
    }
    public String getReleases_table_date() {

        return Releases_table_date;
    }


}