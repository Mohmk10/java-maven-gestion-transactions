package com.gestion_transaction.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.gestion_transaction.utils.DateFormat;

public abstract class Compte {
    protected int id;
    protected String numero;
    protected BigDecimal solde;
    protected LocalDate dateOuverture;
    protected TypeCompte type;

    public Compte(int id, String numero, BigDecimal solde, LocalDate dateOuverture, TypeCompte type) {
        this.id = id;
        this.numero = numero;
        this.solde = solde;
        this.dateOuverture = dateOuverture;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public LocalDate getDateOuverture() {
        return dateOuverture;
    }

    public TypeCompte getType() {
        return type;
    }

    public abstract void depot (double montant);
    public abstract boolean retrait (double montant);

    @Override
    public String toString() {
        return "\nID: " + id 
             + "\nNuméro: " + numero 
             + "\nSolde: " + solde
             + "\nType de compte: " + type
             + "\nDate de création: " + DateFormat.formatDate(dateOuverture);
    }
}
