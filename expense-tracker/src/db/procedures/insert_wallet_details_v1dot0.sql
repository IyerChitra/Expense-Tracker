DELIMITER $$
create procedure insert_user_details_v1dot0 (
    IN in_wallet_name varchar(50),
    IN in_amount bigint,
    IN in_status varchar(20),
    IN in_user_id bigint,
    OUT f_id bigint,
    OUT f_first_name varchar(50),
    OUT response_code int,
    OUT error_code varchar(100),
    OUT error_desc text
)
begin

-- SQLCODE 1364 is for not having value i.e null or empty.
  declare exit handler for 1364
    begin
      SET response_code = -1;
      SET error_code = 'NULL_VALUE';
      SET error_desc = 'Null value not allowed.';
      rollback;
  end;

-- SQLCODE 1062 is for duplicate entry.
  declare exit handler for 1062
    begin
    SET response_code = -1;
    SET error_code = 'DUPLICATE_ENTRY';
    SET error_desc = 'Email or Mobile already taken';
    rollback;
  end;

-- general exit handler in case transaction fails.
  declare exit handler for sqlexception
    begin
    SET response_code = -1;
    SET error_code = 'TECHNICAL_ERROR';
    SET error_desc = 'Technical Error Occured. Please try again.';
    rollback;
  end;

  start transaction;
    insert into t_wallet_details(f_id, f_wallet_name, f_amount, f_wallet_status, f_created_by)
    values(
    null,
    in_wallet_name,
    in_amount,
    in_status,
    in_user_id,
    );
    
    insert into t_user_wallets(f_email_id, f_wallet_id, f_created_by)
    values(
    (select f_email from t_user_details where f_id = f_created_by),
    (select f_id from t_wallet_details where f_wallet_name = in_wallet_name and f_created_by = in_user_id),
    in_user_id
    );
  commit;
  SET response_code = 0;
    select f_id from t_wallet_details where f_wallet_name = in_wallet_name and f_created_by = in_user_id;
    select f_first_name from t_user_details where f_email IN (select f_email_id from t_user_wallets where f_wallet_id = (select f_id from t_wallet_details where f_wallet_name = in_wallet_name and f_created_by = in_user_id));
end $$
DELIMITER ;

```