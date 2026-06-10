package br.unisinos.library.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import br.unisinos.library.entity.Usuario;
import br.unisinos.library.entity.Livro;
import br.unisinos.library.entity.Emprestimo;
import br.unisinos.library.exception.RegraLibraryException;
import br.unisinos.library.repository.EmprestimoRepository;
import br.unisinos.library.repository.LivroRepository;
import br.unisinos.library.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;

    public Emprestimo emprestar(Long usuarioId, Long livroId) {

        if (emprestimoRepository.existsByUsuarioIdAndDevolvidoFalse(usuarioId)) {
            throw new RegraLibraryException("Usuário já possui um empréstimo ativo.");
        }

        if (emprestimoRepository.existsByLivroIdAndDevolvidoFalse(livroId)) {
            throw new RegraLibraryException("Livro já está emprestado.");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();

        Livro livro = livroRepository.findById(livroId).orElseThrow();

        Emprestimo emprestimo = Emprestimo.builder()
                                .usuario(usuario)
                                .livro(livro)
                                .dataEmprestimo(LocalDate.now())
                                .dataPrevistaDevolucao(LocalDate.now().plusDays(7))
                                .devolvido(false)
                                .build();

        return emprestimoRepository.save(emprestimo);
    }

}
