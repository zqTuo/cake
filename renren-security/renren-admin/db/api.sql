create table wxuser(
id BIGINT(20) AUTO_INCREMENT,
user_name VARCHAR(200) not null default '' COMMENT '用户昵称',
user_head VARCHAR(200) not null default '' COMMENT '用户头像',
user_member int not null default 0 comment '会员等级 0：普通用户 1：会员用户',
user_openid VARCHAR(200) not null default '' COMMENT 'openid',
user_unionid VARCHAR(200) default '' COMMENT 'unionid',
user_lastIp VARCHAR(100) not NULL default '' COMMENT '上次登录IP地址',
user_lastLoginTime datetime not null comment '上次登录时间',
user_state int not null default 0 comment '用户状态',
create_time datetime not null COMMENT '创建时间',
update_time datetime COMMENT '修改时间',
update_by VARCHAR(100) default '' comment '修改管理员',
PRIMARY KEY(id)
);

create table product(
  id bigint(20) AUTO_INCREMENT,
  product_name varchar(200) not null default '' comment '商品名称',
  product_cateid bigint(20) not null default 0 comment '商品类型ID',
  product_price decimal(11,2) not null default 0 comment '商品售价',
  product_origin decimal(11,2) not null default 0 comment '商品原价',
  product_sku varchar(20) not null default '' comment '商品SKU，主要是为了匹配美团套餐',
  product_img varchar(200) not null default '' comment '商品主图',
  product_banner varchar(200) not null default '' comment '商品副图',
  product_desc varchar(500) not null default '' comment '商品描述',
  product_info text not null comment '商品详情HTML代码',
  product_flag int not null default 1 comment '商品状态 0：下架 1：上架',
  product_hot int not null default 0 comment '热销标记(展示在首页) 0：不推荐 1：推荐',
  create_time datetime not null COMMENT '创建时间',
  update_time datetime COMMENT '修改时间',
  update_by VARCHAR(100) default '' comment '修改管理员',
  primary key (id)
);

create table product_detail(
  id bigint(20) AUTO_INCREMENT,
  detail_cover varchar(200) not null default '' comment '商品规格图片',
  detail_name varchar(200) not null default '' comment '商品规格名称',
  product_id bigint(20) not null default 0 comment '所属商品ID',
  detail_price decimal(11,2) not null default 0 comment '商品规格价格',
  detail_price_region varchar(100) not null default '' comment '商品规格价格区间',
  detail_size varchar(100) not null default '' comment '商品尺寸',
  detail_taste varchar(100) not null default '' comment '商品口味',
  create_time datetime not null COMMENT '创建时间',
  update_time datetime COMMENT '修改时间',
  update_by VARCHAR(100) default '' comment '修改管理员',
  primary key (id)
);

create table product_category(
  id bigint(20) AUTO_INCREMENT,
  category_name varchar(100) not null default '' comment '分类名称',
  category_parentid bigint(20) not null default 0 comment '父类ID',
  category_flag int not null default 1 comment '分类状态 0：禁用 1：启用',
  create_time datetime not null COMMENT '创建时间',
  update_time datetime COMMENT '修改时间',
  update_by VARCHAR(100) default '' comment '修改管理员',
  primary key (id)
);

create table shop_order(
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
    addr_pro varchar(100) not null default '' comment '配送省份',
    addr_city varchar(100) not null default '' comment '配送城市',
    addr_dis varchar(100) not null default '' comment '配送区域',
    addr_detail varchar(100) not null default '' comment '配送详细地址',
    addr_receiver varchar(100) default '' comment '收货人',
    addr_phone varchar(20) default '' comment '联系方式',
    send_time datetime default '' comment '派送时间',
    create_time datetime not null COMMENT '创建时间',
    pay_time datetime comment '支付时间',
    pay_type int default 0 comment '支付方式 0：微信支付',
    cancel_time datetime comment '取消时间',
    finish_time datetime comment '签收时间',
    update_time datetime COMMENT '修改时间',
    update_by VARCHAR(100) default '' comment '修改管理员',
    primary key (id)
);

create table shop_order_item(
    id bigint(20) AUTO_INCREMENT,
    order_no varchar(200) not null default '' comment '订单编号',
    product_name varchar(200) default '' comment '商品名称',
    product_id bigint(20) default 0 comment '商品ID',
    detail_cover varchar(200) default '' comment '商品规格图片',
    detail_price decimal(11,2) default 0 comment '商品规格价格',
    detail_size varchar(100) default '' comment '商品尺寸',
    detail_taste varchar(100) default '' comment '商品口味',
    buy_num int default 1 comment '购买数量'
)

