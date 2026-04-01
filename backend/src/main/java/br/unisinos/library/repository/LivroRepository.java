package br.unisinos.library.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.unisinos.library.entity.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query(
        """
            select l
            from Livro l
            where (:titulo is null or lower(l.titulo) like lower(concat('%', :titulo, '%')))
            and (:categoriaId is null or l.categoria.id = :categoriaId)    
        """
    )
    Page<Livro> buscarComFiltros (String titulo, Long categoriaId, Pageable pageable);

    @Query(
        """
            select c.nome as categoria, count(l) as quantidade
            from Livro l
            join l.categoria c
            group by c.nome
        """)
    List<LivroPorCategoriaProjection> relatorioLivrosPorCategoria();

}
