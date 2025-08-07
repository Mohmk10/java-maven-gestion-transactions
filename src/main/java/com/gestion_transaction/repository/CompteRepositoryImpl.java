package com.gestion_transaction.repository;

import java.math.BigDecimal;
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
import com.gestion_transaction.entity.CompteCheque;
import com.gestion_transaction.entity.CompteEpargne;
import com.gestion_transaction.utils.Database;

public class CompteRepositoryImpl implements CompteRepository{
    
    @Override
    public Compte save(Compte compte) {
        String sqlCompte = "INSERT INTO compte (numero, solde, date_ouverture, type) VALUES (?, ?, ?, ?) RETURNING id";

        try (Connection conn = Database.getConnection();
             PreparedStatement psCompte = conn.prepareStatement(sqlCompte)) {

            psCompte.setString(1, compte.getNumero());
            psCompte.setBigDecimal(2, compte.getSolde());
            psCompte.setDate(3, Date.valueOf(compte.getDateOuverture()));
            psCompte.setString(4, compte.getType().name());

            ResultSet rs = psCompte.executeQuery();
            if (rs.next()) {
                int compteId = rs.getInt("id");

                // INSERT dans la table fille selon le type
                if (compte instanceof CompteCheque cheque) {
                    saveCompteCheque(compteId);
                    return new CompteCheque(compteId, cheque.getNumero(), cheque.getSolde(), cheque.getDateOuverture());
                } else if (compte instanceof CompteEpargne epargne) {
                    saveCompteEpargne(epargne, compteId);
                    return new CompteEpargne(compteId, epargne.getNumero(), epargne.getSolde(), epargne.getDateOuverture(),
                            epargne.getDateDebut(), epargne.getDureeBlocage());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveCompteCheque(int compteId) throws SQLException {
        String sql = "INSERT INTO compte_cheque (id_compte) VALUES (?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, compteId);
            ps.executeUpdate();
        }
    }

    private void saveCompteEpargne(CompteEpargne epargne, int compteId) throws SQLException {
        String sql = "INSERT INTO compte_epargne (id_compte, date_debut, duree) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, compteId);
            ps.setDate(2, Date.valueOf(epargne.getDateDebut()));
            ps.setInt(3, epargne.getDureeBlocage());
            ps.executeUpdate();
        }
    }

    @Override
    public boolean existsByNumero(String numero) {
        String sql = "SELECT COUNT(*) FROM compte WHERE numero = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, numero);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    

    @Override
    public void updateSolde(int compteId, BigDecimal nouveauSolde) {
        String sql = "UPDATE compte SET solde = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, nouveauSolde);
            ps.setInt(2, compteId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Compte findById(int id) {
        String sql = "SELECT * FROM compte WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapCompte(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Compte> findAll() {
        List<Compte> comptes = new ArrayList<>();
        String sql = "SELECT * FROM compte";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                comptes.add(mapCompte(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comptes;
    }

    private Compte mapCompte(ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        int id = rs.getInt("id");
        String numero = rs.getString("numero");
        BigDecimal solde = rs.getBigDecimal("solde");
        LocalDate dateOuverture = rs.getDate("date_ouverture").toLocalDate();

        if ("CHEQUE".equalsIgnoreCase(type)) {
            return new CompteCheque(id, numero, solde, dateOuverture);
        } else {
            // Charger info épargne
            return loadCompteEpargne(id, numero, solde, dateOuverture);
        }
    }

    private CompteEpargne loadCompteEpargne(int id, String numero, BigDecimal solde, LocalDate dateOuverture) throws SQLException {
        String sql = "SELECT date_debut, duree FROM compte_epargne WHERE id_compte = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LocalDate dateDebut = rs.getDate("date_debut").toLocalDate();
                int duree = rs.getInt("duree");
                return new CompteEpargne(id, numero, solde, dateOuverture, dateDebut, duree);
            }
        }
        return null;
    }
}
