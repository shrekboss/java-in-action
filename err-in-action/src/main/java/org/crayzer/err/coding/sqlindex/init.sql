drop table IF EXISTS `person`;

create TABLE `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `score` int(11) NOT NULL,
  `create_time` timestamp NOT NULL,
  PRIMARY KEY (`id`)
#   KEY `name_score` (`name`,`score`) USING BTREE,
#   KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER table `person` ADD INDEX `name_score` (`name`,`score`) USING BTREE;
ALTER table `person` ADD INDEX `create_time` (`create_time`) USING BTREE;

