DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    `id`           BIGINT(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`     VARCHAR(255) COMMENT '用户名',
    `password`     VARCHAR(255) COMMENT '密码',
    `enable`       TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否已启用',
    `created_time` DATETIME COMMENT '创建时间',
    `updated_time` DATETIME COMMENT '更新时间',
    `delete_flag`  TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标记',
    PRIMARY KEY (id)
) COMMENT = '用户表';
