package br.unisinos.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.unisinos.library.dto.LivroRequestDTO;
import br.unisinos.library.dto.LivroResponseDTO;
import br.unisinos.library.entity.Categoria;
import br.unisinos.library.entity.Livro;
import br.unisinos.library.exception.RecursoNaoEncontradoException;
import br.unisinos.library.exception.RegraLibraryException;
import br.unisinos.library.repository.CategoriaRepository;
import br.unisinos.library.repository.LivroPorAutorProjection;
import br.unisinos.library.repository.LivroPorCategoriaProjection;
import br.unisinos.library.repository.LivroRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final CategoriaRepository categoriaRepository;

    public LivroResponseDTO salvar (LivroRequestDTO livroDTO) {
        Categoria categoria = buscarCategoria(livroDTO.categoriaId());
        Livro livro = Livro.builder()
                        .titulo(livroDTO.titulo())
                        .nomeAutor(livroDTO.nomeAutor())
                        .editora(livroDTO.editora())
                        .numeroPaginas(livroDTO.numeroPaginas())
                        .isbn(livroDTO.isbn())
                        .categoria(categoria)
                        .build();
        return toResponseDTO(livroRepository.save(livro));
    }

    private LivroResponseDTO toResponseDTO(Livro livro) {
        return new LivroResponseDTO(
            livro.getId(),
            livro.getTitulo(),
            livro.getNomeAutor(),
            livro.getEditora(),
            livro.getNumeroPaginas(),
            livro.getIsbn(),
            livro.getCategoria() != null ? livro.getCategoria().getId() : null,
            livro.getCategoria() != null ? livro.getCategoria().getNome() : null);
    }
    
    public Livro atualizar(Long id, Livro livroAtualizado) {
        Livro livro = livroRepository.findById(id)
        .orElseThrow(() -> new RegraLibraryException("Livro não encontrado"));

        livro.setTitulo(livroAtualizado.getTitulo());
        livro.setNomeAutor(livroAtualizado.getNomeAutor());
        livro.setEditora(livroAtualizado.getEditora());
        livro.setNumeroPaginas(livroAtualizado.getNumeroPaginas());
        livro.setIsbn(livroAtualizado.getIsbn());

        return livroRepository.save(livro);
    }

    private Categoria buscarCategoria(Long categoriaId) {
        if (categoriaId == null) {
            throw new RegraLibraryException("A categoria do exemplar é obrigatória"); 
        }
        return categoriaRepository.findById(categoriaId).orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada com o id: " + categoriaId));
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Optional<Livro> buscarPorId(Long id) {
        return livroRepository.findById(id);
    }

    public void excluir(Long id) {
        livroRepository.deleteById(id);
    }

    public List<Livro> buscar(String nome, Long categoriaId, String nomeAutor) {
        return livroRepository.buscarComFiltros(nome, categoriaId, nomeAutor);
    }

    public List<LivroPorCategoriaProjection> relatorioLivrosPorCategoria() {
        return livroRepository.relatorioLivrosPorCategoria();
    }

    public List<LivroPorAutorProjection> relatorioLivrosPorAutor() {
        return livroRepository.relatorioLivrosPorAutor();
    }


    public List<Livro> buscarPorAutor(String nomeAutor) {
        return livroRepository.findByNomeAutorContainingIgnoreCase(nomeAutor);
    }

}
