-- ----------------------------
-- create nancal demo tables
-- ----------------------------
CREATE TABLE `demo_user` (
    `id` bigint NOT NULL COMMENT '主键',
    `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '名称',
    `sex` tinyint DEFAULT NULL COMMENT '性别，0：男，1：女',
    `cellphone` varchar(12) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号',
    `email` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
    `family_address` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '家庭住址',
    `create_at` datetime DEFAULT NULL COMMENT '创建时间',
    `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人ID',
    `create_by` varchar(16) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
    `update_at` datetime DEFAULT NULL COMMENT '修改时间',
    `update_user_id` varchar(32) DEFAULT NULL COMMENT '修改人ID',
    `update_by` varchar(16) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改人',
    `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除状态，0：未删除，1：已删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
