package com.fiap.restapi.service;

import com.fiap.restapi.model.Professor;
import com.fiap.restapi.repository.ProfessorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ProfessorService {

    private final ProfessorRepository repository;
    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    public ProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }

    public Professor adicionar(String nome, String departamento, String email, String titulacao) {
        validarObrigatorio(nome, "Nome");
        validarObrigatorio(departamento, "Departamento");
        validarEmail(email);
        validarTitulacao(titulacao);

        Professor professor = new Professor(null, nome.trim(), departamento.trim(), email.trim(), titulacao.trim());

        return repository.adicionar(professor);
    }

    public Optional<Professor> buscarPorId(Long id) {
        validarId(id);
        return repository.buscarPorId(id);
    }

    public List<Professor> listar() {
        return repository.listar();
    }

    public Optional<Professor> atualizar(Long id, String nome, String departamento, String email, String titulacao) {
        validarId(id);
        nome = trim(nome);
        departamento = trim(departamento);
        email = trim(email);
        titulacao = trim(titulacao);

        validarObrigatorio(nome, "Nome");
        validarObrigatorio(departamento, "Departamento");
        validarEmail(email);
        validarTitulacao(titulacao);

        Professor p = new Professor(id, nome, departamento, email, titulacao);
        return repository.atualizar(id, p);
    }

    public boolean deletar(Long id) {
        validarId(id);
        return repository.deletar(id);
    }

    private void validarId(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID inválido.");
    }

    private void validarObrigatorio(String v, String campo) {
        if (v == null || v.isEmpty()) throw new IllegalArgumentException(campo + " é obrigatório.");
    }

    private void validarEmail(String email) {
        if (email == null || email.isEmpty() || !EMAIL.matcher(email).matches())
            throw new IllegalArgumentException("Email inválido.");
    }

    private void validarTitulacao(String titulacao) {
        if (titulacao != null && titulacao.length() > 80)
            throw new IllegalArgumentException("Titulação deve ter no máximo 80 caracteres.");
    }

    private String trim(String s) {
        return s == null ? null : s.trim();
    }
}
