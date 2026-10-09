CREATE DATABASE  IF NOT EXISTS `biblioteca` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `biblioteca`;
-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: biblioteca
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,'Ciencia Ficción'),(2,'Historia'),(3,'Filosofía'),(4,'Literatura'),(5,'Tecnología');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estudiantes`
--

DROP TABLE IF EXISTS `estudiantes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estudiantes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `rut` varchar(12) DEFAULT NULL,
  `curso` varchar(20) DEFAULT NULL,
  `correo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `rut` (`rut`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estudiantes`
--

LOCK TABLES `estudiantes` WRITE;
/*!40000 ALTER TABLE `estudiantes` DISABLE KEYS */;
INSERT INTO `estudiantes` VALUES (1,'Carlos Ruiz','98765432-1','3ro Medio A','carlos@correo.cl'),(2,'María Torres','11222333-4','4to Medio B','maria@correo.cl'),(3,'Luis Gómez','19283746-5','2do Medio C','luis@correo.cl'),(4,'Ignacio Silva','22334455-6','1ro Medio A','ignacio@correo.cl'),(5,'Laura Méndez','33445566-7','3ro Medio B','laura@correo.cl'),(6,'Javier Soto','44556677-8','4to Medio A','javier@correo.cl'),(7,'Fernanda Ríos','55667788-9','2do Medio B','fer@correo.cl'),(8,'Pedro Lagos','66778899-0','1ro Medio C','pedro@correo.cl'),(9,'Valentina Jara','77889900-1','3ro Medio C','valen@correo.cl'),(10,'Tomás Vidal','88990011-2','4to Medio C','tomas@correo.cl'),(11,'eduardo','11111111-0','programacion','edu@gmail.com'),(12,'seba','22222222-0','geologia','seba@gmail.com'),(13,'eduardo','12222222-8','poo','poo@gmail.com'),(15,'eduardo','11111111-3','programaciion','edu@gmail.com');
/*!40000 ALTER TABLE `estudiantes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libros`
--

DROP TABLE IF EXISTS `libros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `libros` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(200) DEFAULT NULL,
  `autor` varchar(100) DEFAULT NULL,
  `isbn` varchar(20) DEFAULT NULL,
  `editorial` varchar(100) DEFAULT NULL,
  `stock` int DEFAULT NULL,
  `id_categoria` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `isbn` (`isbn`),
  KEY `id_categoria` (`id_categoria`),
  CONSTRAINT `libros_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categorias` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libros`
--

LOCK TABLES `libros` WRITE;
/*!40000 ALTER TABLE `libros` DISABLE KEYS */;
INSERT INTO `libros` VALUES (1,'Dune','Frank Herbert','9780441013593','Ace Books',7,1),(2,'Fundación','Isaac Asimov','9788497594256','Debolsillo',3,1),(3,'Breve Historia del Tiempo','Stephen Hawking','9780553176988','Debate',2,2),(4,'El mundo de Sofía','Jostein Gaarder','9788478884452','Siruela',4,3),(5,'1984','George Orwell','9780451524935','Penguin Books',6,4),(6,'Crónica de una muerte anunciada','Gabriel García Márquez','9780307387340','Sudamericana',3,4),(7,'Introducción a la Inteligencia Artificial','Stuart Russell','9780136042594','Pearson',2,5),(8,'Fahrenheit 451','Ray Bradbury','9781451673319','Simon & Schuster',5,1),(9,'Sapiens','Yuval Noah Harari','9788499924211','Debate',4,2),(10,'Más allá del bien y del mal','Friedrich Nietzsche','9788420688114','Alianza',3,3);
/*!40000 ALTER TABLE `libros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prestamos`
--

DROP TABLE IF EXISTS `prestamos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prestamos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_estudiante` int DEFAULT NULL,
  `id_libro` int DEFAULT NULL,
  `fecha_prestamo` date DEFAULT NULL,
  `fecha_devolucion` date DEFAULT NULL,
  `devuelto` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id_estudiante` (`id_estudiante`),
  KEY `id_libro` (`id_libro`),
  CONSTRAINT `prestamos_ibfk_1` FOREIGN KEY (`id_estudiante`) REFERENCES `estudiantes` (`id`),
  CONSTRAINT `prestamos_ibfk_2` FOREIGN KEY (`id_libro`) REFERENCES `libros` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prestamos`
--

LOCK TABLES `prestamos` WRITE;
/*!40000 ALTER TABLE `prestamos` DISABLE KEYS */;
INSERT INTO `prestamos` VALUES (1,1,1,'2025-06-15','2025-06-22',1),(2,2,2,'2025-06-10','2025-06-17',1),(3,3,3,'2025-06-20','2025-06-27',0),(4,4,4,'2025-06-22','2025-06-29',0),(5,5,5,'2025-06-18','2025-06-25',1),(6,6,6,'2025-06-12','2025-06-19',0),(7,7,7,'2025-06-05','2025-06-12',1),(8,8,8,'2025-06-21','2025-06-28',0),(9,9,9,'2025-06-16','2025-06-23',1),(10,10,10,'2025-06-19','2025-06-26',0),(11,11,6,'2026-10-05','2026-10-12',1),(12,12,1,'2026-10-05','2026-10-12',1),(13,13,1,'2026-10-08','2026-10-15',1),(14,11,5,'2026-10-08','2026-10-15',1);
/*!40000 ALTER TABLE `prestamos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `rut` varchar(12) DEFAULT NULL,
  `correo` varchar(100) DEFAULT NULL,
  `contraseña` varchar(100) DEFAULT NULL,
  `rol` enum('bibliotecario','estudiante') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `rut` (`rut`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Antonia Pérez','12345678-9','antonia@correo.cl','clave123','bibliotecario'),(2,'Carlos Ruiz','98765432-1','carlos@correo.cl','clave123','estudiante'),(3,'María Torres','11222333-4','maria@correo.cl','clave123','estudiante'),(4,'Ignacio Silva','22334455-6','ignacio@correo.cl','clave123','estudiante'),(5,'Laura Méndez','33445566-7','laura@correo.cl','clave123','estudiante'),(6,'Javier Soto','44556677-8','javier@correo.cl','clave123','estudiante'),(7,'Fernanda Ríos','55667788-9','fer@correo.cl','clave123','estudiante'),(8,'Pedro Lagos','66778899-0','pedro@correo.cl','clave123','estudiante'),(9,'Valentina Jara','77889900-1','valen@correo.cl','clave123','estudiante'),(10,'Tomás Vidal','88990011-2','tomas@correo.cl','clave123','estudiante'),(11,'eduardo','11111111-3','edu@gmail.com','clave123','estudiante');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-08 20:25:04
