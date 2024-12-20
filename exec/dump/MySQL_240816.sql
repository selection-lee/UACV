-- MySQL dump 10.13  Distrib 8.4.2, for Linux (x86_64)
--
-- Host: localhost    Database: uacv
-- ------------------------------------------------------
-- Server version	8.4.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Member`
--

DROP TABLE IF EXISTS `Member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Member` (
  `createdDate` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `lastModifiedDate` datetime(6) DEFAULT NULL,
  `m_id` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `rnk` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `memberRole` enum('ADMIN','CONTROL','MONITOR') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK73pcydbur7ap0v0abheab5sfr` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Member`
--

LOCK TABLES `Member` WRITE;
/*!40000 ALTER TABLE `Member` DISABLE KEYS */;
INSERT INTO `Member` VALUES ('2024-08-14 01:54:20.476301',3,'2024-08-14 01:54:20.476301','24-20240702','$2a$10$C58kCRF67nMxRxn./ThQyO4zmWJBLrV0rCgVsc6.BYVgPMpe7nWP.','관리자','iamnotadmin','ADMIN'),('2024-08-14 01:59:56.746192',4,'2024-08-14 01:59:56.746192','24-20240814','$2a$10$uydFz4cOQepvIUt.8z06dOhq55GdJtmm7.WmSsXhQNaKAf0GJ42YK','테스트','testclient','MONITOR'),('2024-08-14 08:13:14.100748',5,'2024-08-14 08:13:14.100748','24-20240815','$2a$10$H54A3yj2MAzXYbzNhkafQ.jJvCCp.FTpTf7vC5flT1yTqBqK9DmuG','테스트','areyouok','MONITOR'),('2024-08-15 15:45:13.869797',6,'2024-08-15 15:45:13.869797','24-20240816','$2a$10$vfsY5P7.9pxquwNkgkW3.eZh3nT/0YedM1QBACwqM3ebDo7p7a8Wq','TEST','testcontrol','CONTROL');
/*!40000 ALTER TABLE `Member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sound_data`
--

DROP TABLE IF EXISTS `sound_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sound_data` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `receivedAt` datetime(6) DEFAULT NULL,
  `soundType` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=394 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sound_data`
--

LOCK TABLES `sound_data` WRITE;
/*!40000 ALTER TABLE `sound_data` DISABLE KEYS */;
INSERT INTO `sound_data` VALUES (151,'2024-08-14 01:51:18.343146','M762'),(152,'2024-08-14 01:51:25.309801','AUG'),(153,'2024-08-14 01:52:47.445796','AUG'),(154,'2024-08-14 01:52:52.377842','M762'),(155,'2024-08-14 01:53:00.676571','M762'),(156,'2024-08-14 01:53:08.205335','M762'),(157,'2024-08-14 01:53:18.149569','M762'),(158,'2024-08-14 01:53:50.807317','AUG'),(159,'2024-08-14 01:53:58.873415','AUG'),(160,'2024-08-14 01:54:11.321167','M762'),(162,'2024-08-14 01:55:06.432385','AUG'),(163,'2024-08-14 01:55:40.495834','AUG'),(164,'2024-08-14 01:56:31.219329','M762'),(165,'2024-08-14 01:57:40.979524','AUG'),(166,'2024-08-14 01:57:52.444628','AUG'),(168,'2024-08-14 01:58:23.057965','AUG'),(171,'2024-08-14 04:47:44.494005','AUG'),(172,'2024-08-14 04:47:49.775085','AKM'),(180,'2024-08-14 05:03:42.592135','AUG'),(192,'2024-08-14 05:06:00.227077','AKM'),(206,'2024-08-14 05:08:10.385855','AUG'),(240,'2024-08-14 05:15:10.440943','AKM'),(244,'2024-08-14 05:17:15.111930','AUG'),(245,'2024-08-14 05:17:43.836328','M762'),(252,'2024-08-14 05:20:03.648900','M762'),(258,'2024-08-14 08:29:05.593048','AUG'),(259,'2024-08-14 08:31:26.758660','AUG'),(260,'2024-08-14 08:33:29.558664','AUG'),(261,'2024-08-14 08:33:31.136233','AUG'),(262,'2024-08-14 08:36:37.833673','Mk24'),(265,'2024-08-15 07:27:02.673714','Mk24'),(310,'2024-08-15 11:53:24.455067','K2'),(316,'2024-08-15 12:04:32.772564','AUG'),(317,'2024-08-15 12:04:36.749620','AUG'),(320,'2024-08-15 12:05:00.111826','MK24'),(346,'2024-08-15 13:28:58.755125','AUG'),(347,'2024-08-15 13:29:04.993681','AUG'),(348,'2024-08-15 13:31:17.659919','K2'),(349,'2024-08-15 13:32:16.135810','AUG'),(350,'2024-08-15 13:32:27.490278','K2'),(351,'2024-08-15 13:39:43.641082','aug'),(352,'2024-08-15 13:40:48.649093','aug'),(353,'2024-08-15 13:49:24.435099','AUG'),(354,'2024-08-15 13:50:04.327944','K2'),(355,'2024-08-15 14:14:41.002774','K2'),(360,'2024-08-15 14:25:24.001307','AUG'),(362,'2024-08-15 14:26:02.543165','AUG'),(363,'2024-08-15 14:26:58.452053','AUG'),(364,'2024-08-15 14:29:18.119489','K2'),(365,'2024-08-15 14:32:17.704056','AUG'),(366,'2024-08-15 14:32:23.889281','AUG'),(367,'2024-08-15 14:32:25.829260','AUG'),(368,'2024-08-15 14:35:55.473219','AKM'),(369,'2024-08-15 14:36:18.284466','K2'),(370,'2024-08-15 14:36:48.886405','AUG'),(371,'2024-08-15 14:37:24.593572','AUG'),(372,'2024-08-15 14:38:45.092327','M762'),(373,'2024-08-15 14:40:33.171778','AUG'),(374,'2024-08-15 14:44:56.541686','K2'),(375,'2024-08-15 14:48:33.345592','K2'),(376,'2024-08-15 14:48:39.156618','K2'),(377,'2024-08-15 14:50:29.284233','AUG'),(378,'2024-08-15 14:50:36.377185','K2'),(379,'2024-08-15 14:52:03.303845','AUG'),(380,'2024-08-15 15:10:43.084372','AUG'),(384,'2024-08-15 15:42:59.551286','AUG'),(385,'2024-08-15 15:43:58.223264','K2'),(386,'2024-08-15 15:46:29.443938','AUG'),(387,'2024-08-15 15:52:08.114373','AUG'),(388,'2024-08-15 16:01:43.199461','AKM'),(389,'2024-08-15 16:15:14.264462','AKM'),(390,'2024-08-15 16:15:25.806898','AUG'),(391,'2024-08-15 16:15:46.531439','M762'),(392,'2024-08-15 16:19:00.641281','M762'),(393,'2024-08-15 16:19:08.537579','AUG');
/*!40000 ALTER TABLE `sound_data` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-16  4:01:28
