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
CREATE SCHEMA IF NOT EXISTS `chladnickatest` DEFAULT CHARACTER SET utf8 COLLATE utf8_slovak_ci ;
USE `chladnickatest` ;

-- -----------------------------------------------------
-- Table `chladnicka`.`diet`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chladnickatest`.`diet` ;

CREATE TABLE IF NOT EXISTS `chladnickatest`.`diet` (
  `diet_id` INT NOT NULL,
  `diet_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`diet_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chladnicka`.`recipe`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chladnickatest`.`recipe` ;

CREATE TABLE IF NOT EXISTS `chladnickatest`.`recipe` (
  `recipe_id` INT NOT NULL AUTO_INCREMENT,
  `recipe_name` VARCHAR(45) NOT NULL,
  `calorific_value` DECIMAL(6,2) NULL,
  `description` VARCHAR(1000) NULL,
  `diet_diet_id` INT NOT NULL,
  PRIMARY KEY (`recipe_id`),
  INDEX `fk_recipe_diet1_idx` (`diet_diet_id` ASC) VISIBLE,
  CONSTRAINT `fk_recipe_diet1`
    FOREIGN KEY (`diet_diet_id`)
    REFERENCES `chladnickatest`.`diet` (`diet_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chladnicka`.`allergie`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chladnickatest`.`allergie` ;

CREATE TABLE IF NOT EXISTS `chladnickatest`.`allergie` (
  `allergie_id` INT NOT NULL AUTO_INCREMENT,
  `category` INT NOT NULL,
  `information` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`allergie_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chladnicka`.`measure`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chladnickatest`.`measure` ;

CREATE TABLE IF NOT EXISTS `chladnickatest`.`measure` (
  `measure_id` INT NOT NULL AUTO_INCREMENT,
  `unit` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`measure_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chladnicka`.`ingredient`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chladnickatest`.`ingredient` ;

CREATE TABLE IF NOT EXISTS `chladnickatest`.`ingredient` (
  `ingredient_id` INT NOT NULL AUTO_INCREMENT,
  `ingredient_name` VARCHAR(45) NOT NULL,
  `quantity_fridge` DECIMAL(6,2) NULL,
  `allergie_allergie_id` INT NULL,
  `measure_measure_id` INT NOT NULL,
  PRIMARY KEY (`ingredient_id`),
  INDEX `fk_ingredient_allergie1_idx` (`allergie_allergie_id` ASC) VISIBLE,
  INDEX `fk_ingredient_measure1_idx` (`measure_measure_id` ASC) VISIBLE,
  CONSTRAINT `fk_ingredient_allergie1`
    FOREIGN KEY (`allergie_allergie_id`)
    REFERENCES `chladnickatest`.`allergie` (`allergie_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ingredient_measure1`
    FOREIGN KEY (`measure_measure_id`)
    REFERENCES `chladnickatest`.`measure` (`measure_id`)
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
DROP TABLE IF EXISTS `chladnickatest`.`favourite` ;

CREATE TABLE IF NOT EXISTS `chladnickatest`.`favourite` (
  `hodnotenie` INT NOT NULL,
  `recipe_recipe_id` INT NOT NULL,
  INDEX `fk_favourite_recipe1_idx` (`recipe_recipe_id` ASC) VISIBLE,
  CONSTRAINT `fk_favourite_recipe1`
    FOREIGN KEY (`recipe_recipe_id`)
    REFERENCES `chladnickatest`.`recipe` (`recipe_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chladnicka`.`recipe_has_ingredient`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chladnickatest`.`recipe_has_ingredient` ;

CREATE TABLE IF NOT EXISTS `chladnickatest`.`recipe_has_ingredient` (
  `recipe_recipe_id` INT NOT NULL,
  `ingredient_ingredient_id` INT NOT NULL,
  `recipe_amount` DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (`recipe_recipe_id`, `ingredient_ingredient_id`),
  INDEX `fk_recipe_has_ingredient_ingredient1_idx` (`ingredient_ingredient_id` ASC) VISIBLE,
  INDEX `fk_recipe_has_ingredient_recipe1_idx` (`recipe_recipe_id` ASC) VISIBLE,
  CONSTRAINT `fk_recipe_has_ingredient_recipe1`
    FOREIGN KEY (`recipe_recipe_id`)
    REFERENCES `chladnickatest`.`recipe` (`recipe_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_recipe_has_ingredient_ingredient1`
    FOREIGN KEY (`ingredient_ingredient_id`)
    REFERENCES `chladnickatest`.`ingredient` (`ingredient_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO `chladnickatest`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('1', '1', 'Obilniny obsahujúce lepok');
INSERT INTO `chladnickatest`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('2', '2', 'Kôrovce a výrobky z nich');
INSERT INTO `chladnickatest`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('3', '3', 'Vajcia a výrobky z nich');
INSERT INTO `chladnickatest`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('4', '4', 'Ryby a výrobky z nich okrem');
INSERT INTO `chladnickatest`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('5', '5', 'Arašídy a výrobky z nich');
INSERT INTO `chladnickatest`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('6', '6', 'Sójové zrná a výrobky z nich okrem');
INSERT INTO `chladnickatest`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('7', '7', 'Mlieko výrobky z neho (vrátane laktózy) okrem');
INSERT INTO `chladnickatest`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('8', '8', 'Orechy');
INSERT INTO `chladnickatest`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('9', '9', 'Zeler a výrobky z neho');
INSERT INTO `chladnickatest`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('10', '10', 'Horčica a výrobky z nej');
INSERT INTO `chladnickatest`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('11', '11', 'Sézamové semená a výrobky z nich');
INSERT INTO `chladnickatest`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('12', '12', 'Kysličník síričitý a síričitany v koncentráciách vyšších ako 10 mg/kg alebo 10mg/liter vyjadrené  ako SO2');
INSERT INTO `chladnickatest`.`allergie` (`allergie_id`, `category`, `information`) VALUES ('13', '13', 'None');

INSERT INTO `chladnickatest`.`measure` (`measure_id`, `unit`) VALUES ('1', 'g');
INSERT INTO `chladnickatest`.`measure` (`measure_id`, `unit`) VALUES ('2', 'ml');
INSERT INTO `chladnickatest`.`measure` (`measure_id`, `unit`) VALUES ('3', 'ks');

INSERT INTO `chladnickatest`.`diet` (`diet_id`, `diet_name`) VALUES ('1', 'none');
INSERT INTO `chladnickatest`.`diet` (`diet_id`, `diet_name`) VALUES ('2', 'vegan');
INSERT INTO `chladnickatest`.`diet` (`diet_id`, `diet_name`) VALUES ('3', 'vegetarian');

INSERT INTO `chladnickatest`.`ingredient` (`ingredient_id`, `ingredient_name`, `quantity_fridge`, `allergie_allergie_id`, `measure_measure_id`) VALUES ('1', 'maslo', '500', '7', '1');
INSERT INTO `chladnickatest`.`ingredient` (`ingredient_id`, `ingredient_name`, `quantity_fridge`, `allergie_allergie_id`, `measure_measure_id`) VALUES ('2', 'mlieko', '1000', '7', '2');
INSERT INTO `chladnickatest`.`ingredient` (`ingredient_id`, `ingredient_name`, `quantity_fridge`, `allergie_allergie_id`, `measure_measure_id`) VALUES ('3', 'hladka muka', '1000', '1', '1');
INSERT INTO `chladnickatest`.`ingredient` (`ingredient_id`, `ingredient_name`, `quantity_fridge`, `allergie_allergie_id`, `measure_measure_id`) VALUES ('4', 'vajce', '10', '3', '3');
INSERT INTO `chladnickatest`.`ingredient` (`ingredient_id`, `ingredient_name`, `quantity_fridge`, `allergie_allergie_id`, `measure_measure_id`) VALUES ('5', 'sol', '500', '13', '1');
INSERT INTO `chladnickatest`.`ingredient` (`ingredient_name`, `quantity_fridge`, `allergie_allergie_id`, `measure_measure_id`) VALUES ('mrkva', '1000', '13', '1');
INSERT INTO `chladnickatest`.`ingredient` (`ingredient_name`, `quantity_fridge`, `allergie_allergie_id`, `measure_measure_id`) VALUES ('paradajky', '500', '13', '1');
INSERT INTO `chladnickatest`.`ingredient` (`ingredient_id`, `ingredient_name`, `quantity_fridge`, `allergie_allergie_id`, `measure_measure_id`) VALUES ('8', 'zemiaky', '1000', '13', '1');
INSERT INTO `chladnickatest`.`ingredient` (`ingredient_id`, `ingredient_name`, `quantity_fridge`, `allergie_allergie_id`, `measure_measure_id`) VALUES ('9', 'olej', '1000', '13', '2');



INSERT INTO `chladnickatest`.`recipe` (`recipe_id`, `recipe_name`, `calorific_value`, `description`, `diet_diet_id`) VALUES ('1', 'palacinky', '186', 'Ako prvé si nachystáme potrebné suroviny. Z uvedených surovín vypracujeme (rozhabarkujeme) hladké cesto. Cesto lejeme naberačkou na rozpálenú panvicu a kvapkou oleja alebo masla a pečieme z oboch strán. Hotové palacinky plníme džemom a posypeme cukrom. Plnku môžete použiť podľa vlastnej chute a fantázie. Dobrú chuť!', '1');
INSERT INTO `chladnickatest`.`recipe` (`recipe_id`, `recipe_name`, `calorific_value`, `description`, `diet_diet_id`) VALUES ('2', 'hranolky', '312', 'Ci uz robite hranolky domace,alebo ich kupite mrazene,pre naozaj chutne a chrumkave hranolky existuje trik. Hranolky vyprazajte v oleji,do ktoreho ste dali mast.Najlepsie tak 50na50. Vyprazajte na dva krat.Najprv preprazte asi na 2 min,vyberte a nechajte asi 1min.vonku.Potom pokracujte a hranolky doprazte az do konca.', '2');


INSERT INTO `chladnickatest`.`recipe_has_ingredient` (`recipe_recipe_id`, `ingredient_ingredient_id`, `recipe_amount`) VALUES ('1', '2', '400');
INSERT INTO `chladnickatest`.`recipe_has_ingredient` (`recipe_recipe_id`, `ingredient_ingredient_id`, `recipe_amount`) VALUES ('1', '3', '200');
INSERT INTO `chladnickatest`.`recipe_has_ingredient` (`recipe_recipe_id`, `ingredient_ingredient_id`, `recipe_amount`) VALUES ('1', '4', '1');
INSERT INTO `chladnickatest`.`recipe_has_ingredient` (`recipe_recipe_id`, `ingredient_ingredient_id`, `recipe_amount`) VALUES ('1', '5', '3');
INSERT INTO `chladnickatest`.`recipe_has_ingredient` (`recipe_recipe_id`, `ingredient_ingredient_id`, `recipe_amount`) VALUES ('2', '8', '600');
INSERT INTO `chladnickatest`.`recipe_has_ingredient` (`recipe_recipe_id`, `ingredient_ingredient_id`, `recipe_amount`) VALUES ('2', '9', '500');
INSERT INTO `chladnickatest`.`recipe_has_ingredient` (`recipe_recipe_id`, `ingredient_ingredient_id`, `recipe_amount`) VALUES ('2', '5', '5');



INSERT INTO `chladnickatest`.`favourite` (`hodnotenie`, `recipe_recipe_id`) VALUES ('5', '1');

