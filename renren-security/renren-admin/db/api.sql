create table wxuser(
id BIGINT(20) AUTO_INCREMENT,
user_name VARCHAR(200) not null default '' COMMENT '用户昵称',
user_head VARCHAR(200) not null default '' COMMENT '用户头像',
user_openid VARCHAR(200) not null default '' COMMENT 'openid',
user_unionid VARCHAR(200) default '' COMMENT 'unionid',
user_lastIp VARCHAR(100) not NULL default '' COMMENT '上次登录IP地址',
user_lastLoginTime datetime not null comment '上次登录时间',
user_state int not null default 0 comment '用户状态',
create_time datetime not null COMMENT '创建时间',
update_time datetime not null COMMENT '修改时间',
update_by VARCHAR(100) default '' comment '修改管理员',
PRIMARY KEY(id)
);

create table product(
  id bigint(20) AUTO_INCREMENT,
  product_name varchar(200) not null default '' comment '商品名称',
  product_cateid bigint(20) not null default 0 comment '商品类型ID',
  product_price decimal(11,2) not null default 0 comment '商品售价',
  product_origin decimal(11,2) not null default 0 comment '商品原价',
  product_sku varchar(20) not null default '' comment '商品SKU',
  product_img varchar(200) not null default '' comment '商品主图',
  product_banner varchar(200) not null default '' comment '商品副图',
  product_desc varchar(500) not null default '' comment '商品描述',
  product_info text not null comment '商品详情HTML代码',
  product_flag int not null default 1 comment '商品状态 0：下架 1：上架',
  product_hot int not null default 0 comment '热销标记(展示在首页) 0：不推荐 1：推荐',
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
  primary key (id)
);

create table product_category(
  id bigint(20) AUTO_INCREMENT,
  category_name varchar(100) not null default '' comment '分类名称',
  category_parentid bigint(20) not null default 0 comment '父类ID',
  category_flag int not null default 1 comment '分类状态 0：禁用 1：启用',
  primary key (id)
);

create table

