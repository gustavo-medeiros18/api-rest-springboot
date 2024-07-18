# Warning: If you edit migration files after running them, the checksum on the database
# won't be the same and flyway will throw an error.

CREATE TABLE IF NOT EXISTS `person` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(80) NOT NULL,
  `last_name` VARCHAR(80) NOT NULL,
  `address` VARCHAR(100) NOT NULL,
  `gender` VARCHAR(6) NOT NULL,
  PRIMARY KEY (`id`)
);
