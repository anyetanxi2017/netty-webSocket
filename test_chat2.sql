-- MySQL dump 10.13  Distrib 5.7.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: test_chat2
-- ------------------------------------------------------
-- Server version	5.7.23-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `chat_group`
--

DROP TABLE IF EXISTS `chat_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chat_group` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `group_name` varchar(255) NOT NULL,
  `icon` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '群类型',
  `max_number_of_people` smallint(5) unsigned DEFAULT '400' COMMENT '加群人数最大值',
  `save_db` tinyint(1) unsigned DEFAULT '0' COMMENT '是否保存数据到数据库',
  `create_time` datetime NOT NULL,
  `modified_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_group`
--

LOCK TABLES `chat_group` WRITE;
/*!40000 ALTER TABLE `chat_group` DISABLE KEYS */;
INSERT INTO `chat_group` VALUES (14,'skyAndyy','/test.png',NULL,1,200,0,'2019-11-19 11:09:15','2019-11-19 11:09:15'),(15,'圆圆大军群','/test.png',NULL,1,200,1,'2019-11-19 19:27:43','2019-11-19 19:27:43'),(16,'圆圆的群聊','/test.png',NULL,0,200,0,'2019-11-21 10:24:34','2019-11-21 10:24:34');
/*!40000 ALTER TABLE `chat_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_group_message`
--

DROP TABLE IF EXISTS `chat_group_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chat_group_message` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `group_id` int(10) unsigned NOT NULL,
  `from_user_id` int(10) unsigned NOT NULL,
  `content` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `group_id` (`group_id`),
  KEY `from_user_id` (`from_user_id`),
  CONSTRAINT `chat_group_message_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `chat_group` (`id`) ON DELETE CASCADE,
  CONSTRAINT `chat_group_message_ibfk_2` FOREIGN KEY (`from_user_id`) REFERENCES `ums_user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_group_message`
--

LOCK TABLES `chat_group_message` WRITE;
/*!40000 ALTER TABLE `chat_group_message` DISABLE KEYS */;
INSERT INTO `chat_group_message` VALUES (3,14,1,'大家好!','2019-11-20 10:23:20');
/*!40000 ALTER TABLE `chat_group_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_room`
--

DROP TABLE IF EXISTS `chat_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chat_room` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `explain` varchar(255) NOT NULL,
  `icon` varchar(255) NOT NULL,
  `is_hot` tinyint(1) unsigned DEFAULT '0',
  `room_type` tinyint(3) unsigned DEFAULT '0' COMMENT '房间类型',
  `sort` int(10) unsigned NOT NULL DEFAULT '5',
  `save_db` tinyint(1) unsigned DEFAULT '0' COMMENT '是否记录聊天数据',
  `no_say` tinyint(1) unsigned DEFAULT '0' COMMENT '是否禁言',
  `admins` varchar(255) DEFAULT NULL COMMENT '管理员列表 使用逗号隔开',
  `state` tinyint(1) unsigned DEFAULT '0' COMMENT '是否开启房间',
  `join_type` tinyint(1) unsigned DEFAULT '0' COMMENT '用户加入类型 0 游客可进 1不可进',
  `user_level` tinyint(3) unsigned DEFAULT '1' COMMENT '玩家等级限制',
  `agent_level` tinyint(3) unsigned DEFAULT '1' COMMENT '代理等级限制',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_room`
--

LOCK TABLES `chat_room` WRITE;
/*!40000 ALTER TABLE `chat_room` DISABLE KEYS */;
INSERT INTO `chat_room` VALUES (2,'yy的房间','圆圆的房间','/test.png',0,1,5,0,0,NULL,1,1,0,0),(5,'圆圆的房间','圆圆的房间','/test.png',0,1,5,0,0,NULL,1,1,0,0);
/*!40000 ALTER TABLE `chat_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_room_message`
--

DROP TABLE IF EXISTS `chat_room_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chat_room_message` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `room_id` int(10) unsigned NOT NULL,
  `from_user_id` int(10) unsigned NOT NULL,
  `content` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `from_user_id` (`from_user_id`),
  KEY `room_id` (`room_id`),
  CONSTRAINT `chat_room_message_ibfk_1` FOREIGN KEY (`from_user_id`) REFERENCES `ums_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `chat_room_message_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `chat_room` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_room_message`
--

LOCK TABLES `chat_room_message` WRITE;
/*!40000 ALTER TABLE `chat_room_message` DISABLE KEYS */;
INSERT INTO `chat_room_message` VALUES (1,2,1,'房间里的朋友们大家好!','2019-11-20 14:22:54');
/*!40000 ALTER TABLE `chat_room_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_single_message`
--

DROP TABLE IF EXISTS `chat_single_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chat_single_message` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `from_user_id` int(10) unsigned NOT NULL,
  `to_user_id` int(10) unsigned DEFAULT NULL,
  `content` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `from_user_id` (`from_user_id`),
  CONSTRAINT `chat_single_message_ibfk_1` FOREIGN KEY (`from_user_id`) REFERENCES `ums_user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_single_message`
--

LOCK TABLES `chat_single_message` WRITE;
/*!40000 ALTER TABLE `chat_single_message` DISABLE KEYS */;
INSERT INTO `chat_single_message` VALUES (1,1,2,'hello','2019-11-18 15:56:41'),(2,1,2,'hello22','2019-11-18 15:58:07');
/*!40000 ALTER TABLE `chat_single_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_user_group_relation`
--

DROP TABLE IF EXISTS `chat_user_group_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chat_user_group_relation` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(10) unsigned NOT NULL,
  `gid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `gid` (`gid`),
  CONSTRAINT `chat_user_group_relation_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `ums_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `chat_user_group_relation_ibfk_2` FOREIGN KEY (`gid`) REFERENCES `chat_group` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_user_group_relation`
--

LOCK TABLES `chat_user_group_relation` WRITE;
/*!40000 ALTER TABLE `chat_user_group_relation` DISABLE KEYS */;
INSERT INTO `chat_user_group_relation` VALUES (25,2,14),(26,1,14),(29,1,15),(30,2,15),(31,2,16),(32,3,14);
/*!40000 ALTER TABLE `chat_user_group_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_friend`
--

DROP TABLE IF EXISTS `ums_friend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_friend` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(10) unsigned NOT NULL COMMENT '用户id',
  `fid` int(10) unsigned NOT NULL COMMENT '朋友id',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  CONSTRAINT `ums_friend_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `ums_user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_friend`
--

LOCK TABLES `ums_friend` WRITE;
/*!40000 ALTER TABLE `ums_friend` DISABLE KEYS */;
/*!40000 ALTER TABLE `ums_friend` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_user`
--

DROP TABLE IF EXISTS `ums_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `avatar` varchar(255) NOT NULL,
  `user_level` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '用户等级',
  `user_type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '用户类型',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否删除 0删除 1正常',
  `email` varchar(16) DEFAULT NULL,
  `qq` varchar(16) DEFAULT NULL,
  `tel` varchar(16) DEFAULT NULL,
  `wechat` varchar(16) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `modified_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_user`
--

LOCK TABLES `ums_user` WRITE;
/*!40000 ALTER TABLE `ums_user` DISABLE KEYS */;
INSERT INTO `ums_user` VALUES (1,'sky','$2a$10$I0ibbRbpkL2YMUwSODWNFevIQrSjfHAxLdVbRR8rSrv13w.ubVTBu','/test.png',1,1,0,NULL,NULL,NULL,NULL,'2019-11-17 18:32:02','2019-11-17 18:32:02'),(2,'yy','$2a$10$itsXji7ywRTvghAOjdaPtOZiFHiqvuGdZ9h3WZFvUCu8scZvQ0YKq','/test.png',1,1,0,NULL,NULL,NULL,NULL,'2019-11-18 15:18:19','2019-11-18 15:18:19'),(3,'anyetanxi','$2a$10$G8qQdxo1nPYVxtXdLDRxOeByetz.b/mFzOTRmAJE0NE7z02DX2mAi','/test.png',1,1,0,NULL,NULL,NULL,NULL,'2019-11-19 14:24:46','2019-11-19 14:24:46');
/*!40000 ALTER TABLE `ums_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-21 10:55:02
