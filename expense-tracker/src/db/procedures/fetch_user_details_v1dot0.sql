DELIMITER $$
create procedure fetch_user_details_v1dot0 (
    IN in_user_id bigint
)
BEGIN
    select * from t_user_details where f_id = in_user_id;
    select t_wallet_details.f_id, t_wallet_details.f_wallet_name from t_wallet_details
	join t_user_wallet_ref on t_wallet_details.f_id=t_user_wallet_ref.f_wallet_id
 	where t_user_wallet_ref.f_user_id=in_user_id;
END $$
DELIMITER ;