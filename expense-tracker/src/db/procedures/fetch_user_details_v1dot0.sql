DELIMITER $$
create procedure fetch_user_details_v1dot0 (
    IN in_user_id bigint
)
BEGIN
    select * from t_user_details where f_id = in_user_id;
    select f_id, f_wallet_name from t_wallet_details where f_id IN
    (select f_wallet_id from t_user_wallet_ref where f_user_id = in_user_id);
END $$
DELIMITER ;