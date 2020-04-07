DELIMITER $$
drop procedure if exists fetch_user_details_v1dot0;
create procedure fetch_user_details_v1dot0 (
    IN in_email_id varchar(100)
)
BEGIN
    select * from t_user_details where f_email = in_email_id;
    select * from t_wallet_details where f_id IN (select f_wallet_id from t_user_wallet_ref where f_email_id = in_email_id);
END $$
DELIMITER ;