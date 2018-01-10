-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 20, 2017 at 07:32 AM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bgtaraporter`
--

--
-- Dumping data for table `city_`
--

INSERT INTO `city_` (`ID`, `code`, `is_active`, `name`, `province`) VALUES
(1, '1101', 'Y', 'Kab. Simeulue', '11'),
(2, '1102', 'Y', 'Kab. Aceh Singkil', '11'),
(3, '1103', 'Y', 'Kab. Aceh Selatan', '11'),
(4, '1104', 'Y', 'Kab. Aceh Tenggara', '11'),
(5, '1105', 'Y', 'Kab. Aceh Timur', '11'),
(6, '1106', 'Y', 'Kab. Aceh Tengah', '11'),
(7, '1107', 'Y', 'Kab. Aceh Barat', '11'),
(8, '1108', 'Y', 'Kab. Aceh Besar', '11'),
(9, '1109', 'Y', 'Kab. Pidie', '11'),
(10, '1110', 'Y', 'Kab. Bireuen', '11'),
(11, '1111', 'Y', 'Kab. Aceh Utara', '11'),
(12, '1112', 'Y', 'Kab. Aceh Barat Daya', '11'),
(13, '1113', 'Y', 'Kab. Gayo Lues', '11'),
(14, '1114', 'Y', 'Kab. Aceh Tamiang', '11'),
(15, '1115', 'Y', 'Kab. Nagan Raya', '11'),
(16, '1116', 'Y', 'Kab. Aceh Jaya', '11'),
(17, '1117', 'Y', 'Kab. Bener Meriah', '11'),
(18, '1118', 'Y', 'Kab. Pidie Jaya', '11'),
(19, '1171', 'Y', 'Kota Banda Aceh', '11'),
(20, '1172', 'Y', 'Kota Sabang', '11'),
(21, '1173', 'Y', 'Kota Langsa', '11'),
(22, '1174', 'Y', 'Kota Lhokseumawe', '11'),
(23, '1175', 'Y', 'Kota Subulussalam', '11'),
(24, '1201', 'Y', 'Kab. Nias', '12'),
(25, '1202', 'Y', 'Kab. Mandailing Natal', '12'),
(26, '1203', 'Y', 'Kab. Tapanuli Selatan', '12'),
(27, '1204', 'Y', 'Kab. Tapanuli Tengah', '12'),
(28, '1205', 'Y', 'Kab. Tapanuli Utara', '12'),
(29, '1206', 'Y', 'Kab. Toba Samosir', '12'),
(30, '1207', 'Y', 'Kab. Labuhan Batu', '12'),
(31, '1208', 'Y', 'Kab. Asahan', '12'),
(32, '1209', 'Y', 'Kab. Simalungun', '12'),
(33, '1210', 'Y', 'Kab. Dairi', '12'),
(34, '1211', 'Y', 'Kab. Karo', '12'),
(35, '1212', 'Y', 'Kab. Deli Serdang', '12'),
(36, '1213', 'Y', 'Kab. Langkat', '12'),
(37, '1214', 'Y', 'Kab. Nias Selatan', '12'),
(38, '1215', 'Y', 'Kab. Humbang Hasundutan', '12'),
(39, '1216', 'Y', 'Kab. Pakpak Bharat', '12'),
(40, '1217', 'Y', 'Kab. Samosir', '12'),
(41, '1218', 'Y', 'Kab. Serdang Bedagai', '12'),
(42, '1219', 'Y', 'Kab. Batu Bara', '12'),
(43, '1220', 'Y', 'Kab. Padang Lawas Utara', '12'),
(44, '1221', 'Y', 'Kab. Padang Lawas', '12'),
(45, '1222', 'Y', 'Kab. Labuhan Batu Selatan', '12'),
(46, '1223', 'Y', 'Kab. Labuhan Batu Utara', '12'),
(47, '1224', 'Y', 'Kab. Nias Utara', '12'),
(48, '1225', 'Y', 'Kab. Nias Barat', '12'),
(49, '1271', 'Y', 'Kota Sibolga', '12'),
(50, '1272', 'Y', 'Kota Tanjung Balai', '12'),
(51, '1273', 'Y', 'Kota Pematang Siantar', '12'),
(52, '1274', 'Y', 'Kota Tebing Tinggi', '12'),
(53, '1275', 'Y', 'Kota Medan', '12'),
(54, '1276', 'Y', 'Kota Binjai', '12'),
(55, '1277', 'Y', 'Kota Padangsidimpuan', '12'),
(56, '1278', 'Y', 'Kota Gunungsitoli', '12'),
(57, '1301', 'Y', 'Kab. Kepulauan Mentawai', '13'),
(58, '1302', 'Y', 'Kab. Pesisir Selatan', '13'),
(59, '1303', 'Y', 'Kab. Solok', '13'),
(60, '1304', 'Y', 'Kab. Sijunjung', '13'),
(61, '1305', 'Y', 'Kab. Tanah Datar', '13'),
(62, '1306', 'Y', 'Kab. Padang Pariaman', '13'),
(63, '1307', 'Y', 'Kab. Agam', '13'),
(64, '1308', 'Y', 'Kab. Lima Puluh Kota', '13'),
(65, '1309', 'Y', 'Kab. Pasaman', '13'),
(66, '1310', 'Y', 'Kab. Solok Selatan', '13'),
(67, '1311', 'Y', 'Kab. Dharmasraya', '13'),
(68, '1312', 'Y', 'Kab. Pasaman Barat', '13'),
(69, '1371', 'Y', 'Kota Padang', '13'),
(70, '1372', 'Y', 'Kota Solok', '13'),
(71, '1373', 'Y', 'Kota Sawah Lunto', '13'),
(72, '1374', 'Y', 'Kota Padang Panjang', '13'),
(73, '1375', 'Y', 'Kota Bukittinggi', '13'),
(74, '1376', 'Y', 'Kota Payakumbuh', '13'),
(75, '1377', 'Y', 'Kota Pariaman', '13'),
(76, '1401', 'Y', 'Kab. Kuantan Singingi', '14'),
(77, '1402', 'Y', 'Kab. Indragiri Hulu', '14'),
(78, '1403', 'Y', 'Kab. Indragiri Hilir', '14'),
(79, '1404', 'Y', 'Kab. Pelalawan', '14'),
(80, '1405', 'Y', 'Kab. S I A K', '14'),
(81, '1406', 'Y', 'Kab. Kampar', '14'),
(82, '1407', 'Y', 'Kab. Rokan Hulu', '14'),
(83, '1408', 'Y', 'Kab. Bengkalis', '14'),
(84, '1409', 'Y', 'Kab. Rokan Hilir', '14'),
(85, '1410', 'Y', 'Kab. Kepulauan Meranti', '14'),
(86, '1471', 'Y', 'Kota Pekanbaru', '14'),
(87, '1473', 'Y', 'Kota D U M A I', '14'),
(88, '1501', 'Y', 'Kab. Kerinci', '15'),
(89, '1502', 'Y', 'Kab. Merangin', '15'),
(90, '1503', 'Y', 'Kab. Sarolangun', '15'),
(91, '1504', 'Y', 'Kab. Batang Hari', '15'),
(92, '1505', 'Y', 'Kab. Muaro Jambi', '15'),
(93, '1506', 'Y', 'Kab. Tanjung Jabung Timur', '15'),
(94, '1507', 'Y', 'Kab. Tanjung Jabung Barat', '15'),
(95, '1508', 'Y', 'Kab. Tebo', '15'),
(96, '1509', 'Y', 'Kab. Bungo', '15'),
(97, '1571', 'Y', 'Kota Jambi', '15'),
(98, '1572', 'Y', 'Kota Sungai Penuh', '15'),
(99, '1601', 'Y', 'Kab. Ogan Komering Ulu', '16'),
(100, '1602', 'Y', 'Kab. Ogan Komering Ilir', '16'),
(101, '1603', 'Y', 'Kab. Muara Enim', '16'),
(102, '1604', 'Y', 'Kab. Lahat', '16'),
(103, '1605', 'Y', 'Kab. Musi Rawas', '16'),
(104, '1606', 'Y', 'Kab. Musi Banyuasin', '16'),
(105, '1607', 'Y', 'Kab. Banyu Asin', '16'),
(106, '1608', 'Y', 'Kab. Ogan Komering Ulu Selatan', '16'),
(107, '1609', 'Y', 'Kab. Ogan Komering Ulu Timur', '16'),
(108, '1610', 'Y', 'Kab. Ogan Ilir', '16'),
(109, '1611', 'Y', 'Kab. Empat Lawang', '16'),
(110, '1671', 'Y', 'Kota Palembang', '16'),
(111, '1672', 'Y', 'Kota Prabumulih', '16'),
(112, '1673', 'Y', 'Kota Pagar Alam', '16'),
(113, '1674', 'Y', 'Kota Lubuklinggau', '16'),
(114, '1701', 'Y', 'Kab. Bengkulu Selatan', '17'),
(115, '1702', 'Y', 'Kab. Rejang Lebong', '17'),
(116, '1703', 'Y', 'Kab. Bengkulu Utara', '17'),
(117, '1704', 'Y', 'Kab. Kaur', '17'),
(118, '1705', 'Y', 'Kab. Seluma', '17'),
(119, '1706', 'Y', 'Kab. Mukomuko', '17'),
(120, '1707', 'Y', 'Kab. Lebong', '17'),
(121, '1708', 'Y', 'Kab. Kepahiang', '17'),
(122, '1709', 'Y', 'Kab. Bengkulu Tengah', '17'),
(123, '1771', 'Y', 'Kota Bengkulu', '17'),
(124, '1801', 'Y', 'Kab. Lampung Barat', '18'),
(125, '1802', 'Y', 'Kab. Tanggamus', '18'),
(126, '1803', 'Y', 'Kab. Lampung Selatan', '18'),
(127, '1804', 'Y', 'Kab. Lampung Timur', '18'),
(128, '1805', 'Y', 'Kab. Lampung Tengah', '18'),
(129, '1806', 'Y', 'Kab. Lampung Utara', '18'),
(130, '1807', 'Y', 'Kab. Way Kanan', '18'),
(131, '1808', 'Y', 'Kab. Tulangbawang', '18'),
(132, '1809', 'Y', 'Kab. Pesawaran', '18'),
(133, '1810', 'Y', 'Kab. Pringsewu', '18'),
(134, '1811', 'Y', 'Kab. Mesuji', '18'),
(135, '1812', 'Y', 'Kab. Tulang Bawang Barat', '18'),
(136, '1813', 'Y', 'Kab. Pesisir Barat', '18'),
(137, '1871', 'Y', 'Kota Bandar Lampung', '18'),
(138, '1872', 'Y', 'Kota Metro', '18'),
(139, '1901', 'Y', 'Kab. Bangka', '19'),
(140, '1902', 'Y', 'Kab. Belitung', '19'),
(141, '1903', 'Y', 'Kab. Bangka Barat', '19'),
(142, '1904', 'Y', 'Kab. Bangka Tengah', '19'),
(143, '1905', 'Y', 'Kab. Bangka Selatan', '19'),
(144, '1906', 'Y', 'Kab. Belitung Timur', '19'),
(145, '1971', 'Y', 'Kota Pangkal Pinang', '19'),
(146, '2101', 'Y', 'Kab. Karimun', '21'),
(147, '2102', 'Y', 'Kab. Bintan', '21'),
(148, '2103', 'Y', 'Kab. Natuna', '21'),
(149, '2104', 'Y', 'Kab. Lingga', '21'),
(150, '2105', 'Y', 'Kab. Kepulauan Anambas', '21'),
(151, '2171', 'Y', 'Kota B A T A M', '21'),
(152, '2172', 'Y', 'Kota Tanjung Pinang', '21'),
(153, '3101', 'Y', 'Kab. Kepulauan Seribu', '31'),
(154, '3171', 'Y', 'Kota Jakarta Selatan', '31'),
(155, '3172', 'Y', 'Kota Jakarta Timur', '31'),
(156, '3173', 'Y', 'Kota Jakarta Pusat', '31'),
(157, '3174', 'Y', 'Kota Jakarta Barat', '31'),
(158, '3175', 'Y', 'Kota Jakarta Utara', '31'),
(159, '3201', 'Y', 'Kab. Bogor', '32'),
(160, '3202', 'Y', 'Kab. Sukabumi', '32'),
(161, '3203', 'Y', 'Kab. Cianjur', '32'),
(162, '3204', 'Y', 'Kab. Bandung', '32'),
(163, '3205', 'Y', 'Kab. Garut', '32'),
(164, '3206', 'Y', 'Kab. Tasikmalaya', '32'),
(165, '3207', 'Y', 'Kab. Ciamis', '32'),
(166, '3208', 'Y', 'Kab. Kuningan', '32'),
(167, '3209', 'Y', 'Kab. Cirebon', '32'),
(168, '3210', 'Y', 'Kab. Majalengka', '32'),
(169, '3211', 'Y', 'Kab. Sumedang', '32'),
(170, '3212', 'Y', 'Kab. Indramayu', '32'),
(171, '3213', 'Y', 'Kab. Subang', '32'),
(172, '3214', 'Y', 'Kab. Purwakarta', '32'),
(173, '3215', 'Y', 'Kab. Karawang', '32'),
(174, '3216', 'Y', 'Kab. Bekasi', '32'),
(175, '3217', 'Y', 'Kab. Bandung Barat', '32'),
(176, '3218', 'Y', 'Kab. Pangandaran', '32'),
(177, '3271', 'Y', 'Kota Bogor', '32'),
(178, '3272', 'Y', 'Kota Sukabumi', '32'),
(179, '3273', 'Y', 'Kota Bandung', '32'),
(180, '3274', 'Y', 'Kota Cirebon', '32'),
(181, '3275', 'Y', 'Kota Bekasi', '32'),
(182, '3276', 'Y', 'Kota Depok', '32'),
(183, '3277', 'Y', 'Kota Cimahi', '32'),
(184, '3278', 'Y', 'Kota Tasikmalaya', '32'),
(185, '3279', 'Y', 'Kota Banjar', '32'),
(186, '3301', 'Y', 'Kab. Cilacap', '33'),
(187, '3302', 'Y', 'Kab. Banyumas', '33'),
(188, '3303', 'Y', 'Kab. Purbalingga', '33'),
(189, '3304', 'Y', 'Kab. Banjarnegara', '33'),
(190, '3305', 'Y', 'Kab. Kebumen', '33'),
(191, '3306', 'Y', 'Kab. Purworejo', '33'),
(192, '3307', 'Y', 'Kab. Wonosobo', '33'),
(193, '3308', 'Y', 'Kab. Magelang', '33'),
(194, '3309', 'Y', 'Kab. Boyolali', '33'),
(195, '3310', 'Y', 'Kab. Klaten', '33'),
(196, '3311', 'Y', 'Kab. Sukoharjo', '33'),
(197, '3312', 'Y', 'Kab. Wonogiri', '33'),
(198, '3313', 'Y', 'Kab. Karanganyar', '33'),
(199, '3314', 'Y', 'Kab. Sragen', '33'),
(200, '3315', 'Y', 'Kab. Grobogan', '33'),
(201, '3316', 'Y', 'Kab. Blora', '33'),
(202, '3317', 'Y', 'Kab. Rembang', '33'),
(203, '3318', 'Y', 'Kab. Pati', '33'),
(204, '3319', 'Y', 'Kab. Kudus', '33'),
(205, '3320', 'Y', 'Kab. Jepara', '33'),
(206, '3321', 'Y', 'Kab. Demak', '33'),
(207, '3322', 'Y', 'Kab. Semarang', '33'),
(208, '3323', 'Y', 'Kab. Temanggung', '33'),
(209, '3324', 'Y', 'Kab. Kendal', '33'),
(210, '3325', 'Y', 'Kab. Batang', '33'),
(211, '3326', 'Y', 'Kab. Pekalongan', '33'),
(212, '3327', 'Y', 'Kab. Pemalang', '33'),
(213, '3328', 'Y', 'Kab. Tegal', '33'),
(214, '3329', 'Y', 'Kab. Brebes', '33'),
(215, '3371', 'Y', 'Kota Magelang', '33'),
(216, '3372', 'Y', 'Kota Surakarta', '33'),
(217, '3373', 'Y', 'Kota Salatiga', '33'),
(218, '3374', 'Y', 'Kota Semarang', '33'),
(219, '3375', 'Y', 'Kota Pekalongan', '33'),
(220, '3376', 'Y', 'Kota Tegal', '33'),
(221, '3401', 'Y', 'Kab. Kulon Progo', '34'),
(222, '3402', 'Y', 'Kab. Bantul', '34'),
(223, '3403', 'Y', 'Kab. Gunung Kidul', '34'),
(224, '3404', 'Y', 'Kab. Sleman', '34'),
(225, '3471', 'Y', 'Kota Yogyakarta', '34'),
(226, '3501', 'Y', 'Kab. Pacitan', '35'),
(227, '3502', 'Y', 'Kab. Ponorogo', '35'),
(228, '3503', 'Y', 'Kab. Trenggalek', '35'),
(229, '3504', 'Y', 'Kab. Tulungagung', '35'),
(230, '3505', 'Y', 'Kab. Blitar', '35'),
(231, '3506', 'Y', 'Kab. Kediri', '35'),
(232, '3507', 'Y', 'Kab. Malang', '35'),
(233, '3508', 'Y', 'Kab. Lumajang', '35'),
(234, '3509', 'Y', 'Kab. Jember', '35'),
(235, '3510', 'Y', 'Kab. Banyuwangi', '35'),
(236, '3511', 'Y', 'Kab. Bondowoso', '35'),
(237, '3512', 'Y', 'Kab. Situbondo', '35'),
(238, '3513', 'Y', 'Kab. Probolinggo', '35'),
(239, '3514', 'Y', 'Kab. Pasuruan', '35'),
(240, '3515', 'Y', 'Kab. Sidoarjo', '35'),
(241, '3516', 'Y', 'Kab. Mojokerto', '35'),
(242, '3517', 'Y', 'Kab. Jombang', '35'),
(243, '3518', 'Y', 'Kab. Nganjuk', '35'),
(244, '3519', 'Y', 'Kab. Madiun', '35'),
(245, '3520', 'Y', 'Kab. Magetan', '35'),
(246, '3521', 'Y', 'Kab. Ngawi', '35'),
(247, '3522', 'Y', 'Kab. Bojonegoro', '35'),
(248, '3523', 'Y', 'Kab. Tuban', '35'),
(249, '3524', 'Y', 'Kab. Lamongan', '35'),
(250, '3525', 'Y', 'Kab. Gresik', '35'),
(251, '3526', 'Y', 'Kab. Bangkalan', '35'),
(252, '3527', 'Y', 'Kab. Sampang', '35'),
(253, '3528', 'Y', 'Kab. Pamekasan', '35'),
(254, '3529', 'Y', 'Kab. Sumenep', '35'),
(255, '3571', 'Y', 'Kota Kediri', '35'),
(256, '3572', 'Y', 'Kota Blitar', '35'),
(257, '3573', 'Y', 'Kota Malang', '35'),
(258, '3574', 'Y', 'Kota Probolinggo', '35'),
(259, '3575', 'Y', 'Kota Pasuruan', '35'),
(260, '3576', 'Y', 'Kota Mojokerto', '35'),
(261, '3577', 'Y', 'Kota Madiun', '35'),
(262, '3578', 'Y', 'Kota Surabaya', '35'),
(263, '3579', 'Y', 'Kota Batu', '35'),
(264, '3601', 'Y', 'Kab. Pandeglang', '36'),
(265, '3602', 'Y', 'Kab. Lebak', '36'),
(266, '3603', 'Y', 'Kab. Tangerang', '36'),
(267, '3604', 'Y', 'Kab. Serang', '36'),
(268, '3671', 'Y', 'Kota Tangerang', '36'),
(269, '3672', 'Y', 'Kota Cilegon', '36'),
(270, '3673', 'Y', 'Kota Serang', '36'),
(271, '3674', 'Y', 'Kota Tangerang Selatan', '36'),
(272, '5101', 'Y', 'Kab. Jembrana', '51'),
(273, '5102', 'Y', 'Kab. Tabanan', '51'),
(274, '5103', 'Y', 'Kab. Badung', '51'),
(275, '5104', 'Y', 'Kab. Gianyar', '51'),
(276, '5105', 'Y', 'Kab. Klungkung', '51'),
(277, '5106', 'Y', 'Kab. Bangli', '51'),
(278, '5107', 'Y', 'Kab. Karang Asem', '51'),
(279, '5108', 'Y', 'Kab. Buleleng', '51'),
(280, '5171', 'Y', 'Kota Denpasar', '51'),
(281, '5201', 'Y', 'Kab. Lombok Barat', '52'),
(282, '5202', 'Y', 'Kab. Lombok Tengah', '52'),
(283, '5203', 'Y', 'Kab. Lombok Timur', '52'),
(284, '5204', 'Y', 'Kab. Sumbawa', '52'),
(285, '5205', 'Y', 'Kab. Dompu', '52'),
(286, '5206', 'Y', 'Kab. Bima', '52'),
(287, '5207', 'Y', 'Kab. Sumbawa Barat', '52'),
(288, '5208', 'Y', 'Kab. Lombok Utara', '52'),
(289, '5271', 'Y', 'Kota Mataram', '52'),
(290, '5272', 'Y', 'Kota Bima', '52'),
(291, '5301', 'Y', 'Kab. Sumba Barat', '53'),
(292, '5302', 'Y', 'Kab. Sumba Timur', '53'),
(293, '5303', 'Y', 'Kab. Kupang', '53'),
(294, '5304', 'Y', 'Kab. Timor Tengah Selatan', '53'),
(295, '5305', 'Y', 'Kab. Timor Tengah Utara', '53'),
(296, '5306', 'Y', 'Kab. Belu', '53'),
(297, '5307', 'Y', 'Kab. Alor', '53'),
(298, '5308', 'Y', 'Kab. Lembata', '53'),
(299, '5309', 'Y', 'Kab. Flores Timur', '53'),
(300, '5310', 'Y', 'Kab. Sikka', '53'),
(301, '5311', 'Y', 'Kab. Ende', '53'),
(302, '5312', 'Y', 'Kab. Ngada', '53'),
(303, '5313', 'Y', 'Kab. Manggarai', '53'),
(304, '5314', 'Y', 'Kab. Rote Ndao', '53'),
(305, '5315', 'Y', 'Kab. Manggarai Barat', '53'),
(306, '5316', 'Y', 'Kab. Sumba Tengah', '53'),
(307, '5317', 'Y', 'Kab. Sumba Barat Daya', '53'),
(308, '5318', 'Y', 'Kab. Nagekeo', '53'),
(309, '5319', 'Y', 'Kab. Manggarai Timur', '53'),
(310, '5320', 'Y', 'Kab. Sabu Raijua', '53'),
(311, '5371', 'Y', 'Kota Kupang', '53'),
(312, '6101', 'Y', 'Kab. Sambas', '61'),
(313, '6102', 'Y', 'Kab. Bengkayang', '61'),
(314, '6103', 'Y', 'Kab. Landak', '61'),
(315, '6104', 'Y', 'Kab. Pontianak', '61'),
(316, '6105', 'Y', 'Kab. Sanggau', '61'),
(317, '6106', 'Y', 'Kab. Ketapang', '61'),
(318, '6107', 'Y', 'Kab. Sintang', '61'),
(319, '6108', 'Y', 'Kab. Kapuas Hulu', '61'),
(320, '6109', 'Y', 'Kab. Sekadau', '61'),
(321, '6110', 'Y', 'Kab. Melawi', '61'),
(322, '6111', 'Y', 'Kab. Kayong Utara', '61'),
(323, '6112', 'Y', 'Kab. Kubu Raya', '61'),
(324, '6171', 'Y', 'Kota Pontianak', '61'),
(325, '6172', 'Y', 'Kota Singkawang', '61'),
(326, '6201', 'Y', 'Kab. Kotawaringin Barat', '62'),
(327, '6202', 'Y', 'Kab. Kotawaringin Timur', '62'),
(328, '6203', 'Y', 'Kab. Kapuas', '62'),
(329, '6204', 'Y', 'Kab. Barito Selatan', '62'),
(330, '6205', 'Y', 'Kab. Barito Utara', '62'),
(331, '6206', 'Y', 'Kab. Sukamara', '62'),
(332, '6207', 'Y', 'Kab. Lamandau', '62'),
(333, '6208', 'Y', 'Kab. Seruyan', '62'),
(334, '6209', 'Y', 'Kab. Katingan', '62'),
(335, '6210', 'Y', 'Kab. Pulang Pisau', '62'),
(336, '6211', 'Y', 'Kab. Gunung Mas', '62'),
(337, '6212', 'Y', 'Kab. Barito Timur', '62'),
(338, '6213', 'Y', 'Kab. Murung Raya', '62'),
(339, '6271', 'Y', 'Kota Palangka Raya', '62'),
(340, '6301', 'Y', 'Kab. Tanah Laut', '63'),
(341, '6302', 'Y', 'Kab. Kota Baru', '63'),
(342, '6303', 'Y', 'Kab. Banjar', '63'),
(343, '6304', 'Y', 'Kab. Barito Kuala', '63'),
(344, '6305', 'Y', 'Kab. Tapin', '63'),
(345, '6306', 'Y', 'Kab. Hulu Sungai Selatan', '63'),
(346, '6307', 'Y', 'Kab. Hulu Sungai Tengah', '63'),
(347, '6308', 'Y', 'Kab. Hulu Sungai Utara', '63'),
(348, '6309', 'Y', 'Kab. Tabalong', '63'),
(349, '6310', 'Y', 'Kab. Tanah Bumbu', '63'),
(350, '6311', 'Y', 'Kab. Balangan', '63'),
(351, '6371', 'Y', 'Kota Banjarmasin', '63'),
(352, '6372', 'Y', 'Kota Banjar Baru', '63'),
(353, '6401', 'Y', 'Kab. Paser', '64'),
(354, '6402', 'Y', 'Kab. Kutai Barat', '64'),
(355, '6403', 'Y', 'Kab. Kutai Kartanegara', '64'),
(356, '6404', 'Y', 'Kab. Kutai Timur', '64'),
(357, '6405', 'Y', 'Kab. Berau', '64'),
(358, '6409', 'Y', 'Kab. Penajam Paser Utara', '64'),
(359, '6471', 'Y', 'Kota Balikpapan', '64'),
(360, '6472', 'Y', 'Kota Samarinda', '64'),
(361, '6474', 'Y', 'Kota Bontang', '64'),
(362, '6501', 'Y', 'Kab. Malinau', '65'),
(363, '6502', 'Y', 'Kab. Bulungan', '65'),
(364, '6503', 'Y', 'Kab. Tana Tidung', '65'),
(365, '6504', 'Y', 'Kab. Nunukan', '65'),
(366, '6571', 'Y', 'Kota Tarakan', '65'),
(367, '7101', 'Y', 'Kab. Bolaang Mongondow', '71'),
(368, '7102', 'Y', 'Kab. Minahasa', '71'),
(369, '7103', 'Y', 'Kab. Kepulauan Sangihe', '71'),
(370, '7104', 'Y', 'Kab. Kepulauan Talaud', '71'),
(371, '7105', 'Y', 'Kab. Minahasa Selatan', '71'),
(372, '7106', 'Y', 'Kab. Minahasa Utara', '71'),
(373, '7107', 'Y', 'Kab. Bolaang Mongondow Utara', '71'),
(374, '7108', 'Y', 'Kab. Siau Tagulandang Biaro', '71'),
(375, '7109', 'Y', 'Kab. Minahasa Tenggara', '71'),
(376, '7110', 'Y', 'Kab. Bolaang Mongondow Selatan', '71'),
(377, '7111', 'Y', 'Kab. Bolaang Mongondow Timur', '71'),
(378, '7171', 'Y', 'Kota Manado', '71'),
(379, '7172', 'Y', 'Kota Bitung', '71'),
(380, '7173', 'Y', 'Kota Tomohon', '71'),
(381, '7174', 'Y', 'Kota Kotamobagu', '71'),
(382, '7201', 'Y', 'Kab. Banggai Kepulauan', '72'),
(383, '7202', 'Y', 'Kab. Banggai', '72'),
(384, '7203', 'Y', 'Kab. Morowali', '72'),
(385, '7204', 'Y', 'Kab. Poso', '72'),
(386, '7205', 'Y', 'Kab. Donggala', '72'),
(387, '7206', 'Y', 'Kab. Toli-toli', '72'),
(388, '7207', 'Y', 'Kab. Buol', '72'),
(389, '7208', 'Y', 'Kab. Parigi Moutong', '72'),
(390, '7209', 'Y', 'Kab. Tojo Una-una', '72'),
(391, '7210', 'Y', 'Kab. Sigi', '72'),
(392, '7271', 'Y', 'Kota Palu', '72'),
(393, '7301', 'Y', 'Kab. Kepulauan Selayar', '73'),
(394, '7302', 'Y', 'Kab. Bulukumba', '73'),
(395, '7303', 'Y', 'Kab. Bantaeng', '73'),
(396, '7304', 'Y', 'Kab. Jeneponto', '73'),
(397, '7305', 'Y', 'Kab. Takalar', '73'),
(398, '7306', 'Y', 'Kab. Gowa', '73'),
(399, '7307', 'Y', 'Kab. Sinjai', '73'),
(400, '7308', 'Y', 'Kab. Maros', '73'),
(401, '7309', 'Y', 'Kab. Pangkajene Dan Kepulauan', '73'),
(402, '7310', 'Y', 'Kab. Barru', '73'),
(403, '7311', 'Y', 'Kab. Bone', '73'),
(404, '7312', 'Y', 'Kab. Soppeng', '73'),
(405, '7313', 'Y', 'Kab. Wajo', '73'),
(406, '7314', 'Y', 'Kab. Sidenreng Rappang', '73'),
(407, '7315', 'Y', 'Kab. Pinrang', '73'),
(408, '7316', 'Y', 'Kab. Enrekang', '73'),
(409, '7317', 'Y', 'Kab. Luwu', '73'),
(410, '7318', 'Y', 'Kab. Tana Toraja', '73'),
(411, '7322', 'Y', 'Kab. Luwu Utara', '73'),
(412, '7325', 'Y', 'Kab. Luwu Timur', '73'),
(413, '7326', 'Y', 'Kab. Toraja Utara', '73'),
(414, '7371', 'Y', 'Kota Makassar', '73'),
(415, '7372', 'Y', 'Kota Parepare', '73'),
(416, '7373', 'Y', 'Kota Palopo', '73'),
(417, '7401', 'Y', 'Kab. Buton', '74'),
(418, '7402', 'Y', 'Kab. Muna', '74'),
(419, '7403', 'Y', 'Kab. Konawe', '74'),
(420, '7404', 'Y', 'Kab. Kolaka', '74'),
(421, '7405', 'Y', 'Kab. Konawe Selatan', '74'),
(422, '7406', 'Y', 'Kab. Bombana', '74'),
(423, '7407', 'Y', 'Kab. Wakatobi', '74'),
(424, '7408', 'Y', 'Kab. Kolaka Utara', '74'),
(425, '7409', 'Y', 'Kab. Buton Utara', '74'),
(426, '7410', 'Y', 'Kab. Konawe Utara', '74'),
(427, '7471', 'Y', 'Kota Kendari', '74'),
(428, '7472', 'Y', 'Kota Baubau', '74'),
(429, '7501', 'Y', 'Kab. Boalemo', '75'),
(430, '7502', 'Y', 'Kab. Gorontalo', '75'),
(431, '7503', 'Y', 'Kab. Pohuwato', '75'),
(432, '7504', 'Y', 'Kab. Bone Bolango', '75'),
(433, '7505', 'Y', 'Kab. Gorontalo Utara', '75'),
(434, '7571', 'Y', 'Kota Gorontalo', '75'),
(435, '7601', 'Y', 'Kab. Majene', '76'),
(436, '7602', 'Y', 'Kab. Polewali Mandar', '76'),
(437, '7603', 'Y', 'Kab. Mamasa', '76'),
(438, '7604', 'Y', 'Kab. Mamuju', '76'),
(439, '7605', 'Y', 'Kab. Mamuju Utara', '76'),
(440, '8101', 'Y', 'Kab. Maluku Tenggara Barat', '81'),
(441, '8102', 'Y', 'Kab. Maluku Tenggara', '81'),
(442, '8103', 'Y', 'Kab. Maluku Tengah', '81'),
(443, '8104', 'Y', 'Kab. Buru', '81'),
(444, '8105', 'Y', 'Kab. Kepulauan Aru', '81'),
(445, '8106', 'Y', 'Kab. Seram Bagian Barat', '81'),
(446, '8107', 'Y', 'Kab. Seram Bagian Timur', '81'),
(447, '8108', 'Y', 'Kab. Maluku Barat Daya', '81'),
(448, '8109', 'Y', 'Kab. Buru Selatan', '81'),
(449, '8171', 'Y', 'Kota Ambon', '81'),
(450, '8172', 'Y', 'Kota Tual', '81'),
(451, '8201', 'Y', 'Kab. Halmahera Barat', '82'),
(452, '8202', 'Y', 'Kab. Halmahera Tengah', '82'),
(453, '8203', 'Y', 'Kab. Kepulauan Sula', '82'),
(454, '8204', 'Y', 'Kab. Halmahera Selatan', '82'),
(455, '8205', 'Y', 'Kab. Halmahera Utara', '82'),
(456, '8206', 'Y', 'Kab. Halmahera Timur', '82'),
(457, '8207', 'Y', 'Kab. Pulau Morotai', '82'),
(458, '8271', 'Y', 'Kota Ternate', '82'),
(459, '8272', 'Y', 'Kota Tidore Kepulauan', '82'),
(460, '9101', 'Y', 'Kab. Fakfak', '91'),
(461, '9102', 'Y', 'Kab. Kaimana', '91'),
(462, '9103', 'Y', 'Kab. Teluk Wondama', '91'),
(463, '9104', 'Y', 'Kab. Teluk Bintuni', '91'),
(464, '9105', 'Y', 'Kab. Manokwari', '91'),
(465, '9106', 'Y', 'Kab. Sorong Selatan', '91'),
(466, '9107', 'Y', 'Kab. Sorong', '91'),
(467, '9108', 'Y', 'Kab. Raja Ampat', '91'),
(468, '9109', 'Y', 'Kab. Tambrauw', '91'),
(469, '9110', 'Y', 'Kab. Maybrat', '91'),
(470, '9171', 'Y', 'Kota Sorong', '91'),
(471, '9401', 'Y', 'Kab. Merauke', '94'),
(472, '9402', 'Y', 'Kab. Jayawijaya', '94'),
(473, '9403', 'Y', 'Kab. Jayapura', '94'),
(474, '9404', 'Y', 'Kab. Nabire', '94'),
(475, '9408', 'Y', 'Kab. Kepulauan Yapen', '94'),
(476, '9409', 'Y', 'Kab. Biak Numfor', '94'),
(477, '9410', 'Y', 'Kab. Paniai', '94'),
(478, '9411', 'Y', 'Kab. Puncak Jaya', '94'),
(479, '9412', 'Y', 'Kab. Mimika', '94'),
(480, '9413', 'Y', 'Kab. Boven Digoel', '94'),
(481, '9414', 'Y', 'Kab. Mappi', '94'),
(482, '9415', 'Y', 'Kab. Asmat', '94'),
(483, '9416', 'Y', 'Kab. Yahukimo', '94'),
(484, '9417', 'Y', 'Kab. Pegunungan Bintang', '94'),
(485, '9418', 'Y', 'Kab. Tolikara', '94'),
(486, '9419', 'Y', 'Kab. Sarmi', '94'),
(487, '9420', 'Y', 'Kab. Keerom', '94'),
(488, '9426', 'Y', 'Kab. Waropen', '94'),
(489, '9427', 'Y', 'Kab. Supiori', '94'),
(490, '9428', 'Y', 'Kab. Mamberamo Raya', '94'),
(491, '9429', 'Y', 'Kab. Nduga', '94'),
(492, '9430', 'Y', 'Kab. Lanny Jaya', '94'),
(493, '9431', 'Y', 'Kab. Mamberamo Tengah', '94'),
(494, '9432', 'Y', 'Kab. Yalimo', '94'),
(495, '9433', 'Y', 'Kab. Puncak', '94'),
(496, '9434', 'Y', 'Kab. Dogiyai', '94'),
(497, '9435', 'Y', 'Kab. Intan Jaya', '94'),
(498, '9436', 'Y', 'Kab. Deiyai', '94'),
(499, '9471', 'Y', 'Kota Jayapura', '94');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;