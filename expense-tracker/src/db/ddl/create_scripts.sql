create table t_user_details (
  f_id bigint PRIMARY KEY AUTO_INCREMENT,
  f_first_name varchar(50) not NULL,
  f_last_name varchar(50),
  f_email varchar(100) not null REFERENCES t_user_wallets(f_email_id),
  f_mobile_number varchar(10) not null
);

create table t_user_wallets (
  f_email_id varchar(100) not null,
  f_wallet_id bigint REFERENCES t_wallet_details(f_id)
 );
 
create table t_wallet_details( 
  f_id bigint PRIMARY KEY AUTO_INCREMENT,
  f_wallet_name varchar(50) NOT NULL,
  f_amount bigint,
  f_wallet_status varchar(20),
  f_created_by bigint REFERENCES t_user_details(f_id),
  f_created_time timestamp default current_timestamp,
  f_updated_time timestamp default current_timestamp
);

create table t_txn_master(
  f_id bigint primary key AUTO_INCREMENT,
  f_txn_id bigint not null,
  f_wallet_id bigint REFERENCES t_wallet_details(f_id),
  f_user_id bigint REFERENCES t_user_details(f_id),
  f_txn_type varchar(20) not null,
  f_txn_amount bigint not null,
  f_created_time timestamp default current_timestamp,
  f_updated_time timestamp default current_timestamp on UPDATE CURRENT_TIMESTAMP
);
