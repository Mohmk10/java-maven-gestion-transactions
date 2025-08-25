package com.gestion_transaction.services;

import java.util.List;

import com.gestion_transaction.entity.Transaction;

public interface TransactionService extends Service<Transaction>{

    public List<Transaction> findById(int id);
    public Transaction getTransactionById(int id);
    
}
