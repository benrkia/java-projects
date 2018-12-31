-- phpMyAdmin SQL Dump
-- version 4.7.2
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:3306
-- Généré le :  lun. 31 déc. 2018 à 16:08
-- Version du serveur :  5.6.35
-- Version de PHP :  7.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Base de données :  `market`
--

-- --------------------------------------------------------

--
-- Structure de la table `address`
--

CREATE TABLE `address` (
  `id` int(11) NOT NULL,
  `street` varchar(50) NOT NULL,
  `city` int(11) NOT NULL,
  `country` int(11) NOT NULL,
  `postalcode` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `address`
--

INSERT INTO `address` (`id`, `street`, `city`, `country`, `postalcode`) VALUES
(15, 'amal1', 65, 10, 10400),
(24, 'AMAL 0', 46, 8, 10400),
(25, 'streeeeet', 56, 9, 15000);

-- --------------------------------------------------------

--
-- Structure de la table `cart`
--

CREATE TABLE `cart` (
  `id` int(11) NOT NULL,
  `creationdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `cart`
--

INSERT INTO `cart` (`id`, `creationdate`) VALUES
(13, '2018-12-05 00:43:19');

-- --------------------------------------------------------

--
-- Structure de la table `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `category`
--

INSERT INTO `category` (`id`, `name`, `description`) VALUES
(2, 'Phones', 'category description'),
(3, 'Apparel', 'category description'),
(4, 'Home & Garden', 'category description'),
(5, 'Electronics', 'category description');

-- --------------------------------------------------------

--
-- Structure de la table `city`
--

CREATE TABLE `city` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `country` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `city`
--

INSERT INTO `city` (`id`, `name`, `country`) VALUES
(1, 'alriyadh', 1),
(2, 'mecca', 1),
(3, 'jeddah', 1),
(10, 'kuwait', 3),
(11, 'jahra', 3),
(12, 'Abu Dhabi', 4),
(13, 'Dubai', 4),
(14, 'Sharjah', 4),
(15, 'Umm Al Quwain', 4),
(16, 'Ras Al Khaimah', 4),
(17, 'Ajman', 4),
(18, 'Other', 4),
(19, 'Madina El Monawara', 1),
(20, 'Qassim', 1),
(21, 'Dammam', 1),
(22, 'Kuaber', 1),
(23, 'Taif', 1),
(24, 'Alkarej', 1),
(25, 'Tabuk', 1),
(26, 'Manama', 5),
(27, 'Muharraq', 5),
(28, 'Riffa', 5),
(29, 'Other', 5),
(30, 'Doha', 6),
(31, 'Khor', 6),
(32, 'Wakra', 6),
(33, 'Rayan', 6),
(34, 'Juryan', 6),
(35, 'Umm Salal', 6),
(36, 'Alshemal', 6),
(37, 'Muscat', 7),
(38, 'Sohar', 7),
(39, 'Dhofar', 7),
(40, 'Buraimi', 7),
(41, 'Salalah', 7),
(42, 'Nizwa', 7),
(43, 'Sur', 7),
(44, 'Hamad City', 5),
(45, 'Cairo', 8),
(46, 'Alexandria', 8),
(47, 'Albuhyrah', 8),
(48, 'Dakahlia', 8),
(49, 'Alssaied', 8),
(50, 'Algharbia', 8),
(51, 'Alsharqia', 8),
(52, 'Amman', 9),
(53, 'Balqa', 9),
(54, 'Irbid', 9),
(55, 'Jerash', 9),
(56, 'Alzzuraqa', 9),
(57, 'Aqaba', 9),
(58, 'Kerak', 9),
(59, 'Baghdad', 10),
(60, 'Arbil', 10),
(61, 'Anbar', 10),
(62, 'Basra', 10),
(63, 'Sulaimaniya', 10),
(64, 'Salahuddin', 10),
(65, 'Kirkuk', 10),
(66, 'Khartoum', 11),
(67, 'Northern State', 11),
(68, ' Nileazraq', 11),
(69, 'Darfur', 11),
(70, 'Omdurman', 11),
(71, ' Red sea', 11),
(72, 'Kordofan', 11),
(73, 'Hail', 1),
(74, 'Abha', 1),
(75, 'Baha', 1),
(76, 'Jizan', 1),
(77, 'Najran', 1),
(78, 'Khamis Mushayt', 1),
(79, 'Alhudud Alshamalia', 1),
(80, 'Yanbu', 1),
(81, 'Jubail', 1),
(82, 'Al-Jouf', 1),
(83, 'Hafr Al-Batin', 1),
(84, 'Al-Ahsa', 1),
(85, 'Arar', 1),
(86, 'Qatif', 1),
(87, 'Buraydah', 1),
(88, 'Onaizah', 1),
(89, 'Quwaiya', 1),
(90, 'Dawadmi', 1),
(91, 'Diriyah', 1),
(92, 'Almzahmiyah', 1),
(93, 'Wadi Alddwaser', 1),
(94, 'Ismailia  ', 8),
(95, 'Luxor', 8),
(96, 'Aswan', 8),
(97, 'Asuat', 8),
(98, 'The Red Sea', 8),
(99, 'Beni Suef ', 8),
(100, 'Port Said', 8),
(101, 'Damiette      ', 8),
(102, 'Suez', 8),
(103, 'fayoum', 8),
(104, 'Qena', 8),
(105, 'matruh', 8),
(106, 'menia', 8),
(107, 'Wadi Algadeed', 8),
(108, 'Cena', 8),
(109, 'qaliubiya ', 8),
(110, 'Aflaj', 1),
(111, 'Al Ain      ', 4),
(112, 'Zayed City', 4),
(113, 'Khorefkan', 4),
(114, 'Kalba', 4),
(115, 'Liwa', 4),
(116, 'Ghayathi', 4),
(117, 'Ruwais', 4),
(118, 'Ruwais  ', 4),
(119, 'Alrwais', 4),
(120, 'Dibba', 4),
(121, ' Al Dhaid', 4),
(122, 'Falaj Almalla', 4),
(123, 'Alsulmah', 4),
(124, 'Alraafh', 4),
(125, 'Masfoot', 4),
(126, 'Manama', 4),
(127, 'Alsulmah', 4),
(128, 'Al Wakra', 6),
(129, 'Batnh', 6),
(130, 'Dasman      ', 3),
(131, 'Eshirq', 3),
(132, 'Al-Sawaber', 3),
(133, 'Almerqab', 3),
(134, 'Algeblah', 3),
(135, 'Salehia', 3),
(136, 'Alwatyah', 3),
(137, 'Mangaf', 3),
(138, 'Fahaheel', 3),
(139, 'Ahmadi', 3),
(140, 'Wafrah', 3),
(141, 'Zour', 3),
(142, 'Karan', 3),
(143, 'Mina Abdullah', 3),
(144, 'Sulaibkhat  ', 3),
(145, 'Nahdah', 3),
(146, ' Jaber Al-Ahmad City', 3),
(147, 'Sabah Al-Ahmad City   ', 3),
(148, 'Nuwaisib', 3),
(149, 'Kiran City', 3),
(150, 'City of Ali Sabah Al-Salem', 3),
(151, 'Andalus    ', 3),
(152, 'Shbilyah', 3),
(153, 'Gleb Shuokh', 3),
(154, 'Khaitan', 3),
(155, 'Qrain', 3),
(156, 'Adm', 7),
(157, 'Hmra', 7),
(158, 'Kaborah', 7),
(159, 'Dakahm', 7),
(160, 'Rastik', 7),
(161, 'Swike', 7),
(162, 'Hima', 7),
(163, 'Seab', 7),
(164, 'Amrat', 7),
(165, 'Brka', 7),
(166, 'Bosher', 7),
(167, 'Thmreat', 7),
(168, 'Kesb', 7),
(169, 'Smail', 7),
(170, 'Hebrew      ', 7),
(171, 'Kiryat', 7),
(172, 'Mhdah', 7),
(173, 'Vezeh', 7),
(174, 'Bahla', 7),
(175, 'Bulead', 7),
(176, 'Duqm', 7);

-- --------------------------------------------------------

--
-- Structure de la table `country`
--

CREATE TABLE `country` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `country`
--

INSERT INTO `country` (`id`, `name`) VALUES
(1, 'saudi arabia'),
(3, 'kuwait'),
(4, 'UAE'),
(5, 'Bahrain'),
(6, 'Qatar'),
(7, 'Oman'),
(8, 'Egypt'),
(9, 'Jordan'),
(10, 'Iraq'),
(11, 'Sudan');

-- --------------------------------------------------------

--
-- Structure de la table `image`
--

CREATE TABLE `image` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `title` varchar(20) NOT NULL,
  `extension` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `image`
--

INSERT INTO `image` (`id`, `name`, `title`, `extension`) VALUES
(2, 'image1', 'product image', 'jpg'),
(3, 'image2', 'product image', 'jpg'),
(4, 'image3', 'product image', 'jpg'),
(5, 'image4', 'product image', 'jpg'),
(12, 'image5', 'product image', 'jpg'),
(16, 'image6', 'product image', 'jpg'),
(20, 'image7', 'product image', 'jpg');

-- --------------------------------------------------------

--
-- Structure de la table `linecmd`
--

CREATE TABLE `linecmd` (
  `id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` float NOT NULL,
  `linecmdtype` tinyint(4) NOT NULL,
  `product` int(11) NOT NULL,
  `order` int(11) DEFAULT NULL,
  `cart` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `linecmd`
--

INSERT INTO `linecmd` (`id`, `quantity`, `price`, `linecmdtype`, `product`, `order`, `cart`) VALUES
(59, 8, 103.92, 2, 8, 23, NULL),
(61, 7, 90.93, 2, 8, 24, NULL),
(64, 4, 61.68, 2, 6, 25, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `order`
--

CREATE TABLE `order` (
  `id` int(11) NOT NULL,
  `creationdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ordertype` tinyint(4) NOT NULL,
  `shippingaddress` int(11) NOT NULL,
  `totalprice` float NOT NULL,
  `commercial` int(11) DEFAULT NULL,
  `client` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `order`
--

INSERT INTO `order` (`id`, `creationdate`, `ordertype`, `shippingaddress`, `totalprice`, `commercial`, `client`) VALUES
(23, '2018-12-31 14:53:27', 1, 24, 103.92, 1, 8),
(24, '2018-12-31 14:56:27', 1, 24, 90.93, 1, 8),
(25, '2018-12-31 15:03:34', 1, 24, 61.68, 1, 8);

-- --------------------------------------------------------

--
-- Structure de la table `person`
--

CREATE TABLE `person` (
  `id` int(11) NOT NULL,
  `firstname` varchar(20) NOT NULL,
  `lastname` varchar(20) NOT NULL,
  `birthdate` date NOT NULL,
  `email` varchar(70) NOT NULL,
  `usertype` tinyint(4) NOT NULL,
  `address` int(11) DEFAULT NULL,
  `cart` int(11) DEFAULT NULL,
  `password` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `person`
--

INSERT INTO `person` (`id`, `firstname`, `lastname`, `birthdate`, `email`, `usertype`, `address`, `cart`, `password`) VALUES
(1, 'ilyasse', 'benrkia', '1997-09-10', 'benrkyailyass@gmail.com', 1, 15, NULL, '`�3�[�l���Rh�Q��y0?�a��>3���m����Fd/B�d��t9��ncJ�\r\nR\r\nV@�)'),
(8, 'mohammed', 'benrkia', '2007-11-20', 'mohammedbenrkia@gmail.com', 2, 24, 13, '`�3�[�l���Rh�Q��y0?�a��>3���m����Fd/B�d��t9��ncJ�\r\nR\r\nV@�)');

-- --------------------------------------------------------

--
-- Structure de la table `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` text NOT NULL,
  `sellingprice` float NOT NULL,
  `buyingprice` float NOT NULL,
  `quantity` int(11) NOT NULL,
  `creationdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `commercial` int(11) NOT NULL,
  `category` int(11) NOT NULL,
  `image` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `product`
--

INSERT INTO `product` (`id`, `name`, `description`, `sellingprice`, `buyingprice`, `quantity`, `creationdate`, `commercial`, `category`, `image`) VALUES
(2, 'Stylish Turn-down Collar', 'Men\'s Coats Stylish Turn-down Collar Comfort Warm - DARK GRAY XL\r\n', 23.58, 20.58, 14, '2018-12-03 20:49:06', 1, 3, 2),
(3, 'Men\'s Parka Thickening ', 'Men\'s Parka Thickening Plus Velvet Warm Hooded Cotton Clothing Large Size - BLUE L\r\n', 24.99, 20.99, 4, '2018-12-03 20:49:06', 1, 3, 4),
(4, '50000mAh Power Bank', '50000mAh Power Bank External Battery Fast charge Dual USB Power bank Portable Mobile phone Charger - BLUE 10MAH', 7.06, 5.06, 92, '2018-12-03 20:52:00', 1, 5, 12),
(5, 'Waterproof Sofa Cover', 'Waterproof Thick Solid Color Sofa Cover Universal Sleeve Elastic Combination Three-person Sofa Cover Four Seasons Amoy - BLACK WATERPROOF SOFA COVER 45X45 PILLOWCASE / ONE', 13.12, 11.12, 12, '2018-12-03 20:52:00', 1, 4, 16),
(6, 'Lace Table Cloth', 'Lace Table Cloth Rectangular Household Bronzing Pvc Tablecloth Waterproof And Oilproof Plastic Coffee Table Cloth European Disposable - 110CM*140CM WHITE', 15.42, 12.42, 182, '2018-12-03 20:53:11', 1, 4, 20),
(7, 'Coat Jacket', 'Loose Comfortable Fashion Coat Jacket - ORANGE L', 15.99, 13, 22, '2018-12-03 21:12:30', 1, 3, 3),
(8, 'Leisure Hoodie', 'Leisure Hoodie Long Sleeve Fleece for Men - BATTLESHIP GRAY L\r\n', 12.99, 10.99, 123, '2018-12-03 21:13:08', 1, 3, 5);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `address`
--
ALTER TABLE `address`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_RELATION_ADDRESS_CITY` (`city`),
  ADD KEY `FK_RELATION_ADDRESS_COUNTRY` (`country`);

--
-- Index pour la table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `city`
--
ALTER TABLE `city`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `country`
--
ALTER TABLE `country`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `image`
--
ALTER TABLE `image`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `linecmd`
--
ALTER TABLE `linecmd`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_RELATION_LINECMD_CART` (`cart`),
  ADD KEY `FK_RELATION_LINECMD_ORDER` (`order`),
  ADD KEY `FK_RELATION_LINECMD_PRODUCT` (`product`);

--
-- Index pour la table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_RELATION_ORDER_ADDRESS` (`shippingaddress`),
  ADD KEY `FK_RELATION_ORDER_COMMERCIAL` (`commercial`),
  ADD KEY `FK_RELATION_ORDER_CLIENT` (`client`);

--
-- Index pour la table `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `FK_RELATION_PERSON_CART` (`cart`),
  ADD KEY `FK_RELATION_PERSON_ADDRESS` (`address`);

--
-- Index pour la table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_RELATION_PRODUCT_CATEGORY` (`category`),
  ADD KEY `FK_RELATION_PRODUCT_COMMERCIAL` (`commercial`),
  ADD KEY `FK_RELATION_PRODUCT_IMAGE` (`image`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `address`
--
ALTER TABLE `address`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;
--
-- AUTO_INCREMENT pour la table `cart`
--
ALTER TABLE `cart`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT pour la table `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT pour la table `city`
--
ALTER TABLE `city`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=177;
--
-- AUTO_INCREMENT pour la table `country`
--
ALTER TABLE `country`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT pour la table `image`
--
ALTER TABLE `image`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
--
-- AUTO_INCREMENT pour la table `linecmd`
--
ALTER TABLE `linecmd`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=65;
--
-- AUTO_INCREMENT pour la table `order`
--
ALTER TABLE `order`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;
--
-- AUTO_INCREMENT pour la table `person`
--
ALTER TABLE `person`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT pour la table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `address`
--
ALTER TABLE `address`
  ADD CONSTRAINT `FK_RELATION_ADDRESS_CITY` FOREIGN KEY (`city`) REFERENCES `city` (`id`),
  ADD CONSTRAINT `FK_RELATION_ADDRESS_COUNTRY` FOREIGN KEY (`country`) REFERENCES `country` (`id`);

--
-- Contraintes pour la table `linecmd`
--
ALTER TABLE `linecmd`
  ADD CONSTRAINT `FK_RELATION_LINECMD_CART` FOREIGN KEY (`cart`) REFERENCES `cart` (`id`),
  ADD CONSTRAINT `FK_RELATION_LINECMD_ORDER` FOREIGN KEY (`order`) REFERENCES `order` (`id`),
  ADD CONSTRAINT `FK_RELATION_LINECMD_PRODUCT` FOREIGN KEY (`product`) REFERENCES `product` (`id`);

--
-- Contraintes pour la table `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `FK_RELATION_ORDER_ADDRESS` FOREIGN KEY (`shippingaddress`) REFERENCES `address` (`id`),
  ADD CONSTRAINT `FK_RELATION_ORDER_CLIENT` FOREIGN KEY (`client`) REFERENCES `person` (`id`),
  ADD CONSTRAINT `FK_RELATION_ORDER_COMMERCIAL` FOREIGN KEY (`commercial`) REFERENCES `person` (`id`);

--
-- Contraintes pour la table `person`
--
ALTER TABLE `person`
  ADD CONSTRAINT `FK_RELATION_PERSON_ADDRESS` FOREIGN KEY (`address`) REFERENCES `address` (`id`),
  ADD CONSTRAINT `FK_RELATION_PERSON_CART` FOREIGN KEY (`cart`) REFERENCES `cart` (`id`);

--
-- Contraintes pour la table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `FK_RELATION_PRODUCT_CATEGORY` FOREIGN KEY (`category`) REFERENCES `category` (`id`),
  ADD CONSTRAINT `FK_RELATION_PRODUCT_COMMERCIAL` FOREIGN KEY (`commercial`) REFERENCES `person` (`id`),
  ADD CONSTRAINT `FK_RELATION_PRODUCT_IMAGE` FOREIGN KEY (`image`) REFERENCES `image` (`id`);
