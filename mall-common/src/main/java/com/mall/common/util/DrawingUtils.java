package com.mall.common.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * 图片合并
 *
 * @version 2020-2-17 上午11:12:09
 */
public class DrawingUtils {

    private static final int TEXT_HEIGHT = 80;
    private static final int CZG_HEIGHT = 130;
    private static final int QR_CODE_HEIGHT = 680;
    private static final int FONT_SIZE = 28;
    private static final String FONT_STYLE = "微软雅黑";
    private static Graphics2D graphics2d = null;

    /**
     * 导入URL图片到缓冲区
     */
    public static BufferedImage loadImageURL(String imgURL) {
        try {
            URL url = new URL(imgURL);
            return ImageIO.read(url);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 导入本地图片到缓冲区
     */
    public static BufferedImage loadImageLocal(String imgPath) {
        try {
            return ImageIO.read(new File(imgPath));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 合并图片
     */
    public static BufferedImage modifyImagetogeter(BufferedImage baseImg, BufferedImage CZGImg, BufferedImage QRCodeImg, String shopName, String productName, double price) {

        try {
            int baseImgWidth = baseImg.getWidth();
            int leftWidth = (baseImgWidth - CZGImg.getWidth()) / 2;
            graphics2d = baseImg.createGraphics();
            // 画入主图
            graphics2d.drawImage(CZGImg, leftWidth, CZG_HEIGHT, null);
            int rightWidth = baseImgWidth - (QRCodeImg.getWidth() + leftWidth);
            // 画入二维码
            graphics2d.drawImage(QRCodeImg, rightWidth, QR_CODE_HEIGHT, null);
            // 设置字体
            Font font = new Font(FONT_STYLE, Font.PLAIN, FONT_SIZE);
            graphics2d.setFont(font);
            // 抗锯齿
            graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 计算文字长度，计算居中的x点坐标
            FontMetrics fm = graphics2d.getFontMetrics(font);
            int centerWidth = (baseImgWidth - fm.stringWidth(shopName)) / 2;
            // 黑色画笔
            graphics2d.setColor(Color.black);
            // 写入文字。
            graphics2d.drawString(shopName, centerWidth, TEXT_HEIGHT);
            graphics2d.drawString(productName, leftWidth, 705);
            // 红色画笔
            graphics2d.setColor(Color.RED);
            String priceStr = "¥ "+String.valueOf(price);
            // 写入价格
            graphics2d.drawString(priceStr, leftWidth, 740);
            // 关闭资源
            graphics2d.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return baseImg;
    }

    /**
     * 生成新图片到本地
     */
    public static void writeImageLocal(String newImage, BufferedImage img) {
        if (newImage != null && img != null) {
            try {
                File outputfile = new File(newImage);
                ImageIO.write(img, "jpg", outputfile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        BufferedImage d = DrawingUtils.loadImageLocal("E:/123.jpg");
        BufferedImage z = DrawingUtils.loadImageLocal("E:/主图.jpg");
        BufferedImage e = DrawingUtils.loadImageLocal("E:/二维码.jpg");

        DrawingUtils.writeImageLocal("E:/new12.jpg",
                DrawingUtils.modifyImagetogeter(d, z, e, "联想博罗先锋电脑城专卖店", "保赐利75%酒精消毒喷雾剂 200ml",70.00));

        System.out.println("success");
    }
}