package com.example.system.utilsOth;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 导出文件，vue下载
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Slf4j
public class DownloadVue {
    public static final String downloadFilePath = "E:\\data";

    public static void main(String[] args) {
        //表头
        List<Object> head = new ArrayList<>();
        head.add("姓名");
        head.add("年龄");
        List<List<Object>> dataList = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            List<Object> list = new ArrayList<>();
            list.add("张" + i);
            list.add(i);
            dataList.add(list);
        }

        String fileName = "学生信息" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        createFile(head, dataList, downloadFilePath, fileName, "xls");
        String outFileName = fileName + "." + "xls";
//        response.setHeader("Content-Disposition", "attachment;filename=" + outFileName);
//        response.setContentType("application/csv;charset=UTF-8");
//        try (InputStream in = new FileInputStream(new File(downloadFilePath + "/" + outFileName));
//             OutputStream outputStream = response.getOutputStream()) {
//            // add BOM 此处解决csv文件,微软 excel打开乱码
//            outputStream.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
//            int len;
//            byte[] bytes = new byte[1024 * 8];
//            while ((len = in.read(bytes)) != -1) {
//                outputStream.write(bytes, 0, len);
//            }
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        }


        /**
         * VUE接收
         */
//        if (res && res.data) {
//            let link = document.createElement('a')
//            link.href = window.URL.createObjectURL(new Blob([res.data]))
//            let nowTime = moment().format('YYYY-MM-DD HH:mm:ss').replace(/\s|-|:/g, '')
//            link.setAttribute('download', `报表详情-${nowTime}.${params.suffix}`)
//            link.click()
//            link.remove()
//            window.URL.revokeObjectURL(link.href)
//            this.$message.success('导出成功,请查看文件')
//        } else {
//            this.$message.error('导出失败')
//        }
    }

    //创建文件
    public static void createFile(List<Object> head, List<List<Object>> dataList, String outPutPath, String fileName, String suffix) {
        File file = null;
        BufferedWriter bw = null;
        try {
            file = new File(outPutPath + File.separator + fileName + "." + suffix);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            file.createNewFile();

            // GB2312使正确读取分隔符","
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"), 1024);
            // 写入文件头部
            writeRow(head, bw);
            // 写入文件内容
            for (List<Object> row : dataList) {
                writeRow(row, bw);
            }
            bw.flush();
        } catch (Exception e) {
            log.error("异常", e);
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                log.error("异常", e);
            }
        }
    }

    //写入行记录
    public static void writeRow(List<Object> row, BufferedWriter bw) throws IOException {
        for (Object data : row) {
            StringBuffer sb = new StringBuffer();
            if (data == null) {
                data = "";
            }
            String rowStr = sb.append("\"").append(data).append("\",").toString();
            bw.write(rowStr);
        }
        bw.newLine();
    }
}
