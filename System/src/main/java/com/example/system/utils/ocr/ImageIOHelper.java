package com.example.system.utils.ocr;

import com.github.jaiimageio.plugins.tiff.TIFFImageWriteParam;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;

/**
 * 图像IO Helper
 *
 * @author laughlook
 * @date 2022/07/21
 */
public class ImageIOHelper {
    public File createImage(File imageFile) throws IOException {
        Iterator<ImageReader> readers = ImageIO.getImageReaders(new FileImageInputStream(imageFile));
        ImageReader reader = readers.next();
        ImageInputStream iis = ImageIO.createImageInputStream(imageFile);
        reader.setInput(iis);

        IIOMetadata streamMetadata = reader.getStreamMetadata();
        TIFFImageWriteParam tiffWriteParam = new TIFFImageWriteParam(Locale.CHINESE);
        tiffWriteParam.setCompressionMode(ImageWriteParam.MODE_DISABLED);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("tiff");
        ImageWriter writer = writers.next();
        BufferedImage bi = reader.read(0);

        IIOImage image = new IIOImage(bi, null, reader.getImageMetadata(0));
        File tempFile = tempImageFile(imageFile);
        ImageOutputStream ios = ImageIO.createImageOutputStream(tempFile);
        writer.setOutput(ios);
        writer.write(streamMetadata, image, tiffWriteParam);

        ios.close();
        iis.close();
        writer.dispose();
        reader.dispose();
        return tempFile;
    }

    public File tempImageFile(File imageFile) throws IOException {
        String path = imageFile.getPath();
        StringBuilder strB = new StringBuilder(path);
        strB.insert(path.lastIndexOf('.'), "_text_recognize_temp");
        String s = strB.toString().replaceFirst("(?<=//.)(//w+)$", "tif");
        // 设置文件隐藏
        Runtime.getRuntime().exec("attrib " + "\"" + s + "\"" + " +H");
        return new File(strB.toString());
    }
}
