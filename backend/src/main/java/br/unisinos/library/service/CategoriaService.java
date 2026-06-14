package br.unisinos.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.unisinos.library.dto.CategoriaRequestDTO;
import br.unisinos.library.dto.CategoriaResponseDTO;
import br.unisinos.library.entity.Categoria;
import br.unisinos.library.exception.RecursoNaoEncontradoException;
import br.unisinos.library.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    
    private final CategoriaRepository categoriaRepository;

    public CategoriaResponseDTO salvar (CategoriaRequestDTO categoriaDTO) {
        Categoria categoria = Categoria.builder()
                                        .nome(categoriaDTO.nome())
                                        .build();

        return toResponseDTO(categoriaRepository.save(categoria));
    }

    private CategoriaResponseDTO toResponseDTO(Categoria categoria) {
        return new CategoriaResponseDTO(
            categoria.getId(),
            categoria.getNome());
    }

    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaRepository.findAll()
            .stream()
            .map(this::toResponseDTO)
            .toList();
    }

    public Optional <Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public Categoria atualizar (Long id, Categoria categoriaAtualizada) {
        Categoria categoria = buscarPorId(id).orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada pelo id " + id));
        categoria.setNome(categoriaAtualizada.getNome());
        return categoriaRepository.save(categoria);
    }

    public void excluir (Long id) {
        Categoria categoria = buscarPorId(id).orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada pelo id " + id));
        categoriaRepository.delete(categoria);
    }


}
