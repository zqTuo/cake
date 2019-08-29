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
user_state int not null default 0 comment '用户状态 1：正常使用 0：禁用',
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
  product_hot int not null default 0 comment '热销标记(展示在首页) 0：不推荐 1：推荐',
  product_extra int not null default 0 comment '加购标记 0：不设为加购 1：设为加购',
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
    order_state int not null default -1 comment '订单状态 -1：未支付 0：已取消 1：已支付 2：已发货 3：（已确认）已签收',
    order_source_type int not null default 0 comment '订单来源 0：蛋糕订购 1：预约烘焙课程 2:购买会员',
    order_remark varchar(500) default '' comment '备注',
    addr_pro varchar(100) default '' comment '配送省份',
    addr_city varchar(100) default '' comment '配送城市',
    addr_dis varchar(100) default '' comment '配送区域',
    addr_detail varchar(100) default '' comment '配送详细地址',
    addr_receiver varchar(100) default '' comment '收货人',
    send_type int not null default 0 comment '配送方式 0：送货上门 1：门店自取',
    addr_phone varchar(20) default '' comment '联系方式',
    send_time varchar(100) not null comment '派送时间',
    create_time datetime not null COMMENT '创建时间',
    pay_time datetime comment '支付时间',
    pay_type int default 0 comment '支付方式 0：微信支付',
    shop_id bigint(20) not null default 0 comment '门店ID',
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

-- 热销栏目与商品关联表
create table tb_hot_relation(
    id bigint(20) auto_increment,
    hot_id bigint(20) not null default 0 comment '热销栏目ID',
    product_id bigint(20) not null default 0 comment '商品ID',
    create_time datetime not null COMMENT '创建时间',
    update_time datetime COMMENT '修改时间',
    update_by VARCHAR(100) default '' comment '修改管理员',
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='热销栏目与商品关联表';

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



