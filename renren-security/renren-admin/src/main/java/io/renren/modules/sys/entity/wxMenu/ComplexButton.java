package io.renren.modules.sys.entity.wxMenu;

/**
 * Created by 24537 on 2017/9/5.
 * 父菜单项的封装
 * 包含有二级菜单项的一级菜单
 * 这类菜单项包含有二个属性：name和sub_button，而sub_button以是一个子菜单项数组
 */
public class ComplexButton extends Button {
    private Button[] sub_button;

    public Button[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(Button[] sub_button) {
        this.sub_button = sub_button;
    }
}
