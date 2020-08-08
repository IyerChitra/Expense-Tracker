DELIMITER $$
CREATE PROCEDURE insert_bulk_user_wallet_ref_v1dot0(
	IN in_wallet_id bigint,
    IN user_ids longtext,
    OUT response_code int,
    OUT error_code varchar(100),
    OUT error_desc text
    )
BEGIN

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

    declare idx INT default 1;
    declare user_id longtext;

  start transaction;

    label: LOOP
		
        SET user_id=SUBSTRING_INDEX(user_ids,',',idx);
        idx = idx + 1;
        IF user_id='' THEN
			LEAVE label;
        END IF; 
        insert into t_user_wallet_ref(f_user_id,f_wallet_id) values (user_id, in_wallet_id);
     end loop label;
     commit;
     SET response_code = 0;
END$$
DELIMITER ;
