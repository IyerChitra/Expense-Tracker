DELIMITER $$
CREATE PROCEDURE `fetch_user_email_v1dot0`(
    IN in_email_id varchar(100),
    IN in_wallet_id bigint,
    IN in_offset int,
    IN in_limit int
)
BEGIN
    select * from t_user_details 
	join t_user_wallet_ref
	on t_user_details.f_id <> t_user_wallet_ref.f_user_id
	where t_user_details.f_email LIKE concat('%',in_email_id,'%') and t_user_wallet_ref.f_wallet_id=in_wallet_id LIMIT in_offset, in_limit;
END$$
DELIMITER ;
