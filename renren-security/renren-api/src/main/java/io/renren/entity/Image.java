package io.renren.entity;

/**
 * @Author: Clarence
 * @Description:
 * @Date: 2018/1/8 17:22.
 */
public class Image {
    private String MediaId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public Image() {
    }

    public Image(String mediaId) {
        MediaId = mediaId;
    }
}
