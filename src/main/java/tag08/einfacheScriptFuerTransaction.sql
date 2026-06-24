USE bank;
#SET autocommit = 0;

START TRANSACTION;
SELECT * FROM kunde;

UPDATE kunde SET name = 'WBS Köln';

SELECT * FROM kunde;

ROLLBACK;
#COMMIT;

SELECT * FROM kunde;