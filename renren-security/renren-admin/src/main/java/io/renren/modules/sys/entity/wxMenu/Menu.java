package io.renren.modules.sys.entity.wxMenu;

/**
 * Created by 24537 on 2017/9/5.
 * 整个菜单对象的封装
 * 包含多个菜单项（最多只能有3个）
 * 这些菜单项即可以是子菜单项（不含二级菜单的一级菜单），也可以是父菜单项（包含二级菜单的菜单项）
 */
public class Menu {
    private Button[] button;

    public Button[] getButton() {
        return button;
    }

    public void setButton(Button[] button) {
        this.button = button;
    }
}
