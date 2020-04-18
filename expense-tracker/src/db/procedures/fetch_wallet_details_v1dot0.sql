DELIMITER $$
create procedure fetch_user_details_v1dot0 (
    IN in_email_id varchar(50),
    IN in_wallet_name varchar(50)
)
BEGIN
    select * from t_wallet_details where f_wallet_name = in_wallet_name and f_id IN (select f_wallet_id from t_user_wallets where f_email_id = in_user_id);
END $$
DELIMITER ;

