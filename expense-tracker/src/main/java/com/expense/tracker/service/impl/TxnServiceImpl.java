package com.expense.tracker.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import com.expense.tracker.models.Pagination;
import com.expense.tracker.models.Transaction;
import com.expense.tracker.service.ITxnService;

import ch.qos.logback.classic.Logger;

@Component
public class TxnServiceImpl implements ITxnService{

	@Autowired
	JdbcTemplate jdbcTemplate;
	Logger logger = (Logger) LoggerFactory.getLogger(TxnServiceImpl.class);
	
	@Override
	public List<Transaction> getTxns(Long walletId, Pagination page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction txnCredit(Transaction txn) {
		// TODO Auto-generated method stub
		logger.debug("Recording Credit Transaction by: ".concat(txn.getUser().toString()).concat(" to wallet: ").concat(txn.getWalletId().toString()));
			

		return null;
	}
	
	@Override
	public Transaction txnDebit(Transaction txn) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public long createTxnId(){
		Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String datetime = ft.format(dNow);
        return Long.parseUnsignedLong(datetime);
	}
}
