package com.gestion_transaction.container;

import com.gestion_transaction.repository.CompteRepository;
import com.gestion_transaction.repository.TransactionRepository;
import com.gestion_transaction.repository.Impl.CompteRepositoryImpl;
import com.gestion_transaction.repository.Impl.TransactionRepositoryImpl;
import com.gestion_transaction.services.CompteService;
import com.gestion_transaction.services.TransactionService;
import com.gestion_transaction.services.Impl.CompteServiceImpl;
import com.gestion_transaction.services.Impl.TransactionServiceImpl;
import com.gestion_transaction.views.CompteView;
import com.gestion_transaction.views.TransactionView;

public final class Factory {

    private Factory() {}
    

    private static final CompteRepository COMPTE_REPO = new CompteRepositoryImpl();
    private static final TransactionRepository TRANSACTION_REPO = new TransactionRepositoryImpl();

    // --- Singletons services (haut niveau) ---
    private static final CompteService COMPTE_SERVICE = new CompteServiceImpl(COMPTE_REPO);

    private static final TransactionService TRANSACTION_SERVICE = new TransactionServiceImpl(TRANSACTION_REPO, COMPTE_REPO);

    // --- Vues (si elles n'ont pas d'état important, on peut réutiliser) ---
    private static final CompteView COMPTE_VIEW = new CompteView();
    private static final TransactionView TRANSACTION_VIEW = new TransactionView();

    // --- Accès contrôlé depuis Main ---
    public static CompteService compteService() { return COMPTE_SERVICE; }
    public static TransactionService transactionService() { return TRANSACTION_SERVICE; }
    public static CompteView compteView() { return COMPTE_VIEW; }
    public static TransactionView transactionView() { return TRANSACTION_VIEW; }
}
