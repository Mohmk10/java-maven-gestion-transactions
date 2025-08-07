package com.gestion_transaction.views;

import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

import com.gestion_transaction.entity.Compte;
import com.gestion_transaction.entity.Transaction;
import com.gestion_transaction.entity.TypeCompte;
import com.gestion_transaction.entity.TypeTransaction;

public class TransactionView {
    private Scanner sc = new Scanner(System.in);

    public int saisie(String message) {
        while (true) {
            try {
                System.out.print("\n" + message + ": ");
                return sc.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("\nEntrée invalide. Veuillez saisir un nombre entier.\n");
                sc.next();
            }
        }
    }


    public Transaction saisieTransaction(Compte compte) {

        System.out.print("\nEntrez le montant: ");
        double montant;

        while (true) {
            try {
                montant = sc.nextDouble();
                if (montant <= 0) {
                    System.out.println("⚠ Le montant doit être supérieur à 0");
                    continue;
                }
                break;
            } catch (java.util.InputMismatchException e) {
                System.out.println("\nEntrée invalide. Veuillez saisir un nombre valide.\n");
                sc.next();
            }
        }

        System.out.println("\n=== Type de transaction ===");
        System.out.println("1 - DÉPÔT");
        System.out.println("2 - RETRAIT");

        int choix;

        do {
            choix = saisie("Faites un choix");
            if (choix != 1 && choix != 2) {
                System.out.println("\n Veuillez choisir entre 1 & 2");
            }
        } while (choix != 1 && choix != 2);

        TypeTransaction type = (choix == 1) ? TypeTransaction.DEPOT : TypeTransaction.RETRAIT;

        
        if (type == TypeTransaction.DEPOT) {
            compte.depot(montant);
        } else {
            boolean valider = compte.retrait(montant);
            if (!valider && compte.getType() == TypeCompte.CHEQUE) {
                System.out.println("\nFonds insuffisants");
                return null;
            } else if (!valider && compte.getType() == TypeCompte.EPARGNE) {
                System.out.println("\nFonds insuffisants ou Blocage épargne");
                return null;
            }
        }

        return new Transaction(0, montant, LocalDate.now(), type, compte);
    }

    
    public void afficherTransactions(List<Transaction> transactions) {
        
        if (transactions == null || transactions.isEmpty()) {
            System.out.println("\nAucune transaction trouvée.\n");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction.toString());
            }
        }
    }

    
    public void afficherTransactionsParCompte(Compte compte, List<Transaction> transactions) {

        System.out.println("\n=== Transactions du compte " + compte.getNumero() + " ===");

        if (transactions == null || transactions.isEmpty()) {
            System.out.println("\nAucune transaction trouvée pour ce compte\n");
            return;
        }

        for (Transaction transaction : transactions) {
            System.out.println(transaction.toString());
        }
    }


}
