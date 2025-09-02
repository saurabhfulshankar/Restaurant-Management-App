create database YORU;

use YORU;

create table if not exists  oauth_client_details (
  client_id varchar(255) not null,
  client_secret varchar(255) not null,
  web_server_redirect_uri varchar(2048) default null,
  scope varchar(255) default null,
  access_token_validity int(11) default null,
  refresh_token_validity int(11) default null,
  resource_ids varchar(1024) default null,
  authorized_grant_types varchar(1024) default null,
  authorities varchar(1024) default null,
  additional_information varchar(4096) default null,
  autoapprove varchar(255) default null,
  primary key (client_id)
) engine=innodb ;

create table if not exists  permission (
  id bigint(11) not null auto_increment,
  name varchar(512) default null,
  primary key (id),
  unique key name (name)
) engine=innodb ;

create table if not exists role (
  id bigint(11) not null auto_increment,
  name varchar(255) default null,
  primary key (id),
  unique key name (name)
) engine=innodb ;

create table if not exists  user (
  id bigint(11) not null auto_increment,
  username varchar(100) not null,
  password varchar(1024) not null,
  email varchar(1024) not null,
  enabled tinyint(4) not null,
  accountNonExpired tinyint(4) not null,
  credentialsNonExpired tinyint(4) not null,
  accountNonLocked tinyint(4) not null,
  primary key (id),
  unique key username (username)
--  unique key email (email)
) engine=innodb ;


create table  if not exists permission_role (
  permission_id bigint(11) default null,
  role_id bigint(11) default null,
  key permission_id (permission_id),
  key role_id (role_id),
  constraint permission_role_ibfk_1 foreign key (permission_id) references permission (id),
  constraint permission_role_ibfk_2 foreign key (role_id) references role (id)
) engine=innodb ;

create table if not exists role_user (
  role_id bigint(11) default null,
  user_id bigint(11) default null,
  key role_id (role_id),
  key user_id (user_id),
  constraint role_user_ibfk_1 foreign key (role_id) references role (id),
  constraint role_user_ibfk_2 foreign key (user_id) references user (id)
) engine=innodb ;


create table if not exists oauth_client_token (
  token_id VARCHAR(256),
  token LONG VARBINARY,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);

create table if not exists oauth_access_token (
  token_id VARCHAR(256),
  token LONG VARBINARY,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication LONG VARBINARY,
  refresh_token VARCHAR(256)
);

create table if not exists oauth_refresh_token (
  token_id VARCHAR(256),
  token LONG VARBINARY,
  authentication LONG VARBINARY
);

create table if not exists oauth_code (
  code VARCHAR(256), authentication LONG VARBINARY
);

create table if not exists oauth_approvals (
	userId VARCHAR(256),
	clientId VARCHAR(256),
	scope VARCHAR(256),
	status VARCHAR(10),
	expiresAt TIMESTAMP
);

--create table if not exists menu_items (
--    id bigint(11) PRIMARY KEY AUTO_INCREMENT,
--    created_date datetime not null,
--    last_modified_date datetime not null,
--    is_active boolean not null ,
--    name VARCHAR(256) not null,
--    description VARCHAR(256),
--    price DOUBLE not null,
--    image blob,
--    category VARCHAR(256),
--    tenantId VARCHAR(256) not null
--) engine=innodb;
--
--CREATE TABLE if not exists orders (
--  id bigint(11) PRIMARY KEY AUTO_INCREMENT,
--  created_date datetime not null,
--  last_modified_date datetime not null,
--  is_active boolean not null DEFAULT 1,
--  order_date DATE NOT NULL,
--  customer_email VARCHAR(255) NOT NULL,
--  subtotal DOUBLE NOT NULL,
--  gst_percentage DOUBLE NOT NULL default 5,
--  sgst DOUBLE NOT NULL default 0,
--  cgst DOUBLE NOT NULL default 0,
--  total_tax DOUBLE NOT NULL default 0,
--  total_amount DOUBLE NOT NULL default 0,
--  tenant_id VARCHAR(255) NOT NULL
--) engine=innodb;
--
--CREATE TABLE if not exists order_items (
--  id bigint(11) PRIMARY KEY AUTO_INCREMENT,
--  created_date datetime not null,
--  last_modified_date datetime not null,
--  is_active boolean not null DEFAULT 1,
--  menu_item_id bigint(11) NOT NULL,
--  quantity BIGINT NOT NULL,
--  total_amount DOUBLE NOT NULL,
--  tenant_id VARCHAR(255) NOT NULL,
--  order_id bigint(11) NOT NULL,
--  FOREIGN KEY (menu_item_id) REFERENCES menu_items (id),
--  FOREIGN KEY (order_id) REFERENCES orders (id)
--) engine=innodb;
--
--CREATE TABLE if not exists scratch_cards (
--  id bigint(11) PRIMARY KEY AUTO_INCREMENT,
--  created_date datetime not null,
--  last_modified_date datetime not null,
--  is_active boolean not null DEFAULT 1,
--  code BIGINT NOT NULL,
--  is_redeemed TINYINT(1) NOT NULL,
--  expiry_date DATE NOT NULL,
--  is_mail_send TINYINT(1) NOT NULL,
--  tenant_id VARCHAR(255) NOT NULL,
--  menu_item_id bigint(11),
--  order_id bigint(11) NOT NULL,
--  FOREIGN KEY (menu_item_id) REFERENCES menu_items (id),
--  FOREIGN KEY (order_id) REFERENCES orders (id),
--  UNIQUE (code)
--) engine=innodb;