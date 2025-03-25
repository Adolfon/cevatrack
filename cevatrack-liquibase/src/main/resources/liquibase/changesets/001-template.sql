--liquibase formatted sql
--DO NOT DELETE ABOVE LINE. IT IS NEEDED BY LIQUIBASE.

--changeset mahendran.mookkiah:001-aaf-db-001 failOnError:false splitStatements:false logicalFilePath:001-template.sql
CREATE TABLE aaf_user
(
  user_id SERIAL,
  user_principal character varying(64) NOT NULL,
  first_name character varying(64) NOT NULL,
  last_name character varying(64) NOT NULL,
  email character varying(255) NOT NULL,
  password character varying(255),
  user_type character varying(1) NOT NULL,
  is_active boolean default true,
  incorrect_login_attempts smallint default 0,
  is_locked boolean default false,
  last_password_change_time timestamp(6) with time zone,
  is_password_change_required boolean default false,
  last_successful_login timestamp(6) with time zone,
  account_expiration_date timestamp(6) with time zone,
  created_on timestamp(6) with time zone NOT NULL,
  created_by character varying(64) NOT NULL,
  updated_on timestamp(6) with time zone,
  updated_by character varying(64),
  version smallint NOT NULL,
  CONSTRAINT aaf_user_pkey PRIMARY KEY (user_id),
  CONSTRAINT aaf_user_ukey UNIQUE (user_principal)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE aaf_role
(
  role_id SERIAL,
  role_name character varying(255) NOT NULL,
  description character varying(255) NOT NULL,
  created_on timestamp(6) with time zone NOT NULL,
  created_by character varying(64) NOT NULL,
  updated_on timestamp(6) with time zone,
  updated_by character varying(64),
  version smallint NOT NULL,
  CONSTRAINT aaf_role_pkey PRIMARY KEY (role_id),
  CONSTRAINT aaf_role_ukey UNIQUE (role_name)
)
WITH (
  OIDS=FALSE
);


CREATE TABLE aaf_permission
(
  permission_id SERIAL,
  domain character varying(50) NOT NULL,
  action character varying(50) NOT NULL,
  instance character varying(50) NOT NULL,
  description character varying(255) NOT NULL,
-- type -- EntityPermission, MethodPermission, FuntionalPermission, ResourcePermission, ShiroPermission(Default)
  created_on timestamp(6) with time zone NOT NULL,
  created_by character varying(64) NOT NULL,
  updated_on timestamp(6) with time zone,
  updated_by character varying(64),
  version smallint NOT NULL,
  CONSTRAINT aaf_permission_pkey PRIMARY KEY (permission_id)
)
WITH (
  OIDS=FALSE
);


CREATE TABLE aaf_user_role
(
  user_role_id SERIAL,
  user_id bigint NOT NULL,
  role_id bigint NOT NULL,
  created_on timestamp(6) with time zone NOT NULL,
  created_by character varying(64) NOT NULL,
  updated_on timestamp(6) with time zone,
  updated_by character varying(64),
  version smallint NOT NULL,
  CONSTRAINT aaf_user_role_pkey PRIMARY KEY (user_role_id),
  CONSTRAINT aaf_user_role_role_id_fk FOREIGN KEY (role_id)
      REFERENCES aaf_role (role_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT aaf_user_role_user_id_fk FOREIGN KEY (user_id)
      REFERENCES aaf_user (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

  
CREATE TABLE aaf_role_permission
(
  role_permission_id SERIAL,
  role_id bigint NOT NULL,
  permission_id bigint NOT NULL,
  created_on timestamp(6) with time zone NOT NULL,
  created_by character varying(64) NOT NULL,
  updated_on timestamp(6) with time zone,
  updated_by character varying(64),
  version smallint NOT NULL,
  CONSTRAINT aaf_role_permission_pkey PRIMARY KEY (role_permission_id),
   CONSTRAINT aaf_role_permission_role_id_fk FOREIGN KEY (role_id)
      REFERENCES aaf_role (role_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT aaf_role_permission_permission_id_fk FOREIGN KEY (permission_id)
      REFERENCES aaf_permission (permission_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
  

CREATE TABLE aaf_domain_lookup
(
  aaf_domain_code character varying(64) NOT NULL,
  description character varying(255) NOT NULL,
  display_order smallint NOT NULL,
  display_label character varying(64),
  created_on timestamp(6) with time zone,
  created_by character varying(15),
  updated_on timestamp(6) with time zone,
  updated_by character varying(15),
  version smallint,
  CONSTRAINT aaf_domain_lookup_pk PRIMARY KEY (aaf_domain_code)
);

CREATE TABLE aaf_domain_action_lookup
(
  aaf_domain_code character varying(64) NOT NULL,
  aaf_domain_action_code character varying(64) NOT NULL,
  description character varying(255) NOT NULL,
  display_order smallint NOT NULL,
  display_label character varying(64),
  created_on timestamp(6) with time zone,
  created_by character varying(15),
  updated_on timestamp(6) with time zone,
  updated_by character varying(15),
  version smallint,
  CONSTRAINT aaf_domain_action_lookup_pk PRIMARY KEY (aaf_domain_code, aaf_domain_action_code),
  CONSTRAINT aaf_domain_action_lookup_domain_code_fk FOREIGN KEY (aaf_domain_code)
      REFERENCES aaf_domain_lookup (aaf_domain_code) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
);

-- Create new AAF functions add_aaf_domain_lookup:
CREATE FUNCTION add_aaf_domain_lookup(aaf_domain_code text, description text, display_order integer, display_label text) RETURNS text AS $$
DECLARE
      count_domain_codes integer;
result text;
BEGIN 
      -- Check if the domain action lookup already exists.     
      EXECUTE 'SELECT Count(aaf_domain_code) FROM aaf_domain_lookup where aaf_domain_code=$1' INTO count_domain_codes USING aaf_domain_code;   
      IF (count_domain_codes = 0) THEN
                  
          result := 'OK';
          INSERT INTO aaf_domain_lookup(
            aaf_domain_code, description, display_order, display_label, created_on, 
            created_by, updated_on, updated_by, version)
		    VALUES ($1, $2, $3, $4, now(), 'SYSTEM', null, null, 1); 
                  
      ELSE
            result := 'KO: the domain code [aaf_domain_code="' || aaf_domain_code || '" ] already exists in the target DB';
      END IF;
      RETURN result;
END
$$ LANGUAGE plpgsql;

-- Create new AAF functions add_aaf_domain_action_lookup:
CREATE FUNCTION add_aaf_domain_action_lookup(aaf_domain_code text, aaf_domain_action_code text, description text, display_order integer, display_label text) RETURNS text AS $$
DECLARE
      count_domain_codes integer;
      count_aaf_domain_code integer;
result text;
BEGIN 
      -- Check if the domain action lookup already exists.     
      EXECUTE 'SELECT Count(aaf_domain_code) FROM aaf_domain_action_lookup where aaf_domain_code=$1 and aaf_domain_action_code=$2' INTO count_domain_codes USING aaf_domain_code,aaf_domain_action_code;   
      IF (count_domain_codes = 0) THEN
                  -- Check if the aaf_domain_code exists in the table aaf_domain_lookup.
                  EXECUTE 'SELECT Count(aaf_domain_code) FROM aaf_domain_lookup where aaf_domain_code=$1' INTO count_aaf_domain_code USING aaf_domain_code;
                  IF (count_aaf_domain_code != 0) THEN
                  result := 'OK';
                  INSERT INTO aaf_domain_action_lookup(
                                   aaf_domain_code, aaf_domain_action_code, description, display_order, display_label, 
                                   created_on, created_by, updated_on, updated_by, version)
                             VALUES (aaf_domain_code, aaf_domain_action_code, description, display_order, display_label, 
                                   now(), 'SYSTEM', null, null, 1);
                  ELSE
                        result := 'KO: the aaf_domain_code=' || aaf_domain_code || '" does not exist in the target DB';  
                  END IF;
      ELSE
            result := 'KO: the domain action lookup [aaf_domain_code="' || aaf_domain_code || '", aaf_domain_action_code="' || aaf_domain_action_code || '" ] already exists in the target DB';
      END IF;
      RETURN result;
END
$$ LANGUAGE  PLPGSQL;


-- Create new AAF functions add_aaf_permission:
CREATE FUNCTION add_aaf_permission(domain text, action text, instance text, description text) RETURNS text AS $$
DECLARE
count_permissions integer;
result text;
BEGIN 
      -- Check if the permission already exists.     
      EXECUTE 'SELECT Count(domain) FROM aaf_permission where domain=$1 and action=$2 and instance=$3' INTO count_permissions USING domain, action, instance;   
      IF (count_permissions = 0) THEN
            result := 'OK';
            INSERT INTO aaf_permission ( domain, action, instance, description, created_on, created_by, updated_on, updated_by, version) 
                  VALUES (domain, action, instance, description, now(), 'SYSTEM', NULL, NULL, 1);
      ELSE
            result := 'KO: the permission [domain="' || domain || '", action="' || action || '", instance="' || instance || '" ] already exists in the target DB';
      END IF;
      RETURN result;          
END
$$ LANGUAGE plpgsql;

-- Create new AAF functions add_aaf_role:
CREATE FUNCTION add_aaf_role(role_name text, description text) RETURNS text AS $$
DECLARE
count_roles integer;
result text;
BEGIN 
      -- Check if the permission already exists.     
      EXECUTE 'SELECT Count(role_name) FROM aaf_role where role_name=$1' INTO count_roles USING role_name;   
      IF (count_roles = 0) THEN
            result := 'OK';
            INSERT INTO aaf_role ( role_name, description, created_on, created_by, updated_on, updated_by, version) 
			VALUES ($1, $2, now(), 'SYSTEM', NULL, NULL, 0);
      ELSE
            result := 'KO: the role [role_name="' || role_name || '" ] already exists in the target DB';
      END IF;
      RETURN result;          
END
$$ LANGUAGE plpgsql;

-- Parameters: role_name, permission_domain, permission_action, permission_action
CREATE FUNCTION add_aaf_role_permission(text, text, text, text) RETURNS void AS $$
	INSERT INTO aaf_role_permission (role_id, permission_id, created_on, created_by, updated_on, updated_by, version) 
	VALUES (
	(SELECT role_id from aaf_role where role_name = $1), 
	(SELECT permission_id from aaf_permission where domain = $2 and action = $3 and instance=$4),
	now(), 'SYSTEM', NULL, NULL, 0);
$$ LANGUAGE SQL;

-- Create new AAF functions add_aaf_user:
CREATE FUNCTION add_aaf_user(user_principal text, password text, fname text, lname text, email text) RETURNS text AS $$
DECLARE
count_users integer;
result text;
BEGIN 
      -- Check if the permission already exists.     
      EXECUTE 'SELECT Count(user_principal) FROM aaf_user where user_principal=$1' INTO count_users USING user_principal;   
      IF (count_users = 0) THEN
            result := 'OK';
            INSERT INTO aaf_user (user_principal, password, first_name, last_name, email, user_type, is_locked, is_password_change_required, created_on, created_by, version)
    			VALUES ($1, $2, $3, $4, $5, 'E', false, false, now(), 'initial_load', 0);            
      ELSE
            result := 'KO: the user [user_principal="' || user_principal || '" ] already exists in the target DB';
      END IF;
      RETURN result;          
END    
$$ LANGUAGE plpgsql;

-- Parameters: user_principal, role_name
CREATE FUNCTION add_aaf_user_role(text, text) RETURNS void AS $$
	INSERT INTO aaf_user_role (user_id, role_id, created_on, created_by, version)
	VALUES ( 
	(SELECT user_id FROM aaf_user where user_principal=$1),
	(SELECT role_id FROM aaf_role where role_name=$2), 
	now(), 'initial_load', 0);
$$ LANGUAGE SQL;



-- Parameters: aaf_domain_code, description, display_order, display_label
SELECT add_aaf_domain_lookup('aaf_user', 'user management', 1, 'User');
SELECT add_aaf_domain_lookup('aaf_role', 'role management', 2, 'Role');
SELECT add_aaf_domain_lookup('aaf_permission', 'permisssion management', 3, 'Permission');
     

-- Parameters: aaf_domain_code, aaf_domain_action_code, description, display_order, display_label
select add_aaf_domain_action_lookup('aaf_user', 'view', 'view action', 1, 'View User');     
select add_aaf_domain_action_lookup('aaf_user', 'create', 'create action', 2, 'Create User');
select add_aaf_domain_action_lookup('aaf_user', 'edit', 'edit action', 3, 'Edit User');
select add_aaf_domain_action_lookup('aaf_user', 'delete', 'delete action', 4, 'Delete User');
select add_aaf_domain_action_lookup('aaf_user', '*', 'manage action', 5, 'Manage User');

select add_aaf_domain_action_lookup('aaf_role', 'view', 'view action', 1, 'View Role');     
select add_aaf_domain_action_lookup('aaf_role', 'create', 'create action', 2, 'Create Role');
select add_aaf_domain_action_lookup('aaf_role', 'edit', 'edit action', 3, 'Edit Role');
select add_aaf_domain_action_lookup('aaf_role', 'delete', 'delete action', 4, 'Delete Role');
select add_aaf_domain_action_lookup('aaf_role', 'assign', 'assign action', 5, 'Assign Role');
select add_aaf_domain_action_lookup('aaf_role', '*', 'manage action', 6, 'Manage Role');

select add_aaf_domain_action_lookup('aaf_permission', 'view', 'view action', 1, 'View Permission');     
select add_aaf_domain_action_lookup('aaf_permission', 'create', 'create action', 2, 'Create Permission');
select add_aaf_domain_action_lookup('aaf_permission', 'edit', 'edit action', 3, 'Edit Permission');
select add_aaf_domain_action_lookup('aaf_permission', 'delete', 'delete action', 4, 'Delete Permission');
select add_aaf_domain_action_lookup('aaf_permission', 'assign', 'assign action', 5, 'Assign Permission');
select add_aaf_domain_action_lookup('aaf_permission', '*', 'manage action', 6, 'Manage Permission');

-- Parameters: domain, action, instance, description
SELECT add_aaf_permission ('aaf_user', '*', '*', 'Manage User');
SELECT add_aaf_permission ('aaf_user', 'create', '*', 'Create User');
SELECT add_aaf_permission ('aaf_user', 'view', '*', 'View User');
SELECT add_aaf_permission ('aaf_user', 'edit', '*', 'Edit User');
SELECT add_aaf_permission ('aaf_user', 'delete', '*', 'Delete User');

SELECT add_aaf_permission ('aaf_role', '*', '*', 'Manage Role');
SELECT add_aaf_permission ('aaf_role', 'create', '*', 'Create Role');
SELECT add_aaf_permission ('aaf_role', 'view', '*', 'View Role');
SELECT add_aaf_permission ('aaf_role', 'edit', '*', 'Edit Role');
SELECT add_aaf_permission ('aaf_role', 'delete', '*', 'Delete Role');
SELECT add_aaf_permission ('aaf_role', 'assign', '*', 'Assign Role');

SELECT add_aaf_permission ('aaf_permission', '*', '*', 'Manage Permission');
SELECT add_aaf_permission ('aaf_permission', 'create', '*', 'Create Permission');
SELECT add_aaf_permission ('aaf_permission', 'view', '*', 'View Permission');
SELECT add_aaf_permission ('aaf_permission', 'edit', '*', 'Edit Permission');
SELECT add_aaf_permission ('aaf_permission', 'delete', '*', 'Delete Permission');
SELECT add_aaf_permission ('aaf_permission', 'assign', '*', 'Assign Permission');

-- Parameters: role_name, description
SELECT add_aaf_role ('AAF Admin', 'AAF Admin');

-- Parameters: role_name, permission_domain, permission_action, permission_action
SELECT add_aaf_role_permission ('AAF Admin', 'aaf_user', '*', '*');
SELECT add_aaf_role_permission ('AAF Admin', 'aaf_role', '*', '*');
SELECT add_aaf_role_permission ('AAF Admin', 'aaf_permission', '*', '*');

--encrypted password is AAFAdmin1$
SELECT add_aaf_user('aaf.admin', '$shiro1$SHA-256$500000$NloEOMDwdx69paycM+bKTQ==$vPfNio0VSVPbSW504PfksqMFLfooI5vvKUY5av8reWo=', 'aaf', 'admin', 'noreply@cevalogistics.com');
SELECT add_aaf_user_role('aaf.admin', 'AAF Admin');



CREATE TABLE aaf_user_password_hist
(
  password_id SERIAL PRIMARY KEY,
  user_id int,
  password character varying(255),
  start_time timestamp(6) with time zone NOT NULL,
  end_time timestamp(6) with time zone,
  created_on timestamp(6) with time zone NOT NULL,
  created_by character varying(64) NOT NULL,
  updated_on timestamp(6) with time zone,
  updated_by character varying(64),
  version smallint NOT NULL,
  CONSTRAINT aaf_user_password_hist_user_id_fk FOREIGN KEY (user_id)
      REFERENCES aaf_user (user_id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);

SELECT add_aaf_permission ('aaf_user', 'change_roles', '*', 'Change Roles User');
select add_aaf_domain_action_lookup('aaf_user', 'change_roles', 'change roles action', 7, 'Change Roles User');

ALTER TABLE aaf_permission ADD CONSTRAINT aaf_permission_ukey UNIQUE (domain, action, instance);

SELECT add_aaf_permission ('aaf_role', 'change_permission', '*', 'Change Permission Role');
select add_aaf_domain_action_lookup('aaf_role', 'change_permission', 'change permissions action', 8, 'Change Permission Role');
SELECT add_aaf_permission ('aaf_permission', 'assign', '*', 'Assign Permission');
select add_aaf_domain_action_lookup('aaf_permission', 'assign', 'Assign Permission Action', 9, 'Assign Permission');

select add_aaf_domain_action_lookup('aaf_user', 'change_roles', 'change roles action', 6, 'Change Roles User');
select add_aaf_domain_action_lookup('aaf_role', 'change_permission', 'change permissions action', 7, 'Change Permission Role');
select add_aaf_domain_action_lookup('aaf_permission', 'assign', 'Assign Permission Action', 7, 'Assign Permission');


--changeset venkateswarlu:001-cjf-template-db-001 failOnError:false splitStatements:false logicalFilePath:001-template.sql 
CREATE TABLE todo
(
     id            SERIAL,
     title         CHARACTER VARYING (255),
     description   CHARACTER VARYING (255),
     duedate       timestamp (6) WITHOUT TIME ZONE,
     priority      integer,
     owner   CHARACTER VARYING (100),
     created_on timestamp(6) with time zone NOT NULL,
	 created_by character varying(64) NOT NULL,
	 updated_on timestamp(6) with time zone,
	 updated_by character varying(64),
	 version smallint NOT NULL
	);

CREATE TABLE cjf_user
(
	id SERIAL,
	login CHARACTER VARYING(255),
	created_on timestamp(6) with time zone NOT NULL,
 	created_by character varying(64) NOT NULL,
 	updated_on timestamp(6) with time zone,
 	updated_by character varying(64),
 	version smallint NOT NULL
);

--changeset srinivas.lyagala:001-cjf-template-db-002 failOnError:false splitStatements:false 
 SELECT add_aaf_domain_lookup('todo', 'Todo management', 4, 'Todo');
 
 select add_aaf_domain_action_lookup('todo', 'view', 'view action', 1, 'View Todo');     
 select add_aaf_domain_action_lookup('todo', 'create', 'create action', 2, 'Create Todo');
 select add_aaf_domain_action_lookup('todo', 'edit', 'edit action', 3, 'Edit Todo');
 select add_aaf_domain_action_lookup('todo', 'delete', 'delete action', 4, 'Delete Todo');
 select add_aaf_domain_action_lookup('todo', '*', 'manage action', 5, 'Manage Todo');
 
--changeset venkateswarlu:001-cjf-template-db-003 failOnError:false splitStatements:false  
 --encrypted password is CJFAdmin1$
SELECT add_aaf_user('cjf.admin', '$shiro1$SHA-256$500000$IY8UpTSgw+IJDSX5n9mWWg==$HlTbeTYyX1PnaLbsdD8m2YLUZ1YZhsMTD7zBV3IP3RA=', 'cjf', 'admin', 'noreply@cevalogistics.com');

-- Parameters: role_name, description
SELECT add_aaf_role ('Todo Management', 'Todo Management');

SELECT add_aaf_user_role('cjf.admin', 'Todo Management');
SELECT add_aaf_user_role('cjf.admin', 'AAF Admin');

-- Parameters: domain, action, instance, description
SELECT add_aaf_permission ('todo', '*', '*', 'Manage Todo');
SELECT add_aaf_permission ('todo', 'create', '*', 'Create Todo');
SELECT add_aaf_permission ('todo', 'view', '*', 'View Todo');
SELECT add_aaf_permission ('todo', 'edit', '*', 'Edit Todo');
SELECT add_aaf_permission ('todo', 'delete', '*', 'Delete Todo');

-- Parameters: role_name, permission_domain, permission_action, permission_instance
SELECT add_aaf_role_permission ('Todo Management', 'todo', '*', '*');

--changeset venkateswarlu:001-cjf-template-db-004 failOnError:false splitStatements:false
-- This change is part of release 1.4 
update aaf_user set account_expiration_date=(now() + interval '100 years') where account_expiration_date is null and user_type='E';

--changeset mahendran.mookkiah:001-cjf-template-db-005 failOnError:false splitStatements:false context:DEV
SELECT add_aaf_role ('Demo Time', 'Demo Time operator');


--changeset brian.goodall:001-cjf-template-db-006 failOnError:false splitStatements:false 

-- Parameters: aaf_domain_code, description, display_order, display_label
 SELECT add_aaf_domain_lookup('sn_client', 'SmartNet Client', 5, 'SmartNet Client');
 SELECT add_aaf_domain_lookup('sn_site', 'SmartNet Site', 6, 'SmartNet Site');
 
 -- Parameters: aaf_domain_code, aaf_domain_action_code, description, display_order, display_label
 select add_aaf_domain_action_lookup('sn_client', 'view', 'View SmartNet Client', 1, 'View SmartNet Client');     
 select add_aaf_domain_action_lookup('sn_site', 'view', 'View SmartNet Site', 1, 'View SmartNet Site');  
 
 --encrypted password is CJFAdmin1$
 -- Parameters: name, password, forename, lastname, email
SELECT add_aaf_user('nike296633', '$shiro1$SHA-256$500000$IY8UpTSgw+IJDSX5n9mWWg==$HlTbeTYyX1PnaLbsdD8m2YLUZ1YZhsMTD7zBV3IP3RA=', 'nike', 'client', 'noreply@cevalogistics.com');
SELECT add_aaf_user('nike', '$shiro1$SHA-256$500000$IY8UpTSgw+IJDSX5n9mWWg==$HlTbeTYyX1PnaLbsdD8m2YLUZ1YZhsMTD7zBV3IP3RA=', 'nike', 'site 296633', 'noreply@cevalogistics.com');
SELECT add_aaf_user('ceva', '$shiro1$SHA-256$500000$IY8UpTSgw+IJDSX5n9mWWg==$HlTbeTYyX1PnaLbsdD8m2YLUZ1YZhsMTD7zBV3IP3RA=', 'ceva', 'global', 'noreply@cevalogistics.com');

-- Parameters: role_name, description
SELECT add_aaf_role ('sn_all', 'SmartNet Global');
SELECT add_aaf_role ('sn_nike_all', 'SmartNet Nike All Sites');
SELECT add_aaf_role ('sn_nike_296633', 'SmartNet Site 296633');

-- Parameters: user_principal, role_name
SELECT add_aaf_user_role('ceva', 'sn_all');
SELECT add_aaf_user_role('nike', 'sn_nike_all');
SELECT add_aaf_user_role('nike296633', 'sn_nike_296633');

-- Parameters: domain, action, instance, description
SELECT add_aaf_permission ('sn_client', 'view', '*', 'View SmartNet All Clients');
SELECT add_aaf_permission ('sn_client', 'view', 'nike', 'View SmartNet Nike');
SELECT add_aaf_permission ('sn_site', 'view', '*', 'Manage SmartNet All Sites');
SELECT add_aaf_permission ('sn_site', 'view', '296633', 'Manage SmartNet Site 296633');

-- Parameters: role_name, permission_domain, permission_action, permission_instance
SELECT add_aaf_role_permission ('sn_all', 'sn_client', 'view', '*');
SELECT add_aaf_role_permission ('sn_all', 'sn_site', 'view', '*');
SELECT add_aaf_role_permission ('sn_nike_all', 'sn_client', 'view', 'nike');
SELECT add_aaf_role_permission ('sn_nike_all', 'sn_site', 'view', '*');
SELECT add_aaf_role_permission ('sn_nike_296633', 'sn_client', 'view', 'nike');
SELECT add_aaf_role_permission ('sn_nike_296633', 'sn_site', 'view', '296633');

--changeset brian.goodall:001-cjf-template-db-007 failOnError:false splitStatements:false  
CREATE TABLE smartnet_client
(
     id            SERIAL,
     name         CHARACTER VARYING (50),
     prefixes     CHARACTER VARYING (255),
     image        CHARACTER VARYING (255),
     aaf_instance CHARACTER VARYING (20),
     created_on timestamp(6) with time zone NOT NULL,
	   created_by character varying(64) NOT NULL,
	   updated_on timestamp(6) with time zone,
	   updated_by character varying(64),
	   version smallint NOT NULL
  );
  

--changeset brian.goodall:001-cjf-template-db-008 failOnError:false splitStatements:false  
INSERT INTO smartnet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Aspall','ASPALL','Aspall.png','aspall',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO smartnet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('British Gypsum Sherburn UAT','BGYPSH','default.png','bgypsh',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO smartnet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Bentley','BENTLEY','Bentley.png','bentley',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO smartnet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('CanPak','CANPAK','CanPak.png','canpak',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO smartnet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Continental','CONTI','Continental.png','continental',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO smartnet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Crown Paints','CWNRAD','Cwnrad.png','crown',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO smartnet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Gap','GAP','Gap.png','gap',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO smartnet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Lush','LUSH','Lush.png','lush',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO smartnet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('New TMG','NEWTMG','NewTMG.png','newtmg',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO smartnet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Nike','NIKE','Nike.png','nike',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO smartnet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Twinings','TWINNG','Twinings.png','twinings',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO smartnet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Uniqlo','ARVATO','Uniqlo.png','uniqlo',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO smartnet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Uniqlo Adhoc','AdhocARVATO','UniqloAdhoc.png','uniqloadhoc',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO smartnet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Whitworths','WHITWR','Whitworths.png','whitworths',now(),'SYSTEM',now(),'SYSTEM',1);
  
--changeset brian.goodall:001-cjf-template-db-009 failOnError:false splitStatements:false 

-- Parameters: aaf_domain_code, description, display_order, display_label
 SELECT add_aaf_domain_lookup('sn_microlise', 'Microlise Webservice for SmartNet', 7, 'SmartNet Microlise');
 
 -- Parameters: aaf_domain_code, aaf_domain_action_code, description, display_order, display_label
 select add_aaf_domain_action_lookup('sn_microlise', 'tibco', 'Connect via Tibco', 1, 'Tibco Connection');  
 select add_aaf_domain_action_lookup('sn_microlise', 'direct', 'Connect directly to Microlise (DEV ONLY)', 1, 'Direct Connection');     
 
-- Parameters: role_name, description
SELECT add_aaf_role ('sn_microlise_prod_via_tibco', 'Production microlise via Tibco');
SELECT add_aaf_role ('sn_microlise_test_via_tibco', 'Test microlise via Tibco');
SELECT add_aaf_role ('sn_microlise_direct_prod', 'Direct to microlise production (DEV ONLY)');
SELECT add_aaf_role ('sn_microlise_direct_test', 'Direct to microlise test system (DEV ONLY)');

-- Parameters: user_principal, role_name
SELECT add_aaf_user_role('ceva', 'sn_microlise_direct_prod');
SELECT add_aaf_user_role('nike', 'sn_microlise_direct_prod');
SELECT add_aaf_user_role('nike296633', 'sn_microlise_direct_prod');

-- Parameters: domain, action, instance, description
SELECT add_aaf_permission ('sn_microlise', 'tibco', 'prod', 'Connect indirectly to Microlise Production');
SELECT add_aaf_permission ('sn_microlise', 'tibco', 'uat', 'Connect indirectly to Microlise Test');
SELECT add_aaf_permission ('sn_microlise', 'direct', 'prod', 'Connect directly to Microlise Production');
SELECT add_aaf_permission ('sn_microlise', 'direct', 'uat', 'Connect directly to Microlise Test');

-- Parameters: role_name, permission_domain, permission_action, permission_instance
SELECT add_aaf_role_permission ('sn_microlise_prod_via_tibco', 'sn_microlise', 'tibco', 'prod');
SELECT add_aaf_role_permission ('sn_microlise_test_via_tibco', 'sn_microlise', 'tibco', 'uat');
SELECT add_aaf_role_permission ('sn_microlise_direct_prod', 'sn_microlise', 'direct', 'prod');
SELECT add_aaf_role_permission ('sn_microlise_direct_test', 'sn_microlise', 'direct', 'uat');

--changeset adolfo.cano:001-cjf-template-db-010 failOnError:false splitStatements:false
UPDATE smartnet_client
 SET name = 'Twinings', image = 'Twinings.png'
   WHERE name = 'Twinings' and prefixes='TWINNG';
   
--changeset adolfo.cano:001-cjf-template-db-011 failOnError:false splitStatements:false  
UPDATE smartnet_client
 SET name = 'British Gypsum Sherburn', image = 'bgypsh.png'
   WHERE aaf_instance = 'bgypsh' and prefixes='BGYPSH';   

--changeset adolfo.cano:001-cjf-template-db-012 failOnError:false splitStatements:false  
UPDATE smartnet_client
 SET  image = 'Cwnrad2.png'
   WHERE aaf_instance = 'crown' and prefixes='CWNRAD';   

--changeset adolfo.cano:001-cjf-template-db-013 failOnError:false splitStatements:false  
UPDATE smartnet_client
 SET  prefixes = 'BENTL'
   WHERE aaf_instance = 'bentley' and name='Bentley';
   
--changeset adolfo.cano:001-cjf-template-db-014 failOnError:false splitStatements:false
SELECT add_aaf_permission ('aaf_permission', 'time', '*', 'Session never expires');
select add_aaf_domain_action_lookup('aaf_permission','time','No session expiration time',1,'Session time');

--changeset adolfo.cano:001-cjf-template-db-015 failOnError:false splitStatements:false
DELETE from smartnet_client WHERE prefixes = 'ASPALL';
DELETE from smartnet_client WHERE prefixes = 'CANPAK';
DELETE from smartnet_client WHERE prefixes = 'NEWTMG';


--changeset mohammad.weshah:001-cjf-template-db-016 failOnError:false splitStatements:false logicalFilePath:001-template.sql
-- Parameters: aaf_domain_code, description, display_order, display_label 
SELECT add_aaf_domain_lookup('cjf-admin', 'CJF Administration', 5, 'CJF Admin');
-- Parameters: aaf_domain_code, aaf_domain_action_code, description, display_order, display_label
SELECT add_aaf_domain_action_lookup('cjf-admin', 'generate', 'Generate', 8, 'Generate');
-- Parameters: role_name, description
SELECT add_aaf_role ('CJF Admin', 'CJF Admin');
-- Parameters: domain, action, instance, description
SELECT add_aaf_permission ('cjf-admin', '*', '*','Administration');
-- Parameters: role_name, permission_domain, permission_action, permission_action_instance
SELECT add_aaf_role_permission ('CJF Admin', 'cjf-admin', '*', '*');

--changeset pruthvi.janapareddy:001-cjf-template-db-017 failOnError:false splitStatements:false logicalFilePath:001-template.sql
--To drop the tables if exists
drop table if exists user_profile;
drop table if exists user_profile_options;

--Create User Profile Options
CREATE TABLE user_profile_options
(
  option_id SERIAL,
  key character varying(255) NOT NULL,
  value character varying(64) NOT NUll,
  app_default boolean default false,
  created_on timestamp(6) with time zone,
  created_by character varying(15),
  updated_on timestamp(6) with time zone,
  updated_by character varying(15),
  version smallint,
  CONSTRAINT user_profile_options_pk PRIMARY KEY (option_id)
);

--Create User Profile 
CREATE TABLE user_profile
(
  user_profile_id SERIAL,
  user_principal character varying(64) NOT NULL,
  language character varying(255),
  date_format character varying(64),
  warehouse_code character varying(10),
  client_code character varying(50),
  time_format character varying(64),
  currency character varying(64),
  time_zone character varying(10),
  created_on timestamp(6) with time zone,
  created_by character varying(15),
  updated_on timestamp(6) with time zone,
  updated_by character varying(15),
  version smallint,
  CONSTRAINT user_profile_pk PRIMARY KEY (user_profile_id)
);

--Dummy Data for options
INSERT INTO user_profile_options ( option_id, key, value, app_default, created_on, created_by, updated_on, updated_by, version) 
			VALUES (1, 'dateFormat', 'mm/dd/yyyy', true, now(), 'CJF', now(), 'CJF', 0);
INSERT INTO user_profile_options ( option_id, key, value, app_default, created_on, created_by, updated_on, updated_by, version) 
			VALUES (2, 'dateFormat', 'dd/mm/yyyy', false, now(), 'CJF', now(), 'CJF', 0);
INSERT INTO user_profile_options ( option_id, key, value, app_default, created_on, created_by, updated_on, updated_by, version) 
			VALUES (3, 'timeFormat', '24HR', true, now(), 'CJF', now(), 'CJF', 0);
INSERT INTO user_profile_options ( option_id, key, value, app_default, created_on, created_by, updated_on, updated_by, version) 
			VALUES (4, 'timeFormat', '12HR', false, now(), 'CJF', now(), 'CJF', 0);
INSERT INTO user_profile_options ( option_id, key, value, app_default, created_on, created_by, updated_on, updated_by, version) 
			VALUES (5, 'currency', 'EUR', true, now(), 'CJF', now(), 'CJF', 0);
INSERT INTO user_profile_options ( option_id, key, value, app_default, created_on, created_by, updated_on, updated_by, version) 
			VALUES (6, 'currency', 'USD', false, now(), 'CJF', now(), 'CJF', 0);
INSERT INTO user_profile_options ( option_id, key, value, app_default, created_on, created_by, updated_on, updated_by, version) 
			VALUES (7, 'timeZone', 'CST', true, now(), 'CJF', now(), 'CJF', 0);
INSERT INTO user_profile_options ( option_id, key, value, app_default, created_on, created_by, updated_on, updated_by, version) 
			VALUES (8, 'timeZone', 'EST', false, now(), 'CJF', now(), 'CJF', 0);
INSERT INTO user_profile_options ( option_id, key, value, app_default, created_on, created_by, updated_on, updated_by, version) 
			VALUES (9, 'language', 'English', true, now(), 'CJF', now(), 'CJF', 0);

--changeset murthy.koppaka:001-cjf-template-db-018 failOnError:false splitStatements:false logicalFilePath:001-template.sql
--alter table for todo
ALTER TABLE todo ADD CONSTRAINT todo_pk PRIMARY KEY (id);

-- create table for File Uploader
CREATE TABLE file_uploader (
	id SERIAL,
	todo_id int4 NOT NULL,
	file_name varchar(255) NOT NULL,
	content_type varchar(10) NULL,
	created_on timestamptz NOT NULL,
	created_by varchar(64) NOT NULL,
	updated_on timestamptz NULL,
	updated_by varchar(64) NULL,
	"version" int2 NOT NULL,
	CONSTRAINT file_uploader_pk PRIMARY KEY (id),
	CONSTRAINT file_uploader_id_fk FOREIGN KEY (todo_id) REFERENCES todo(id) ON DELETE CASCADE
)
WITH (
	OIDS=FALSE
) ;


			
--changeset pruthvi.janapareddy:001-cjf-template-db-019 failOnError:false splitStatements:false logicalFilePath:001-template.sql
--alter table for User Profile to add new column
ALTER TABLE user_profile ADD COLUMN one_id varchar(12);

-- The below inserts are used to restrict an edit on the OneId
-- Parameters: aaf_domain_code, description, display_order, display_label 
SELECT add_aaf_domain_lookup('user_profile_one_id', 'User Profile One Id', 6, 'User Profile One Id');
-- Parameters: aaf_domain_code, aaf_domain_action_code, description, display_order, display_label
SELECT add_aaf_domain_action_lookup('user_profile_one_id', 'edit', 'Edit', 9, 'Edit');


--changeset leonardo.oliveira:001-cjf-template-db-020 failOnError:false splitStatements:false logicalFilePath:001-template.sql
--Change cjf-admin domain, change actions
UPDATE aaf_domain_lookup SET description = 'System Administration', display_label = 'System Administration' WHERE aaf_domain_code = 'cjf-admin';
UPDATE aaf_domain_action_lookup SET aaf_domain_action_code = 'manage', description = 'Allowed to perform all actions', display_label = 'Manage' WHERE aaf_domain_action_code = 'generate';

-- Parameters: domain, action, instance, description
SELECT add_aaf_permission ('cjf-admin', 'manage', 'heap', 'Create Heap Dump');
SELECT add_aaf_permission ('cjf-admin', 'manage', 'serving', 'Enable/Disable Serving Application');

--changeset leonardo.oliveira:001-cjf-template-db-021 failOnError:false splitStatements:false logicalFilePath:001-template.sql
-- adding this record to match the permission Created
SELECT add_aaf_domain_action_lookup('cjf-admin', '*', 'All', 1, 'All');

--changeset pruthvi.janapareddy:001-cjf-template-db-022 failOnError:false splitStatements:false logicalFilePath:001-template.sql
--alter the length of created by and updated by fields for user profile and user profile options
ALTER TABLE user_profile ALTER COLUMN created_by TYPE varchar(64);
ALTER TABLE user_profile ALTER COLUMN updated_by TYPE varchar(64);

ALTER TABLE user_profile_options ALTER COLUMN created_by TYPE varchar(64);
ALTER TABLE user_profile_options ALTER COLUMN updated_by TYPE varchar(64);

--Add foreign constraint on aaf permission table
ALTER TABLE aaf_permission DROP CONSTRAINT IF EXISTS aaf_permission_domain_action_fk;
ALTER TABLE aaf_permission
   ADD CONSTRAINT aaf_permission_domain_action_fk FOREIGN KEY (domain, action)
          REFERENCES aaf_domain_action_lookup (aaf_domain_code,aaf_domain_action_code)
             ON UPDATE CASCADE;
   
--Add unique key constraint on role permission table
ALTER TABLE aaf_role_permission DROP CONSTRAINT IF EXISTS aaf_role_permission_ukey;			 
ALTER TABLE aaf_role_permission ADD CONSTRAINT aaf_role_permission_ukey UNIQUE (role_id,permission_id);

--Add unique key constraint on user role table
ALTER TABLE aaf_user_role DROP CONSTRAINT IF EXISTS aaf_user_role_ukey;	   
ALTER TABLE aaf_user_role ADD CONSTRAINT aaf_user_role_ukey UNIQUE (user_id,role_id);

--Add unique key constraint on user profile table
ALTER TABLE user_profile DROP CONSTRAINT IF EXISTS user_profile_ukey;
ALTER TABLE user_profile ADD CONSTRAINT user_profile_ukey UNIQUE (user_principal);

--Add unique key constraint on user profile options table
ALTER TABLE user_profile_options DROP CONSTRAINT IF EXISTS user_profile_options_ukey;
ALTER TABLE user_profile_options ADD CONSTRAINT user_profile_options_ukey UNIQUE (key,value);

--changeset varsha.anumolu:001-cjf-template-db-023 failOnError:false splitStatements:false logicalFilePath:001-template.sql
UPDATE aaf_user SET email ='adolfo.cano@Cevalogistics.com' 
where user_principal = 'cjf.admin';

--changeset varsha.chowdary:001-cjf-template-db-024 failOnError:false splitStatements:false logicalFilePath:001-template.sql
ALTER TABLE user_profile ADD COLUMN menu_mode varchar(25);
ALTER TABLE user_profile ADD COLUMN theme varchar(50);

--Default values for menu mode and theme
INSERT INTO user_profile_options ( option_id, key, value, app_default, created_on, created_by, updated_on, updated_by, version) 
			VALUES (10, 'menuMode', 'layout-menu-static', false, now(), 'CJF', now(), 'CJF', 0);
INSERT INTO user_profile_options ( option_id, key, value, app_default, created_on, created_by, updated_on, updated_by, version) 
			VALUES (11, 'menuMode', 'layout-menu-overlay', false, now(), 'CJF', now(), 'CJF', 0);
INSERT INTO user_profile_options ( option_id, key, value, app_default, created_on, created_by, updated_on, updated_by, version) 
			VALUES (12, 'menuMode', 'layout-menu-slim', false, now(), 'CJF', now(), 'CJF', 0);
INSERT INTO user_profile_options ( option_id, key, value, app_default, created_on, created_by, updated_on, updated_by, version) 
			VALUES (13, 'menuMode', 'layout-menu-horizontal', true, now(), 'CJF', now(), 'CJF', 0);
INSERT INTO user_profile_options ( option_id, key, value, app_default, created_on, created_by, updated_on, updated_by, version) 
			VALUES (14, 'theme', 'cjf', true, now(), 'CJF', now(), 'CJF', 0);

--changeset varsha.chowdary:001-cjf-template-db-025 failOnError:false splitStatements:false logicalFilePath:001-template.sql
INSERT INTO user_profile (user_principal, language, date_format, time_format, currency, time_zone, created_on, created_by, version, menu_mode, theme ) 
SELECT 'aaf.admin', 'English', 'mm/dd/yyyy', '24HR', 'EUR', 'CST', now(), 'Anonymous', '1', 'layout-menu-horizontal', 'cjf' 
WHERE
    NOT EXISTS (
        SELECT user_principal FROM user_profile WHERE user_principal = 'aaf.admin'
    );

INSERT INTO user_profile (user_principal, language, date_format, time_format, currency, time_zone, created_on, created_by, version, menu_mode, theme ) 
SELECT 'cjf.admin', 'English', 'mm/dd/yyyy', '24HR', 'EUR', 'CST', now(), 'Anonymous', '1', 'layout-menu-horizontal', 'cjf' 
WHERE
    NOT EXISTS (
        SELECT user_principal FROM user_profile WHERE user_principal = 'cjf.admin'
    );
    
--changeset leonardo.oliveira:001-cjf-template-db-026 failOnError:false splitStatements:false logicalFilePath:001-template.sql  
 --encrypted password is CJFAdmin1$
SELECT add_aaf_user('three.automation', '$shiro1$SHA-256$500000$IY8UpTSgw+IJDSX5n9mWWg==$HlTbeTYyX1PnaLbsdD8m2YLUZ1YZhsMTD7zBV3IP3RA=', 'Three', 'Automation', 'three.automation@mailinator.com');
SELECT add_aaf_user_role('three.automation', 'Todo Management');
SELECT add_aaf_user_role('three.automation', 'AAF Admin');
SELECT add_aaf_user_role('three.automation', 'CJF Admin');

--changeset varsha.chowdary:001-cjf-template-db-027-1 failOnError:false splitStatements:false logicalFilePath:001-template.sql
UPDATE user_profile_options SET value = 'MM/dd/yyyy' 
where key = 'dateFormat' AND value = 'mm/dd/yyyy';

UPDATE user_profile_options SET value = 'dd/MM/yyyy' 
where key = 'dateFormat' AND value = 'dd/mm/yyy';

UPDATE user_profile SET date_format = 'MM/dd/yyyy' 
where date_format = 'mm/dd/yyyy';

UPDATE user_profile SET date_format = 'dd/MM/yyyy' 
where date_format = 'dd/mm/yyyy';

--changeset mahendran.mookkiah:001-cjf-template-db-028 failOnError:false splitStatements:false logicalFilePath:001-template.sql
UPDATE aaf_user SET email ='_SH_-GL-US-CJF@Cevalogistics.com' where user_principal = 'cjf.admin';

--changeset pruthvi.janapareddy:001-cjf-template-db-029 failOnError:false splitStatements:false logicalFilePath:001-template.sql
CREATE TABLE app_user_profile
(
	app_user_profile_id SERIAL,
	user_profile_id int,
	enable_todo_notification boolean default false,
	created_on timestamp(6) with time zone NOT NULL,
 	created_by character varying(64) NOT NULL,
 	updated_on timestamp(6) with time zone,
 	updated_by character varying(64),
 	version smallint NOT NULL,
 	CONSTRAINT app_user_profile_pk PRIMARY KEY (app_user_profile_id),
 	CONSTRAINT user_profile_id_fk FOREIGN KEY (user_profile_id)
          REFERENCES user_profile (user_profile_id)
             ON UPDATE CASCADE
);

--changeset varsha.chowdary:001-cjf-template-db-030 failOnError:false splitStatements:false logicalFilePath:001-template.sql
UPDATE user_profile_options SET value = 'dd/MM/yyyy' 
where key = 'dateFormat' AND value = 'dd/mm/yyyy';

--changeset shanmuk.pragada:001-cjf-template-db-031 failOnError:false splitStatements:false logicalFilePath:001-template.sql
UPDATE user_profile_options SET value = 'HH:mm:ss', updated_on = now()
where key = 'timeFormat' AND value = '24HR';

UPDATE user_profile_options SET value = 'hh:mm:ss a', updated_on = now()
where key = 'timeFormat' AND value = '12HR';

UPDATE user_profile SET time_format = 'HH:mm:ss', updated_on = now()
where time_format = '24HR';

UPDATE user_profile SET time_format = 'hh:mm:ss a', updated_on = now()
where time_format = '12HR';
			
INSERT INTO user_profile_options ( option_id, key, value, app_default, created_on, created_by, updated_on, updated_by, version) 
			VALUES (15, 'timeFormat', 'HH:mm:ss.s', false, now(), 'CJF', now(), 'CJF', 0);
			
INSERT INTO user_profile_options ( option_id, key, value, app_default, created_on, created_by, updated_on, updated_by, version) 
			VALUES (16, 'timeFormat', 'hh:mm:ss.s a', false, now(), 'CJF', now(), 'CJF', 0);
			
INSERT INTO user_profile_options ( option_id, key, value, app_default, created_on, created_by, updated_on, updated_by, version) 
			VALUES (17, 'timeFormat', 'HH:mm', false, now(), 'CJF', now(), 'CJF', 0);
			
INSERT INTO user_profile_options ( option_id, key, value, app_default, created_on, created_by, updated_on, updated_by, version) 
			VALUES (18, 'timeFormat', 'hh:mm a', false, now(), 'CJF', now(), 'CJF', 0);

--changeset arun.kumar:001-cjf-template-db-032 failOnError:false splitStatements:false logicalFilePath:001-template.sql
alter table user_profile alter column time_zone TYPE varchar(30);

UPDATE user_profile SET time_zone = 'America/Cancun'
where time_zone = 'EST';

UPDATE user_profile SET time_zone = 'America/Managua'
where time_zone = 'CST';

UPDATE user_profile_options SET value = 'America/Cancun' 
where key = 'timeZone' AND value = 'EST';

UPDATE user_profile_options SET value = 'America/Managua' 
where key = 'timeZone' AND value = 'CST';

--changeset varsha.chowdary:001-cjf-template-db-033 failOnError:false splitStatements:false logicalFilePath:001-template.sql
-- Parameters: aaf_domain_code, aaf_domain_action_code, description, display_order, display_label
SELECT add_aaf_domain_action_lookup('cjf-admin', 'manage', 'Manage', 11, 'Manage');
-- Parameters: domain, action, instance, description
SELECT add_aaf_permission ('cjf-admin','manage','log-config','Manage Logs');

--changeset varsha.anumolu:001-cjf-template-db-034 failOnError:false splitStatements:false logicalFilePath:001-template.sql
-- Parameters: aaf_domain_code, aaf_domain_action_code, description, display_order, display_label
SELECT add_aaf_domain_action_lookup('cjf-admin', 'view', 'View', 10, 'View');
-- Parameters: domain, action, instance, description
SELECT add_aaf_permission ('cjf-admin','view','log','View Logs');

--changeset pruthvi.janapareddy:001-cjf-template-db-035 failOnError:false splitStatements:false logicalFilePath:001-template.sql
-- Below is to grop the columns warehouse code, client code and one id was they are removed from the user profile
ALTER TABLE user_profile DROP COLUMN warehouse_code, DROP COLUMN client_code, DROP COLUMN one_id;

DELETE FROM aaf_role_permission where permission_id IN (select permission_id from aaf_permission where domain = 'user_profile_one_id');
DELETE FROM aaf_permission where domain = 'user_profile_one_id';

-- Since the one id is removed we need to remove the domain and the domina action
DELETE FROM aaf_domain_action_lookup where aaf_domain_code = 'user_profile_one_id';
DELETE FROM aaf_domain_lookup where aaf_domain_code = 'user_profile_one_id';

-- Below is to add a new permission and domain action to edit the User Profile
-- Parameters: aaf_domain_code, aaf_domain_action_code, description, display_order, display_label
SELECT add_aaf_domain_action_lookup('aaf_user', 'user_profile_edit', 'To Edit User Profile', 8, 'Edit User Profile');
-- Parameters: domain, action, instance, description
SELECT add_aaf_permission ('aaf_user','user_profile_edit','*','Edit User Profile');

--changeset adolfo.cano:001-cjf-template-db-036 failOnError:true splitStatements:false logicalFilePath:001-template.sql
INSERT INTO cevatrack_responsive.todo
(id, title, description, duedate, priority, "owner", created_on, created_by, updated_on, updated_by, "version")
VALUES(1, 'test', 'desc test', '2020-01-01 00:00:00.000', 1, 'adolfo', '2020-01-01 00:00:00.000', 'adolfo', '2020-01-01 00:00:00.000', 'adolfo', 1);

--changeset adolfo.cano:001-cjf-template-db-037 failOnError:true splitStatements:false logicalFilePath:001-template.sql
-- Parameters: aaf_domain_code, description, display_order, display_label
SELECT add_aaf_domain_lookup('tn_microlise', 'Microlise WS for Technet', 7, 'Microlise WS for Technet');
SELECT add_aaf_domain_lookup('tn_site', 'TechNet web site', 7, 'TechNet web site');
SELECT add_aaf_domain_lookup('tn_client', 'TechNet web client', 7, 'TechNet web client');
-- Parameters: aaf_domain_code, aaf_domain_action_code, description, display_order, display_label
select add_aaf_domain_action_lookup('tn_microlise', 'tibco', 'Connections for Technet web in Microlise', 1, 'Connections for Technet web network in Microlise');
select add_aaf_domain_action_lookup('tn_site', 'view', 'view technet web site', 1, 'view technet web site');
select add_aaf_domain_action_lookup('tn_client', 'view', 'View technet web client', 1, 'View technet web client');
-- Parameters: domain, action, instance, description
SELECT add_aaf_permission ('tn_microlise', 'tibco', 'uat', 'Connects to technet UAT through tibco');
SELECT add_aaf_permission ('tn_microlise', 'tibco', 'prod', 'Connects to technet PROD through tibco');
SELECT add_aaf_permission ('tn_site', 'view', '*', 'View all the technet web sites');
SELECT add_aaf_permission ('tn_client', 'view', '*', 'View all the technet web clients of a given site');
-- Parameters: role_name, description
SELECT add_aaf_role ('tn_all', 'Technet network web all sites and clients');
SELECT add_aaf_role ('tn_microlise_tibco_uat', 'Technet web connection to UAT throgh Tibco');
SELECT add_aaf_role ('tn_microlise_tibco_prod', 'Technet web connection to PROD throgh Tibco');
-- Parameters: role_name, permission_domain, permission_action, permission_action
SELECT add_aaf_role_permission ('tn_all', 'tn_client', 'view', '*');
SELECT add_aaf_role_permission ('tn_microlise_tibco_uat', 'tn_microlise', 'tibco', 'uat');
SELECT add_aaf_role_permission ('tn_microlise_tibco_prod', 'tn_microlise', 'tibco', 'prod');

--changeset adolfo.cano:001-cjf-template-db-038 failOnError:true splitStatements:false logicalFilePath:001-template.sql
CREATE TABLE technet_client
(
     id            SERIAL,
     name         CHARACTER VARYING (50),
     prefixes     CHARACTER VARYING (255),
     image        CHARACTER VARYING (255),
     aaf_instance CHARACTER VARYING (20),
     created_on timestamp(6) with time zone NOT NULL,
	   created_by character varying(64) NOT NULL,
	   updated_on timestamp(6) with time zone,
	   updated_by character varying(64),
	   version smallint NOT NULL
  );
   
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Canon','CANON','Canon001.png','CANON',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Amazon','AMAZON','Amazon001.png','AMAZON',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('NHS','NHSUK','NHS001.png','NHSUK',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('BMI Healthcare','BMI','BMIHealthcare001.png','BMI',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('XEROX','XEROX','Xerox001.png','XEROX',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('COCA COLA','COKE','CocaCola001.png','COKE',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('RICOHP','RICOHP','Ricoh001.png','RICOHP',now(),'SYSTEM',now(),'SYSTEM',1);

--changeset adolfo.cano:001-cjf-template-db-039 failOnError:true splitStatements:false logicalFilePath:001-template.sql
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('RICOH','RICOH','Ricoh001.png','RICOH',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('RICOHD','RICOHD','Ricoh001.png','RICOHD',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('RICOHE','RICOHE','Ricoh001.png','RICOHE',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('RICOHR','RICOHR','Ricoh001.png','RICOHR',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('RICOHT','RICOHT','Ricoh001.png','RICOHT',now(),'SYSTEM',now(),'SYSTEM',1);
  
 
--changeset adolfo.cano:001-cjf-template-db-040 failOnError:false splitStatements:false logicalFilePath:001-template.sql
  SELECT add_aaf_role_permission ('tn_all', 'tn_site', 'view', '*');
--changeset adolfo.cano:001-cjf-template-db-041 failOnError:true splitStatements:false logicalFilePath:001-template.sql
UPDATE technet_client SET name ='NHSUK' where prefixes = 'NHSUK';
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('NHS','NHS','NHS001.png','NHS',now(),'SYSTEM',now(),'SYSTEM',1);
--changeset adolfo.cano:001-cjf-template-db-042 failOnError:true splitStatements:false logicalFilePath:001-template.sql
INSERT INTO smartnet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Lotus','LOTPRO','Lotus.png','lotus',now(),'SYSTEM',now(),'SYSTEM',1);
--changeset adolfo.cano:001-cjf-template-db-043 failOnError:true splitStatements:false logicalFilePath:001-template.sql
  CREATE TABLE order_lookup
(    
    client text COLLATE pg_catalog."default",
    client_ref text COLLATE pg_catalog."default",
    load_id text COLLATE pg_catalog."default",
    load_date date,
    order_number text COLLATE pg_catalog."default",
    notify_method text COLLATE pg_catalog."default",
    shipped_date timestamp with time zone,
    ml_order_ref text COLLATE pg_catalog."default",
    last_updated timestamp with time zone,    
    CONSTRAINT orderlookup_unique UNIQUE (client, client_ref)
);
--changeset adolfo.cano:001-cjf-template-db-044 failOnError:true splitStatements:false logicalFilePath:001-template.sql
INSERT INTO order_lookup (client,client_ref,load_id,load_date,order_number,notify_method,shipped_date,ml_order_ref,last_updated) VALUES 
('NIKE','302332068','8846028','2018-04-09','NIK1616179','NONE','2018-04-09 03:53:47.000','NIKE/NIK1616179','2020-12-15 17:17:47.000')
,('NIKE','302332049','8846028','2018-04-09','NIK1616170','NONE','2018-04-09 03:52:46.000','NIKE/NIK2860811','2020-12-14 17:11:47.000');
--changeset adolfo.cano:001-cjf-template-db-045 failOnError:true splitStatements:false logicalFilePath:001-template.sql
-- Parameters: aaf_domain_code, aaf_domain_action_code, description, display_order, display_label
select add_aaf_domain_action_lookup('am_microlise', 'tibco', 'Connections for Automotive web in Microlise', 1, 'Connections for Automotive web network in Microlise');
select add_aaf_domain_action_lookup('am_site', 'view', 'view automotive web site', 1, 'view automotive web site');
select add_aaf_domain_action_lookup('am_client', 'view', 'View automotive web client', 1, 'View automotive web client');
-- Parameters: domain, action, instance, description
SELECT add_aaf_permission ('am_microlise', 'tibco', 'uat', 'Connects to automotive UAT through tibco');
SELECT add_aaf_permission ('am_microlise', 'tibco', 'prod', 'Connects to automotive PROD through tibco');
SELECT add_aaf_permission ('am_site', 'view', '*', 'View all the automotive web sites');
SELECT add_aaf_permission ('am_client', 'view', '*', 'View all the automotive web clients of a given site');
-- Parameters: role_name, description
SELECT add_aaf_role ('am_all', 'Automotive network web all sites and clients');
SELECT add_aaf_role ('am_microlise_tibco_uat', 'Automotive web connection to UAT throgh Tibco');
SELECT add_aaf_role ('am_microlise_tibco_prod', 'Automotive web connection to PROD throgh Tibco');
-- Parameters: role_name, permission_domain, permission_action, permission_action
SELECT add_aaf_role_permission ('am_all', 'am_client', 'view', '*');
SELECT add_aaf_role_permission ('am_microlise_tibco_uat', 'am_microlise', 'tibco', 'uat');
SELECT add_aaf_role_permission ('am_microlise_tibco_prod', 'am_microlise', 'tibco', 'prod');
--changeset adolfo.cano:001-cjf-template-db-046 failOnError:true splitStatements:false logicalFilePath:001-template.sql
CREATE TABLE automotive_client
(
     id            SERIAL,
     name         CHARACTER VARYING (50),
     prefixes     CHARACTER VARYING (255),
     image        CHARACTER VARYING (255),
     aaf_instance CHARACTER VARYING (20),
     created_on timestamp(6) with time zone NOT NULL,
	   created_by character varying(64) NOT NULL,
	   updated_on timestamp(6) with time zone,
	   updated_by character varying(64),
	   version smallint NOT NULL
  );
   
INSERT INTO automotive_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('BMW','BMW','BMW.png','BMW',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO automotive_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Land Rover','LROVER','LandRover.png','LROVER',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO automotive_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Jaguar','JAGUAR','Jaguar.png','JAGUAR',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO automotive_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('UKPN','EDF','UKPN.png','EDF',now(),'SYSTEM',now(),'SYSTEM',1);
--changeset adolfo.cano:001-cjf-template-db-047 failOnError:true splitStatements:false logicalFilePath:001-template.sql
INSERT INTO aaf_domain_lookup
(aaf_domain_code, description, display_order, display_label, created_on, created_by, updated_on, updated_by, "version")
VALUES('am_microlise', 'Microlise Webservice for Automotive', 7, 'Automotive Microlise', '2021-01-21 17:47:36.989', 'SYSTEM', NULL, NULL, 1);
INSERT INTO aaf_domain_action_lookup
(aaf_domain_code, aaf_domain_action_code, description, display_order, display_label, created_on, created_by, updated_on, updated_by, "version")
VALUES('am_microlise', 'tibco', 'Connect via Tibco', 1, 'Tibco Connection', '2021-01-20 17:47:36.989', 'SYSTEM', NULL, NULL, 1);
INSERT INTO aaf_domain_lookup
(aaf_domain_code, description, display_order, display_label, created_on, created_by, updated_on, updated_by, "version")
VALUES('am_client', 'Automotive Client', 5, 'Automotive Client', '2020-01-20 17:47:36.953', 'SYSTEM', NULL, NULL, 1);
INSERT INTO aaf_domain_lookup
(aaf_domain_code, description, display_order, display_label, created_on, created_by, updated_on, updated_by, "version")
VALUES('am_site', 'Automotive Site', 5, 'Automotive Site', '2020-01-20 17:47:36.953', 'SYSTEM', NULL, NULL, 1);
INSERT INTO aaf_domain_action_lookup
(aaf_domain_code, aaf_domain_action_code, description, display_order, display_label, created_on, created_by, updated_on, updated_by, "version")
VALUES('am_client', 'view', 'View Automotive Client', 1, 'View Automotive Client', '2021-01-20 17:47:36.953', 'SYSTEM', NULL, NULL, 1);
INSERT INTO aaf_domain_action_lookup
(aaf_domain_code, aaf_domain_action_code, description, display_order, display_label, created_on, created_by, updated_on, updated_by, "version")
VALUES('am_site', 'view', 'View Automotive Site', 1, 'View Automotive Site', '2021-01-20 17:47:36.953', 'SYSTEM', NULL, NULL, 1);
SELECT add_aaf_role_permission ('am_all', 'am_site', 'view', '*');
--changeset adolfo.cano:001-cjf-template-db-048 failOnError:false splitStatements:false logicalFilePath:001-template.sql
ALTER TABLE order_lookup ADD COLUMN IF NOT EXISTS sent_to_ml timestamp with time zone;
ALTER TABLE order_lookup ADD COLUMN IF NOT EXISTS eta timestamp with time zone;
--changeset adolfo.cano:001-cjf-template-db-055 failOnError:true splitStatements:false logicalFilePath:001-template.sql
CREATE TABLE IF NOT EXISTS mailer_order (
	load_id text NULL,
	load_date date NULL,
	ml_order_ref text NOT NULL,
	client text NULL,
	client_ref text NULL,
	order_number text NULL,
	notify_method text NULL,
	delivery_date timestamptz NULL,
	shipped_date timestamptz NULL,
	sent_to_ml timestamptz NULL,
	access_token text NULL,
	earliest_booking timestamptz NULL,
	latest_booking timestamptz NULL,
	eta timestamptz NULL,
	email_sent timestamptz NULL,
	loc_name text NULL,
	loc_addr1 text NULL,
	loc_addr2 text NULL,
	loc_addr3 text NULL,
	loc_addr4 text NULL,
	loc_postcode text NULL,
	loc_latitude text NULL,
	loc_longitude text NULL,
	phone text NULL,
	email text NULL,
	products text NULL,
	driver text NULL,
	last_updated timestamptz NULL,
	ml_site_lat text NULL,
	ml_site_long text NULL,
	ml_route_id text NULL,
	ml_journey_alias text NULL,
	ml_journey_id text NULL,
	ml_driver_fullname text NULL,
	ml_pta timestamptz NULL,
	ml_site_id text NULL,
	ml_journey_drop_id text NULL,
	email_attempts int4 NULL DEFAULT 0,
	email_failure_code text NULL,
	ml_ata timestamp with time zone,
	CONSTRAINT mailer_order_p_key PRIMARY KEY (ml_order_ref),
	CONSTRAINT mailer_ord_unique UNIQUE (ml_order_ref),
	CONSTRAINT mailer_tkn_unique UNIQUE (access_token)
);
--changeset adolfo.cano:001-cjf-template-db-050 failOnError:true splitStatements:false logicalFilePath:001-template.sql
CREATE TABLE healthcare_client
(
     id            SERIAL,
     name         CHARACTER VARYING (50),
     prefixes     CHARACTER VARYING (255),
     image        CHARACTER VARYING (255),
     aaf_instance CHARACTER VARYING (20),
     created_on timestamp(6) with time zone NOT NULL,
	   created_by character varying(64) NOT NULL,
	   updated_on timestamp(6) with time zone,
	   updated_by character varying(64),
	   version smallint NOT NULL
  );
   
INSERT INTO healthcare_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Nutricia','NUTRIC','Nutricia.png','NUTRIC',now(),'SYSTEM',now(),'SYSTEM',1);
--changeset adolfo.cano:001-cjf-template-db-051 failOnError:true splitStatements:false logicalFilePath:001-template.sql
-- Parameters: aaf_domain_code, aaf_domain_action_code, description, display_order, display_label
select add_aaf_domain_action_lookup('hc_microlise', 'tibco', 'Connections for Healthcare web in Microlise', 1, 'Connections for Healthcare web network in Microlise');
select add_aaf_domain_action_lookup('hc_site', 'view', 'view Healthcare web site', 1, 'view Healthcare web site');
select add_aaf_domain_action_lookup('hc_client', 'view', 'View Healthcare web client', 1, 'View Healthcare web client');
-- Parameters: domain, action, instance, description
SELECT add_aaf_permission ('hc_microlise', 'tibco', 'uat', 'Connects to healthcare UAT through tibco');
SELECT add_aaf_permission ('hc_microlise', 'tibco', 'prod', 'Connects to healthcare PROD through tibco');
SELECT add_aaf_permission ('hc_site', 'view', '*', 'View all the healthcare web sites');
SELECT add_aaf_permission ('hc_client', 'view', '*', 'View all the healthcare web clients of a given site');
-- Parameters: role_name, description
SELECT add_aaf_role ('hc_all', 'Healthcare network web all sites and clients');
SELECT add_aaf_role ('hc_microlise_tibco_uat', 'Healthcare web connection to UAT throgh Tibco');
SELECT add_aaf_role ('hc_microlise_tibco_prod', 'Healthcare web connection to PROD throgh Tibco');
-- Parameters: role_name, permission_domain, permission_action, permission_action
SELECT add_aaf_role_permission ('hc_all', 'hc_site', 'view', '*');
SELECT add_aaf_role_permission ('hc_all', 'hc_client', 'view', '*');
SELECT add_aaf_role_permission ('hc_microlise_tibco_uat', 'hc_microlise', 'tibco', 'uat');
SELECT add_aaf_role_permission ('hc_microlise_tibco_prod', 'hc_microlise', 'tibco', 'prod');
--changeset adolfo.cano:001-cjf-template-db-052 failOnError:true splitStatements:false logicalFilePath:001-template.sql
INSERT INTO aaf_domain_lookup
(aaf_domain_code, description, display_order, display_label, created_on, created_by, updated_on, updated_by, "version")
VALUES('hc_microlise', 'Microlise Webservice for Healthcare', 7, 'Healthcare Microlise', '2021-08-03 17:47:36.989', 'SYSTEM', NULL, NULL, 1);
INSERT INTO aaf_domain_lookup
(aaf_domain_code, description, display_order, display_label, created_on, created_by, updated_on, updated_by, "version")
VALUES('hc_client', 'Healthcare Client', 5, 'Healthcare Client', '2021-08-03 17:47:36.953', 'SYSTEM', NULL, NULL, 1);
INSERT INTO aaf_domain_lookup
(aaf_domain_code, description, display_order, display_label, created_on, created_by, updated_on, updated_by, "version")
VALUES('hc_site', 'Healthcare Site', 5, 'Healthcare Site', '2021-08-03 17:47:36.953', 'SYSTEM', NULL, NULL, 1);
INSERT INTO aaf_domain_action_lookup
(aaf_domain_code, aaf_domain_action_code, description, display_order, display_label, created_on, created_by, updated_on, updated_by, "version")
VALUES('hc_microlise', 'tibco', 'Connect via Tibco', 1, 'Tibco Connection', '2021-08-03 17:47:36.989', 'SYSTEM', NULL, NULL, 1);
INSERT INTO aaf_domain_action_lookup
(aaf_domain_code, aaf_domain_action_code, description, display_order, display_label, created_on, created_by, updated_on, updated_by, "version")
VALUES('hc_client', 'view', 'View Healthcare Client', 1, 'View Healthcare Client', '2021-08-03 17:47:36.953', 'SYSTEM', NULL, NULL, 1);
INSERT INTO aaf_domain_action_lookup
(aaf_domain_code, aaf_domain_action_code, description, display_order, display_label, created_on, created_by, updated_on, updated_by, "version")
VALUES('hc_site', 'view', 'View Healthcare Site', 1, 'View Healthcare Site', '2021-08-03 17:47:36.953', 'SYSTEM', NULL, NULL, 1);
--changeset adolfo.cano:001-cjf-template-db-053 failOnError:true splitStatements:false logicalFilePath:001-template.sql
CREATE TABLE IF NOT EXISTS mailer_client (
	client text NOT NULL,
	feedback_link text NULL,
	housekeep_days int4 NULL,
	window_eta_minus_mins int4 NULL,
	window_eta_plus_mins int4 NULL,
	from_email text NULL,
	replyto_email text NULL,
	email_link text NULL,
	email_pre_link text NULL,
	email_post_link text NULL,
	email_signature text NULL,
	web_service_group text NULL,
	latest_notify_mins_before_window int4 NULL,
	sms_body text NULL,
	email_subject text NULL,
	token_length_email int4 NULL,
	token_length_sms int4 NULL,
	redirect_to_test_email bool NULL,
	test_email_recipient text NULL,
	redirect_to_test_sms bool NULL,
	test_sms_recipient text NULL,
	icetrack_prefix text NULL,
	sms_product bool NULL,
	email_product bool NULL,
	CONSTRAINT mailer_client_pkey PRIMARY KEY (client)
);
--changeset adolfo.cano:001-cjf-template-db-054 failOnError:true splitStatements:false logicalFilePath:001-template.sql
CREATE TABLE if not exists clients_api
(
	carrier_name text,
	tracking_number numeric,
	status text,
	status_date timestamp with time zone,
	revise_delivery_date timestamp with time zone,
	original_required_date timestamp with time zone,
	reason_code text,
	shipment_number text,
	customer_name text,
	customer_address text,
	actual_delivery_date timestamp with time zone,
	failure_reason_code text,
	late_reason_code text,
	redelivery boolean,
	failure_reason text,
	client_id text,
	CONSTRAINT shipping_number_pkey PRIMARY KEY (shipment_number, client_id)
)
--changeset adolfo.cano:001-cjf-template-db-056 failOnError:false splitStatements:false logicalFilePath:001-template.sql
ALTER TABLE clients_api
	ADD COLUMN delivered_date timestamp with time zone NULL,
	ADD COLUMN claused_date timestamp with time zone NULL;
--changeset adolfo.cano:001-cjf-template-db-057 failOnError:true splitStatements:false logicalFilePath:001-template.sql
CREATE TABLE if not exists seb_order (
	load_id text NULL,
	scheduled_timeout timestamptz NULL,
	seb_date date NULL,
	route_id text NULL,
	drop_number int4 NULL,
	client text NULL,
	ml_order_ref text NOT NULL,
	loc_name text NULL,
	loc_addr1 text NULL,
	loc_addr2 text NULL,
	loc_addr3 text NULL,
	loc_addr4 text NULL,
	loc_postcode text NULL,
	driver text NULL,
	vehicle text NULL,
	last_updated timestamptz NULL,
	last_ws_call timestamptz NULL,
	pta timestamptz NULL,
	ptd timestamptz NULL,
	eta timestamptz NULL,
	etd timestamptz NULL,
	ata timestamptz NULL,
	atd timestamptz NULL,
	site_id text NULL,
	site_name text NULL,
	site_postcode text NULL,
	clause_code text NULL,
	clause_description text NULL,
	driver_name text NULL,
	planned_start_time timestamptz NULL,
	actual_start_time timestamptz NULL,
	pod_start_time timestamptz NULL,
	journey_actual_start_time timestamptz NULL,
	journey_id int4 NULL,
	ml_route_id int4 NULL,
	ml_journey_drop_id int4 NULL,
	site_lat text NULL,
	site_long text NULL,
	ml_vehicle text NULL,
	quantity_planned int4 NULL,
	quantity_actual int4 NULL,
	quantity_claused int4 NULL,
	ml_status text NULL,
	time_window_start timestamptz NULL,
	time_window_end timestamptz NULL,
	CONSTRAINT seb_order_pkey PRIMARY KEY (ml_order_ref)
);
--changeset adolfo.cano:001-cjf-template-db-058 failOnError:true splitStatements:false logicalFilePath:001-template.sql
SELECT add_aaf_domain_lookup('cevatrack', 'Cevatrack access to the views', 7, 'Cevatrack access to the views');
SELECT add_aaf_domain_action_lookup('cevatrack', 'view', 'view access to cevatrack screen', 1, 'view access to cevatrack screen');
SELECT add_aaf_permission ('cevatrack', 'view', 'SEB', 'Grant access to the SEB view');
SELECT add_aaf_role ('access_seb', 'access to the SEB screen in cevatrack');
SELECT add_aaf_role_permission ('access_seb', 'cevatrack', 'view', 'SEB');
--changeset adolfo.cano:001-cjf-template-db-060 failOnError:true splitStatements:false logicalFilePath:001-template.sql
INSERT INTO automotive_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Ford','FORD','Ford.png','FORD',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO healthcare_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Johnson & Johnson','JHNJHN','JJ.png','JHNJHN',now(),'SYSTEM',now(),'SYSTEM',1);
--changeset adolfo.cano:001-cjf-template-db-061 failOnError:true splitStatements:false logicalFilePath:001-template.sql
CREATE TABLE if not exists tracking_reference_suspense (
	order_number text NOT NULL,
	tracking_reference text NULL,
	last_updated timestamptz NULL,
	applied timestamptz NULL,
	CONSTRAINT tracking_reference_suspense_pkey PRIMARY KEY (order_number)
);
--changeset adolfo.cano:001-cjf-template-db-062 failOnError:false splitStatements:false logicalFilePath:001-template.sql
ALTER TABLE order_lookup ADD COLUMN tracking_reference text;
--changeset adolfo.cano:001-cjf-template-db-063 failOnError:true splitStatements:false logicalFilePath:001-template.sql
 delete from smartnet_client where name = 'Gap';
 delete from smartnet_client where name = 'Whitworths';
 delete from smartnet_client where name = 'Continental';
 delete from smartnet_client where name = 'British Gypsum Sherburn';
 INSERT INTO smartnet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('The Body Shop','BDYDTS','BS.png','bodyshop',now(),'SYSTEM',now(),'SYSTEM',1);
--changeset adolfo.cano:001-cjf-template-db-064 failOnError:true splitStatements:false logicalFilePath:001-template.sql
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('REGENCY','REGENC','blank.png','',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Hill Rom','HILLRO','blank.png','',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Pitney','PITNEY','blank.png','',now(),'SYSTEM',now(),'SYSTEM',1);
--changeset adolfo.cano:001-cjf-template-db-065 failOnError:true splitStatements:false logicalFilePath:001-template.sql
  UPDATE technet_client SET image = 'HillRom.png' WHERE prefixes='HILLRO';
  UPDATE technet_client SET image = 'Pitney_1.png' WHERE prefixes='PITNEY';
  UPDATE technet_client SET image = 'Regency.png' WHERE prefixes='REGENC';
--changeset adolfo.cano:001-cjf-template-db-066 failOnError:true splitStatements:false logicalFilePath:001-template.sql
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('FAGOR','FAGOR','blank.png','',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('XDOA','XDOA','blank.png','',now(),'SYSTEM',now(),'SYSTEM',1);
--changeset adolfo.cano:001-cjf-template-db-068 failOnError:true splitStatements:false logicalFilePath:001-template.sql
  create table if not exists p44_consignments (
      label1 text NULL,
      label2  text NULL,
      label3 text NULL,
      label4 text NULL,
      consignmentReference text NOT NULL,
      consignmentCustomerOrderRef text NULL,
      consignmentStatus smallint NULL,
  --   ArrayList<ConsignmentDetail> consignmentDetails text NULL,
      ata text NULL,
      atd text NULL,
      signatory text NULL,
      signature text NULL,
      journeyDropId integer NULL,
      pta text NULL,
      ptd text NULL,
      eta text NULL,
      etd text NULL,
      timeWindowStart text NULL,
      timeWindowEnd text NULL,
      siteId text NULL,
      siteName text NULL,
      address1 text NULL,
      address2 text NULL,
      address3 text NULL,
      county text NULL,
      postCode text NULL,
      country text NULL,
      contactName text NULL,
      contactNumber text NULL,
      siteLat float8 NULL,
      siteLong float8 NULL,
      routeId text NULL,
      journeyAlias text NULL,
      plannedStartTime text NULL,
      actualStartTime text NULL,
      podStartTime text NULL,
  --   ArrayList<Vehicle> vehicles text NULL,
  --   ArrayList<Driver> drivers text NULL,
     siteGeoRadius integer NULL,
     durationOnSite float8 NULL,
     journeyActualStartTime text NULL,
     isAdHoc boolean NULL,
     isTranshipment boolean NULL,
     clauseCode text NULL,
     parentCode text NULL,
     consignmentHeaderId int4 NULL,
     journeyId int4 NULL,
     consignmentHeaderInfo1 text NULL,
     consignmentHeaderInfo2 text NULL,
     consignmentHeaderInfo3 text NULL,
     consignmentHeaderInfo4 text NULL,
     consignmentHeaderInfo5 text NULL,
     consignmentHeaderInfo6 text NULL,
     consignmentHeaderInfo7 text NULL,
     consignmentHeaderInfo8 text NULL,
     consignmentHeaderInfo9 text NULL,
     consignmentHeaderInfo10 text NULL,
     consignmentHeaderInfo11 text NULL,
     consignmentHeaderInfo12 text null,
     CONSTRAINT consignmentReference_pkey PRIMARY KEY (consignmentReference)
	);	
  create table if not exists p44_consignment_detail(	 
     detailRef text NOT NULL,
     customerDetailRef text NULL,
     parentRef text NULL,
     orderRef text NULL,
     description text NULL,
     plannedQuantity INTEGER NULL,
     actualQuantity INTEGER NULL,
     status SMALLINT,
  --  protected ArrayList<ClausedConsignmentDetail> clauseDetails text NULL,
     info1 text NULL,
     info2 text NULL,
     info3 text NULL,
     info4 text NULL,
     consignmentHeaderId INTEGER null,
     consignmentReference text NOT NULL,
     UNIQUE(detailRef),
     CONSTRAINT consignmentReference_fk FOREIGN KEY(consignmentReference)
	          REFERENCES p44_consignments(consignmentReference) ON DELETE cascade	 
	);
	 create table if not exists p44_claused_consignment_detail(
	 parentCode text NULL,
	 parentClauseDescription text NULL,
	 clauseCode text NOT NULL,
	 clauseDescription text NULL,
	 clausedQuantity int4 NULL,
	 consignmentDetailRef text NOT NULL,
	 UNIQUE(clauseCode),
	 CONSTRAINT consignmentDetailRef_fk FOREIGN KEY(consignmentDetailRef)
	          REFERENCES p44_consignment_detail(detailRef) ON DELETE CASCADE 
	);
	create table if not exists p44_consignment_vehicle(
		displayName text,
	    lastLocation text NULL,
	    lastContactLat float4 NULL,
	    lastContactLong float4 NULL,
	    journeyId int4 NULL,
		consignmentReference text NOT NULL,
		UNIQUE(displayName),
     CONSTRAINT consignmentReference_fk FOREIGN KEY(consignmentReference)
	          REFERENCES p44_consignments(consignmentReference) ON DELETE CASCADE
	);	
	create table if not exists p44_consignment_driver(
	 displayName text,
	 fullName text NULL,
	 contactNumber text NULL,
	 journeyId int4 null,
	 consignmentReference text NOT NULL,
	 UNIQUE(displayName),
     CONSTRAINT consignmentReference_fk FOREIGN KEY(consignmentReference)
	          REFERENCES p44_consignments(consignmentReference) ON DELETE CASCADE
	);	
	
  --changeset adolfo.cano:001-cjf-template-db-069 failOnError:true splitStatements:false logicalFilePath:001-template.sql
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('CB Heating','CBHEAT','CBheating.png','',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('OVE','OVE','OVE.png','',now(),'SYSTEM',now(),'SYSTEM',1);
  --changeset adolfo.cano:001-cjf-template-db-070 failOnError:true splitStatements:false logicalFilePath:001-template.sql 
  create table if not exists ml_image (
	load_id text NULL,
	order_number text NOT NULL,
	journey_alias text NULL,
	call_sequence text NULL,
	actual_call_sequence text NULL,
	activity_name text NOT NULL,
	activity_image_no text NULL,
	activity_start timestamptz NULL,
	activity_end timestamptz NULL,
	created_timestamp timestamptz NULL,
	updated_timestamp timestamptz NULL,
	CONSTRAINT ml_image_pkey PRIMARY KEY (order_number, activity_name, activity_image_no)
	);
			
	--changeset adolfo.cano:001-cjf-template-db-071 failOnError:true splitStatements:false logicalFilePath:001-template.sql 
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('SNAP ON','SNAPON','SnapOn.png','',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('TECHNE','TECHNE','CEVA_TECHNE.png','',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO smartnet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('FLYING','FLYING','Flying_Tiger.png','',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('SOTUK','SOTUK','SnapOn.png','',now(),'SYSTEM',now(),'SYSTEM',1);
INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('QUAD','QUAD','QUAD.png','',now(),'SYSTEM',now(),'SYSTEM',1);
  
--changeset adolfo.cano:001-cjf-template-db-072 failOnError:true splitStatements:false logicalFilePath:001-template.sql
  delete from smartnet_client where prefixes ='LOTPRO' or prefixes = 'BENTL' or prefixes = 'CONTI' or prefixes = 'BGYPSH';
  
--changeset adolfo.cano:001-cjf-template-db-073 failOnError:true splitStatements:false logicalFilePath:001-template.sql  
  UPDATE smartnet_client SET aaf_instance = 'flying' WHERE name = 'flying';
  UPDATE technet_client SET aaf_instance = 'SNAPON' WHERE prefixes = 'SNAPON';
  UPDATE technet_client SET aaf_instance = 'TECHNE' WHERE prefixes = 'TECHNE';
  UPDATE technet_client SET aaf_instance = 'QUAD' WHERE prefixes = 'QUAD';
  DELETE from technet_client where prefixes='SOTUK';
  UPDATE technet_client SET aaf_instance = 'PITNEY' WHERE prefixes = 'PITNEY';
  UPDATE technet_client SET aaf_instance = 'REGENC' WHERE prefixes = 'REGENC';
  UPDATE technet_client SET aaf_instance = 'FAGOR' WHERE prefixes = 'FAGOR';
  UPDATE technet_client SET aaf_instance = 'XDOA' WHERE prefixes = 'XDOA';
  UPDATE technet_client SET aaf_instance = 'CBHEAT' WHERE prefixes = 'CBHEAT';
  UPDATE technet_client SET aaf_instance = 'OVE' WHERE prefixes = 'OVE';
  UPDATE technet_client SET aaf_instance = 'HILLRO' WHERE prefixes = 'HILLRO';
  
--changeset adolfo.cano:001-cjf-template-db-074 failOnError:true splitStatements:false logicalFilePath:001-template.sql  
INSERT INTO automotive_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('JLR','JAGLR','JAGLR.png','',now(),'SYSTEM',now(),'SYSTEM',1);
  INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Vibrant Foods','VIBRAN','VIBRAN.png','',now(),'SYSTEM',now(),'SYSTEM',1);
  
--changeset adolfo.cano:001-cjf-template-db-075 failOnError:true splitStatements:false logicalFilePath:001-template.sql
DELETE from technet_client where prefixes='VIBRAN';
INSERT INTO smartnet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Vibrant Foods','VIBRAN','VIBRAN.png','',now(),'SYSTEM',now(),'SYSTEM',1);
  
--changeset adolfo.cano:001-cjf-template-db-076 failOnError:true splitStatements:false logicalFilePath:001-template.sql
 UPDATE automotive_client SET aaf_instance = 'JAGLR' WHERE prefixes = 'JAGLR';
 
 --changeset adolfo.cano:001-cjf-template-db-077 failOnError:true splitStatements:false logicalFilePath:001-template.sql
 INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('Merrithew','MERRI','MERRITHEW.png','MERRI',now(),'SYSTEM',now(),'SYSTEM',1); 

 --changeset adolfo.cano:001-cjf-template-db-078 failOnError:true splitStatements:false logicalFilePath:001-template.sql
 CREATE TABLE IF NOT EXISTS jlr_consignment_image (
	load_id text NOT NULL,
	journey_drop_id text NOT NULL,
	image_no text NOT NULL,
	is_proof bool NULL,
	is_clause bool NULL,
	journey_alias text NULL,
	call_sequence text NULL,
	actual_call_sequence text NULL,
	site_name text NULL,
	site_info_1 text NULL,
	site_info_2 text NULL,
	clause text NULL,
	description text NULL,
	ata timestamptz NULL,
	atd timestamptz NULL,
	created_timestamp timestamptz NULL,
	updated_timestamp timestamptz NULL,
	CONSTRAINT jlr_consignment_image_pk PRIMARY KEY (load_id,journey_drop_id,image_no)
);
 CREATE TABLE IF NOT EXISTS jlr_line_image (
	order_number text NOT NULL,
	detail_no text NOT NULL,
	image_no text NOT NULL,
	journey_alias text NULL,
	load_id text NULL,
	journey_drop_id text NULL,
	call_sequence text NULL,
	actual_call_sequence text NULL,
	site_name text NULL,
	site_info_1 text NULL,
	site_info_2 text NULL,
	clause text NULL,
	description text NULL,
	clean text NULL,
	planned_qty int4 NULL,
	actual_qty int4 NULL,
	claused_qty int4 NULL,
	qty_precision int4 NULL,
	ata timestamptz NULL,
	atd timestamptz NULL,
	created_timestamp timestamptz NULL,
	updated_timestamp timestamptz NULL,
	CONSTRAINT jlr_line_image_pk PRIMARY KEY (order_number,detail_no,image_no)
);


--changeset adolfo.cano:001-cjf-template-db-079 failOnError:true splitStatements:false logicalFilePath:001-template.sql
 INSERT INTO technet_client (name, prefixes, image, aaf_instance, created_on, created_by, updated_on, updated_by, version) 
  VALUES ('BSH','BSH100','Bsh.png','bsh100',now(),'SYSTEM',now(),'SYSTEM',1);
  
  