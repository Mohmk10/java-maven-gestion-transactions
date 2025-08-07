package com.gestion_transaction.repository;

import java.util.List;

import com.gestion_transaction.entity.Transaction;

public interface TransactionRepository {
    
    Transaction save(Transaction transaction);
    Transaction findById(int id);
    List<Transaction> findByCompteId(int compteId);
    List<Transaction> findAll();
}
