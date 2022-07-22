package com.example.system.utils.ocr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.jdesktop.swingx.util.OS;

/**
 * ocrUtil
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Slf4j
public class OCRUtil {
    /**
     * 英文字母小写l，并非数字1
     */
    private final String LANG_OPTION = "-l";
    /**
     * EOL
     */
    private final String EOL = System.getProperty("line.separator");
    /**
     * ocr默认安装路径
     */
    private final String tessPath = "E:\\gitee\\tesseract-ocr\\Tesseract-OCR";
    /**
     * 默认中文语言包，识别中文
     */
    private final String transname = "chi_sim";

    /**
     * 识别文本
     * 从图片中识别文字
     *
     * @param imageFile 图像文件
     * @return text recognized in image
     * @throws Exception 异常
     */
    public String recognizeText(File imageFile) throws IOException, InterruptedException {
        File tempImage = new ImageIOHelper().createImage(imageFile);
        return ocrImages(tempImage, imageFile);
    }

    /**
     * ocr图像
     * 识别图片中的文字
     *
     * @param tempImage 临时形象
     * @param imageFile 图像文件
     * @return {@link String}
     * @throws IOException          ioexception
     * @throws InterruptedException 中断异常
     */
    private String ocrImages(File tempImage, File imageFile) throws IOException, InterruptedException {
        File outputFile = new File(imageFile.getParentFile(), "output");
        Runtime.getRuntime().exec("attrib " + "\"" + outputFile.getAbsolutePath() + "\"" + " +H"); // 设置文件隐藏
        StringBuffer strB = new StringBuffer();
        List<String> cmd = new ArrayList<String>();
        if (OS.isWindowsXP()) {
            cmd.add(tessPath + "//tesseract");
        } else if (OS.isLinux()) {
            cmd.add("tesseract");
        } else {
            cmd.add(tessPath + "//tesseract");
        }
        cmd.add("");
        cmd.add(outputFile.getName());
        cmd.add(LANG_OPTION);
        cmd.add(transname);

        ProcessBuilder pb = new ProcessBuilder();
        Map<String, String> env = pb.environment();
        env.put("TESSDATA_PREFIX", "E:\\gitee\\tesseract-ocr\\Tesseract-OCR\\tessdata");
        pb.directory(imageFile.getParentFile());
        cmd.set(1, tempImage.getName());
        pb.command(cmd);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        int w = process.waitFor();

        tempImage.delete();// 删除临时正在工作文件
        if (w == 0) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(new FileInputStream(outputFile.getAbsolutePath() + ".txt"), "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                strB.append(str).append(EOL);
            }
            in.close();
        } else {
            String msg;
            switch (w) {
                case 1:
                    msg = "Errors accessing files.There may be spaces in your image's filename.";
                    break;
                case 29:
                    msg = "Cannot recongnize the image or its selected region.";
                    break;
                case 31:
                    msg = "Unsupported image format.";
                    break;
                default:
                    msg = "Errors occurred.";
            }
            tempImage.delete();
            throw new RuntimeException(msg);
        }
        new File(outputFile.getAbsolutePath() + ".txt").delete();
        return strB.toString();
    }

    public static void main(String[] args) throws Exception {
        log.info("begin");
        String path = "C:\\Users\\laughlook\\Desktop\\1.jpg";
        String valCode = new OCRUtil().recognizeText(new File(path));
        log.info("{}", valCode);
        log.info("end");
    }

}
