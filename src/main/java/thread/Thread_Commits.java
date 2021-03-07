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
import table.Table_Issus;
import table.Table_Releases;
import tools.Main;
import tools.httpclint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class Thread_Commits extends Thread  {

    //线程调用
    private Button thread_button;
    private String thread_date;
    private String[] thread_obj_list;


    private TableView<Table_Commits> thread_Commits_tables;
    private TableColumn<Table_Commits, String> thread_Commits_table_user;
    private TableColumn<Table_Commits, String> thread_Commits_table_obj_name;
    private TableColumn<Table_Commits, String> thread_Commits_table_obj_addrss;
    private TableColumn<Table_Commits, String> thread_Commits_table_obj_desc;
    private TableColumn<Table_Commits, String> thread_Commits_table_date;


    public void run() {
        thread_button.setDisable(true);
        for(String obj : thread_obj_list) {
            try{


                String url = Main.config_all_jsonobj.getJSONObject("api").getString("commits_api").replace("{user}/{project}",obj);
                JSONArray http_data = JSON.parseArray(httpclint.doGet(url));
    //            System.out.print(http_data);
                Iterator<Object> it = http_data.iterator();
                while (it.hasNext()) {
                    JSONObject ob = (JSONObject) it.next();
                    if(ob.getString("node_id")!=null){
                        try {
                            //更新日期
                            String updated_at = ob.getJSONObject("commit").getJSONObject("committer").getString("date");
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            Date chaxun_date = df.parse(thread_date);
    //                        System.out.print(chaxun_date.getTime());
                            String updated_date_str =updated_at.split("T")[0];
                            Date updated_date = df.parse(updated_date_str);

                            if(updated_date.getTime()>chaxun_date.getTime())
                            {
                                ObservableList<Table_Commits> listobj = FXCollections.observableArrayList();

                                //提交人
                                String author = ob.getJSONObject("commit").getJSONObject("committer").getString("name");


                                //项目地址
                                String html_url = ob.getString("html_url");
                                                            //项目描述
                                String commit_description =ob.getJSONObject("commit").getString("message");
                                //初始化为table添加值
                                Table_Commits valueobj = new Table_Commits();//构建值对象
                                valueobj.setCommits_table_user(author);
                                valueobj.setCommits_table_obj_name(obj);
                                valueobj.setCommits_table_obj_addrss(html_url);
                                valueobj.setCommits_table_obj_desc(commit_description);
                                valueobj.setCommits_table_date(updated_at);
    //                            System.out.print(obj);
                                listobj.add(valueobj);        //list添加值对象
                                thread_Commits_table_user.setCellValueFactory(new PropertyValueFactory<>("Commits_table_user"));//映射
                                thread_Commits_table_obj_name.setCellValueFactory(new PropertyValueFactory<>("Commits_table_obj_name"));//映射
                                thread_Commits_table_obj_addrss.setCellValueFactory(new PropertyValueFactory<>("Commits_table_obj_addrss"));//映射
                                thread_Commits_table_obj_desc.setCellValueFactory(new PropertyValueFactory<>("Commits_table_obj_desc"));//映射
                                thread_Commits_table_date.setCellValueFactory(new PropertyValueFactory<>("Commits_table_date"));//映射
                                thread_Commits_tables.getItems().addAll(listobj);

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


        }
        //查询结束
        thread_button.setDisable(false);
    }


    public void setValue(String date, String[] obj_list, Button commits_go_button, TableView<Table_Commits> commit_table, TableColumn<Table_Commits, String> commit_table_user, TableColumn<Table_Commits, String> commit_table_obj_name, TableColumn<Table_Commits, String> commit_table_address, TableColumn<Table_Commits, String> commit_table_desc, TableColumn<Table_Commits, String> commit_table_date) {

        this.thread_date = date;
        this.thread_obj_list = obj_list;
        this.thread_button = commits_go_button;
        this.thread_Commits_tables = commit_table;
        this.thread_Commits_table_user = commit_table_user;
        this.thread_Commits_table_obj_name = commit_table_obj_name;
        this.thread_Commits_table_obj_addrss = commit_table_address;
        this.thread_Commits_table_obj_desc = commit_table_desc;
        this.thread_Commits_table_date  =  commit_table_date;
    }

}
