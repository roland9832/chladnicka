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
  `diet_id` INT NOT NULL,
  `diet_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`diet_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chladnicka`.`recipe`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chladnicka`.`recipe` ;

CREATE TABLE IF NOT EXISTS `chladnicka`.`recipe` (
  `recipe_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `calorific value` DECIMAL(6,2) NULL,
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
  `name` VARCHAR(45) NOT NULL,
  `quantity_fridge` DECIMAL(6,2) NULL,
  `allergie_allergie_id` INT NULL,
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
DROP TABLE IF EXISTS `chladnicka`.`fridge` ;

CREATE TABLE IF NOT EXISTS `chladnicka`.`fridge` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`, `password`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chladnicka`.`favourite`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chladnicka`.`favourite` ;

CREATE TABLE IF NOT EXISTS `chladnicka`.`favourite` (
  `recipe_recipe_id` INT NOT NULL,
  `hodnotenie` INT NOT NULL,
  `fridge_username` VARCHAR(45) NOT NULL,
  `fridge_password` VARCHAR(45) NOT NULL,
  INDEX `fk_favourite_recipe1_idx` (`recipe_recipe_id` ASC) VISIBLE,
  PRIMARY KEY (`fridge_username`, `fridge_password`),
  CONSTRAINT `fk_favourite_recipe1`
    FOREIGN KEY (`recipe_recipe_id`)
    REFERENCES `chladnicka`.`recipe` (`recipe_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_favourite_fridge1`
    FOREIGN KEY (`fridge_username` , `fridge_password`)
    REFERENCES `chladnicka`.`fridge` (`username` , `password`)
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
  `recipe_amout` DECIMAL(6,2) NOT NULL,
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
