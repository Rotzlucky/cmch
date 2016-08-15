# --- !Ups
CREATE TABLE IF NOT EXISTS `titles` (
  `uuid` binary(16) NOT NULL,
  `titleName` varchar(255) NOT NULL,
  `titleNumber` int(11) NOT NULL,
  `createdAt` datetime NOT NULL,
  `modifiedAt` datetime NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `titles_unique_key_1` (`titleName`,`titleNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `issues` (
  `uuid` binary(16) NOT NULL,
  `titleUuid` binary(16) NOT NULL,
  `issueName` varchar(255) NOT NULL,
  `issueNumber` int(11) NOT NULL,
  `issueNumberSuffix` varchar(1) NOT NULL,
  `publishDate` date NOT NULL,
  `createdAt` datetime NOT NULL,
  `modifiedAt` datetime NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `issues_unique_key_1` (`titleUuid`,`issueNumber`, `issueNumberSuffix`),
  CONSTRAINT `issues_ibfk_1` FOREIGN KEY (`titleUuid`) REFERENCES `titles` (`uuid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `orders` (
  `uuid` binary(16) NOT NULL,
  `issueUuid` binary(16) NOT NULL,
  `orderNumber` int(11) NOT NULL,
  `orderType` enum('Main','Extended','Core','Essential') NOT NULL,
  `createdAt` datetime NOT NULL,
  `modifiedAt` datetime NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `orders_unique_key_1` (`issueUuid`,`orderType`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`issueUuid`) REFERENCES `issues` (`uuid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `characters` (
  `uuid` binary(16) NOT NULL,
  `characterName` varchar(255) NOT NULL,
  `characterAlias` varchar(255) NOT NULL,
  `createdAt` datetime NOT NULL,
  `modifiedAt` datetime NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `characters_unique_key_1` (`characterName`,`characterAlias`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `teams` (
  `uuid` binary(16) NOT NULL,
  `teamName` varchar(255) NOT NULL,
  `createdAt` datetime NOT NULL,
  `modifiedAt` datetime NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `teams_unique_key_1` (`teamName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `characterAppearances` (
  `issueUuid` binary(16) NOT NULL,
  `characterUuid` binary(16) NOT NULL,
  `createdAt` datetime NOT NULL,
  `modifiedAt` datetime NOT NULL,
  CONSTRAINT `characterAppearances_ibfk_1` FOREIGN KEY (`issueUuid`) REFERENCES `issues` (`uuid`) ON DELETE CASCADE,
  CONSTRAINT `characterAppearances_ibfk_2` FOREIGN KEY (`characterUuid`) REFERENCES `characters` (`uuid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `teamAffiliations` (
  `teamUuid` binary(16) NOT NULL,
  `characterUuid` binary(16) NOT NULL,
  `createdAt` datetime NOT NULL,
  `modifiedAt` datetime NOT NULL,
  CONSTRAINT `teamAffiliations_ibfk_1` FOREIGN KEY (`teamUuid`) REFERENCES `teams` (`uuid`) ON DELETE CASCADE,
  CONSTRAINT `teamAffiliations_ibfk_2` FOREIGN KEY (`characterUuid`) REFERENCES `characters` (`uuid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# --- !Downs
DROP TABLE `teamAffiliations`;
DROP TABLE `characterAppearances`;
DROP TABLE `teams`;
DROP TABLE `characters`;
DROP TABLE `orders`;
DROP TABLE `issues`;
DROP TABLE `titles`;
