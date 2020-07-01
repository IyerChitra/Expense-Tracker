DELIMITER $$
CREATE PROCEDURE insert_bulk_user_wallet_ref_v1dot0(
	IN in_wallet_id bigint,
    IN user_ids longtext
    )
BEGIN

    declare idx INT default 1;
    declare user_id longtext;
    label: LOOP
		
        SET user_id=SUBSTRING_INDEX(user_ids,',',idx);
        SET user_ids=SUBSTRING(user_ids,3,length(user_ids));
        IF user_id='' THEN
			LEAVE label;
        END IF; 
        insert into t_user_wallet_ref(f_user_id,f_wallet_id) values (user_id, in_wallet_id);
     end loop label;
     
END$$
DELIMITER ;
