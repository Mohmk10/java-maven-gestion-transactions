package com.gestion_transaction.repository;

import java.util.List;

import com.gestion_transaction.entity.Transaction;

public interface TransactionRepository extends Repository<Transaction>{
    
    Transaction save(Transaction transaction);
    Transaction findById(int id);
    List<Transaction> findAll();
    List<Transaction> findByCompteId(int compteId);
    
}
