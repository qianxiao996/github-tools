package thread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import table.Table_Releases;
import table.Table_Star;
import tools.Main;
import tools.httpclint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class Thread_Releases extends Thread  {

    //线程调用
    private Button thread_button;
    private String thread_date;
    private String[] thread_obj_list;


    private TableView<Table_Releases> thread_releases_tables;
    private TableColumn<Table_Releases, String> thread_releases_table_user;
    private TableColumn<Table_Releases, String> thread_releases_table_obj_name;
    private TableColumn<Table_Releases, String> thread_releases_table_obj_addrss;
    private TableColumn<Table_Releases, String> thread_releases_table_version;
    private TableColumn<Table_Releases, String> thread_releases_table_version_name;
    private TableColumn<Table_Releases, String> thread_releases_table_version_desc;
    private TableColumn<Table_Releases, String> thread_releases_table_date;



    public void setValue(String date, String[] obj_list, Button go_button,TableView<Table_Releases> releases_tables,TableColumn<Table_Releases, String> releases_table_user,TableColumn<Table_Releases, String> releases_table_obj_name,TableColumn<Table_Releases, String> releases_table_obj_addrss,TableColumn<Table_Releases, String> releases_table_version,TableColumn<Table_Releases, String> releases_table_version_name,TableColumn<Table_Releases, String> releases_table_version_desc,TableColumn<Table_Releases, String> releases_table_date) {
        this.thread_date = date;
        this.thread_obj_list = obj_list;
        this.thread_button = go_button;
        this.thread_releases_tables = releases_tables;
        this.thread_releases_table_user = releases_table_user;
        this.thread_releases_table_obj_name = releases_table_obj_name;
        this.thread_releases_table_obj_addrss = releases_table_obj_addrss;
        this.thread_releases_table_version =  releases_table_version;
        this.thread_releases_table_version_name =  releases_table_version_name;
        this.thread_releases_table_version_desc = releases_table_version_desc;
        this.thread_releases_table_date  =  releases_table_date;

    }
    public void run() {
        thread_button.setDisable(true);
        for(String obj : thread_obj_list) {
            try {
                String url = Main.config_all_jsonobj.getJSONObject("api").getString("releases_api").replace("{user}/{project}",obj);

                JSONArray http_data = JSON.parseArray(httpclint.doGet(url));
                Iterator<Object> it = http_data.iterator();
                while (it.hasNext()) {
                    JSONObject ob = (JSONObject) it.next();
                    if (ob.getString("id") != null) {
                        try {
                            //更新日期
                            String updated_at = ob.getString("created_at");
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            Date chaxun_date = df.parse(thread_date);
                            //                        System.out.print(chaxun_date.getTime());
                            String updated_date_str = updated_at.split("T")[0];
                            Date updated_date = df.parse(updated_date_str);

                            if (updated_date.getTime() > chaxun_date.getTime()) {
                                ObservableList<Table_Releases> listobj = FXCollections.observableArrayList();

                                //发布者
                                String author = ob.getJSONObject("author").getString("login");


                                //项目地址
                                String html_url = ob.getString("html_url");
                                //版本
                                String vsrsion = ob.getString("tag_name");
                                //版本名称
                                String vsrsion_name = ob.getString("name");
                                //项目描述
                                String vsrsion_description = ob.getString("body");
                                //初始化为table添加值
                                Table_Releases valueobj = new Table_Releases();//构建值对象
                                valueobj.setReleases_table_user(author);
                                valueobj.setReleases_table_obj_name(obj);
                                valueobj.setReleases_table_obj_addrss(html_url);
                                valueobj.setReleases_table_version(vsrsion);
                                valueobj.setReleases_table_version_name(vsrsion_name);
                                valueobj.setReleases_table_version_desc(vsrsion_description);
                                valueobj.setReleases_table_date(updated_at);
                                valueobj.setReleases_table_user(author);
                                listobj.add(valueobj);        //list添加值对象
                                thread_releases_table_user.setCellValueFactory(new PropertyValueFactory<>("releases_table_user"));//映射
                                thread_releases_table_obj_name.setCellValueFactory(new PropertyValueFactory<>("releases_table_obj_name"));//映射
                                thread_releases_table_obj_addrss.setCellValueFactory(new PropertyValueFactory<>("releases_table_obj_addrss"));//映射
                                thread_releases_table_version.setCellValueFactory(new PropertyValueFactory<>("releases_table_version"));//映射
                                thread_releases_table_version_name.setCellValueFactory(new PropertyValueFactory<>("releases_table_version_name"));//映射
                                thread_releases_table_version_desc.setCellValueFactory(new PropertyValueFactory<>("releases_table_version_desc"));//映射
                                thread_releases_table_date.setCellValueFactory(new PropertyValueFactory<>("releases_table_date"));//映射
                                thread_releases_tables.getItems().addAll(listobj);

                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                            thread_button.setDisable(false);
                        }
                    }
                }
            }
            catch (Exception e )
            {
//                Main.f_alert_informationDialog("查询结果","","未查询到数据！");
                thread_button.setDisable(false);
            }


//            if(list.size()==0)
//            {
//                Main.f_alert_informationDialog("Error","","未查询到数据!");
//
//            }
//            else
//            {
//
//            }


//            thread_releases_table.setItems(list); //tableview添加list


        }
        //查询结束
        thread_button.setDisable(false);
    }


}
