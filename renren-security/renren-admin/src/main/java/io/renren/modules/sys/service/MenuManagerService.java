package io.renren.modules.sys.service;


import io.renren.modules.sys.entity.wxMenu.Menu;

/**
 * Created by 24537 on 2017/9/5.
 */
public interface MenuManagerService {

    /**
     * 创建微信菜单
     * @return
     */
    Menu getMenu();
}
