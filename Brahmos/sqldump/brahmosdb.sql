DROP DATABASE IF EXISTS `Brahmosdb`;
CREATE DATABASE `Brahmosdb`;


USE `Brahmosdb`;

# CREATE USER 'brahmosadmin'@'localhost' IDENTIFIED BY 'brahmos@1234';
# Structure for table "projects"
# GRANT ALL PRIVILEGES ON brahmos.* TO 'brahmosadmin'@'localhost';

#DROP TABLE IF EXISTS `projects`;
CREATE TABLE `projects`(
`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
`name` VARCHAR(50) NOT NULL,
`description` VARCHAR(50) NOT NULL,
`lastModifiedDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   ON UPDATE CURRENT_TIMESTAMP,
`createdDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
`isDeleted` BOOLEAN NOT NULL DEFAULT 0,
`comments` VARCHAR(4000) NOT NULL,
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE projects AUTO_INCREMENT=9000;

#
# Structure for table "scenarios"
#

#DROP TABLE IF EXISTS `scenarios`;
CREATE TABLE `scenarios`(
`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
`project_id` INT(10) UNSIGNED NOT NULL,
`name` VARCHAR(50) NOT NULL,
`description` VARCHAR(50) NOT NULL,
`defaultBuildInfo` VARCHAR(450) NOT NULL,
`lastModifiedDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   ON UPDATE CURRENT_TIMESTAMP,
`createdDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
`isDeleted` BOOLEAN NOT NULL DEFAULT 0,
`comments` VARCHAR(4000) NOT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`project_id`) REFERENCES `projects`(`id`) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "builds"
#

#DROP TABLE IF EXISTS `builds`;
CREATE TABLE `builds`(
`id` INT(10) UNSIGNED NOT NULL,
`scenario_id` INT(10) UNSIGNED NOT NULL,
`description` VARCHAR(50) NOT NULL,
`buildInfo` MEDIUMBLOB,
`lastModifiedDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   ON UPDATE CURRENT_TIMESTAMP,
`createdDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
`isDeleted` BOOLEAN NOT NULL DEFAULT 0,
`comments` VARCHAR(4000) NOT NULL,
PRIMARY KEY (`id`, `scenario_id`),
FOREIGN KEY (`scenario_id`) REFERENCES `scenarios`(`id`) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "consolidatedReport"
#

#DROP TABLE IF EXISTS `consolidatedReport`;
CREATE TABLE `consolidatedReport`(
`project_id` INT(10) UNSIGNED NOT NULL,
`scenario_id` INT(10) UNSIGNED NOT NULL,
`year` INT(4) UNSIGNED NOT NULL,
`month` VARCHAR(18) NOT NULL,
`averageResponseTime` VARCHAR(50) NOT NULL,
`maximumThroughput` VARCHAR(50) NOT NULL,
`transactionCount` VARCHAR(50) NOT NULL,
`cpuUtilization` VARCHAR(50) NOT NULL,
`lastModifiedDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   ON UPDATE CURRENT_TIMESTAMP,
`createdDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
`isDeleted` BOOLEAN NOT NULL DEFAULT 0,
`comments` VARCHAR(4000) NOT NULL,
PRIMARY KEY (`project_id`, `scenario_id`, `year`, `month`),
FOREIGN KEY (`scenario_id`) REFERENCES `scenarios`(`id`) ON DELETE CASCADE,
FOREIGN KEY (`project_id`) REFERENCES `projects`(`id`) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
#  Trigger to auto generate consolidateReport record for each new scenario
#

DELIMITER $$
CREATE TRIGGER on_new_scenario_create
AFTER INSERT 
ON scenarios FOR EACH ROW 
BEGIN
   #Auto-create a consolidateReport record for each new scenario
   INSERT INTO consolidatedReport (project_id, scenario_id, year, month, averageResponseTime, maximumThroughput, transactionCount, cpuUtilization, comments) VALUES(NEW.project_id, New.id, EXTRACT(YEAR FROM CURRENT_TIMESTAMP), MONTHNAME(CURRENT_TIMESTAMP),"0", "0", "0", "0","New Consolidated Report");
END $$


SET GLOBAL event_scheduler = ON;

#
#	An Event to create a new record every new month to maintain performance reports at month level
#

DELIMITER $$
CREATE EVENT report_scheduler
ON SCHEDULE EVERY '1' MONTH
STARTS CURRENT_TIMESTAMP
ON COMPLETION PRESERVE
DO 
BEGIN
DECLARE n INT DEFAULT 0;
DECLARE i INT DEFAULT 0;
SELECT COUNT(*) FROM scenarios INTO n;
SET i=0;
WHILE i<n DO
	#loop to insert a
	INSERT INTO consolidatedReport (project_id, scenario_id, year, month, averageResponseTime, maximumThroughput, transactionCount, cpuUtilization, comments) VALUES((Select s.project_id from scenarios s where s.id = i + 1), i + 1, EXTRACT(YEAR FROM CURRENT_TIMESTAMP), MONTHNAME(CURRENT_TIMESTAMP),"0", "0", "0", "0","New Consolidated Report");
	SET i = i + 1;
END WHILE;
END$$