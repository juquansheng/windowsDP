package sample.utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

public class FileChooserUtil {

    /*
     * 打开文件
     */
    public static void showFileOpenDialog(Component parent, JTextArea msgTextArea) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        //设置当前路径为桌面路径,否则将我的文档作为默认路径
        /*Preferences pref = Preferences.userRoot().node("/com/registerFile");
        String lastPath = pref.get("lastPath", "");
        if (!lastPath.equals(""))
            fileChooser = new JFileChooser(lastPath);
        else
            fileChooser = new JFileChooser();*/


        FileSystemView fsv = FileSystemView.getFileSystemView();
        fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
        // 设置默认显示的文件夹为当前文件夹
        //fileChooser.setCurrentDirectory(new File("."));

        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(true);

        // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("excel(*.xls, *.xlsx)", "xls", "xlsx"));
        // 设置默认使用的文件过滤器
        fileChooser.setFileFilter(new FileNameExtensionFilter("excel1(*.csv)", "csv"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showDialog(parent,"选择文件");

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径
            //File file = fileChooser.getSelectedFile();

            // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
            File[] files = fileChooser.getSelectedFiles();
            String str="";
            for (File file : files) {

                if(file.isDirectory())
                    str=file.getPath();
                else{
                    str=file.getPath();
                }
                msgTextArea.append(str+"\n");

            }

            //msgTextArea.append("打开文件: " + file.getAbsolutePath() + "\n\n");
        }

    }

    /*
     * 选择文件保存路径
     */
   public static void showFileSaveDialog(Component parent, JTextArea msgTextArea) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置打开文件选择框后默认输入的文件名
        fileChooser.setSelectedFile(new File("恬恬专用.csv"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showSaveDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"保存", 则获取选择的保存路径
            File file = fileChooser.getSelectedFile();
            msgTextArea.append("保存到文件:" + file.getAbsolutePath() + "\n\n");
        }
    }
}
