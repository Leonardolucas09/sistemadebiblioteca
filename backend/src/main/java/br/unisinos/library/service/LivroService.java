package br.unisinos.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.unisinos.library.entity.Categoria;
import br.unisinos.library.entity.Livro;
import br.unisinos.library.exception.RegraLibraryException;
import br.unisinos.library.repository.CategoriaRepository;
import br.unisinos.library.repository.LivroPorCategoriaProjection;
import br.unisinos.library.repository.LivroRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final CategoriaRepository categoriaRepository;

    public Livro salvar(Livro livro) {

        if (livro.getCategoria() == null || livro.getCategoria().getId() == null) {
            throw new RegraLibraryException("Categoria é obrigatória");
        }
            Categoria categoria = categoriaRepository.findById(livro.getCategoria().getId()).orElseThrow(() -> new RegraLibraryException("Categoria não encontrada com id " + livro.getCategoria().getId()));
        
            livro.setCategoria(categoria);
            return livroRepository.save(livro);
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

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Optional<Livro> buscarPorId(Long id) {
        return livroRepository.findById(id);
    }

    public void excluir(Long id) {
        livroRepository.deleteById(id);
    }

    public Page<Livro> buscar(String nome, Long categoriaId, Pageable pageable) {
        return livroRepository.buscarComFiltros(nome, categoriaId, pageable);
    }

    public List<LivroPorCategoriaProjection> relatorioLivrosPorCategoria() {
        return livroRepository.relatorioLivrosPorCategoria();
    }



}
