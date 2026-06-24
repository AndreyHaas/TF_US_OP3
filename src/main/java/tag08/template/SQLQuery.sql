# Transaktionen in MySQL
# Mit Transaktionen können wir im Falle eines Fehlers Änderungen rückgängig machen.
# https://www.freecodecamp.org/news/how-to-use-mysql-transactions/
# https://dev.mysql.com/doc/refman/8.4/en/innodb-autocommit-commit-rollback.html
# https://www.mysqltutorial.org/mysql-error-handling-in-stored-procedures/

USE bank;

# Kurzes Beispiel zu Transaktionen:
#SET autocommit = 0;
# Jeder SQL Befehl wird automatisch in einer Transaktion ausgeführt, die auch automatisch committed wird, wenn kein Fehler auftritt.
# Durch autocommit = 0 kann dieses automatische committen deaktiviert werden.
# Alternativ können wir manuell eine Transaktion starten, die wir dann explizit committen müssen.
START TRANSACTION;
SELECT * FROM Kunde;
UPDATE Kunde SET name = "WBS Köln";
SELECT * FROM Kunde;
ROLLBACK; # Mit Rollback können wir alle Änderungen seit dem Start der Transaktion rückgängig machen.
#COMMIT; # Mit Commit können wir alle Änderungen festschreiben.
SELECT * FROM Kunde;

# Beispiel: Wir buchen Geld von einem Konto auf ein anderes. Zuerst ziehen wir von A das Geld ab und buchen es auf B.
# Wenn nun aber beim Buchen auf B ein Fehler auftritt, muss das Abbuchen von A rückgängig gemacht werden.

DROP PROCEDURE IF EXISTS überweisung;

DELIMITER $

CREATE PROCEDURE überweisung (kontoVon CHAR(10), kontoNach CHAR(10), betrag DOUBLE)
BEGIN
	START TRANSACTION; # Transaktion starten.

    UPDATE konto SET kontostand = kontostand - betrag WHERE nummer = kontoVon;
    SET @count1 = ROW_COUNT(); # ROW_COUNT() gibt die Anzahl der betroffenen Zeilen des letzten Befehls zurück.

    UPDATE konto SET kontostand = kontostand + betrag WHERE nummer = kontoNach;
    SET @count2 = ROW_COUNT();

    IF @count1 = 1 AND @count2 = 1 THEN
    	COMMIT; # Wenn bei beiden Befehlen jeweils ein Datensatz betroffen wurde, bestätigen wir die Transaktion.
    ELSE
    	ROLLBACK; # Funktioniert eine der Befehle nicht richtig, machen wir alles rückgängig.
        SELECT CONCAT('Fehler bei der Überweisung von ', kontoVon, ' auf ', kontoNach) AS Meldung;
    END IF;
END$

DELIMITER ;

SELECT * FROM konto;
CALL überweisung('0000987654', '0000123456', 500.00);
SELECT * FROM konto;
CALL überweisung('0000987654', '0000', 500.00);
SELECT * FROM konto;