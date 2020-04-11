DELIMITER $$
create procedure fetch_user_details_v1dot0 (
    IN in_first_name varchar(50),
    IN in_last_name varchar(50),
    IN in_email varchar(100),
    IN in_mobile_number varchar(10),
    OUT f_id bigint
)
BEGIN
    insert into t_user_details(f_id, f_first_name, f_last_name, f_email, f_mobile_number)
    values(
    NULL,
    in_first_name,
    in_last_name,
    in_email,
    in_mobile_number
    );
    commit;
    select f_id from t_user_details where f_email=in_email;
END $$
DELIMITER ;
