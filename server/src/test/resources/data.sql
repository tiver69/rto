DROP TABLE IF EXISTS `coach_type`;
CREATE TABLE `coach_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name_en` varchar(45) NOT NULL,
  `name_ru` varchar(45) NOT NULL,
  `places` int(11) NOT NULL,
  `markup` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
INSERT INTO `coach_type` VALUES (1,'Seating first class','Сидячий первый класс',56,25),(2,'Seating second class','Сидячий второй класс',80,15),(3,'Compartment','Купе',36,10),(4,'Berth','Плацкарт',54,5),(5,'De Luxe','Люкс',20,35);

DROP TABLE IF EXISTS `passenger`;
CREATE TABLE `passenger` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
INSERT INTO `passenger` VALUES (1,'Кибко','Александра','tiver69','6c2a5c9ead1d7d6ba86c8764d5cad395'),(2,'Savina','Evgenia','shipper1232','6c2a5c9ead1d7d6ba86c8764d5cad395'),(3,'Protasov','Vladislav','protasov1','0102812fbd5f73aa18aa0bae2cd8f79f'),(4,'Test','Test','test11','0102812fbd5f73aa18aa0bae2cd8f79f');

--DROP TABLE IF EXISTS `passenger_roles`;
--CREATE TABLE `passenger_roles` (
--  `id` int(11) NOT NULL AUTO_INCREMENT,
--  `passenger_id` int(11) NOT NULL,
--  `role` varchar(45) NOT NULL DEFAULT 'USER',
--  PRIMARY KEY (`id`,`passenger_id`),
--  UNIQUE KEY `idpassenger_roles_UNIQUE` (`id`),
-- FOREIGN KEY (`passenger_id`) REFERENCES `passenger` (`id`)
--) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
--INSERT INTO `passenger_roles` VALUES (1,1,'USER'),(2,2,'USER'),(3,3,'USER'),(4,1,'ADMIN'),(5,2,'ADMIN'),(6,4,'USER');

DROP TABLE IF EXISTS `station`;
CREATE TABLE `station` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name_en` varchar(45) NOT NULL,
  `name_ru` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
INSERT INTO `station` VALUES (1,'Zaporizhzhya 1','Запорожье 1'),(2,'Kyiv-Pasazhyrsky','Киев-Пассажирский'),(3,'Im. T. Shevchenka','Им. Т. Шевченка'),(4,'Znamyanka Pasazhyrska','Знаменка Пассажирская'),(5,'Oleksandriya','Александрия'),(6,'Pyatykhatky','Пятихатки'),(7,'Kamianske-Pasazhyrske','Каменское-Пассажирское'),(8,'Dnipro-Holovny','Днепр-Главный'),(9,'Darnytsya','Дарница'),(10,'Myrhorod','Миргород'),(11,'Poltava Kyivska','Полтава Киевская'),(12,'Kharkiv-Pas','Харьков-Пас'),(13,'Dniprobud 2','Днепробуд 2'),(14,'Nikopol','Никополь'),(15,'Trypillya Dniprovske','Триполье Днепровское'),(16,'Melitopol','Мелитополь'),(17,'Novooleksiyivka','Новоалексеевка'),(18,'Henichesk','Геническ'),(19,'Synelnykove-1','Синельниково-1'),(20,'Krasnopavlivka','Краснопавловка');

DROP TABLE IF EXISTS `train`;
CREATE TABLE `train` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `markup` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1704 DEFAULT CHARSET=utf8;
INSERT INTO `train` VALUES (12,5),(72,5),(73,5),(724,15),(729,10),(732,10);

DROP TABLE IF EXISTS `train_coach`;
CREATE TABLE `train_coach` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `train_id` int(11) NOT NULL,
  `coach_type_id` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  PRIMARY KEY (`id`,`train_id`,`coach_type_id`),
  FOREIGN KEY (`coach_type_id`) REFERENCES `coach_type` (`id`),
  FOREIGN KEY (`train_id`) REFERENCES `train` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
INSERT INTO `train_coach` VALUES (1,732,1,2),(2,732,1,5),(3,732,1,8),(4,732,2,1),(5,732,2,3),(6,732,2,4),(7,732,2,6),(8,732,2,7),(9,732,2,9),(10,724,2,1),(11,724,2,3),(12,724,2,4),(13,724,2,6),(14,724,2,7),(15,724,2,9),(16,724,1,2),(17,724,1,5),(18,724,1,8),(19,72,4,5),(20,72,4,7),(21,72,4,8),(22,72,3,1),(23,72,3,2),(24,72,3,3),(25,72,3,4),(26,72,3,6),(27,73,4,5),(28,73,4,7),(29,73,4,8),(30,73,3,1),(31,73,3,2),(32,73,3,3),(33,73,3,4),(34,73,3,6),(35,12,3,1),(36,12,3,2),(37,12,4,3),(38,12,4,4),(39,12,4,5),(40,12,4,6),(41,12,3,7),(42,12,5,8),(43,12,3,9),(44,12,3,10),(45,729,1,1),(46,729,1,2),(47,729,2,3),(48,729,2,4),(49,729,2,5);

DROP TABLE IF EXISTS `train_frequency`;
CREATE TABLE `train_frequency` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `train_id` int(11) NOT NULL,
  `week_day` enum('SUN','MON','TUE','WED','THU','FRI','SAT') NOT NULL,
  PRIMARY KEY (`id`,`train_id`),
  FOREIGN KEY (`train_id`) REFERENCES `train` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
INSERT INTO `train_frequency` VALUES (1,72,'SUN'),(2,72,'MON'),(3,72,'TUE'),(4,72,'WED'),(5,72,'THU'),(6,72,'FRI'),(7,72,'SAT'),(8,73,'SUN'),(9,73,'MON'),(10,73,'TUE'),(11,73,'WED'),(12,73,'THU'),(13,73,'FRI'),(14,73,'SAT'),(15,12,'SUN'),(16,724,'FRI'),(17,724,'MON'),(18,724,'SAT'),(19,724,'SUN'),(20,724,'THU'),(21,724,'TUE'),(22,724,'WED'),(23,729,'FRI'),(24,729,'MON'),(25,729,'SAT'),(26,729,'SUN'),(27,729,'THU'),(28,729,'TUE'),(29,729,'WED'),(30,732,'MON'),(31,732,'WED'),(32,732,'FRI');

DROP TABLE IF EXISTS `train_station`;
CREATE TABLE `train_station` (
  `station_id` int(11) NOT NULL AUTO_INCREMENT,
  `train_id` int(11) NOT NULL,
  `stop_order` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `arrival` time DEFAULT NULL,
  `departure` time DEFAULT NULL,
  PRIMARY KEY (`station_id`,`train_id`),
  FOREIGN KEY (`station_id`) REFERENCES `station` (`id`),
  FOREIGN KEY (`train_id`) REFERENCES `train` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
INSERT INTO `train_station` VALUES (1,12,7,25,'01:58:00','02:06:00'),(1,72,4,50,'07:49:00',NULL),(1,73,0,0,NULL,'18:20:00'),(1,729,3,50,'10:22:00','10:25:00'),(1,732,7,50,'14:35:00',NULL),(2,12,0,0,NULL,'15:07:00'),(2,72,0,0,NULL,'20:12:00'),(2,73,4,50,'05:52:00',NULL),(2,724,0,0,NULL,'13:25:00'),(2,732,0,0,NULL,'07:07:00'),(3,12,2,25,'18:29:00','18:34:00'),(3,72,1,50,'23:02:00','23:07:00'),(3,73,3,50,'02:55:00','03:00:00'),(3,732,1,50,'09:23:00','09:24:00'),(4,12,3,25,'19:45:00','19:48:00'),(4,732,2,50,'10:24:00','10:25:00'),(5,732,3,50,'10:52:00','10:53:00'),(6,12,4,25,'21:38:00','21:40:00'),(6,732,4,50,'11:39:00','11:40:00'),(7,12,5,25,'23:04:00','23:06:00'),(7,732,5,50,'12:25:00','12:26:00'),(8,12,6,25,'23:42:00','00:08:00'),(8,732,6,50,'12:52:00','13:02:00'),(9,724,1,50,'13:43:00','13:45:00'),(10,724,2,50,'15:43:00','15:45:00'),(11,724,3,50,'16:38:00','16:40:00'),(12,724,4,50,'18:16:00',NULL),(12,729,0,0,NULL,'06:26:00'),(13,72,3,50,'07:13:00','07:15:00'),(13,73,2,50,'20:17:00','20:37:00'),(14,72,2,50,'05:18:00','05:32:00'),(14,73,1,50,'18:55:00','18:56:00'),(15,12,1,25,'15:47:00','15:55:00'),(16,12,8,25,'03:41:00','03:46:00'),(16,729,4,25,'12:02:00','12:04:00'),(17,12,9,25,'04:49:00',NULL),(17,729,5,25,'13:07:00','13:13:00'),(18,729,6,25,'13:27:00',NULL),(19,729,2,25,'09:29:00','09:31:00'),(20,729,1,25,'07:48:00','07:49:00');

DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `passenger_id` int(11) NOT NULL,
  `departure_station_id` int(11) NOT NULL,
  `destination_station_id` int(11) NOT NULL,
  `departure_date` date NOT NULL,
  `arrival_date` varchar(45) NOT NULL,
  `coach_id` int(11) NOT NULL,
  `place` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  PRIMARY KEY (`id`,`passenger_id`,`departure_station_id`,`destination_station_id`,`coach_id`),
  FOREIGN KEY (`passenger_id`) REFERENCES `passenger` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`departure_station_id`) REFERENCES `station` (`id`),
  FOREIGN KEY (`destination_station_id`) REFERENCES `station` (`id`),
  FOREIGN KEY (`coach_id`) REFERENCES `train_coach` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
INSERT INTO `ticket` VALUES (1,1,2,1,'2019-08-31','2019-08-31',2,17,472),(2,2,2,1,'2019-08-31','2019-08-31',2,18,472),(3,1,2,3,'2019-08-31','2019-08-31',5,19,67),(4,4,10,12,'2019-08-31','2019-08-31',17,33,140),(5,1,1,3,'2019-05-15','2019-05-16',27,4,164),(6,1,2,12,'2019-06-06','2019-06-06',17,10,280),(7,1,2,1,'2019-06-12','2019-06-12',2,16,472),(8,1,1,2,'2019-06-12','2019-06-13',27,18,220),(9,1,1,2,'2019-06-12','2019-06-13',27,20,220),(10,4,2,1,'2019-06-14','2019-06-14',19,12,210),(11,1,1,2,'2019-11-16','2019-11-17',30,21,230),(12,1,2,1,'2019-09-30','2019-09-30',2,17,472),(13,1,2,1,'2019-09-30','2019-09-30',2,18,472),(14,1,2,1,'2019-09-30','2019-09-30',2,19,472);
