package cn.abovesky.shopping.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/*******************************************************************************
 * 缩略图类（通用） 本java类能将jpg、bmp、png、gif图片文件，进行等比或非等比的大小转换。 具体使用方法
 * compressPic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true))
 */
public class CompressImage {
    private File file = null; // 文件对象
    private String input;
    private String output;
    private InputStream inputStream;
    private int outputWidth = 100; // 默认输出图片宽
    private int outputHeight = 100; // 默认输出图片高
    private boolean proportion = true; // 是否等比缩放标记(默认为等比缩放)
    public CompressImage() { // 初始化变量
        input = "";
        output = "";
        outputWidth = 100;
        outputHeight = 100;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setOutputWidth(int outputWidth) {
        this.outputWidth = outputWidth;
    }
    public void setOutputHeight(int outputHeight) {
        this.outputHeight = outputHeight;
    }
    public void setWidthAndHeight(int width, int height) {
        this.outputWidth = width;
        this.outputHeight = height;
    }

    /*
     * 获得图片大小
     * 传入参数 String path ：图片路径
     */
    public long getPicSize(String path) {
        file = new File(path);
        return file.length();
    }

    // 图片处理
    public boolean compressPic() {
        try {
            //获得源文件
            file = new File(input);
            if (!file.exists()) {
                return false;
            }
            Image img = ImageIO.read(file);
            // 判断图片格式是否正确
            if (img.getWidth(null) == -1) {
                return false;
            } else {
                int newWidth; int newHeight;
                // 判断是否是等比缩放
                if (this.proportion == true) {
                    // 为等比缩放计算输出的图片宽度及高度
                    double rate1 = ((double) img.getWidth(null)) / (double) outputWidth + 0.1;
                    double rate2 = ((double) img.getHeight(null)) / (double) outputHeight + 0.1;
                    // 根据缩放比率大的进行缩放控制
                    double rate = rate1 > rate2 ? rate1 : rate2;
                    newWidth = (int) (((double) img.getWidth(null)) / rate);
                    newHeight = (int) (((double) img.getHeight(null)) / rate);
                } else {
                    newWidth = outputWidth; // 输出的图片宽度
                    newHeight = outputHeight; // 输出的图片高度
                }
                BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);

			 	/*
				 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的
				 * 优先级比速度高 生成的图片质量比较好 但速度慢
				 */
                tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
                FileOutputStream out = new FileOutputStream(output);
                // JPEGImageEncoder可适用于其他图片类型的转换
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                encoder.encode(tag);
                out.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    // 图片处理
    public boolean compressPicWithInputStream() {
        try {
            Image img = ImageIO.read(inputStream);
            // 判断图片格式是否正确
            if (img.getWidth(null) == -1) {
                return false;
            } else {
                int newWidth; int newHeight;
                // 判断是否是等比缩放
                if (this.proportion == true) {
                    // 为等比缩放计算输出的图片宽度及高度
                    double rate1 = ((double) img.getWidth(null)) / (double) outputWidth + 0.1;
                    double rate2 = ((double) img.getHeight(null)) / (double) outputHeight + 0.1;
                    // 根据缩放比率大的进行缩放控制
                    double rate = rate1 > rate2 ? rate1 : rate2;
                    newWidth = (int) (((double) img.getWidth(null)) / rate);
                    newHeight = (int) (((double) img.getHeight(null)) / rate);
                } else {
                    newWidth = outputWidth; // 输出的图片宽度
                    newHeight = outputHeight; // 输出的图片高度
                }
                BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);

			 	/*
				 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的
				 * 优先级比速度高 生成的图片质量比较好 但速度慢
				 */
                tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
                FileOutputStream out = new FileOutputStream(output);
                // JPEGImageEncoder可适用于其他图片类型的转换
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                encoder.encode(tag);
                out.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public boolean compressPic(String input, String output, int width, int height, boolean gp) {
        // 输入图路径
        this.input = input;
        // 输出图路径
        this.output = output;
        // 设置图片长宽
        setWidthAndHeight(width, height);
        // 是否是等比缩放 标记
        this.proportion = gp;
        return compressPic();
    }

    public boolean compressPic(InputStream inputStream, String output, int width, int height, boolean gp) {
        // 输入图片流
        this.inputStream = inputStream;
        // 输出图路径
        this.output = output;
        // 设置图片长宽
        setWidthAndHeight(width, height);
        // 是否是等比缩放 标记
        this.proportion = gp;
        return compressPicWithInputStream();
    }
}
