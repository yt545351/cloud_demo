package com.example.system.tool;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * 文件工具
 *
 * @author laughlook
 * @date 2022/08/12
 */
@Slf4j
public class FileTools {
    /**
     * 导出文件
     *
     * @param headList         负责人名单
     * @param dataList         数据列表
     * @param downloadFilePath 下载文件路径
     * @param fileName         文件名称
     * @param suffix           后缀
     * @param response         响应
     */
    public static void exportFile(List<Object> headList, List<List<Object>> dataList, String downloadFilePath, String fileName, String suffix, HttpServletResponse response) {
        FileTools.createFile(headList, dataList, downloadFilePath, fileName, suffix);
        String outFileName = fileName + "." + suffix;
        response.setHeader("Content-Disposition", "attachment;filename=" + outFileName);
        response.setContentType("application/csv;charset=UTF-8");
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(new File(downloadFilePath + "/" + outFileName));
            outputStream = response.getOutputStream();
            //此处解决csv文件,微软 excel打开乱码
            outputStream.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});

            int len;
            byte[] bytes = new byte[1024 * 8];
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * VUE接收
     */
//        if (res && res.data) {
//            let link = document.createElement('a')
//            link.href = window.URL.createObjectURL(new Blob([res.data]))
//            let nowTime = moment().format('YYYYMMDDHHmmss')
//            link.setAttribute('download', `文件名-${nowTime}.csv`)
//            link.click()
//            link.remove()
//            window.URL.revokeObjectURL(link.href)
//            this.$message.success('导出成功,请查看文件')
//        } else {
//            this.$message.error('导出失败')
//        }

    /**
     * 创建文件
     *
     * @param head       头
     * @param dataList   数据列表
     * @param outPutPath 输出路径
     * @param fileName   文件名称
     * @param suffix     后缀
     */
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

    /**
     * 写入行
     *
     * @param row 行
     * @param bw  bw
     * @throws IOException ioexception
     */
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
