package com.gestion_transaction.repository.Impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.gestion_transaction.entity.Compte;
import com.gestion_transaction.entity.CompteCheque;
import com.gestion_transaction.entity.CompteEpargne;
import com.gestion_transaction.repository.CompteRepository;
import com.gestion_transaction.utils.Database;

public class CompteRepositoryImpl implements CompteRepository {

    @Override
    public Compte save(Compte compte) {
        // Single Table : on insère tout dans "compte"
        final String sql =
            "INSERT INTO compte (numero, solde, date_ouverture, type, date_debut, duree) " +
            "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, compte.getNumero());
            ps.setBigDecimal(2, compte.getSolde());
            ps.setDate(3, Date.valueOf(compte.getDateOuverture()));
            ps.setString(4, compte.getType().name());

            if (compte instanceof CompteEpargne e) {
                ps.setDate(5, Date.valueOf(e.getDateDebut()));
                ps.setInt(6, e.getDureeBlocage());
            } else {
                ps.setNull(5, java.sql.Types.DATE);
                ps.setNull(6, java.sql.Types.INTEGER);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idGenere = rs.getInt("id");
                    if (compte instanceof CompteEpargne e) {
                        return new CompteEpargne(
                            idGenere, e.getNumero(), e.getSolde(), e.getDateOuverture(),
                            e.getDateDebut(), e.getDureeBlocage()
                        );
                    } else if (compte instanceof CompteCheque c) {
                        return new CompteCheque(
                            idGenere, c.getNumero(), c.getSolde(), c.getDateOuverture()
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean existsByNumero(String numero) {
        final String sql = "SELECT COUNT(*) FROM compte WHERE numero = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, numero);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void updateSolde(int compteId, BigDecimal nouveauSolde) {
        final String sql = "UPDATE compte SET solde = ? WHERE id = ?";
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
        final String sql = "SELECT * FROM compte WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapCompte(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Compte> findAll() {
        final List<Compte> comptes = new ArrayList<>();
        final String sql = "SELECT * FROM compte";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                comptes.add(mapCompte(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comptes;
    }

    private Compte mapCompte(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String numero = rs.getString("numero");
        BigDecimal solde = rs.getBigDecimal("solde");
        LocalDate dateOuverture = rs.getDate("date_ouverture").toLocalDate();
        String type = rs.getString("type");

        if ("EPARGNE".equalsIgnoreCase(type)) {
            LocalDate dateDebut = rs.getDate("date_debut").toLocalDate();
            int duree = rs.getInt("duree");
            return new CompteEpargne(id, numero, solde, dateOuverture, dateDebut, duree);
        } else {
            return new CompteCheque(id, numero, solde, dateOuverture);
        }
    }
}
