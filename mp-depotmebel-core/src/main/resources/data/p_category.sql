-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 04, 2017 at 07:29 AM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bgdepotmb`
--

--
-- Dumping data for table `pcategory_`
--

INSERT INTO `pcategory_` (`ID`, `created_date`, `name`, `status_`) VALUES
(1, '2017-12-04 11:40:10', 'Furnitur Kamar Tidur', 0),
(2, '2017-12-04 11:41:03', 'Furnitur Ruang Tamu', 0),
(3, '2017-12-04 11:48:53', 'Furnitur Ruang Kerja Rumah', 0);

--
-- Dumping data for table `pcategory_attribute`
--

INSERT INTO `pcategory_attribute` (`ID`, `created_date`, `label`, `pcategory`) VALUES
(1, '2017-12-04 11:40:10', 'Warna', 1),
(2, '2017-12-04 11:46:34', 'Warna', 2),
(3, '2017-12-04 13:20:38', 'Warna', 3);

--
-- Dumping data for table `pcategory_spec`
--

INSERT INTO `pcategory_spec` (`ID`, `created_date`, `label`, `pcategory`) VALUES
(1, '2017-12-04 11:40:10', 'Model', 1),
(2, '2017-12-04 11:40:10', 'Tipe Garansi', 1),
(3, '2017-12-04 11:40:10', 'Bedding Size', 1),
(4, '2017-12-04 11:40:10', 'Ukuran', 1),
(5, '2017-12-04 11:45:57', 'Model', 2),
(7, '2017-12-04 11:46:05', 'Tipe Garansi', 2),
(8, '2017-12-04 11:46:20', 'Material', 2),
(9, '2017-12-04 13:19:57', 'Model', 3),
(10, '2017-12-04 13:20:07', 'Material', 3),
(11, '2017-12-04 13:20:24', 'Tipe Garansi', 3);

--
-- Dumping data for table `pcategory_sub`
--

INSERT INTO `pcategory_sub` (`ID`, `created_date`, `name`, `pcategory`) VALUES
(1, '2017-12-04 11:40:10', 'Meja Rias dan Laci', 1),
(2, '2017-12-04 11:40:10', 'Tempat Tidur', 1),
(3, '2017-12-04 11:40:10', 'Meja Tempat Tidur', 1),
(4, '2017-12-04 11:40:10', 'Lemari Pakaian', 1),
(5, '2017-12-04 11:40:10', 'Rak Kamar Tidur', 1),
(6, '2017-12-04 11:41:41', 'Sofa', 2),
(7, '2017-12-04 11:42:21', 'Kursi', 2),
(8, '2017-12-04 11:43:36', 'Meja Sofa', 2),
(9, '2017-12-04 11:48:53', 'Meja Ruang Kerja Rumah', 3),
(10, '2017-12-04 13:18:29', 'Kursi Ruang Kerja Rumah', 3),
(11, '2017-12-04 13:18:57', 'Rak Arsip & Dokumen', 3);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
