-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               11.5.2-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for shardworld
CREATE DATABASE IF NOT EXISTS `shardworld` /*!40100 DEFAULT CHARACTER SET armscii8 COLLATE armscii8_bin */;
USE `shardworld`;

-- Dumping structure for table shardworld.recipes
CREATE TABLE IF NOT EXISTS `recipes` (
  `recipeid` int(11) NOT NULL,
  `recipename` varchar(255) NOT NULL,
  `processdescription` varchar(2500) DEFAULT NULL,
  `calenderwaittimefromproductiontoavailable` int(11) DEFAULT NULL,
  `workTime_times10Minutes` int(11) DEFAULT NULL,
  PRIMARY KEY (`recipeid`)
) ENGINE=InnoDB DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table shardworld.recipes: ~26 rows (approximately)
INSERT INTO `recipes` (`recipeid`, `recipename`, `processdescription`, `calenderwaittimefromproductiontoavailable`, `workTime_times10Minutes`) VALUES
	(0, 'Wood Sawing', 'Using a manual saw, cured timber is cut into blocks of required size', 0, 36),
	(1, 'Primitive Wooden Shue', 'Making a shue of one piece of wood, using only what tools can be found in nature', 0, 24),
	(2, 'Gather Raw Timber', 'Felling trees in the forest, using only what tools can be found in nature', 0, 3),
	(3, 'Primitive Timber Curing', 'Drying wood in the most primitive way, building a small shelter around it while it dries.', 180, 2),
	(4, 'Gather Cured Wood', 'Finding cured blocks of timber amoung deadwood in the forest, and forming into blocks with only what tools can be found in nature', 0, 10),
	(5, 'Gather Firewood', 'Walking around to gather firewood ', 0, 3),
	(6, 'Gather iron ore', 'Walking around to gather iron ore. Ore is expected to be 10% iron.', 0, 12),
	(7, 'Gather copper ore', 'Walking around to gather copper ore', 0, 18),
	(8, 'Gather tin ore', 'Walking around to gather tin ore', 0, 24),
	(9, 'Gather coal', 'Walking around to gather coal', 0, 16),
	(10, 'Primitive copper smelting', 'The ore is crushed using stone tools, and mixed with charcoal in a small furnace. The furnace is a one-use structure, built as a pit furnace, dug into the ground or a small clay furnace. Bellows or blowpipes must be used to reach temperatures of around 1,100 degree Celsius required to melt the copper.  After hours of heating, small droplets of molten copper, known as copper prills, forms and settles at the bottom of the furnace. When the furnace is cooled, these copper prills can be collected.', 0, 72),
	(11, 'Making primitive stone tools', 'Using what can be found in the area, construct simple stone tools', 0, 48),
	(12, 'Making copper tools, using stone tools and no molds', 'Copper prills or scraps are reheated and hammered. This process is called cold hammering or hot forging, and drives out any remaining impurities. This hardens the copper, making it suitable for tools. When the copper is sufficiently pure, it is again heated and hammered to form it into copper tools.', 0, 72),
	(13, 'Making copper tools, using copper tools and molds ', 'Copper prills or scraps are reheated and hammered. This process is called cold hammering or hot forging, and drives out any remaining impurities. This hardens the copper, making it suitable for tools. When the copper is sufficiently pure, it is heated in a furnace with a stone collection bowl. Using wooden tools, the molten copper is poored into molds. The cast copper tool is further refined by hammering, sharpening and polishing. ', 0, 48),
	(14, 'Primitive bloomery iron smelting, using stone tools', 'The ore is broken into smaller pieces to increase the surface area, to improve smelting efficiency. This step is labor-intensive and requires hours of work with stone hammers. A bloomery furnace is built, able to create a partially solid mass of iron called a bloom, but not hot enough to fully melt the iron.  The temperature must reach just above 1,200 degree Celsius to cause the iron reduction. The bloom is then hammered while still hot to consolidate the iron, expelling as much slag as possible. This step helps to compact the iron, reduce impurities, and give it a workable shape. The bloom contains a considerable amount of impurities even after initial hammering, so it must be reheated and hammered multiple times. This repetitive heating and hammering process gradually refines the iron, removing more slag and improving the metal’s consistency and workability.', 0, 200),
	(15, 'Bloomery iron smelting, using iron tools', 'The ore is broken into smaller pieces to increase the surface area, to improve smelting efficiency. This step is labor-intensive and requires hours of work with stone hammers. A bloomery furnace is built, able to create a partially solid mass of iron called a bloom, but not hot enough to fully melt the iron.  The temperature must reach just above 1,200 degree Celsius to cause the iron reduction. The bloom is then hammered while still hot to consolidate the iron, expelling as much slag as possible. This step helps to compact the iron, reduce impurities, and give it a workable shape. The bloom contains a considerable amount of impurities even after initial hammering, so it must be reheated and hammered multiple times. This repetitive heating and hammering process gradually refines the iron, removing more slag and improving the metal’s consistency and workability.', 0, 120),
	(16, 'Primitive Iron Smithing, using stone tools and bloomery furnace', 'Wrought iron is heated in a bloomery furnace, and extracted while still glowing hot. The blacksmith shapes it into the desired tool. If crafting an axe, for example, the blacksmith will flatten one end to form the blade and leave the opposite end thicker for attachment to a handle. Using different tools like hammers, chisels, and tongs, the blacksmith bends, cuts, and shapes the iron while it is hot and malleable. For more complex shapes, they might use a hardy (anvil tool) or specialized dies to achieve finer details. To improve durability, the blacksmith might use a technique known as work hardening, which involves hammering the edges of the tool to strengthen them. Alternatively, they may quench the tool, cooling it quickly in water or oil to harden its surface. This quenching process hardens the iron somewhat, though true tempering for steel is less applicable for pure wrought iron, which remains relatively soft compared to steel. After the tool has its final shape, the blacksmith uses grinding stones or abrasive materials to sharpen and polish the edges. This step enhances the tools cutting ability for items like knives, chisels, or axes.', 0, 100),
	(17, 'Primitive Iron Smithing, using iron tools and bloomery furnace', 'Wrought iron is heated in a bloomery furnace, and extracted while still glowing hot. The blacksmith shapes it into the desired tool. If crafting an axe, for example, the blacksmith will flatten one end to form the blade and leave the opposite end thicker for attachment to a handle. Using different tools like hammers, chisels, and tongs, the blacksmith bends, cuts, and shapes the iron while it is hot and malleable. For more complex shapes, they might use a hardy (anvil tool) or specialized dies to achieve finer details. To improve durability, the blacksmith might use a technique known as work hardening, which involves hammering the edges of the tool to strengthen them. Alternatively, they may quench the tool, cooling it quickly in water or oil to harden its surface. This quenching process hardens the iron somewhat, though true tempering for steel is less applicable for pure wrought iron, which remains relatively soft compared to steel. After the tool has its final shape, the blacksmith uses grinding stones or abrasive materials to sharpen and polish the edges. This step enhances the tools cutting ability for items like knives, chisels, or axes.', 0, 70),
	(18, 'Bloomery carburization', 'During  a bloomery heating, blacksmiths introduce carburization, a process in which iron is exposed to carbon-rich environments to create mild steel, a harder and more resilient material. This primitive form of steel is achieved by heating iron in charcoal for extended periods, allowing carbon to diffuse into the surface.', 0, 60),
	(19, 'Shaft Furnace Crucible Mild Steel Smelting', 'Using a Shaft Furnace, the temperature of the iron can be brought to 1,300-1,500 degree Celsius, high enough to produce molten iron, especially with effective bellows. With this, it is much faster to produce steel in a crucible.', 0, 3200),
	(20, 'Shaft Furnace Wrought Iron Smelting', 'Using a Shaft Furnace, the temperature of the iron can be brought to 1,300-1,500 degree Celsius, high enough to produce molten iron, especially with effective bellows.  With this high temperature, the efficiency of the melting process increases, and requires fewer passes through the heating process to reduce impurities.', 0, 3200),
	(21, 'Shaft Furnace Iron Casting and Smithing of Iron Tools', 'Using a Shaft Furnace, the temperature of the iron can be brought to 1,300-1,500 degree Celsius, high enough to produce molten iron, especially with effective bellows.  This allow production of cast iron, by pouring molten iron into molds for larger-scale production.', 0, 3200),
	(22, 'Shaft Furnace Crucible Hard Steel Smelting', 'Using a Shaft Furnace, the temperature of the iron can be brought to 1,300-1,500 degree Celsius, high enough to produce molten iron, especially with effective bellows. TODO: More description here', 0, 3200),
	(23, 'Primitive Charcoal burning', 'Description', 0, 48),
	(24, 'Primitive Stone Tools', 'Making stone tools with only the tools and ingredients which can be found in nature.', 0, 80),
	(25, 'Primitive Wood Tools', 'Making wooden tools with only the tools which can be found in nature.', 0, 30);

-- Dumping structure for table shardworld.recipe_input_sku
CREATE TABLE IF NOT EXISTS `recipe_input_sku` (
  `recipeid` int(11) NOT NULL,
  `skuid` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  PRIMARY KEY (`recipeid`,`skuid`),
  KEY `skuid` (`skuid`),
  CONSTRAINT `recipe_input_sku_ibfk_1` FOREIGN KEY (`recipeid`) REFERENCES `recipes` (`recipeid`),
  CONSTRAINT `recipe_input_sku_ibfk_2` FOREIGN KEY (`skuid`) REFERENCES `stockkeepunits` (`skuid`)
) ENGINE=InnoDB DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table shardworld.recipe_input_sku: ~36 rows (approximately)
INSERT INTO `recipe_input_sku` (`recipeid`, `skuid`, `amount`) VALUES
	(0, 0, 20),
	(1, 3, 2),
	(3, 0, 15),
	(10, 13, 6),
	(10, 17, 10),
	(10, 54, 3),
	(12, 13, 1),
	(12, 15, 1),
	(12, 58, 2),
	(13, 13, 1),
	(13, 15, 1),
	(14, 13, 18),
	(14, 27, 10),
	(14, 58, 2),
	(15, 13, 18),
	(15, 27, 10),
	(15, 58, 2),
	(16, 13, 12),
	(16, 52, 3),
	(16, 58, 1),
	(17, 13, 14),
	(17, 52, 4),
	(17, 58, 1),
	(18, 13, 18),
	(18, 52, 2),
	(18, 58, 1),
	(19, 13, 240),
	(19, 52, 160),
	(20, 13, 350),
	(20, 27, 1500),
	(21, 13, 240),
	(21, 52, 160),
	(22, 13, 240),
	(22, 52, 160),
	(23, 1, 180),
	(25, 3, 2);

-- Dumping structure for table shardworld.recipe_output_sku
CREATE TABLE IF NOT EXISTS `recipe_output_sku` (
  `recipeid` int(11) NOT NULL,
  `skuid` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  PRIMARY KEY (`recipeid`,`skuid`),
  KEY `skuid` (`skuid`),
  CONSTRAINT `recipe_output_sku_ibfk_1` FOREIGN KEY (`recipeid`) REFERENCES `recipes` (`recipeid`),
  CONSTRAINT `recipe_output_sku_ibfk_2` FOREIGN KEY (`skuid`) REFERENCES `stockkeepunits` (`skuid`)
) ENGINE=InnoDB DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table shardworld.recipe_output_sku: ~26 rows (approximately)
INSERT INTO `recipe_output_sku` (`recipeid`, `skuid`, `amount`) VALUES
	(0, 3, 18),
	(1, 6, 1),
	(2, 0, 10),
	(3, 2, 10),
	(4, 3, 5),
	(5, 1, 10),
	(6, 27, 1),
	(7, 17, 1),
	(8, 56, 1),
	(9, 14, 1),
	(10, 15, 1),
	(11, 54, 1),
	(12, 18, 1),
	(13, 18, 1),
	(14, 52, 1),
	(15, 52, 1),
	(16, 28, 2),
	(17, 28, 3),
	(18, 30, 2),
	(19, 30, 150),
	(20, 52, 150),
	(21, 28, 150),
	(22, 26, 150),
	(23, 13, 50),
	(24, 54, 1),
	(25, 58, 1);

-- Dumping structure for table shardworld.recipe_toolreq_sku
CREATE TABLE IF NOT EXISTS `recipe_toolreq_sku` (
  `recipeid` int(11) NOT NULL,
  `skuid` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  PRIMARY KEY (`recipeid`,`skuid`),
  KEY `skuid` (`skuid`),
  CONSTRAINT `recipe_toolreq_sku_ibfk_1` FOREIGN KEY (`recipeid`) REFERENCES `recipes` (`recipeid`),
  CONSTRAINT `recipe_toolreq_sku_ibfk_2` FOREIGN KEY (`skuid`) REFERENCES `stockkeepunits` (`skuid`)
) ENGINE=InnoDB DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table shardworld.recipe_toolreq_sku: ~30 rows (approximately)
INSERT INTO `recipe_toolreq_sku` (`recipeid`, `skuid`, `amount`) VALUES
	(0, 28, 1),
	(1, 54, 3),
	(3, 58, 4),
	(10, 54, 4),
	(10, 58, 10),
	(12, 54, 3),
	(12, 58, 10),
	(13, 18, 10),
	(13, 54, 10),
	(14, 54, 16),
	(14, 58, 12),
	(15, 28, 8),
	(15, 54, 12),
	(15, 58, 12),
	(16, 54, 12),
	(16, 58, 12),
	(17, 28, 8),
	(17, 54, 6),
	(17, 58, 12),
	(18, 28, 2),
	(18, 54, 6),
	(18, 58, 12),
	(19, 28, 30),
	(19, 54, 70),
	(20, 28, 20),
	(20, 54, 70),
	(21, 28, 40),
	(21, 54, 80),
	(22, 28, 30),
	(22, 54, 70);

-- Dumping structure for table shardworld.stockkeepunits
CREATE TABLE IF NOT EXISTS `stockkeepunits` (
  `skuid` int(11) NOT NULL,
  `productname` varchar(255) NOT NULL,
  `initialprice` int(11) DEFAULT NULL,
  `unit` varchar(10) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `usagecategory` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`skuid`)
) ENGINE=InnoDB DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table shardworld.stockkeepunits: ~59 rows (approximately)
INSERT INTO `stockkeepunits` (`skuid`, `productname`, `initialprice`, `unit`, `description`, `usagecategory`) VALUES
	(0, 'Timber, raw, kg', 3, 'kg', 'Raw timber, newly felled and fresh. Sold pr. Kg', 'Raw material'),
	(1, 'Firewood, kg', 3, 'kg', 'Firewood', 'Fuel'),
	(2, 'Timber, cured, kg', 205, 'kg', 'Timber, cured, ready for the sawworks or chopping into firewood. Sold pr. Kg', 'Raw material'),
	(3, 'Wood, kg', 22, 'kg', 'Wood, either in planks, blocks or beams. Sold pr. Kg.', 'Raw material'),
	(4, 'Raw Pelt', 80, 'm2', 'Raw pelts. Sold in m2', 'Raw material'),
	(5, 'Brass, bar', 33, '', '', 'Raw material'),
	(6, 'Pair of Shues, Wooden', 312, 'Count', 'Shue made entirely of wood. ', 'Shue'),
	(7, 'Pair of Shues, Wooden with leather top', 300, 'Count', 'Shue of wood, leather top for comfort', 'Shue'),
	(8, 'Raw Pelt Wearable', 400, 'Count', 'Roughly 1 m2 of raw pelt, roughly formed to be worn as a jacket', 'Jacket'),
	(9, 'Brass, plate', 33, '', '', 'Raw material'),
	(10, 'Brass, wire', 33, '', '', 'Raw material'),
	(11, 'Apples', 33, '', '', 'Food'),
	(12, 'Bronze tools', 33, 'kg', '', 'Tools'),
	(13, 'Charcoal', 22, '', '', 'Fuel'),
	(14, 'Coal, Mined', 176, '', '', 'Fuel'),
	(15, 'Copper', 6019, '', '', 'Raw material'),
	(16, 'Chicken', 33, '', '', 'Animal'),
	(17, 'Copper Ore', 198, '', '', 'Ore'),
	(18, 'Copper tools', 7173, 'kg', '', 'Tools'),
	(19, 'Copper, coin', 33, 'Count', 'The basic money unit. Will always be sold to the market for Money', 'Money'),
	(20, 'Gold Ore', 33, '', '', 'Ore'),
	(21, 'Gold, bar', 33, '', '', 'Raw material'),
	(22, 'Corn', 33, '', '', 'Food'),
	(23, 'Cow', 33, '', '', 'Animal'),
	(24, 'Cows Milk', 33, '', '', 'Food'),
	(25, 'Gold, coin', 33, 'Count', 'Money unit, worth 100 copper. Will always be sold to the market for Money', 'Money'),
	(26, 'Hard Steel', 2318, 'kg', 'This technique involves melting wrought iron with carbon in small crucibles, creating steel with unique properties that allowes for strong, flexible, and sharp-edged weapons. ', ''),
	(27, 'Iron Ore', 132, 'kg', '', 'Ore'),
	(28, 'Iron tools', 2318, 'kg', '', 'Tools'),
	(29, 'Lapis Lazuli', 2318, '', '', 'Precious Stone'),
	(30, 'Mild Steel', 2318, '', 'Mild steel is a type of carbon steel with a low amount of carbon. It is also known as low carbon steel. ', 'Raw material'),
	(31, 'Null', 0, '', 'Null', 'Null'),
	(32, 'Meat, Chicken', 33, '', '', 'Food'),
	(33, 'Meet, Beef', 33, '', '', 'Food'),
	(34, 'Meet, Pork', 33, '', '', 'Food'),
	(35, 'Peas', 33, '', '', 'Food'),
	(36, 'Pig', 33, '', '', 'Animal'),
	(37, 'Potatoes', 33, '', '', 'Food'),
	(38, 'Quartz', 33, '', '', 'Precious Stone'),
	(39, 'Rice', 33, '', '', 'Food'),
	(40, 'Ruby', 33, '', '', 'Precious Stone'),
	(41, 'Sapphire', 33, '', '', 'Precious Stone'),
	(42, 'Scrap Bronze metal', 33, 'kg', '', ''),
	(43, 'Scrap copper metal', 33, 'kg', '', ''),
	(44, 'Soya', 33, '', '', 'Food'),
	(45, 'Scrap iron metal', 33, 'kg', '', ''),
	(46, 'Scrap Steel metal', 33, 'kg', '', ''),
	(47, 'Silver Ore', 88, '', '', 'Ore'),
	(48, 'Silver, bar', 33, '', '', 'Raw material'),
	(49, 'Silver, coin', 1000, 'Count', 'Money unit, worth 10 copper. Will always be sold to the market for Money', 'Money'),
	(50, 'Stainless Steel', 33, 'kg', 'Stainless Steel is alloyed using chromium.', ''),
	(51, 'Wheat', 33, '', '', 'Food'),
	(52, 'Wrought Iron', 1743, 'kg', 'Wrought Iron is relatively soft but malleable. It contains traces of slag, making it less pure than modern iron but still highly functional for making a variety of tools and weapons.', 'Raw material'),
	(53, 'Steel tools', 33, 'kg', '', 'Tools'),
	(54, 'Stone tools', 880, 'kg', '', 'Tools'),
	(55, 'Tin', 33, '', '', 'Raw material'),
	(56, 'Tin Ore', 264, '', '', 'Ore'),
	(57, 'Topaz', 33, '', '', 'Precious Stone'),
	(58, 'Wood tools', 378, 'kg', '', 'Tools');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
