package com.gestion_transaction.services.Impl;

import java.util.List;

import com.gestion_transaction.entity.Transaction;
import com.gestion_transaction.repository.CompteRepository;
import com.gestion_transaction.repository.TransactionRepository;
import com.gestion_transaction.services.TransactionService;

public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;
    private final CompteRepository compteRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, CompteRepository compteRepository) {
        this.transactionRepository = transactionRepository;
        this.compteRepository = compteRepository;
    }

    @Override
    public Transaction add(Transaction transaction) {
        Transaction savedTransaction = transactionRepository.save(transaction);
        compteRepository.updateSolde(transaction.getCompte().getId(),transaction.getCompte().getSolde());

        return savedTransaction;
    }

    @Override
    public Transaction getTransactionById(int id) {
        return transactionRepository.findById(id);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> findById(int compteId) {
        return transactionRepository.findByCompteId(compteId);
    }

}
