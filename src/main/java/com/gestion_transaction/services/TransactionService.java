package com.gestion_transaction.services;

import java.util.List;

import com.gestion_transaction.entity.Transaction;

public interface TransactionService {

    Transaction addTransaction(Transaction transaction);
    Transaction getTransactionById(int id);
    List<Transaction> findByCompteId(int compteId);
    List<Transaction> getAllTransactions();
}
