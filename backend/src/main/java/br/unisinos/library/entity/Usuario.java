package br.unisinos.library.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Usuario {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "O nome do(a) usuário(a) é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;

    @OneToMany(mappedBy = "usuario")
    private List<Emprestimo> emprestimos;
}
