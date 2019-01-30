CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '姓名',
  `password` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `sys_role_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sys_user_id` bigint(20) DEFAULT NULL,
  `sys_role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(125) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '权限名称',
  `descritpion` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '描述',
  `url` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'url',
  `pid` bigint(20) DEFAULT NULL COMMENT '上级ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `sys_permission_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `permission_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



INSERT INTO SYS_USER (id,username, PASSWORD) VALUES (1,'admin', 'admin');
INSERT INTO SYS_USER (id,username, PASSWORD) VALUES (2,'abel', 'abel');

INSERT INTO SYS_ROLE(id,rolename) VALUES(1,'ROLE_ADMIN');
INSERT INTO SYS_ROLE(id,rolename) VALUES(2,'ROLE_USER');

INSERT INTO SYS_ROLE_USER(SYS_USER_ID,SYS_ROLE_ID) VALUES(1,1);
INSERT INTO SYS_ROLE_USER(SYS_USER_ID,SYS_ROLE_ID) VALUES(2,2);

BEGIN;
INSERT INTO `Sys_permission` VALUES ('1', 'ROLE_HOME', 'home', '/', NULL), ('2', 'ROLE_ADMIN', 'ABel', '/admin', NULL);
COMMIT;

BEGIN;
INSERT INTO `Sys_permission_role` VALUES ('1', '1', '1'), ('2', '1', '2'), ('3', '2', '1');
COMMIT;