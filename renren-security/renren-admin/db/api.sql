create table wxuser(
id BIGINT(20) AUTO_INCREMENT,
userName VARCHAR(200) not null default '' COMMENT '用户昵称',
userImg VARCHAR(200) not null default '' COMMENT '用户头像',
userOpenid VARCHAR(200) not null default '' COMMENT 'openid',
unionid VARCHAR(200) default '' COMMENT 'unionid',
lastIp VARCHAR(100) not NULL default '' COMMENT '上次登录IP地址',
lastLoginTime datetime not null comment '上次登录时间',
state int not null default 0 comment '用户状态',
createTime datetime not null COMMENT '创建时间',
updateTime datetime not null COMMENT '修改时间',
updateBy VARCHAR(100) default '' comment '修改管理员',
PRIMARY KEY(id)
)

