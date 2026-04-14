package br.unisinos.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.unisinos.library.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(
        """
            select u
            from Usuario u
            where (lower(u.email) = lower(:email))
            and (lower(u.nome) = lower(:nome))
        """
    )
    Usuario buscarComFiltros (String email, String nome);
        
}
