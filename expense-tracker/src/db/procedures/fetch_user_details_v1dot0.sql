DELIMITER $$
create procedure fetch_user_details_v1dot0 (
    IN in_user_id bigint
)
BEGIN
    select * from t_user_details where f_id = in_user_id;
END $$
DELIMITER ;