package br.unisinos.library.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import br.unisinos.library.dto.EmprestimoRequestDTO;
import br.unisinos.library.dto.EmprestimoResponseDTO;
import br.unisinos.library.entity.Emprestimo;
import br.unisinos.library.entity.Livro;
import br.unisinos.library.entity.Usuario;
import br.unisinos.library.exception.RecursoNaoEncontradoException;
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

    public EmprestimoResponseDTO emprestar(EmprestimoRequestDTO dto) {

        if (emprestimoRepository.existsByUsuarioIdAndDevolvidoFalse(dto.usuarioId())) {
            throw new RegraLibraryException("Usuário já possui um empréstimo ativo.");
        }

        if (emprestimoRepository.existsByLivroIdAndDevolvidoFalse(dto.livroId())) {
            throw new RegraLibraryException("Livro já está emprestado.");
        }

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com o id: " + dto.usuarioId()));

        Livro livro = livroRepository.findById(dto.livroId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado com o id: " + dto.livroId()));

        Emprestimo emprestimo = Emprestimo.builder()
            .usuario(usuario)
            .livro(livro)
            .dataEmprestimo(LocalDate.now())
            .dataPrevistaDevolucao(LocalDate.now().plusDays(7))
            .devolvido(false)
            .build();

        return toResponseDTO(emprestimoRepository.save(emprestimo));
    }

    public EmprestimoResponseDTO devolver(Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Empréstimo não encontrado com o id: " + id));

        if (Boolean.TRUE.equals(emprestimo.getDevolvido())) {
            throw new RegraLibraryException("Este empréstimo já foi devolvido.");
        }

        emprestimo.setDevolvido(true);
        emprestimo.setDataDevolucao(LocalDate.now());

        return toResponseDTO(emprestimoRepository.save(emprestimo));
    }

    public List<EmprestimoResponseDTO> listarTodos(String busca, Long categoriaId, String editora) {
        String buscaNormalizada = normalizarTexto(busca);
        String editoraNormalizada = normalizarTexto(editora);

        List<Emprestimo> emprestimos = buscaNormalizada == null && categoriaId == null && editoraNormalizada == null
            ? emprestimoRepository.listarTodosComRelacionamentos()
            : emprestimoRepository.buscarComFiltros(buscaNormalizada, categoriaId, editoraNormalizada);

        return emprestimos
            .stream()
            .map(this::toResponseDTO)
            .toList();
    }

    public List<EmprestimoResponseDTO> listarPorUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado com o id: " + usuarioId);
        }

        return emprestimoRepository.findByUsuarioId(usuarioId)
            .stream()
            .map(this::toResponseDTO)
            .toList();
    }

    private EmprestimoResponseDTO toResponseDTO(Emprestimo e) {
        Livro livro = e.getLivro();
        return new EmprestimoResponseDTO(
            e.getId(),
            e.getUsuario().getId(),
            e.getUsuario().getNome(),
            livro.getId(),
            livro.getTitulo(),
            livro.getIsbn(),
            livro.getAutor() != null ? livro.getAutor().getNome() : null,
            livro.getEditora(),
            livro.getCategoria() != null ? livro.getCategoria().getId() : null,
            livro.getCategoria() != null ? livro.getCategoria().getNome() : null,
            e.getDataEmprestimo(),
            e.getDataPrevistaDevolucao(),
            e.getDataDevolucao(),
            e.getDevolvido()
        );
    }

    private String normalizarTexto(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }

        return valor.trim();
    }
}
