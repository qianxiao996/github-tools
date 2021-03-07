package tools;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextArea;

public class Conf {
    @FXML
    private TextArea star_api;

    @FXML
    private TextArea commits_api;

    @FXML
    private TextArea issus_api;

    @FXML
    private TextArea releases_api;

    @FXML
    private TextArea add_api;



    @FXML
    private TextArea conf_star_user;


    @FXML
    private TextArea conf_releases_text;




    @FXML
    public void initialize() {
        conf_load_config();

    }

    @FXML
    private TextArea conf_commits_text;

    @FXML
    private Button conf_coommits_save;


    @FXML
    private TextArea conf_issus_textdata;

    @FXML
    private Button conf_issus_savedata;


    @FXML
    private TextArea conf_update_textdata;

    @FXML
    private Button conf_update_savedata;

    //加载数据
    void conf_load_config() {
        star_api.setText(Main.config_all_jsonobj.getJSONObject("api").getString("star_api"));
        releases_api.setText(Main.config_all_jsonobj.getJSONObject("api").getString("releases_api"));
        commits_api.setText(Main.config_all_jsonobj.getJSONObject("api").getString("commits_api"));
        issus_api.setText(Main.config_all_jsonobj.getJSONObject("api").getString("issus_api"));
        add_api.setText(Main.config_all_jsonobj.getJSONObject("api").getString("update_api"));
        conf_star_user.setText(Main.config_all_jsonobj.getString("star_data"));

        conf_releases_text.setText(Main.config_all_jsonobj.getString("releases_data"));
        conf_commits_text.setText(Main.config_all_jsonobj.getString("commits_data"));
        conf_issus_textdata.setText(Main.config_all_jsonobj.getString("issus_data"));
        conf_update_textdata.setText(Main.config_all_jsonobj.getString("update_data"));
    }

    //保存api配置
    @FXML
    void save_api_button(ActionEvent event) {
        Main.config_all_jsonobj.getJSONObject("api").put("star_api",star_api.getText());
        Main.config_all_jsonobj.getJSONObject("api").put("releases_api",releases_api.getText());
        Main.config_all_jsonobj.getJSONObject("api").put("commits_api",commits_api.getText());
        Main.config_all_jsonobj.getJSONObject("api").put("issus_api",issus_api.getText());
        Main.config_all_jsonobj.getJSONObject("api").put("update_api",add_api.getText());
        Main.f_alert_informationDialog("Success","","保存成功！");
//        System.out.print(Main.config_all_jsonobj);/
    }
    //保存star配置
    @FXML
    void save_star_button(ActionEvent event) {
        Main.config_all_jsonobj.put("star_data",conf_star_user.getText());
        Main.f_alert_informationDialog("Success","","保存成功！");

    }

    //保存release配置
    @FXML
    void save_releases_button(ActionEvent event) {
        Main.config_all_jsonobj.put("releases_data",conf_releases_text.getText());
        Main.f_alert_informationDialog("Success","","保存成功！");


    }

    //保存commits配置
    @FXML
    void save_commits_button(ActionEvent event) {
        Main.config_all_jsonobj.put("commits_data",conf_commits_text.getText());
        Main.f_alert_informationDialog("Success","","保存成功！");
    }


    //保存issus
    @FXML
    void save_issus_button(ActionEvent event) {
        Main.config_all_jsonobj.put("issus_data",conf_issus_textdata.getText());
        Main.f_alert_informationDialog("Success","","保存成功！");


    }
    //保存update新增数据
    @FXML
    void save_update_button(ActionEvent event) {
        Main.config_all_jsonobj.put("update_data",conf_update_textdata.getText());
        Main.f_alert_informationDialog("Success","","保存成功！");

    }





}
