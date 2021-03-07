package tools;

import com.alibaba.fastjson.JSONObject;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;

public class Main extends Application {

    public static JSONObject config_all_jsonobj;
    public static String Config_path="./conf.ini";


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/ui/main.fxml"));
        primaryStage.setTitle("Github信息查询工具 by 浅笑996 v1.0");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/img/ico.png")));
        primaryStage.setResizable(false);//窗口不可改变高度 宽度 这样就不用调节自适应了
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
//                    System.out.print("监听到窗口关闭");
                //退出窗口保存配置
                save_config_all_button();

            }
        });
    }
    //    弹出一个信息对话框
    public static void f_alert_informationDialog(String title,String p_header, String p_message){
        Alert _alert = new Alert(Alert.AlertType.INFORMATION);
        _alert.setTitle(title);
        _alert.setHeaderText(p_header);
        _alert.setContentText(p_message);
        _alert.show();
    }

    /**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     *
     * @param filePath
     */

    public static String readTxtFile(String filePath) {
//        System.out.print(filePath);
        String All_data = "";
        try {

            String encoding = "utf-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = "";

                while ((lineTxt = bufferedReader.readLine()) != null) {
                    All_data = All_data + lineTxt;
                }
                read.close();


            } else {
                f_alert_informationDialog("警告","","配置文件丢失！");
                System.exit(0);

            }

        } catch (Exception e) {
            f_alert_informationDialog("警告","","读取文件内容出错！");
            e.printStackTrace();
        }
        return All_data;
    }

    void save_config_all_button()
    {
        try {

            File writeName = new File(Main.Config_path); // 相对路径，如果没有则要建立一个新的output.txt文件
            FileWriter writer = new FileWriter(writeName);
            BufferedWriter out = new BufferedWriter(writer);
            out.write(String.valueOf(Main.config_all_jsonobj));
            out.flush(); // 把缓存区内容压入文件    out.close();
//            Main.f_alert_informationDialog("Success","","保存成功，重启后生效！");
            f_alert_informationDialog("退出","","全局配置文件保存成功，感谢使用，再见!");
        } catch (IOException e) {
            Main.f_alert_informationDialog("Error","",e.toString());
            e.printStackTrace();
        }


//        set_config_xff_cookies_ua_referer(all_config);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
