package br.unisinos.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.unisinos.library.dto.LivroRequestDTO;
import br.unisinos.library.dto.LivroResponseDTO;
import br.unisinos.library.entity.Autor;
import br.unisinos.library.entity.Categoria;
import br.unisinos.library.entity.Livro;
import br.unisinos.library.exception.RecursoNaoEncontradoException;
import br.unisinos.library.exception.RegraLibraryException;
import br.unisinos.library.repository.AutorRepository;
import br.unisinos.library.repository.CategoriaRepository;
import br.unisinos.library.repository.LivroPorCategoriaProjection;
import br.unisinos.library.repository.LivroRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final CategoriaRepository categoriaRepository;
    private final AutorRepository autorRepository;

    public LivroResponseDTO salvar (LivroRequestDTO livroDTO) {
        Categoria categoria = buscarCategoria(livroDTO.categoriaId());
        Autor autor = buscarOuCriarAutor(livroDTO);
        Livro livro = Livro.builder()
                        .titulo(livroDTO.titulo())
                        .editora(livroDTO.editora())
                        .numeroPaginas(livroDTO.numeroPaginas())
                        .isbn(livroDTO.isbn())
                        .nomeAutor(autor.getNome())
                        .autor(autor)
                        .categoria(categoria)
                        .build();
        return toResponseDTO(livroRepository.save(livro));
    }

    private LivroResponseDTO toResponseDTO(Livro livro) {
        return new LivroResponseDTO(
            livro.getId(),
            livro.getTitulo(),
            livro.getEditora(),
            livro.getNumeroPaginas(),
            livro.getIsbn(),
            livro.getAutor() != null ? livro.getAutor().getId() : null,
            livro.getAutor() != null ? livro.getAutor().getNome() : null,
            livro.getCategoria() != null ? livro.getCategoria().getId() : null,
            livro.getCategoria() != null ? livro.getCategoria().getNome() : null);
    }
    
    public Livro atualizar(Long id, Livro livroAtualizado) {
        Livro livro = livroRepository.findById(id)
        .orElseThrow(() -> new RegraLibraryException("Livro não encontrado"));

        livro.setTitulo(livroAtualizado.getTitulo());
        livro.setEditora(livroAtualizado.getEditora());
        livro.setNumeroPaginas(livroAtualizado.getNumeroPaginas());
        livro.setIsbn(livroAtualizado.getIsbn());
        livro.setNomeAutor(livroAtualizado.getNomeAutor());

        return livroRepository.save(livro);
    }

    private Categoria buscarCategoria(Long categoriaId) {
        if (categoriaId == null) {
            throw new RegraLibraryException("A categoria do exemplar é obrigatória"); 
        }
        return categoriaRepository.findById(categoriaId).orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada com o id: " + categoriaId));
    }

    private Autor buscarAutor(Long autorId) {
        if (autorId == null) {
            throw new RegraLibraryException("O autor do exemplar é obrigatório"); 
        }
        return autorRepository.findById(autorId).orElseThrow(() -> new RecursoNaoEncontradoException("Autor não encontrado com o nome: " + autorId));
    }


    private Autor buscarOuCriarAutor(LivroRequestDTO livroDTO) {
        if (livroDTO.autorId() != null) {
            return buscarAutor(livroDTO.autorId());
        }

        if (livroDTO.autorNome() == null || livroDTO.autorNome().isBlank()) {
            throw new RegraLibraryException("O autor do exemplar e obrigatorio");
        }

        String nomeAutor = livroDTO.autorNome().trim();

        return autorRepository.findByNome(nomeAutor)
            .orElseGet(() -> autorRepository.save(Autor.builder().nome(nomeAutor).build()));
    }

    public List<LivroResponseDTO> listarTodos() {
        return livroRepository.findAll()
        .stream()
        .map(this::toResponseDTO)
        .toList();
    }

    public Optional<Livro> buscarPorId(Long id) {
        return livroRepository.findById(id);
    }

    public void excluir(Long id) {
        livroRepository.deleteById(id);
    }

    public List<Livro> buscar(String nome, Long categoriaId) {
        return livroRepository.buscarComFiltros(nome, categoriaId);
    }

    public List<LivroPorCategoriaProjection> relatorioLivrosPorCategoria() {
        return livroRepository.relatorioLivrosPorCategoria();
    }

    // Estou comentando essa parte do código pois mudei consideravelmente o jeito que funciona os autores do projeto.
    // Agora, Autor é uma classe, não apenas um parâmetro de texto

    // public List<LivroPorAutorProjection> relatorioLivrosPorAutor() {
    //     return livroRepository.relatorioLivrosPorAutor();
    // }


    // public List<Livro> buscarPorAutor(String nomeAutor) {
    //     return livroRepository.findByNomeAutorContainingIgnoreCase(nomeAutor);
    // }

}
