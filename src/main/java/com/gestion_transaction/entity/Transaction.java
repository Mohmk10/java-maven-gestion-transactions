package com.gestion_transaction.entity;

import java.time.LocalDate;

import com.gestion_transaction.utils.DateFormat;

public class Transaction {
    private int id;
    private double montant;
    private LocalDate date;
    private TypeTransaction type;
    private Compte compte;

    public Transaction() {
        this.id = 0;
        this.montant = 0;
        this.date = null;
        this.type = null;
        this.compte = null;
    }

    public Transaction(int id, double montant, LocalDate date, TypeTransaction type, Compte compte) {
        this.id = id;
        this.montant = montant;
        this.date = date;
        this.type = type;
        this.compte = compte;
    }

    public int getId() {
        return id;
    }

    public double getMontant() {
        return montant;
    }

    public LocalDate getDate() {
        return date;
    }

    public TypeTransaction getType() {
        return type;
    }

    public Compte getCompte() {
        return compte;
    }

    @Override
    public String toString() {
        return "\nID: " + id 
             + "\nMontant: " + montant 
             + "\nType de transaction: " + type 
             + "\nDate de transaction: " + DateFormat.formatDate(date);
    }
}
