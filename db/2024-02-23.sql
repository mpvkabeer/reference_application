ALTER TABLE `referencedb`.`user` 
CHANGE COLUMN `Username` `Email` VARCHAR(64) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL ;

CREATE TABLE `referencedb`.`role` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE);


ALTER TABLE `referencedb`.`role` 
CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT ;


CREATE TABLE `referencedb`.`user_role` (
  `user_id` INT(11) NOT NULL,
  `role_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`));


ALTER TABLE `referencedb`.`user_role` 
ADD INDEX `role_id_FK_idx` (`role_id` ASC) VISIBLE;
;
ALTER TABLE `referencedb`.`user_role` 
ADD CONSTRAINT `user_id_FK`
  FOREIGN KEY (`user_id`)
  REFERENCES `referencedb`.`user` (`Id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `role_id_FK`
  FOREIGN KEY (`role_id`)
  REFERENCES `referencedb`.`role` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

INSERT INTO `referencedb`.`role` (`name`) VALUES ('Admin');
INSERT INTO `referencedb`.`role` (`name`) VALUES ('User');

INSERT INTO `referencedb`.`user_role`(`user_id`,`role_id`) VALUES(1,1);
INSERT INTO `referencedb`.`user_role`(`user_id`,`role_id`) VALUES(2,1);
INSERT INTO `referencedb`.`user_role`(`user_id`,`role_id`) VALUES(3,1);
INSERT INTO `referencedb`.`user_role`(`user_id`,`role_id`) VALUES(4,1);

ALTER TABLE `referencedb`.`user` 
ADD COLUMN `Firstname` VARCHAR(45) NOT NULL AFTER `StatusId`,
ADD COLUMN `Lastname` VARCHAR(45) NOT NULL AFTER `Firstname`;

