DELIMITER $$
create procedure fetch_wallet_details_v1dot0 (
    IN in_wallet_id bigint
)
BEGIN
    select * from t_wallet_details where t_wallet_details.f_id = in_wallet_id;
    select f_id, f_first_name from t_user_details where f_id IN (select f_user_id from t_user_wallet_ref where f_wallet_id = in_wallet_id);
END $$
DELIMITER ;

