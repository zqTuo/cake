package io.renren.modules.sys.service.impl;

import io.renren.modules.sys.entity.wxMenu.*;
import io.renren.modules.sys.service.MenuManagerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by 24537 on 2017/9/5.
 */
@Service("menuManagerService")
public class MenuManagerServiceImpl implements MenuManagerService {
    @Value("${project.url_pre}")
    private String url_pre;
    
    public Menu getMenu() {

        ViewButton btn11 = new ViewButton();
        btn11.setName("蛋糕预订");
        btn11.setType("view");
        btn11.setUrl(url_pre +"/cake.html");

        ViewButton btn21 = new ViewButton();
        btn21.setName("课程预订");
        btn21.setType("view");
        btn21.setUrl(url_pre +"/course/index.html");

        CommonButton btn31 = new CommonButton();
        btn31.setName("我的客服");
        btn31.setType("click");
        btn31.setKey("31");

//        CommonButton btn32 = new CommonButton();
//        btn32.setName("售前售后");
//        btn32.setType("click");
//        btn32.setKey("32");

        //主菜单
        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("咨询");
        mainBtn3.setSub_button(new CommonButton[] { btn31});

        /**
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
         *
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
         */
        Menu menu = new Menu();
        menu.setButton(new Button[] { btn11, btn21, mainBtn3 });

        return menu;
    }
}
