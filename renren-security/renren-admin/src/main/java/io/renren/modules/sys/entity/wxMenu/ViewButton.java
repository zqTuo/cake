package io.renren.modules.sys.entity.wxMenu;

/**
 * Created by 24537 on 2017/9/5.
 * 表示二级菜单（VIEW类型）
 * 这里对子菜单是这样定义的：
 * 没有子菜单的菜单项，
 * 有可能是二级菜单项，
 * 也有可能是不含二级菜单的一级菜单。
 * 这类子菜单项一定会包含三个属性：type、name和url
 */
public class ViewButton extends Button{
    private String type;
    private String url; //view路径值

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
