-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema chladnicka
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema chladnicka
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `chladnicka` DEFAULT CHARACTER SET utf8 COLLATE utf8_slovak_ci ;
USE `chladnicka` ;

-- -----------------------------------------------------
-- Table `chladnicka`.`diet`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chladnicka`.`diet` ;

CREATE TABLE IF NOT EXISTS `chladnicka`.`diet` (
  `diet_id` INT NOT NULL AUTO_INCREMENT,
  `diet_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`diet_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chladnicka`.`recipe`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chladnicka`.`recipe` ;

CREATE TABLE IF NOT EXISTS `chladnicka`.`recipe` (
  `recipe_id` INT NOT NULL AUTO_INCREMENT,
  `recipe_name` VARCHAR(45) NOT NULL,
  `calorific_value` DECIMAL(6,2) NULL,
  `description` VARCHAR(1000) NULL,
  `diet_diet_id` INT NOT NULL,
  PRIMARY KEY (`recipe_id`),
  INDEX `fk_recipe_diet1_idx` (`diet_diet_id` ASC) VISIBLE,
  CONSTRAINT `fk_recipe_diet1`
    FOREIGN KEY (`diet_diet_id`)
    REFERENCES `chladnicka`.`diet` (`diet_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chladnicka`.`allergie`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chladnicka`.`allergie` ;

CREATE TABLE IF NOT EXISTS `chladnicka`.`allergie` (
  `allergie_id` INT NOT NULL AUTO_INCREMENT,
  `category` INT NOT NULL,
  `information` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`allergie_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chladnicka`.`measure`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chladnicka`.`measure` ;

CREATE TABLE IF NOT EXISTS `chladnicka`.`measure` (
  `measure_id` INT NOT NULL AUTO_INCREMENT,
  `unit` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`measure_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chladnicka`.`ingredient`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chladnicka`.`ingredient` ;

CREATE TABLE IF NOT EXISTS `chladnicka`.`ingredient` (
  `ingredient_id` INT NOT NULL AUTO_INCREMENT,
  `ingredient_name` VARCHAR(45) NOT NULL,
  `quantity_fridge` DECIMAL(6,2) NULL,
  `allergie_allergie_id` INT NOT NULL,
  `measure_measure_id` INT NOT NULL,
  PRIMARY KEY (`ingredient_id`),
  INDEX `fk_ingredient_allergie1_idx` (`allergie_allergie_id` ASC) VISIBLE,
  INDEX `fk_ingredient_measure1_idx` (`measure_measure_id` ASC) VISIBLE,
  CONSTRAINT `fk_ingredient_allergie1`
    FOREIGN KEY (`allergie_allergie_id`)
    REFERENCES `chladnicka`.`allergie` (`allergie_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ingredient_measure1`
    FOREIGN KEY (`measure_measure_id`)
    REFERENCES `chladnicka`.`measure` (`measure_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chladnicka`.`fridge`
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS `chladnickatest`.`fridge` ;

-- CREATE TABLE IF NOT EXISTS `chladnickatest`.`fridge` (
--  `username` VARCHAR(45) NOT NULL,
--   `password` VARCHAR(45) NOT NULL,
--   PRIMARY KEY (`username`, `password`))
-- ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chladnicka`.`favourite`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chladnicka`.`favourite` ;

CREATE TABLE IF NOT EXISTS `chladnicka`.`favourite` (
  `favourite_id` INT NOT NULL AUTO_INCREMENT,
  `hodnotenie` INT NOT NULL,
  `recipe_recipe_id` INT NOT NULL,
  PRIMARY KEY (`favourite_id`),
  INDEX `fk_favourite_recipe1_idx` (`recipe_recipe_id` ASC) VISIBLE,
  CONSTRAINT `fk_favourite_recipe1`
    FOREIGN KEY (`recipe_recipe_id`)
    REFERENCES `chladnicka`.`recipe` (`recipe_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chladnicka`.`recipe_has_ingredient`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chladnicka`.`recipe_has_ingredient` ;

CREATE TABLE IF NOT EXISTS `chladnicka`.`recipe_has_ingredient` (
  `recipe_recipe_id` INT NOT NULL,
  `ingredient_ingredient_id` INT NOT NULL,
  `recipe_amount` DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (`recipe_recipe_id`, `ingredient_ingredient_id`),
  INDEX `fk_recipe_has_ingredient_ingredient1_idx` (`ingredient_ingredient_id` ASC) VISIBLE,
  INDEX `fk_recipe_has_ingredient_recipe1_idx` (`recipe_recipe_id` ASC) VISIBLE,
  CONSTRAINT `fk_recipe_has_ingredient_recipe1`
    FOREIGN KEY (`recipe_recipe_id`)
    REFERENCES `chladnicka`.`recipe` (`recipe_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_recipe_has_ingredient_ingredient1`
    FOREIGN KEY (`ingredient_ingredient_id`)
    REFERENCES `chladnicka`.`ingredient` (`ingredient_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO `chladnicka`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('1', '1', 'Obilniny obsahujúce lepok');
INSERT INTO `chladnicka`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('2', '2', 'Kôrovce a výrobky z nich');
INSERT INTO `chladnicka`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('3', '3', 'Vajcia a výrobky z nich');
INSERT INTO `chladnicka`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('4', '4', 'Ryby a výrobky z nich okrem');
INSERT INTO `chladnicka`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('5', '5', 'Arašídy a výrobky z nich');
INSERT INTO `chladnicka`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('6', '6', 'Sójové zrná a výrobky z nich okrem');
INSERT INTO `chladnicka`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('7', '7', 'Mlieko výrobky z neho (vrátane laktózy) okrem');
INSERT INTO `chladnicka`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('8', '8', 'Orechy');
INSERT INTO `chladnicka`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('9', '9', 'Zeler a výrobky z neho');
INSERT INTO `chladnicka`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('10', '10', 'Horčica a výrobky z nej');
INSERT INTO `chladnicka`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('11', '11', 'Sézamové semená a výrobky z nich');
INSERT INTO `chladnicka`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('12', '12', 'Kysličník síričitý a síričitany v koncentráciách vyšších ako 10 mg/kg alebo 10mg/liter vyjadrené  ako SO2');
INSERT INTO `chladnicka`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('13', '13', 'None');

INSERT INTO `chladnicka`.`measure` (`measure_id`, `unit`) VALUES ('1', 'g');
INSERT INTO `chladnicka`.`measure` (`measure_id`, `unit`) VALUES ('2', 'ml');
INSERT INTO `chladnicka`.`measure` (`measure_id`, `unit`) VALUES ('3', 'ks');

INSERT INTO `chladnicka`.`diet` (`diet_id`, `diet_name`) VALUES ('1', 'none');
INSERT INTO `chladnicka`.`diet` (`diet_id`, `diet_name`) VALUES ('2', 'vegan');
INSERT INTO `chladnicka`.`diet` (`diet_id`, `diet_name`) VALUES ('3', 'vegetarian');

