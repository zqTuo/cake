package io.renren.modules.sys.entity.wxMenu;

/**
 * Created by 24537 on 2017/9/5.
 * 微信公众号菜单 基类，一级菜单，二级菜单都继承此类
 */
public class Button {
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
