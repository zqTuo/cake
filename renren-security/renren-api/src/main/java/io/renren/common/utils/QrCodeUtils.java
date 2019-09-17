package io.renren.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/7/4 19:40
 */
public class QrCodeUtils {
    private static final String CHARSET = "utf-8";
    private static final String FORMAT = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int LOGO_WIDTH = 60;
    // LOGO高度
    private static final int LOGO_HEIGHT = 60;

    /**
     * 生成二维码
     *
     * @param content      二维码内容
     * @return 图片
     * @throws Exception
     */
    public static BufferedImage createImage(String content) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

//    /**
//     * 生成二维码(内嵌LOGO)
//     * 二维码文件名随机，文件名可能会有重复
//     *
//     * @param content      内容
//     * @param logoPath     LOGO地址
//     * @param destPath     存放目录
//     * @param needCompress 是否压缩LOGO
//     * @throws Exception
//     */
//    public static String encode(String content, String destPath) throws Exception {
//        BufferedImage image = QrCodeUtils.createImage(content);
//        mkdirs(destPath);
//        String fileName = new Random().nextInt(99999999) + "." + FORMAT.toLowerCase();
//        ImageIO.write(image, FORMAT, new File(destPath + "/" + fileName));
//        return fileName;
//    }

    public static void writeImage(String codeUrl, HttpServletResponse response) throws Exception {
        BufferedImage image = QrCodeUtils.createImage(codeUrl);
        ImageIO.write(image, FORMAT, response.getOutputStream());
    }

//    /**
////     * 生成二维码(内嵌LOGO)
////     *
////     * @param content  内容
////     * @param logoPath LOGO地址
////     * @param destPath 存储地址
////     * @throws Exception
////     */
////    public static String encode(String content, String logoPath, String destPath) throws Exception {
////        return QrCodeUtils.encode(content, logoPath, destPath, false);
////    }

//    /**
//     * 生成二维码
//     *
//     * @param content      内容
//     * @param destPath     存储地址
//     * @param needCompress 是否压缩LOGO
//     * @throws Exception
//     */
//    public static String encode(String content, String destPath, boolean needCompress) throws Exception {
//        return QrCodeUtils.encode(content,  destPath, needCompress);
//    }

//    /**
//     * 生成二维码
//     *
//     * @param content  内容
//     * @param destPath 存储地址
//     * @throws Exception
//     */
//    public static String encode(String content, String destPath) throws Exception {
//        return QrCodeUtils.encode(content, null, destPath, false);
//    }

//    /**
//     * 生成二维码(内嵌LOGO)
//     *
//     * @param content      内容
//     * @param logoPath     LOGO地址
//     * @param output       输出流
//     * @param needCompress 是否压缩LOGO
//     * @throws Exception
//     */
//    public static void encode(String content, String logoPath, OutputStream output, boolean needCompress)
//            throws Exception {
//        BufferedImage image = QrCodeUtils.createImage(content, logoPath, needCompress);
//        ImageIO.write(image, FORMAT, output);
//    }

//    /**
//     * 生成二维码
//     *
//     * @param content 内容
//     * @param output  输出流
//     * @throws Exception
//     */
//    public static void encode(String content, OutputStream output) throws Exception {
//        QrCodeUtils.encode(content, null, output, false);
//    }
}
