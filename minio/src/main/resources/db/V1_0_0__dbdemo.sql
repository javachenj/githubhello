-- ----------------------------
-- create nancal demo tables
-- ----------------------------
CREATE TABLE `file` (
    `id` bigint NOT NULL COMMENT '主键',
    `uuid` char(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '文件id',
    `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '文件名称',
    `file_suffix` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '文件后缀',
    `file_status` tinyint DEFAULT '1' COMMENT '可用状态，0：可用，1：不可用',
    `create_at` datetime DEFAULT NULL COMMENT '创建时间',
    `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人ID',
    `create_by` varchar(16) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
    `update_at` datetime DEFAULT NULL COMMENT '修改时间',
    `update_user_id` varchar(32) DEFAULT NULL COMMENT '修改人ID',
    `update_by` varchar(16) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改人',
    `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除状态，0：未删除，1：已删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
