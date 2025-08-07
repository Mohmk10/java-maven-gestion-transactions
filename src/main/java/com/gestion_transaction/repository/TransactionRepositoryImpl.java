package com.gestion_transaction.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.gestion_transaction.entity.Compte;
import com.gestion_transaction.entity.Transaction;
import com.gestion_transaction.entity.TypeTransaction;
import com.gestion_transaction.utils.Database;

public class TransactionRepositoryImpl implements TransactionRepository{

    @Override
    public Transaction save(Transaction transaction) {
        String sql = "INSERT INTO transaction (montant, date_transaction, type, compte_id) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, transaction.getMontant());
            ps.setDate(2, Date.valueOf(transaction.getDate()));
            ps.setString(3, transaction.getType().name());
            ps.setInt(4, transaction.getCompte().getId());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idGenere = rs.getInt("id");
                return new Transaction(
                    idGenere,
                    transaction.getMontant(),
                    transaction.getDate(),
                    transaction.getType(),
                    transaction.getCompte()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Transaction findById(int id) {
        String sql = "SELECT * FROM transaction WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapTransaction(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Transaction> findByCompteId(int compteId) {
        String sql = "SELECT * FROM transaction WHERE compte_id = ?";
        List<Transaction> transactions = new ArrayList<>();
    
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, compteId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                transactions.add(mapTransaction(rs));
            }
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }


    @Override
    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transaction";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                transactions.add(mapTransaction(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    private Transaction mapTransaction(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        double montant = rs.getDouble("montant");
        LocalDate date = rs.getDate("date_transaction").toLocalDate();
        TypeTransaction type = TypeTransaction.valueOf(rs.getString("type"));

        // Récupérer le compte associé
        int compteId = rs.getInt("compte_id");
        CompteRepositoryImpl compteRepo = new CompteRepositoryImpl();
        Compte compte = compteRepo.findById(compteId);

        return new Transaction(id, montant, date, type, compte);
    }
}
