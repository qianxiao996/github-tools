package tools;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import com.alibaba.fastjson.JSON;
import table.*;
import thread.*;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.LocalDate;

public class Controller {



    //[Star代码开始]
    @FXML
    private ComboBox<String> star_com_user;
    @FXML
    private DatePicker star_data_text;

    @FXML
    private Button star_go_button;


    @FXML
    private TableView<Table_Star> star_table;
    @FXML
    private TableColumn<Table_Star,String>  star_table_user;
    @FXML
    private TableColumn<Table_Star,String>  star_table_obj_name;
    @FXML
    private TableColumn<Table_Star,String>  star_table_obj_address;
    @FXML
    private TableColumn<Table_Star,String>  star_table_obj_miaoshu;
    @FXML
    private TableColumn<Table_Star,String>  star_table_date;

//    [Star代码结束]

//[releases代码开始]
    @FXML
    private ComboBox<String> releases_com_obj;

    @FXML
    private DatePicker releases_data_text;

    @FXML
    private Button releases_go_button;


    @FXML
    private TableView<Table_Releases> releases_table;
    @FXML
    private TableColumn<Table_Releases, String> releases_table_user;

    @FXML
    private TableColumn<Table_Releases, String> releases_table_obj_name;

    @FXML
    private TableColumn<Table_Releases, String> releases_table_obj_addrss;

    @FXML
    private TableColumn<Table_Releases, String> releases_table_version;

    @FXML
    private TableColumn<Table_Releases, String> releases_table_version_name;

    @FXML
    private TableColumn<Table_Releases, String> releases_table_version_desc;

    @FXML
    private TableColumn<Table_Releases, String> releases_table_date;
//[releases代码结束]


//[commits代码开始]

    @FXML
    private DatePicker commits_date;

    @FXML
    private Button commits_go_button;

    @FXML
    private ComboBox<String> commit_name_obj;

    @FXML
    private TableView<Table_Commits> commit_table;

    @FXML
    private TableColumn<Table_Commits, String> commit_table_user;

    @FXML
    private TableColumn<Table_Commits, String> commit_table_obj_name;

    @FXML
    private TableColumn<Table_Commits, String> commit_table_address;

    @FXML
    private TableColumn<Table_Commits, String> commit_table_desc;

    @FXML
    private TableColumn<Table_Commits, String> commit_table_date;




//[commits代码结束]
//[issus代码开始]
    @FXML
    private DatePicker issus_date;

    @FXML
    private Button issus_go_button;




    @FXML
    private ComboBox<String> issus_name_obj;

    @FXML
    private TableView<Table_Issus> issus_table;
    @FXML
    private TableColumn<Table_Issus, String> issus_table_user;

    @FXML
    private TableColumn<Table_Issus, String> issus_table_obj_name;


    @FXML
    private TableColumn<Table_Issus, String> issus_table_obj_address;

    @FXML
    private TableColumn<Table_Issus, String> issus_table_title;

    @FXML
    private TableColumn<Table_Issus, String> issus_table_body;

    @FXML
    private TableColumn<Table_Issus, String> issus_table_date;



//[issus代码结束]
//[update代码开始]

    @FXML
    private DatePicker update_date;

    @FXML
    private Button update_go_button;

    @FXML
    private ComboBox<String> update_name_obj;

    @FXML
    private TableView<Table_Update> update_table;

    @FXML
    private TableColumn<Table_Update,String> update_table_user;

    @FXML
    private TableColumn<Table_Update,String> update_table_obj_value;


    @FXML
    private TableColumn<Table_Update,String> update_table_obj_name;

    @FXML
    private TableColumn<Table_Update,String> update_table_obj_address;
    @FXML
    private TableColumn<Table_Update,String> update_table_obj_desc;

    @FXML
    private TableColumn<Table_Update,String> update_table_date;





//[update代码结束]
    @FXML
    public void initialize() throws ParseException {
        load_config();
        set_star_conf();
        set_releases_conf();
        set_commits_conf();
        set_issus_conf();
        set_update_conf();
        add_tableclunmn_double_click();
        star_table.setPlaceholder(new Label(""));
        releases_table.setPlaceholder(new Label(""));
        commit_table.setPlaceholder(new Label(""));
        issus_table.setPlaceholder(new Label(""));
        update_table.setPlaceholder(new Label(""));


    }

    private void add_tableclunmn_double_click() {
        star_table.setRowFactory( tv -> {
            TableRow<Table_Star> row = new TableRow<Table_Star>();
            row.setOnMouseClicked(event -> {
                //表格双击事件,打开浏览器.
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Table_Star emailInfo = row.getItem();
//                    System.out.println(emailInfo.getStar_obj_address());
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        desktop.browse(new URI(emailInfo.getStar_obj_address()));
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }


                }
            });
            return row ;
        });
        releases_table.setRowFactory( tv -> {
            TableRow<Table_Releases> row = new TableRow<Table_Releases>();
            row.setOnMouseClicked(event -> {
                //表格双击事件,打开浏览器.
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Table_Releases emailInfo = row.getItem();
//                    System.out.println(emailInfo.getStar_obj_address());
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        desktop.browse(new URI(emailInfo.getReleases_table_obj_addrss()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }


                }
            });
            return row ;
        });
        commit_table.setRowFactory( tv -> {
            TableRow<Table_Commits> row = new TableRow<Table_Commits>();
            row.setOnMouseClicked(event -> {
                //表格双击事件,打开浏览器.
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Table_Commits emailInfo = row.getItem();
//                    System.out.println(emailInfo.getStar_obj_address());
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        desktop.browse(new URI(emailInfo.getCommits_table_obj_addrss()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }


                }
            });
            return row ;
        });
        issus_table.setRowFactory( tv -> {
            TableRow<Table_Issus> row = new TableRow<Table_Issus>();
            row.setOnMouseClicked(event -> {
                //表格双击事件,打开浏览器.
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Table_Issus emailInfo = row.getItem();
//                    System.out.println(emailInfo.getStar_obj_address());
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        desktop.browse(new URI(emailInfo.getIssus_table_obj_address()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }


                }
            });
            return row ;
        });
        update_table.setRowFactory( tv -> {
            TableRow<Table_Update> row = new TableRow<Table_Update>();
            row.setOnMouseClicked(event -> {
                //表格双击事件,打开浏览器.
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Table_Update emailInfo = row.getItem();
//                    System.out.println(emailInfo.getStar_obj_address());
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        desktop.browse(new URI(emailInfo.getUpdate_table_obj_addrss()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }


                }
            });
            return row ;
        });
    }

    //
    @FXML
    void edit_conf(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/ui/conf.fxml"));
//            System.out.println("loaded!");
            Scene scene = new Scene(root);
            stage.setTitle("配置");
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/img/ico.png")));
            stage.setResizable(false);//窗口不可改变高度 宽度 这样就不用调节自适应了
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
//                    System.out.print("监听到窗口关闭");
                    //退出窗口保存配置
                    set_star_conf();
                    set_commits_conf();
                    set_issus_conf();
                    set_update_conf();
                    set_releases_conf();

                }
            });
        } catch (Exception e) {
            System.out.print(e.toString());
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
//读取全局文件
    void load_config() throws ParseException {
        String data = Main.readTxtFile(Main.Config_path);
        Main.config_all_jsonobj = JSON.parseObject(data);//数据库json对象
        //设置日期初始值
        LocalDate now_Date = LocalDate.now(); //取7天前的日期
        star_data_text.setValue(now_Date.plusDays(-7));
        releases_data_text.setValue(now_Date.plusDays(-30));
        commits_date.setValue(now_Date.plusDays(-30));
        issus_date.setValue(now_Date.plusDays(-30));
        update_date.setValue(now_Date.plusDays(-7));

        star_table.setTableMenuButtonVisible(true);
        commit_table.setTableMenuButtonVisible(true);
        issus_table.setTableMenuButtonVisible(true);
        update_table.setTableMenuButtonVisible(true);
        releases_table.setTableMenuButtonVisible(true);
//        System.out.print(1);

    }
    //设置star用户
    void set_star_conf()
    {
        star_com_user.getItems().clear();
        star_com_user.getItems().addAll("所有用户");
        star_com_user.getSelectionModel().selectFirst();//选中第一个

        String  set_star_user = Main.config_all_jsonobj.getString("star_data");

        String[] user_list = set_star_user.split(",");

        for(String user : user_list) {
            if(!user.equals(""))
            {
                star_com_user.getItems().addAll(user);
            }
//            System.out.print(user);
        }
    }
    //star查询数据
    @FXML
    void star_go(ActionEvent event) throws ParseException {
        String user =  star_com_user.getValue();
        String date  = star_data_text.getValue().toString();
//        System.out.print(date);
        String[] user_list;
        if(user.equals("所有用户"))
        {

            //获取所有用户的数据
            String  all_user = Main.config_all_jsonobj.getString("star_data");
            user_list = all_user.split(",");

        }
        else
        {
            //获取某个用户的数据
            user_list = new String[]{user};
        }
        Thread_Star myThread = new Thread_Star();
        myThread.setValue(date,user_list,star_go_button,star_table,star_table_user,star_table_obj_name,star_table_obj_address,star_table_obj_miaoshu,star_table_date);
        Thread thread = new Thread(myThread);
        thread.start();

    }

    //设置releases项目
    void set_releases_conf()
    {
        releases_com_obj.getItems().clear();
        releases_com_obj.getItems().addAll("所有项目");
        releases_com_obj.getSelectionModel().selectFirst();//选中第一个

        String  set_star_user = Main.config_all_jsonobj.getString("releases_data");

        String[] user_list = set_star_user.split(",");

        for(String user : user_list) {
            if(!user.equals(""))
            {
                releases_com_obj.getItems().addAll(user);
            }
//            System.out.print(user);
        }
    }

    @FXML
    void releases_go(ActionEvent event) {
        String obj =  releases_com_obj.getValue();
        String date  = releases_data_text.getValue().toString();
//        System.out.print(date);
        String[] obj_list;
        if(obj.equals("所有项目"))
        {
            //获取所有用户的数据
            String  all_obj = Main.config_all_jsonobj.getString("releases_data");
            obj_list = all_obj.split(",");
        }
        else
        {
            //获取某个用户的数据
            obj_list = new String[]{obj};

        }
        Thread_Releases myThread = new Thread_Releases();
        myThread.setValue(date,obj_list,releases_go_button,releases_table,releases_table_user,releases_table_obj_name,releases_table_obj_addrss,releases_table_version,releases_table_version_name,releases_table_version_desc,releases_table_date);
        Thread thread = new Thread(myThread);
        thread.start();

    }



    //设置commits项目
    void set_commits_conf()
    {
        commit_name_obj.getItems().clear();
        commit_name_obj.getItems().addAll("所有项目");
        commit_name_obj.getSelectionModel().selectFirst();//选中第一个

        String  set_star_user = Main.config_all_jsonobj.getString("commits_data");

        String[] user_list = set_star_user.split(",");

        for(String user : user_list) {
            if(!user.equals(""))
            {
                commit_name_obj.getItems().addAll(user);
            }
//            System.out.print(user);
        }
    }
    @FXML
    void commits_go(ActionEvent event) {
        String obj =  commit_name_obj.getValue();
        String date  = commits_date.getValue().toString();
//        System.out.print(date);
        String[] obj_list;
        if(obj.equals("所有项目"))
        {
            //获取所有用户的数据
            String  all_obj = Main.config_all_jsonobj.getString("commits_data");
            obj_list = all_obj.split(",");
        }
        else
        {
            //获取某个用户的数据
            obj_list = new String[]{obj};

        }
        Thread_Commits myThread = new Thread_Commits();
        myThread.setValue(date,obj_list,commits_go_button,commit_table,commit_table_user,commit_table_obj_name,commit_table_address,commit_table_desc,commit_table_date);
        Thread thread = new Thread(myThread);
        thread.start();

    }

    //设置issus项目
    void set_issus_conf()
    {
        issus_name_obj.getItems().clear();
        issus_name_obj.getItems().addAll("所有项目");
        issus_name_obj.getSelectionModel().selectFirst();//选中第一个

        String  set_star_user = Main.config_all_jsonobj.getString("commits_data");

        String[] user_list = set_star_user.split(",");

        for(String user : user_list) {
            if(!user.equals(""))
            {
                issus_name_obj.getItems().addAll(user);
            }
//            System.out.print(user);
        }

    }
    @FXML
    void issus_go(ActionEvent event) {
        String obj =  issus_name_obj.getValue();
        String date  = issus_date.getValue().toString();
//        System.out.print(date);
        String[] obj_list;
        if(obj.equals("所有项目"))
        {
            //获取所有用户的数据
            String  all_obj = Main.config_all_jsonobj.getString("issus_data");
            obj_list = all_obj.split(",");
        }
        else
        {
            //获取某个用户的数据
            obj_list = new String[]{obj};

        }
        Thread_Issus myThread = new Thread_Issus();
        myThread.setValue(date,obj_list, issus_go_button,issus_table,issus_table_user,issus_table_obj_name,issus_table_obj_address,issus_table_title,issus_table_body,issus_table_date);
        Thread thread = new Thread(myThread);
        thread.start();

    }

    //设置update项目
    void set_update_conf()
    {
        update_name_obj.getItems().clear();
        update_name_obj.getItems().addAll("所有项目");
        update_name_obj.getSelectionModel().selectFirst();//选中第一个

        String  set_star_user = Main.config_all_jsonobj.getString("update_data");

        String[] user_list = set_star_user.split(",");

        for(String user : user_list) {
            if(!user.equals(""))
            {
                update_name_obj.getItems().addAll(user);
            }
//            System.out.print(user);
        }
    }


    public void update_go(ActionEvent actionEvent) {
        String obj =  update_name_obj.getValue();
        String date  = update_date.getValue().toString();
//        System.out.print(date);
        String[] obj_list;
        if(obj.equals("所有项目"))
        {
            //获取所有用户的数据
            String  all_obj = Main.config_all_jsonobj.getString("update_data");
            obj_list = all_obj.split(",");
        }
        else
        {
            //获取某个用户的数据
            obj_list = new String[]{obj};

        }
        Thread_Update myThread = new Thread_Update();
        myThread.setValue(date,obj_list, update_go_button, update_table,update_table_user,update_table_obj_value,update_table_obj_name,update_table_obj_address,update_table_obj_desc,update_table_date);
        Thread thread = new Thread(myThread);
        thread.start();


    }

    @FXML
    void about_go(ActionEvent event) {
        Main.f_alert_informationDialog("About","关于","这是一个关于信息哈哈哈哈! 我也不知道写什么.");

    }
    @FXML
    void app_update_go(ActionEvent event) {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI("https://baidu.com"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void author_go(ActionEvent event) {
        Main.f_alert_informationDialog("作者","","作者:浅笑996\n邮箱:qianxiao996@126.com");

    }


}

