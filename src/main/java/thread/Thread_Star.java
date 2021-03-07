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
import tools.Main;
import table.Table_Star;
import tools.httpclint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class Thread_Star extends Thread  {

    //线程调用
    private Button thread_button;
    private String thread_date;
    private TableView<Table_Star> thread_star_table;
    private TableColumn<Table_Star,String>  thread_star_table_user;
    private TableColumn<Table_Star,String>  thread_star_table_obj_name;
    private TableColumn<Table_Star,String>  thread_star_table_obj_address;
    private TableColumn<Table_Star,String>  thread_star_table_obj_miaoshu;
    private TableColumn<Table_Star,String>  thread_star_table_date;
    private String[] thread_user_list;

    public void setValue(String date, String[] user_list, Button star_go_button, TableView<Table_Star> star_table, TableColumn<Table_Star, String> star_table_user, TableColumn<Table_Star, String> star_table_obj_name, TableColumn<Table_Star, String> star_table_obj_address, TableColumn<Table_Star, String> star_table_obj_miaoshu, TableColumn<Table_Star, String> star_table_date) {
        this.thread_date = date;
        this.thread_user_list = user_list;
        this.thread_button =star_go_button;
        this.thread_star_table = star_table;
        this.thread_star_table_user= star_table_user;
        this.thread_star_table_obj_name=star_table_obj_name;
        this.thread_star_table_obj_address=star_table_obj_address;
        this.thread_star_table_obj_miaoshu = star_table_obj_miaoshu;
        this.thread_star_table_date = star_table_date;
    }
    public void run() {
        thread_button.setDisable(true);
        try{
            for(String user : thread_user_list) {
                try{
                    String url = Main.config_all_jsonobj.getJSONObject("api").getString("star_api").replace("{user}",user);
                    JSONArray http_data = JSON.parseArray(httpclint.doGet(url));
        //            System.out.print(http_data);
                    Iterator<Object> it = http_data.iterator();
                    while (it.hasNext()) {
                        JSONObject ob = (JSONObject) it.next();
                        if(ob.getString("id")!=null){
                            try {
                                //更新日期
                                String updated_at = ob.getString("updated_at");
                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                Date chaxun_date = df.parse(thread_date);
        //                        System.out.print(chaxun_date.getTime());
                                String updated_date_str =updated_at.split("T")[0];
                                Date updated_date = df.parse(updated_date_str);

                                if(updated_date.getTime()>chaxun_date.getTime())
                                {
                                    ObservableList<Table_Star> list = FXCollections.observableArrayList();
                                    //项目名称
                                    String full_name = ob.getString("full_name");
                                    //项目地址
                                    String html_url = ob.getString("html_url");
                                    //项目描述
                                    String description = ob.getString("description");
                                    //初始化为table添加值
                                    Table_Star valueobj = new Table_Star();//构建值对象
                                    valueobj.setStar_user(user);
                                    valueobj.setStar_obj_name(full_name);
                                    valueobj.setStar_obj_address(html_url);
                                    valueobj.setStar_obj_miaoshu(description);
                                    valueobj.setStar_date(updated_at);
                                    list.add(valueobj);        //list添加值对象
                                    thread_star_table_user.setCellValueFactory(new PropertyValueFactory<>("star_user"));//映射
                                    thread_star_table_obj_name.setCellValueFactory(new PropertyValueFactory<>("star_obj_name"));//映射
                                    thread_star_table_obj_address.setCellValueFactory(new PropertyValueFactory<>("star_obj_address"));//映射
                                    thread_star_table_obj_miaoshu.setCellValueFactory(new PropertyValueFactory<>("star_obj_miaoshu"));//映射
                                    thread_star_table_date.setCellValueFactory(new PropertyValueFactory<>("star_date"));//映射
                                    thread_star_table.getItems().addAll(list);

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


    //            thread_star_table.setItems(list); //tableview添加list


            }
        } catch (Exception e) {
            e.printStackTrace();
            Main.f_alert_informationDialog("Error","",e.toString());
        }
        //查询结束
        thread_button.setDisable(false);
    }


}
