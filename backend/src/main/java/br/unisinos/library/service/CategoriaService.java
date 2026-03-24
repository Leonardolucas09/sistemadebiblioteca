package br.unisinos.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.unisinos.library.entity.Categoria;
import br.unisinos.library.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    
    private final CategoriaRepository categoriaRepository;

    public Categoria salvar (Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Optional <Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public Categoria atualizar (Long id, Categoria categoriaAtualizada) {
        Categoria categoria = buscarPorId(id).orElseThrow(() -> new RuntimeException("Categoria não encontrada pelo id " + id));
        categoria.setNome(categoriaAtualizada.getNome());
        return categoriaRepository.save(categoria);
    }

    public void excluir (Long id) {
        Categoria categoria = buscarPorId(id).orElseThrow(() -> new RuntimeException("Categoria não encontrada pelo id " + id));
        categoriaRepository.delete(categoria);
    }


}
