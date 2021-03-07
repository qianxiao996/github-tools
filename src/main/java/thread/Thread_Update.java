package thread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import table.Table_Commits;
import table.Table_Update;
import tools.Main;
import tools.httpclint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class Thread_Update extends Thread  {

    //线程调用
    private Button thread_button;
    private String thread_date;
    private String[] thread_obj_list;



    private TableView<Table_Update> thread_Update_table;
    private TableColumn<Table_Update, String> thread_Update_table_user;
    private TableColumn<Table_Update, String> thread_Update_table_obj_value;//关键字
    private TableColumn<Table_Update, String> thread_Update_table_obj_name;
    private TableColumn<Table_Update, String> thread_Update_table_obj_address;
    private TableColumn<Table_Update, String> thread_Update_table_obj_desc;
    private TableColumn<Table_Update, String> thread_Update_table_date;



    public void run() {
        thread_button.setDisable(true);
        try {
            for(String obj : thread_obj_list) {
                try{
                    String url = Main.config_all_jsonobj.getJSONObject("api").getString("update_api").replace("{keyword}",obj);
    //                System.out.print(url);
                    JSONArray http_data = JSON.parseObject(httpclint.doGet(url)).getJSONArray("items");
        //            System.out.print(http_data);
                    Iterator<Object> it = http_data.iterator();
                    while (it.hasNext()) {
                        JSONObject ob = (JSONObject) it.next();
                        if(ob.getString("node_id")!=null){

                            String updated_at = ob.getString("created_at");
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            Date chaxun_date = null;
                            try {
                                chaxun_date = df.parse(thread_date);

        //                        System.out.print(chaxun_date.getTime());
                                String updated_date_str =updated_at.split("T")[0];
                                Date updated_date = df.parse(updated_date_str);

                                if(updated_date.getTime()>chaxun_date.getTime()) {


                                    ObservableList<Table_Update> listobj = FXCollections.observableArrayList();

                                    //提交人
                                    String author = ob.getJSONObject("owner").getString("login");

                                    //项目名称
                                    String full_name = ob.getString("full_name");

                                    //项目地址
                                    String html_url = ob.getString("html_url");
                                    //项目描述
                                    String commit_description = ob.getString("description");

                                    //初始化为table添加值
                                    Table_Update valueobj = new Table_Update();//构建值对象
                                    valueobj.setUpdate_table_user(author);
                                    valueobj.setUpdate_table_obj_value(obj);
                                    valueobj.setUpdate_table_obj_name(full_name);
                                    valueobj.setUpdate_table_obj_addrss(html_url);
                                    valueobj.setUpdate_table_obj_desc(commit_description);
                                    valueobj.setUpdate_table_date(updated_at);
                                    //                            System.out.print(obj);
                                    listobj.add(valueobj);        //list添加值对象
                                    thread_Update_table_user.setCellValueFactory(new PropertyValueFactory<>("Update_table_user"));//映射
                                    thread_Update_table_obj_value.setCellValueFactory(new PropertyValueFactory<>("Update_table_obj_value"));//映射
                                    thread_Update_table_obj_name.setCellValueFactory(new PropertyValueFactory<>("Update_table_obj_name"));//映射
                                    thread_Update_table_obj_address.setCellValueFactory(new PropertyValueFactory<>("Update_table_obj_addrss"));//映射
                                    thread_Update_table_obj_desc.setCellValueFactory(new PropertyValueFactory<>("Update_table_obj_desc"));//映射
                                    thread_Update_table_date.setCellValueFactory(new PropertyValueFactory<>("Update_table_date"));//映射
                                    thread_Update_table.getItems().addAll(listobj);

                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                                //查询结束
                                thread_button.setDisable(false);
                            }


                        }
                    }
                }
                catch (Exception e )
                {
                    thread_button.setDisable(false);
                }

            }
        } catch (Exception e) {
            thread_button.setDisable(false);
            e.printStackTrace();
            Main.f_alert_informationDialog("Error","",e.toString());
        }
        //查询结束
        thread_button.setDisable(false);
    }




    public void setValue(String date, String[] obj_list, Button update_go_button, TableView<Table_Update> update_table, TableColumn<Table_Update, String> update_table_user, TableColumn<Table_Update, String> update_table_obj_value,TableColumn<Table_Update, String> update_table_obj_name, TableColumn<Table_Update, String> update_table_obj_address, TableColumn<Table_Update, String> update_table_obj_desc,TableColumn<Table_Update, String> update_table_date) {

        this.thread_date = date;
        this.thread_obj_list = obj_list;
        this.thread_button = update_go_button;
        this.thread_Update_table = update_table;
        this.thread_Update_table_user = update_table_user;
        this.thread_Update_table_obj_value =  update_table_obj_value;
        this.thread_Update_table_obj_name = update_table_obj_name;
        this.thread_Update_table_obj_address = update_table_obj_address;
        this.thread_Update_table_obj_desc = update_table_obj_desc;
        this.thread_Update_table_date = update_table_date;

    }
}
