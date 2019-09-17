-- 微信用户表
create table tb_wxuser(
id BIGINT(20) AUTO_INCREMENT,
user_name VARCHAR(200) not null default '' COMMENT '用户昵称',
user_head VARCHAR(200) not null default '' COMMENT '用户头像',
user_member int not null default 0 comment '会员等级 0：普通用户 1：会员用户',
user_openid VARCHAR(200) not null default '' COMMENT 'openid',
user_unionid VARCHAR(200) default '' COMMENT 'unionid',
user_lastIp VARCHAR(100) not NULL default '' COMMENT '上次登录IP地址',
user_lastLoginTime datetime not null comment '上次登录时间',
user_phone varchar(20) default '' comment '手机号',
kf_account varchar(20) default '' comment '专属客服账号',
user_state int not null default 0 comment '用户状态 1：正常使用 0：禁用',
subscribe int not null default 0 comment '是否关注公众号 1：已关注 0：未关注',
create_time datetime not null COMMENT '创建时间',
update_time datetime COMMENT '修改时间',
update_by VARCHAR(100) default '' comment '修改管理员',
PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- 商品表
DROP TABLE IF EXISTS tb_product;
create table tb_product(
  id bigint(20) AUTO_INCREMENT,
  product_name varchar(200) not null default '' comment '商品名称',
  product_cateid bigint(20) not null default 0 comment '商品类型ID',
  product_price decimal(11,2) not null default 0 comment '商品售价',
  product_price_region varchar(100) not null default '' comment '商品规格价格区间',
  product_origin decimal(11,2) not null default 0 comment '商品原价',
  product_sku varchar(20) not null default '' comment '商品SKU，主要是为了匹配美团套餐',
  product_img varchar(200) not null default '' comment '商品主图',
  product_video varchar(200) default '' comment '商品视频',
  product_banner varchar(200) not null default '' comment '商品副图',
  product_desc varchar(500) not null default '' comment '商品描述',
  product_info text not null comment '商品详情HTML代码',
  product_flag int not null default 1 comment '商品状态 0：下架 1：上架',
  product_hot_id bigint(11) not null default 0 comment '热销标记(展示在首页) 0：不推荐 1：推荐栏目ID',
  product_extra int not null default 0 comment '加购标记 0：不设为加购 1：设为加购',
  product_hot_flag int not null default 0 comment '热门标记 0：不推荐 1：推荐',
  shop_id bigint(20) not null default 0 comment '所属门店',
  create_time datetime not null COMMENT '创建时间',
  update_time datetime COMMENT '修改时间',
  update_by VARCHAR(100) default '' comment '修改管理员',
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';

-- 商品规格（详情）表
create table tb_product_detail(
  id bigint(20) AUTO_INCREMENT,
  detail_cover varchar(200) not null default '' comment '商品规格图片',
  detail_name varchar(200) not null default '' comment '商品规格名称',
  product_id bigint(20) not null default 0 comment '所属商品ID',
  detail_sku varchar(100) not null default '' comment '规格SKU',
  detail_price decimal(11,2) not null default 0 comment '商品规格价格',
  detail_size varchar(100) not null default '' comment '商品尺寸',
  size_id bigint(11) not null default 0 comment '尺寸id',
  detail_taste varchar(100) not null default '' comment '商品口味',
  taste_id bigint(11) not null default 0 comment '口味id',
  create_time datetime not null COMMENT '创建时间',
  update_time datetime COMMENT '修改时间',
  update_by VARCHAR(100) default '' comment '修改管理员',
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品详情表';

-- 商品尺寸表
create table tb_product_size(
    id bigint(11) auto_increment,
    title varchar(100) not null default '' comment '标题',
    product_id bigint(11) not null default 0 comment '商品ID',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品尺寸表';

-- 商品口味表
create table tb_product_taste(
    id bigint(11) auto_increment,
    title varchar(100) not null default '' comment '标题',
    product_id bigint(11) not null default 0 comment '商品ID',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品口味表';

-- 商品种类表
create table tb_product_category(
  id bigint(20) AUTO_INCREMENT,
  category_name varchar(100) not null default '' comment '分类名称',
  category_parentid bigint(20) not null default 0 comment '父类ID',
  category_flag int not null default 1 comment '分类状态 0：禁用 1：启用',
  showFlag int not null default 0 comment '首页是否展示 0：不展示 1：展示 ',
  create_time datetime not null COMMENT '创建时间',
  update_time datetime COMMENT '修改时间',
  update_by VARCHAR(100) default '' comment '修改管理员',
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品种类表';

-- 订单表
create table tb_shop_order(
    id bigint(20) AUTO_INCREMENT,
    order_no varchar(200) not null default '' comment '订单编号',
    user_id bigint(20) not null default 0 comment  '用户ID',
    order_price decimal(11,2) not null default 0 comment '订单支付金额',
    order_discount decimal(11,2) not null default 0 comment '订单优惠金额',
    order_discount_type int not null default 0 comment '订单优惠类型 0：无优惠 1：优惠券',
    coupon_user_id bigint(20) default 0 comment '用户的优惠券ID',
    meituan_id bigint(20) default 0 comment '美团券验券记录ID',
    order_state int not null default -1 comment '订单状态 -1：未支付 0：已取消 1：已支付 2：已发货 3：（已确认）已签收',
    order_source_type int not null default 0 comment '订单来源 0：蛋糕订购 1：预约烘焙课程 2:购买会员',
    order_remark varchar(500) default '' comment '寄语',
    order_des varchar(500) default '' comment '备注',
    addr_pro varchar(100) default '' comment '配送省份',
    addr_city varchar(100) default '' comment '配送城市',
    addr_dis varchar(100) default '' comment '配送区域',
    addr_detail varchar(100) default '' comment '配送详细地址',
    addr_receiver varchar(100) default '' comment '收货人',
    send_type int not null default 0 comment '配送方式 0：送货上门 1：门店自取',
    addr_phone varchar(20) default '' comment '联系方式',
    send_time varchar(100) not null comment '派送时间段',
    send_date date not null comment '配送时间日期',
    send_price decimal(11,2) not null default 0 comment '配送费用',
    create_time datetime not null COMMENT '创建时间',
    pay_time datetime comment '支付时间',
    pay_type int default 0 comment '支付方式 0：微信支付',
    shop_id bigint(20) not null default 0 comment '门店ID',
    kf_nick varchar(20) default '' comment '接单客服昵称',
    adult_num varchar(100) not null comment '同行人数 - 大人',
    kid_num varchar(100) not null comment '同行人数 - 小孩',
    combo_user_id bigint(20) not null default 0 comment '用户课程套餐ID',
    cancel_time datetime comment '取消时间',
    finish_time datetime comment '签收时间',
    update_time datetime COMMENT '修改时间',
    update_by VARCHAR(100) default '' comment '修改管理员',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

-- 订单详情表
create table tb_shop_order_item(
    id bigint(20) AUTO_INCREMENT,
    order_no varchar(200) not null default '' comment '订单编号',
    product_name varchar(200) default '' comment '商品名称',
    product_id bigint(20) default 0 comment '商品ID',
    detail_cover varchar(200) default '' comment '商品规格图片',
    detail_price decimal(11,2) default 0 comment '商品规格价格',
    detail_size varchar(100) default '' comment '商品尺寸',
    detail_taste varchar(100) default '' comment '商品口味',
    buy_num int not null default 1 comment '购买数量',
    user_member int default 0 comment '购买会员等级',
    course_id bigint(20) default 0 comment '购买课程ID',
    combo_course_id bigint(20) default 0 comment '课程套餐ID',
    expired_date datetime comment '课程套餐截止日期',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单详情表';

-- 购物车表
create table tb_shopping_cart(
  id bigint(20) auto_increment,
  user_id bigint(20) not null default 0 comment '用户id',
  product_id bigint(20) not null default 0 comment '商品ID',
  product_detail_id bigint(20) not null default 0 comment '商品详情ID',
  product_name varchar(200) not null default '' comment '商品名称',
  detail_cover varchar(200) not null default '' comment '商品规格图片',
  detail_name varchar(200) not null default '' comment '商品规格名称',
  detail_price decimal(11,2) not null default 0 comment '商品规格价格',
  detail_size varchar(100) not null default '' comment '商品尺寸',
  detail_taste varchar(100) not null default '' comment '商品口味',
  buy_num int not null default 1 comment '购买数量',
  remark varchar(200) default '' comment '寄语',
  create_time datetime not null COMMENT '创建时间',
  update_time datetime COMMENT '修改时间',
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物车表';

-- 优惠券表
create table tb_coupon(
  id bigint(20) auto_increment,
  coupon_name varchar(200) not null default '' comment '优惠券名称',
  coupon_price decimal(11,2) not null default 0 comment '触发价格',
  price decimal(11,2) not null default 0 comment '优惠金额',
  coupon_type int not null default 0 comment '优惠券类型 0：商品优惠券 1：单次体验课程优惠券 2：会员套餐课程优惠券',
  start_time datetime comment '开始时间',
  end_time datetime comment '截止时间',
  date_flag int not null default 0 comment '是否限时 0：不限时 1:限时',
  create_time datetime not null COMMENT '创建时间',
  update_time datetime COMMENT '修改时间',
  update_by VARCHAR(100) default '' comment '修改管理员',
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券表';

-- 优惠券用户表
create table tb_coupon_user(
    id bigint(20) auto_increment,
    coupon_id bigint(20) not null default 0 comment '优惠券ID',
    user_id bigint(20) not null default 0 comment '用户ID',
    source_from int not null default 0 comment '获得来源 0：商城后台赠送',
    get_time datetime not null comment '获得时间',
    use_time datetime comment '使用时间',
    end_time datetime comment '截止时间',
    state int not null default 0 comment '使用状态 1：可用 0：不可用',
    create_time datetime not null COMMENT '创建时间',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券用户表';

-- 门店表
create table tb_shop(
    id bigint(20) auto_increment,
    shop_name varchar(200) not null default '' comment '门店名称',
    shop_addr varchar(500) not null default '' comment '门店详细地址',
    shop_longitude varchar(100) not null default '' comment '所在地 经度',
    shop_latitude varchar(100) not null default '' comment '所在地 纬度',
    meituan_shop_id varchar(100) default '' comment '美团店铺ID',
    dianping_shop_id varchar(100) default '' comment '点评店铺ID',
    create_time datetime not null COMMENT '创建时间',
    update_time datetime COMMENT '修改时间',
    update_by VARCHAR(100) default '' comment '修改管理员',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='门店表';

-- 配送时间表
create table tb_send_time(
    id bigint(20) auto_increment,
    start_time varchar(100) not null comment '派送时间点 HH:mm',
    end_time varchar(100) not null comment '派送时间点 HH:mm',
    max_order int not null comment '最大预约订单数',
    type int not null default 0 comment '时间类别：0：配送时间 1：课程时间',
    create_time datetime not null COMMENT '创建时间',
    update_time datetime COMMENT '修改时间',
    update_by VARCHAR(100) default '' comment '修改管理员',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配送时间表';

-- 运费表
create table tb_send_freight(
    id bigint(20) auto_increment,
    max_distance int not null default 0 comment '最大距离',
    freight decimal(11,2) not null default 0 comment '运费',
    create_time datetime not null COMMENT '创建时间',
    update_time datetime COMMENT '修改时间',
    update_by VARCHAR(100) default '' comment '修改管理员',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运费表';

-- 系统配置表
create table tb_base_data(
    id bigint(20) auto_increment,
    source_type int not null default 0 comment '配置类型 0：首页菜单icon',
    content text not null comment '配置数据内容',
    create_time datetime not null COMMENT '创建时间',
    update_time datetime COMMENT '修改时间',
    update_by VARCHAR(100) default '' comment '修改管理员',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置表';

-- banner图表
create table tb_banner(
    id bigint(20) auto_increment,
    banner_pic varchar(200) not null default '' comment '图片地址',
    link_url varchar(200) not null default '' comment '跳转地址',
    banner_type int not null default 0 comment '展示位置 0：首页',
    banner_state int not null default 1 comment '使用状态 0：禁用 1：启用',
    create_time datetime not null COMMENT '创建时间',
    update_time datetime COMMENT '修改时间',
    update_by VARCHAR(100) default '' comment '修改管理员',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='banner表';

-- 首页热销栏目表
create table tb_hot_column(
    id bigint(20) auto_increment,
    hot_title varchar(100) not null default '' comment '栏目标题',
    create_time datetime not null COMMENT '创建时间',
    update_time datetime COMMENT '修改时间',
    update_by VARCHAR(100) default '' comment '修改管理员',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='首页热销栏目表';

-- 收货地址表
create table tb_address(
    id bigint(20) auto_increment,
    user_id bigint(20) not null default 0 comment '用户ID',
    province varchar(200) not null default '' comment '省份',
    city varchar(100) not null default '' comment '市',
    district varchar(100) not null default '' comment '区',
    address varchar(250) not null default '' comment '详细地址',
    contract varchar(100) not null default '' comment '收货人',
    phone varchar(20) not null default '' comment '手机号',
    defaultFlag int not null default 0 comment '默认地址 0：否 1：是',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收货地址表';

-- 流水表
create table tb_order_sales(
    id bigint(20) auto_increment,
    user_id bigint(20) not null default 0 comment '用户ID',
    order_no varchar(200) not null default '' comment '订单编号',
    order_price decimal(11,2) not null default 0 comment '订单金额',
    order_discount decimal(11,2) not null default 0 comment '优惠金额',
    source_type int(2) not null default 0 comment '业务类型 0：蛋糕订购 1：预约烘焙课程 2:购买会员 3:退款',
    create_time datetime not null COMMENT '创建时间',
    update_time datetime COMMENT '修改时间',
    update_by VARCHAR(100) default '' comment '修改管理员',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='财务流水表';

-- 美团券验券记录表
create table tb_meituan_coupon(
  id bigint(11) auto_increment,
  user_id bigint(11) not null default 0 comment '用户ID',
  code varchar(100) not null default '' comment '美团点评券码',
  sourceType int not null default 0 comment '券码类型 0：美团 1：大众点评',
  flag int not null default 0 comment '使用状态 0:未使用 1：已使用',
  create_time datetime not null COMMENT '验券时间',
  use_time datetime COMMENT '使用时间',
  update_time datetime COMMENT '修改时间',
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='美团券验券记录表';

-- 美团券包含商品信息表
create table tb_meituan_item(
  id bigint(11) auto_increment,
  coupon_id bigint(11) not null default 0 comment '美团券ID',
  product_detail_id bigint(11) not null default 0 comment '商品详情ID',
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='美团券包含商品信息表';

-- 微信客服表
create table tb_custom(
    id bigint(11) auto_increment,
    user_name varchar(200) not null default '' comment '微信昵称',
    user_open_id varchar(200) not null default '' comment 'OPENID',
    user_head varchar(200) not null default '' comment '微信头像',
    kf_account varchar(200) not null default '' comment '完整客服帐号，格式为：帐号前缀@公众号微信号',
    kf_nick varchar(200) not null default '' comment '客服昵称',
    kf_head_img_url varchar(200) default '' comment '客服头像',
    kf_wx varchar(200) default '' comment '如果客服帐号已绑定了客服人员微信号， 则此处显示微信号',
    state int not null default 1 comment '客服状态 0：已停用 1：已启用',
    create_time datetime not null COMMENT '创建时间',
    update_time datetime COMMENT '修改时间',
    update_by VARCHAR(100) default '' comment '修改管理员',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信客服表';

-- 客服接待记录表
create table tb_custom_record(
   id bigint(20) NOT NULL AUTO_INCREMENT,
   user_id bigint(11) not null  DEFAULT 0 comment '用户ID',
   custom_id bigint(11) not null DEFAULT 0 comment '客服ID',
   user_name varchar(200) not null DEFAULT '' comment '用户昵称',
   create_time datetime not null COMMENT '创建时间',
   update_time datetime COMMENT '修改时间',
   update_by VARCHAR(100) default '' comment '修改管理员',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客服接待记录表';

-- 微信关键字表
CREATE TABLE `tb_wx_key` (
     `id` bigint(20) NOT NULL AUTO_INCREMENT,
     `keyword` varchar(200) NOT NULL default '' comment '关键字,多个中间用英文逗号隔开',
     `msg_type` varchar(100) NOT NULL default '' comment '消息类型（text/news/image）',
     `content` text comment '回复内容',
     `mediaID` varchar(200) DEFAULT '' comment '图文ID',
     PRIMARY KEY (`id`),
     UNIQUE KEY `keyword` (`keyword`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信关键字表';

-- 蛋糕课程表
create table tb_course(
    id bigint(20) auto_increment,
    title varchar(200) not null default '' comment '课程名称',
    cate_id bigint(20) not null default 0 comment '分类id',
    combo_type_id bigint(20) not null default 0 comment '套餐课程类别',
    course_img varchar(200) not null default '' comment '主图图片',
    course_banner varchar(200) not null default '' comment '副图图片',
    course_video varchar(200) default '' comment '视频介绍',
    price decimal(11,2) not null default 0 comment '售价',
    course_des varchar(200) not null default '' comment '课程简介',
    course_info text not null comment '课程详情HTML代码',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='蛋糕课程表';

-- 课程套餐表
create table tb_combo_course(
     id bigint(20) auto_increment,
     title varchar(100) not null default '' comment '套餐名称',
     pic_url varchar(200) not null default '' comment '套餐图片',
     valid_period int(3) not null default 0 comment '有效期 0：永久有效',
     price decimal(11,2) not null default 0 comment '套餐价格',
     remark varchar(500) not null default '' comment '套餐描述',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程套餐表';

-- 套餐课程类别表
create table tb_combo_type(
    id bigint(20) auto_increment,
    title varchar(100) not null default '' comment '类别名称',
    primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='套餐课程类别表';

-- 套餐详情表
create table tb_combo_course_item(
    id bigint(11) auto_increment,
    combo_course_id bigint(11) not null default 0 comment '课程套餐ID',
    type_id bigint(11) not null default 0 comment '套餐课程类别ID',
    num int(5) not null default 1 comment '包含课程次数',
    primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='套餐课程表';

-- 课程菜单表
create table tb_course_menu(
    id bigint(20) auto_increment,
    send_time_id bigint(20) not null default 0 comment '时间ID',
    course_id bigint(20) not null default 0 comment '课程ID',
    num int not null default 0 comment '最大人数',
    menu_date date not null comment '课程当前日期',
    cost int not null default 1 comment '消耗课程类别次数',
    primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程菜单表';

-- 用户剩余套餐次数表
create table tb_combo_user(
    id bigint(11) auto_increment,
    user_id bigint(11) not null default 0 comment '用户ID',
    type_id bigint(11) not null default 0 comment '课程类别ID',
    num int not null default 0 comment '剩余次数',
    create_time datetime not null COMMENT '创建时间',
    update_time datetime COMMENT '修改时间',
    expired_time datetime COMMENT '过期时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户剩余套餐次数表';



