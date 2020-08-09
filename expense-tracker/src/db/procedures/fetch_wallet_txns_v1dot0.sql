DELIMITER $$
create procedure fetch_wallet_txns_v1dot0 (
    IN in_wallet_id bigint,
    IN in_from_date bigint,
    IN in_to_date bigint,
    IN in_offset int,
    IN in_limit int
)
BEGIN
    select * from t_txn_master where
    f_wallet_id = in_wallet_id and
    f_updated_time > in_from_date and
    f_updated_time < in_to_date
    LIMIT in_offset, in_limit
    order by f_updated_time desc;
END $$
DELIMITER ;