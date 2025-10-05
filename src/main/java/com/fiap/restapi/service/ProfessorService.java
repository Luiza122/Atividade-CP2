package com.fiap.restapi.service;

import com.fiap.restapi.model.Professor;
import com.fiap.restapi.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    private final ProfessorRepository repo;

    public ProfessorService(ProfessorRepository repo) {
        this.repo = repo;
    }

    public Professor adicionar(String nome, String departamento, String email, String titulacao) {
        validar(nome, departamento, email, titulacao);
        Professor professor = new Professor(null, nome.trim(), departamento.trim(), email.trim(), titulacao.trim());
        return repo.adicionar(professor);
    }

    public Optional<Professor> buscarPorId(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("Id inválido");
        return repo.buscarPorId(id);
    }

    public List<Professor> listar() {
        return repo.listar();
    }

    public Optional<Professor> atualizar(Long id, String nome, String departamento, String email, String titulacao) {
        if (id == null || id <= 0) throw new IllegalArgumentException("Id inválido");
        validar(nome, departamento, email, titulacao);
        Professor professor = new Professor(id, nome.trim(), departamento.trim(), email.trim(), titulacao.trim());
        return repo.atualizar(id, professor);
    }

    public boolean deletar(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("Id inválido");
        return repo.deletar(id);
    }

    // Validação dos dados
    private void validar(String nome, String departamento, String email, String titulacao) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório");
        if (departamento == null || departamento.isBlank()) throw new IllegalArgumentException("Departamento é obrigatório");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email é obrigatório");
        if (titulacao == null || titulacao.isBlank()) throw new IllegalArgumentException("Titulação é obrigatória");

        if (nome.length() > 150) throw new IllegalArgumentException("Nome excede 150 caracteres");
        if (departamento.length() > 150) throw new IllegalArgumentException("Departamento excede 150 caracteres");
        if (email.length() > 150) throw new IllegalArgumentException("Email excede 150 caracteres");
        if (titulacao.length() > 100) throw new IllegalArgumentException("Titulação excede 100 caracteres");
    }
}
