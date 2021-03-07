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
import table.Table_Issus;
import table.Table_Releases;
import tools.Main;
import tools.httpclint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class Thread_Issus extends Thread  {

    //线程调用
    private Button thread_button;
    private String thread_date;
    private String[] thread_obj_list;


    private TableView<Table_Issus> thread_issus_tables;
    private TableColumn<Table_Issus, String> thread_issus_table_user;
    private TableColumn<Table_Issus, String> thread_issus_table_obj_name;
    private TableColumn<Table_Issus, String> thread_issus_table_obj_addrss;
    private TableColumn<Table_Issus, String> thread_issus_table_title;
    private TableColumn<Table_Issus, String> thread_issus_table_body;
    private TableColumn<Table_Issus, String> thread_issus_table_date;



    public void setValue(String date, String[] obj_list, Button go_button, TableView<Table_Issus> issus_tables, TableColumn<Table_Issus, String> issus_table_user, TableColumn<Table_Issus, String> issus_table_obj_name, TableColumn<Table_Issus, String> issus_table_obj_addrss, TableColumn<Table_Issus, String> issus_table_title, TableColumn<Table_Issus, String> issus_table_body, TableColumn<Table_Issus, String> issus_table_date) {
        this.thread_date = date;
        this.thread_obj_list = obj_list;
        this.thread_button = go_button;
        this.thread_issus_tables = issus_tables;

        this.thread_issus_table_user = issus_table_user;
        this.thread_issus_table_obj_name = issus_table_obj_name;
        this.thread_issus_table_obj_addrss = issus_table_obj_addrss;
        this.thread_issus_table_title =  issus_table_title;
        this.thread_issus_table_body =  issus_table_body;
        this.thread_issus_table_date  =  issus_table_date;

    }
    public void run() {
        thread_button.setDisable(true);
        for(String obj : thread_obj_list) {
            try{
                String url = Main.config_all_jsonobj.getJSONObject("api").getString("issus_api").replace("{user}/{project}",obj);
                JSONArray http_data = JSON.parseArray(httpclint.doGet(url));
    //            System.out.print(http_data);
                Iterator<Object> it = http_data.iterator();
                while (it.hasNext()) {
                    JSONObject ob = (JSONObject) it.next();
                    if(ob.getString("id")!=null){
                        try {
                            //更新日期
                            String updated_at = ob.getString("created_at");
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            Date chaxun_date = df.parse(thread_date);
    //                        System.out.print(chaxun_date.getTime());
                            String updated_date_str =updated_at.split("T")[0];
                            Date updated_date = df.parse(updated_date_str);

                            if(updated_date.getTime()>chaxun_date.getTime())
                            {
                                ObservableList<Table_Issus> listobj = FXCollections.observableArrayList();

                                //发布者
                                String author = ob.getJSONObject("user").getString("login");


                                //项目地址
                                String html_url = ob.getString("html_url");
                                //标题
                                String title = ob.getString("title");
                                //描述
                                String body = ob.getString("body");
                                //初始化为table添加值
                                Table_Issus valueobj = new Table_Issus();//构建值对象
                                valueobj.setIssus_table_user(author);
                                valueobj.setIssus_table_obj_name(obj);
                                valueobj.setIssus_table_obj_address(html_url);
                                valueobj.setIssus_table_title(title);
                                valueobj.setIssus_table_body(body);
                                valueobj.setIssus_table_date(updated_at);
                                listobj.add(valueobj);        //list添加值对象
                                thread_issus_table_user.setCellValueFactory(new PropertyValueFactory<>("Issus_table_user"));//映射
                                thread_issus_table_obj_name.setCellValueFactory(new PropertyValueFactory<>("Issus_table_obj_name"));//映射
                                thread_issus_table_obj_addrss.setCellValueFactory(new PropertyValueFactory<>("Issus_table_obj_address"));//映射
                                thread_issus_table_title.setCellValueFactory(new PropertyValueFactory<>("Issus_table_title"));//映射
                                thread_issus_table_body.setCellValueFactory(new PropertyValueFactory<>("Issus_table_body"));//映射
                                thread_issus_table_date.setCellValueFactory(new PropertyValueFactory<>("Issus_table_date"));//映射
                                thread_issus_tables.getItems().addAll(listobj);

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

        }
        //查询结束
        thread_button.setDisable(false);
    }


}
