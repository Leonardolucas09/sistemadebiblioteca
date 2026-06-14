package br.unisinos.library.entity;

import java.util.ArrayList;
import java.util.List;

// import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//especifica que a classe Livro representa uma entidade no banco de dados
@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Livro {
    
    //especifica que o Long id é uma chave identificadora (PK)
    @Id
    //especifica que a chave identificadora (PK) vai ser um número sequencial 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //especifica que nome é uma coluna da tabela, que não pode ser nulo e de tamanho máximo = 100)
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String titulo;

    //ainda não sei se vou deixar a editora como nullable=false, pois alguns livros não fornecem informações de edição
    @NotBlank
    @Column(nullable = false, length = 50)
    private String editora;

    //alguns livros não fornecem número de páginas nem exibem o número nas páginas, por isso essa variável é uma String (caso essas informações não existam, 
    //botar "Não paginado")
    @NotBlank
    @Column(nullable = false, length = 15)
    private String numeroPaginas;


    //código universal do livro, alguns livros (especialmente os mais antigos) não fornecem nem o código de barras nem o número redigido
    @Column(nullable = true, length = 13)
    private Long isbn;

    @Column(name = "nome_autor", nullable = false, length = 100)
    private String nomeAutor;

    //chave estrangeira da categoria
    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = true, foreignKey = @ForeignKey(name = "fk_livro_categoria"))
    @JsonBackReference
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = true, foreignKey = @ForeignKey(name = "fk_livro_autor"))
    @JsonBackReference
    private Autor autor;

    @OneToMany(mappedBy = "livro")
    private List<Emprestimo> emprestimos;
}
