package com.gestion_transaction.views;

import java.util.List;
import java.util.Scanner;

import com.gestion_transaction.entity.Compte;
import com.gestion_transaction.entity.Transaction;
import com.gestion_transaction.services.CompteService;
import com.gestion_transaction.services.TransactionService;
import com.gestion_transaction.services.CompteServiceImpl;
import com.gestion_transaction.services.TransactionServiceImpl;
import com.gestion_transaction.utils.Database;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
    
        CompteService compteService = new CompteServiceImpl();
        TransactionService transactionService = new TransactionServiceImpl();
        CompteView compteView = new CompteView();
        TransactionView transactionView = new TransactionView();

        int choix;

        do {
            choix = menu();
            switch (choix) {

                case 1 -> {
                    Compte compte = compteView.saisieCompte(compteService);
                    if (compte != null) {
                        compteService.addCompte(compte);
                        System.out.println("\nCompte enregistré avec succès\n");
                    } else {
                        System.out.println("\nLe compte n’a pas été enregistr\n");
                    }
                }

                case 2 -> {
                    List<Compte> comptes = compteService.getAllComptes();
                    compteView.afficherComptes(comptes);
                }

                case 3 -> {
                    int id = saisie("Donnez l'ID du compte");
                    Compte compteId = compteService.getCompteById(id);
                    if (compteId != null) {
                        Transaction tr = transactionView.saisieTransaction(compteId);
                        if (tr != null) {
                            transactionService.addTransaction(tr);
                            System.out.println("\nTransaction effectuée avec succès\n");
                        } else {
                            System.out.println("\nLa transaction a échoué\n");
                        }
                    } else {
                        System.out.println("\nAucun compte trouvé avec cet ID\n");
                    }
                }

                case 4 -> {
                    System.out.println("\n=== Toutes les transactions ===\n");
                    List<Transaction> transactions = transactionService.getAllTransactions();
                    transactionView.afficherTransactions(transactions);

                    int a;
                    do {
                        a = menuTr();
                        if (a == 1) {
                            int id = saisie("Donnez l'ID du compte");
                            Compte compte = compteService.getCompteById(id);
                            if (compte != null) {
                                List<Transaction> transCompte = transactionService.findByCompteId(compte.getId());
                                transactionView.afficherTransactionsParCompte(compte, transCompte);
                            } else {
                                System.out.println("\nAucun compte trouvé avec cet ID.");
                            }
                        }
                    } while (a != 2);
                }

                case 5 -> {
                    System.out.println("\nFin du programme. Merci \n");
                    Database.closeConnection();
                } 

                default -> System.out.println("\nChoix invalide, réessayez\n");
            }
        } while (choix != 5);

        sc.close();
    }

    public static int saisie(String message) {
        System.out.print("\n" + message + ": ");
        while (!sc.hasNextInt()) {
            System.out.println("\nVeuillez saisir un nombre valide.");
            sc.next();
        }
        return sc.nextInt();
    }

    public static int menuTr() {
        System.out.println("\n=== Menu Transaction ===");
        System.out.println("1 - Filtrer par compte");
        System.out.println("2 - Quitter");
        System.out.print("Faites un choix: ");
        return sc.nextInt();
    }

    public static int menu() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1 - Ajouter un compte");
        System.out.println("2 - Afficher le(s) compte(s)");
        System.out.println("3 - Ajouter une transaction");
        System.out.println("4 - Lister les transactions d'un compte");
        System.out.println("5 - Quitter");
        System.out.print("➡ Faites un choix: ");
        return sc.nextInt();
    }
}