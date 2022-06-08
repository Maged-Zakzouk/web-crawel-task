USE `boost-crawel`;

CREATE TABLE IF NOT EXISTS website (
  `id`                  INT(11) NOT NULL AUTO_INCREMENT,
  `url`                 VARCHAR (250) NOT NULL,
  `status`              VARCHAR (50) NOT NULL,
  `creation_date`       DATETIME NULL DEFAULT now(),
  `modification_date`   datetime    DEFAULT NULL,
  `created_by`          VARCHAR(250) NULL,
  `modified_by`         VARCHAR(250) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `website_url` (`url` ASC)
);

CREATE TABLE IF NOT EXISTS web_page (
  `id`                  INT(11) NOT NULL AUTO_INCREMENT,
  `website_id`          INT(11) NOT NULL,
  `title`               VARCHAR(250) NULL,
  `url`                 VARCHAR(250) NULL,
  `status`              VARCHAR (50) NOT NULL,
  `creation_date`       DATETIME NULL DEFAULT now(),
  `modification_date`   datetime    DEFAULT NULL,
  `created_by`          VARCHAR(250) NULL,
  `modified_by`         VARCHAR(250) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `web_page_url` (`url` ASC),
  CONSTRAINT `web_page_website_id_fk`
      FOREIGN KEY (`website_id`)
      REFERENCES `website` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);


CREATE TABLE IF NOT EXISTS web_page_link (
  `id`                  INT(11) NOT NULL AUTO_INCREMENT,
  `source_id`           INT(11) NOT NULL,
  `destination_id`      INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `web_page_link_source_id` (`source_id` ASC),
  INDEX `web_page_link_destination_id` (`destination_id` ASC),
  CONSTRAINT `web_page_link_source_id_fk`
    FOREIGN KEY (`source_id`)
    REFERENCES `web_page` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `web_page_link_destination_id_fk`
    FOREIGN KEY (`destination_id`)
    REFERENCES `web_page` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);
