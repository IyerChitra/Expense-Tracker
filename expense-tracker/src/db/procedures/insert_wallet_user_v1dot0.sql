DELIMITER $$
create procedure insert_wallet_user_v1dot0 (
    IN in_wallet_id bigint,
    IN in_email_id varchar(100),
    OUT out_userid bigint,
    OUT out_first_name varchar(50),
    OUT response_code int,
    OUT error_code varchar(100),
    OUT error_desc text
)
begin

  declare count int DEFAULT 0;

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
  select count(*) into count from t_user_details where f_email = in_email_id;
  
  IF count = 1 THEN
  	SET out_userid = (select t_user_details.f_id from t_user_details where  t_user_details.f_email = in_email_id);
  	insert into t_user_wallet_ref(f_user_id, f_wallet_id, f_created_time)
  	 values(
  	 out_userid,
  	 in_wallet_id,
  	 (select t_wallet_details.f_created_time from t_wallet_details where t_wallet_details.f_id = in_wallet_id));
    
  commit;
  SET response_code = 0;
  ELSE
  	SET response_code = -1;
  	SET error_code = 'USER_NOT_FOUND';
    SET error_desc = 'User does not exist! Please invite the user to Sign Up.';
    
   END IF;
   
   
   SET out_first_name = (select t_user_details.f_first_name from t_user_details where t_user_details.f_email = in_email_id);
   
     end $$
DELIMITER ;
