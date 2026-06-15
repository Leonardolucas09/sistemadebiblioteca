# Sistema de Gerenciamento de Biblioteca

## Integrantes do Grupo

- *[Lucas Ávila Kautzmann]*
- *[Leonardo Lucas de Oliveira]*
- *[Isabella Curcio de Oliveira]*
---

## Descrição do Projeto

O *Sistema de Gerenciamento de Biblioteca* é uma aplicação web desenvolvida para gerenciamento do acervo de uma biblioteca, permitindo o controle de livros, categorias, autores, usuários e empréstimos.

O sistema foi desenvolvido seguindo uma *arquitetura em camadas*, separando responsabilidades entre:

- *Frontend (Vue 3 + Vite)*: interface do usuário e interação visual
- *Backend (Spring Boot)*: regras de negócio e API RESTful
- *Banco de Dados (PostgreSQL)*: persistência dos dados

Entre as principais funcionalidades do sistema estão:

-  *Gestão de Livros*: cadastrar, consultar, atualizar e remover livros
-  *Gestão de Categorias*: organizar livros por categoria
-  *Gestão de Autores*: registrar e manter autores
-  *Controle de Empréstimos*: registrar empréstimos e devoluções

A comunicação entre frontend e backend ocorre através de uma *API RESTful*, utilizando requisições HTTP (GET, POST, PUT, DELETE) para manipulação dos dados.
---

## Instruções de Execução

### Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- *Java 17* ou superior
- *Node.js 18* ou superior
- *PostgreSQL 12* ou superior
- *Maven 3.6* ou superior (incluído no projeto)

### Backend

#### 1. Executar o Backend

```bash
# Navegue até a pasta do backend
cd backend

# Compile o projeto
./mvnw clean install

# Inicie o servidor
./mvnw spring-boot:run


Ou se preferir, para caso tenha a extensão *Spring Boot Dashboard*, pode fazer a inicialização através dela.

O backend estará disponível em http://localhost:8082

*Nota*: O arquivo application.properties contém as configurações padrão:

- *URL do banco*: jdbc:postgresql://localhost:5432/biblioteca
- *Usuário*: postgres
- *Senha*: postgres
- *Porta do backend*: 8082

Caso necessário, essas configurações podem ser alteradas no arquivo:

backend/src/main/resources/application.properties

### Frontend

#### 1. Instalar Dependências

```bash
# Navegue até a pasta do frontend
cd frontend

# Instale as dependências
npm install
```

#### 2. Configurar a URL do Backend

A URL base do backend é definida pela variável de ambiente **`VITE_API_URL`**. 

Para usar a URL padrão (`http://localhost:8082`), nenhuma configuração adicional é necessária. Se desejar customizar:

```bash
# No Windows (PowerShell)
$env:VITE_API_URL="http://seu-backend:porta"
npm run dev

# No Linux/Mac
export VITE_API_URL="http://seu-backend:porta"
npm run dev
```

Ou crie um arquivo `.env.local` na pasta `frontend`:

```env
VITE_API_URL=http://localhost:8082
```

#### 3. Iniciar o Servidor de Desenvolvimento

```bash
# No diretório frontend
npm run dev
```

A aplicação estará disponível em `http://localhost:5173`

#### 4. Build para Produção

```bash
npm run build
```

Os arquivos otimizados serão gerados na pasta `dist/`

---

## Rotas e Telas do Frontend

O frontend implementa um sistema de roteamento simples com as seguintes telas:

| Rota | Tela | Descrição |
|------|------|-----------|
| / ou "" | *Tabela de Livros* | Exibe a lista de todos os livros do acervo com opções de filtro e busca |
| /emprestimos | *Empréstimos* | Gerencia empréstimos e devoluções de livros, exibindo histórico e status dos empréstimos |

### Componentes Principais

- *BarraLateral*: Menu lateral de navegação entre as telas
- *Filtros*: Componente de filtro para buscar livros por categoria ou editora
- *InputBuscarLivros*: Campo de busca de livros por título, autor ou o isbn do livro
- *AdicionarLivro*: Modal para adicionar novos livros ao acervo
- *ModalAdicionarLivro*: Modal para adicionar/editar livros

---

## Bibliotecas Adicionais do Frontend

O projeto utiliza as seguintes bibliotecas adicionais no ecossistema Vue:

| Biblioteca | Versão | Propósito |
|-----------|--------|----------|
| *Axios* | ^1.17.0 | Cliente HTTP para requisições ao backend. Facilita a comunicação com a API REST com suporte a interceptadores e cancelamento de requisições |
| *Tailwind CSS* | ^4.3.0 | Framework CSS utilitário para estilização ágil e responsiva da interface. Proporciona componentes estilizados e temas customizáveis |
| *Tailwind CSS Vite Plugin* | ^4.3.0 | Plugin do Vite para integração otimizada do Tailwind CSS, melhorando o build e a experiência de desenvolvimento |

### Justificativa

- *Axios*: Simplifica chamadas HTTP e oferece melhor tratamento de erros em comparação com fetch nativo
- *Tailwind CSS*: Reduz tempo de desenvolvimento com classes utilitárias prontas, melhorando a consistência visual e responsividade
- *Tailwind Vite Plugin*: Otimiza o bundle size durante a produção, removendo estilos não utilizados

---

## 🔌 Endpoints da API (Backend)

### Livros
- GET /api/livros/{id} - Obter detalhes de um livro específico
- GET /api/livros/todos - Listar todos os livros
- GET /api/livros/relatorio/por-categoria - Relatório de livros por categoria
- POST /api/livros - Criar novo livro
- PUT /api/livros/{id} - Atualizar informações do livro

### Categorias
- GET /api/categorias - Listar todas as categorias
- GET /api/categorias/{id} - Obter detalhes de uma categoria
- POST /api/categorias - Criar nova categoria
- PUT /api/categorias/{id} - Atualizar categoria
- DELETE /api/categorias/{id} - Deletar categoria

### Empréstimos
- GET /api/emprestimos - Listar todos os empréstimos
- GET /api/emprestimos/usuario/{usuarioId} - Listar empréstimos de um usuário específico
- POST /api/emprestimos - Registrar novo empréstimo

### Usuários
- GET /api/usuarios - Listar usuários
- POST /api/usuarios - Criar novo usuário
- PUT /api/usuarios/{id} - Atualizar perfil do usuário

### Autores
- GET /api/autores - Listar autores
- POST /api/autores - Criar novo autor
- PUT /api/autores/{id} - Atualizar autor
- DELETE /api/autores/{id} - Deletar autor

---

## Stack Tecnológico

### Backend
- *Spring Boot* 3.5.13
- *Java* 17
- *PostgreSQL* 12+
- *Lombok* (para redução de boilerplate)
- *Validation* (validação de dados)
- *JPA/Hibernate* (ORM)

### Frontend
- *Vue.js* 3.5.34
- *Vite* 8.0.12
- *Tailwind CSS* 4.3.0
- *Axios* 1.17.0
- *Node.js* 18+

---

## [Apresentação em Vídeo](https://youtu.be/ahVCcjiALtk)

---

## Notas Adicionais

- O CORS está configurado para permitir requisições do frontend
- O banco de dados é automaticamente criado/atualizado com base nas entidades JPA (DDL automático)
- O projeto suporta live reload tanto no backend (via Spring DevTools) quanto no frontend (via Vite)

## Considerações Finais

Este projeto foi desenvolvido com o objetivo de aplicar conceitos de desenvolvimento full stack, incluindo arquitetura RESTful, integração frontend-backend, persistência de dados, componentização e boas práticas de desenvolvimento de software.
---

