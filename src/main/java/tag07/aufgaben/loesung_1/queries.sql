USE onlineshop;

DROP PROCEDURE IF EXISTS insertKunde;
DROP PROCEDURE IF EXISTS updateKundeName;


DELIMITER $$
CREATE PROCEDURE insertKunde (name VARCHAR(100), OUT nummer INT)
BEGIN
	INSERT INTO kunde (name)
    VALUES (name);
    # Parameter als Values

    SELECT LAST_INSERT_ID() INTO nummer;
END$$

CREATE PROCEDURE updateKundeName (nummer INT, name VARCHAR(100))
BEGIN
	UPDATE kunde SET kunde.name = name WHERE kunde.nummer = nummer;
END$$
DELIMITER ;
