package com.gestion_transaction.services;

import java.util.List;

import com.gestion_transaction.entity.Transaction;
import com.gestion_transaction.repository.CompteRepository;
import com.gestion_transaction.repository.CompteRepositoryImpl;
import com.gestion_transaction.repository.TransactionRepository;
import com.gestion_transaction.repository.TransactionRepositoryImpl;

public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;
    private final CompteRepository compteRepository;

    public TransactionServiceImpl() {
        this.transactionRepository = new TransactionRepositoryImpl();
        this.compteRepository = new CompteRepositoryImpl();
    }

    @Override
    public Transaction addTransaction(Transaction transaction) {
        Transaction savedTransaction = transactionRepository.save(transaction);

        compteRepository.updateSolde(transaction.getCompte().getId(),transaction.getCompte().getSolde());

        return savedTransaction;
    }

    @Override
    public Transaction getTransactionById(int id) {
        return transactionRepository.findById(id);
    }

    @Override
    public List<Transaction> findByCompteId(int compteId) {
        return transactionRepository.findByCompteId(compteId);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
