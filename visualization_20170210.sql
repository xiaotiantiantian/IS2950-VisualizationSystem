CREATE DATABASE  IF NOT EXISTS `simmandebrief` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `simmandebrief`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: simmandebrief
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event` (
  `EventID` int(11) NOT NULL AUTO_INCREMENT,
  `EventName` varchar(45) DEFAULT NULL,
  `EventType` varchar(45) DEFAULT NULL,
  `EventSubtype` varchar(45) DEFAULT NULL,
  `Hours` int(11) DEFAULT NULL,
  `Minutes` int(11) DEFAULT NULL,
  `Seconds` int(11) DEFAULT NULL,
  `NumVal` float DEFAULT NULL,
  `StringVal` varchar(45) DEFAULT NULL,
  `Priority` int(11) DEFAULT NULL,
  `PatientID` varchar(45) DEFAULT NULL,
  `SimID` int(11) DEFAULT NULL,
  PRIMARY KEY (`EventID`)
) ENGINE=MyISAM AUTO_INCREMENT=1223 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient` (
  `PatientID` varchar(45) NOT NULL,
  `FirstName` varchar(45) DEFAULT NULL,
  `LastName` varchar(45) DEFAULT NULL,
  `Age` int(11) DEFAULT NULL,
  `Height` int(11) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `Gender` varchar(45) DEFAULT NULL,
  `PictureFile` varchar(45) DEFAULT NULL,
  `Description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`PatientID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `simulation`
--

DROP TABLE IF EXISTS `simulation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `simulation` (
  `SimID` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`SimID`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `userevent`
--

DROP TABLE IF EXISTS `userevent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userevent` (
  `eventID` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) DEFAULT NULL,
  `simulationID` int(11) DEFAULT NULL,
  `expertiseLevel` varchar(45) DEFAULT NULL,
  `hours` varchar(45) DEFAULT NULL,
  `minutes` varchar(45) DEFAULT NULL,
  `seconds` varchar(45) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `timestamp` varchar(45) DEFAULT NULL,
  `HR` int(11) DEFAULT NULL,
  `BPSystolic` int(11) DEFAULT NULL,
  `BPDiastolic` int(11) DEFAULT NULL,
  `SpO2` int(11) DEFAULT NULL,
  PRIMARY KEY (`eventID`)
) ENGINE=InnoDB AUTO_INCREMENT=937 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `userinformation`
--

DROP TABLE IF EXISTS `userinformation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userinformation` (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(45) DEFAULT NULL,
  `userXMLPath` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='In this project, use a random session id as user name, then store xml file path to this table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `userlog`
--

DROP TABLE IF EXISTS `userlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userlog` (
  `logID` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) DEFAULT NULL,
  `simulationID` int(11) DEFAULT NULL,
  `XMLPath` varchar(105) DEFAULT NULL,
  `expertLevel` varchar(45) DEFAULT NULL,
  `timestamp` timestamp(6) NULL DEFAULT NULL,
  `grade` int(11) DEFAULT NULL,
  PRIMARY KEY (`logID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'simmandebrief'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-02-10 16:26:40
