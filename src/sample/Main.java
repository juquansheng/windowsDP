package sample;

import com.csvreader.CsvWriter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.utils.CsvToNumUtil;
import sample.utils.FileChooserUtil;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Main extends Application {
    private final static String xls = "xls";
    private final static String xlsx = "xlsx";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        //launch(args);

        // 1. 创建一个顶层容器（窗口）
        JFrame jf = new JFrame("恬恬专用");          // 创建窗口
        jf.setSize(250, 250);                       // 设置窗口大小
        jf.setLocationRelativeTo(null);             // 把窗口位置设置到屏幕中心
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）
        jf.setLocationRelativeTo(null);

        GridLayout layout = new GridLayout(2, 1);

        // 设置 水平 和 竖直 间隙
        // layout.setHgap(10);
        // layout.setVgap(10);

        JPanel panel = new JPanel(layout);

        // 创建文本区域, 用于显示相关信息
        final JTextArea msgTextArea = new JTextArea(10, 30);
        msgTextArea.setLineWrap(true);
        panel.add(msgTextArea);


        JButton openBtn = new JButton("打开文件");
        openBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileChooserUtil.showFileOpenDialog(jf, msgTextArea);
            }
        });
        panel.add(openBtn);

        JButton saveBtn = new JButton("保存地址");
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileChooserUtil.showFileSaveDialog(jf, msgTextArea);
            }
        });
        panel.add(saveBtn);

        JButton exportBtn = new JButton("导出");
        exportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = msgTextArea.getText();
                String[] ss = text.split("保存到文件:");
                String filesString = ss[0];
                String file;
                if (ss.length == 1){
                    FileSystemView fsv = FileSystemView.getFileSystemView();
                    file = fsv.getHomeDirectory().toString();
                }else {
                    file = ss[1];
                }

                try {
                    exportExcel(filesString, file);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        panel.add(exportBtn);


        jf.setContentPane(panel);
        jf.setVisible(true);
    }


    /*
     * 导出文件
     */
    private static void exportExcel(String filesString, String file) throws IOException {

        String replace = file.replaceAll("\\\\", "/").trim();
        //处理获取到的文件
        // 创建CSV写对象
        CsvWriter csvWriter = new CsvWriter(replace);
        //CsvWriter csvWriter = new CsvWriter(file,',', Charset.forName("utf-8"));
        // 写表头
        String[] headers = {"V1(Ix1)","V2(Ix2)","V3(Iy1)","V4(Iy2)"};
        String[] content ;
        csvWriter.writeRecord(headers);

        String[] split = filesString.split("\\u000a");
        for (String s:split){
            try {
                List<Double> getresult = CsvToNumUtil.getresult(s);
                content = new String[]{getresult.get(0).toString(),getresult.get(1).toString(),getresult.get(2).toString(),getresult.get(3).toString()};
                csvWriter.writeRecord(content);
            }catch (Exception e){
                throw e;
            }
        }
        csvWriter.close();
    }

}
