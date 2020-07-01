DELIMITER $$
create procedure insert_wallet_details_v1dot0 (
    IN in_wallet_name varchar(50),
    IN in_amount bigint,
    IN in_status varchar(20),
    IN in_user_id bigint,
    OUT out_walletid bigint,
    OUT out_first_name varchar(50),
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
    SET error_desc = 'Duplicate value not allowed!';
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
    insert into t_wallet_details(f_wallet_name, f_amount, f_wallet_status, f_created_by)
    values(
    in_wallet_name,
    in_amount,
    in_status,
    in_user_id
    );
    
    SET out_walletid = last_insert_id();
    
    insert into t_user_wallet_ref(f_user_id, f_wallet_id)
    values(
    in_user_id,
    out_walletid);
  commit;
  SET response_code = 0;
end $$
DELIMITER ;
