--liquibase formatted sql
--DO NOT DELETE ABOVE LINE. IT IS NEEDED BY LIQUIBASE.

--changeset mahendran.mookkiah:master-data-001-template-001 failOnError:false splitStatements:false logicalFilePath:master-data-001-template.sql
 --encrypted password is CJFAdmin1$
SELECT add_aaf_user('todo.admin', '$shiro1$SHA-256$500000$IY8UpTSgw+IJDSX5n9mWWg==$HlTbeTYyX1PnaLbsdD8m2YLUZ1YZhsMTD7zBV3IP3RA=', 'cjf', 'admin', 'noreply@cevalogistics.com');

SELECT add_aaf_user_role('todo.admin', 'Todo Management');
