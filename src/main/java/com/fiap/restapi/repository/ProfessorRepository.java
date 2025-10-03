package com.fiap.restapi.repository;

import com.fiap.restapi.config.ConnectionFactory;
import com.fiap.restapi.model.Professor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProfessorRepository {
    public Professor adicionar(Professor p) {
        final String sql = "INSERT INTO PROFESSOR2 (NOME, DEPARTAMENTO, EMAIL, TITULACAO) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getDepartamento());
            stmt.setString(3, p.getEmail());
            stmt.setString(4, p.getTitulacao());
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) p.setId(rs.getLong(1));
                }
            }
            return p;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1) { // ORA-00001 unique constraint
                throw new IllegalArgumentException("Email já cadastrado.");
            }
            throw new RuntimeException("Erro ao adicionar professor", e);
        }
    }

    public Optional<Professor> buscarPorId(Long id) {
        final String sql = "SELECT ID, NOME, DEPARTAMENTO, EMAIL, TITULACAO FROM PROFESSOR2 WHERE ID = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Professor p = new Professor(
                        rs.getLong("ID"),
                        rs.getString("NOME"),
                        rs.getString("DEPARTAMENTO"),
                        rs.getString("EMAIL"),
                        rs.getString("TITULACAO")
                    );
                    return Optional.of(p);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar professor por ID", e);
        }
    }

    public List<Professor> listar() {
        final String sql = "SELECT ID, NOME, DEPARTAMENTO, EMAIL, TITULACAO FROM PROFESSOR2 ORDER BY ID";
        List<Professor> result = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.add(new Professor(
                    rs.getLong("ID"),
                    rs.getString("NOME"),
                    rs.getString("DEPARTAMENTO"),
                    rs.getString("EMAIL"),
                    rs.getString("TITULACAO")
                ));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar professores", e);
        }
    }

    public Optional<Professor> atualizar(Long id, Professor p) {
        final String sql = "UPDATE PROFESSOR2 SET NOME = ?, DEPARTAMENTO = ?, EMAIL = ?, TITULACAO = ? WHERE ID = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getDepartamento());
            stmt.setString(3, p.getEmail());
            stmt.setString(4, p.getTitulacao());
            stmt.setLong(5, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                p.setId(id);
                return Optional.of(p);
            }
            return Optional.empty();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1) { // unique email
                throw new IllegalArgumentException("Email já cadastrado.");
            }
            throw new RuntimeException("Erro ao atualizar professor", e);
        }
    }

    public boolean deletar(Long id) {
        final String sql = "DELETE FROM PROFESSOR2 WHERE ID = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar professor", e);
        }
    }
}
