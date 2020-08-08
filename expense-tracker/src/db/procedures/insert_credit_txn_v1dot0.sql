DELIMITER $$
create procedure insert_wallet_details_v1dot0 (
    IN in_txnid bigint,
    IN in_walletid bigint,
    IN in_amount bigint,
    IN in_error_code,
    IN in_wallet_amount

    OUT out_txn_id bigint,
    OUT response_code int,
    OUT error_code varchar(100),
    OUT error_desc text
)
begin

-- SQLCODE 1364 is for not having value i.e null or empty.
  declare exit handler for 1364
    begin
      SET response_code = -1;
      SET error_code = 'NULL_VALUE';
      SET error_desc = 'Null value not allowed.';
      rollback;
      update t_txn_master set f_txn_status = 'FAILED' and f_error_code = error_code where f_txn_id = in_txnid;
      COMMIT;
  end;

-- SQLCODE 1062 is for duplicate entry.
  declare exit handler for 1062
    begin
    SET response_code = -1;
    SET error_code = 'DUPLICATE_ENTRY';
    SET error_desc = 'Duplicate value not allowed!';
    rollback;
    update t_txn_master set f_txn_status = 'FAILED' and f_error_code = error_code where f_txn_id = in_txnid;
    COMMIT;
  end;

-- general exit handler in case transaction fails.
  declare exit handler for sqlexception
    begin
    SET response_code = -1;
    SET error_code = 'TECHNICAL_ERROR';
    SET error_desc = 'Technical Error Occured. Please try again.';
    rollback;
    update t_txn_master set f_txn_status = 'FAILED' and f_error_code = error_code where f_txn_id = in_txnid;
    COMMIT;
  end;

  declare wallet_amt bigint;
IF in_error_code = '' THEN
  start transaction;
  SET wallet_amt = in_amount + in_wallet_amount;

  update t_txn_master set f_txn_status = 'IN_PROGRESS' where f_txn_id=in_txnid;
  COMMIT;
    
   SET out_txn_id = in_txnid;
    
  update t_wallet_details set f_amount = wallet_amt WHERE f_id=in_walletid;

  update t_txn_master set f_txn_status = 'SUCCESS' where f_txn_id=in_txnid;
  commit;

  ELSE
    update t_txn_master set f_txn_status = 'FAILED' and f_error_code = in_error_code where f_txn_id = in_txnid;
    COMMIT;
  END IF;


  SET response_code = 0;
end $$
DELIMITER ;
