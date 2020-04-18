DELIMITER $$
drop procedure if exists fetch_user_details_v1dot0;
create procedure fetch_user_details_v1dot0 (
    IN in_user_id varchar(50)
)
BEGIN
    select * from t_user_details where f_email = in_user_id;
END $$
DELIMITER ;