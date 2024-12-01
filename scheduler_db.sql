-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema scheduler_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema scheduler_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `scheduler_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `scheduler_db` ;

-- -----------------------------------------------------
-- Table `scheduler_db`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scheduler_db`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `account_id` VARCHAR(40) NULL DEFAULT NULL,
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `account_id` (`account_id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `scheduler_db`.`reminders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scheduler_db`.`reminders` (
  `reminder_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `task_name` VARCHAR(255) NOT NULL,
  `reminder_date` DATE NOT NULL,
  `reminder_time` TIME NOT NULL,
  `priority` ENUM('HIGH', 'MEDIUM', 'LOW') NULL DEFAULT NULL,
  `repeat_daily` TINYINT(1) NULL DEFAULT '0',
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `is_active` TINYINT(1) NULL DEFAULT '1',
  PRIMARY KEY (`reminder_id`),
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  CONSTRAINT `reminders_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `scheduler_db`.`users` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 16
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `scheduler_db`.`scheduler_data`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scheduler_db`.`scheduler_data` (
  `user_id` INT NOT NULL,
  `date` DATE NOT NULL,
  `content` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`, `date`),
  CONSTRAINT `scheduler_data_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `scheduler_db`.`users` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
