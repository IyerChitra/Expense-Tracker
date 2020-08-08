DELIMITER $$
create procedure fetch_txn_details_v1dot0 (
    IN in_txn_id bigint,
    IN in_from_date bigint,
    IN in_to_date bigint
)
BEGIN
    select * from t_txn_master where f_txn_id = in_txn_id;


END $$
DELIMITER ;