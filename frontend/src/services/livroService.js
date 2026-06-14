import { api } from './api.js'

export async function listarLivros() {
    const response = await api.get('/livros/todos')
    return response.data
}

export async function listarEditoras() {
    const livros = await listarLivros()
    return [...new Set(livros.map((livro) => livro.editora).filter(Boolean))].sort()
}

export async function cadastrarLivro(livro) {
    const response = await api.post('/livros', livro)
    return response.data
}

export async function alugarLivro(livroId, usuarioId) {
    const response = await api.post('/emprestimos', { livroId, usuarioId })
    return response.data
}

export async function devolverLivro(emprestimoId) {
    const response = await api.patch(`/emprestimos/${emprestimoId}/devolver`)
    return response.data
}
