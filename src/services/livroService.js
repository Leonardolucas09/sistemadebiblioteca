import { api } from './api.js'

export async function listarLivros() {
    const response = await api.get('/livros/todos')
    
    return response.data
}

export async function cadastrarLivro(livro) {
    const response = await api.post('/livros/cadastrar', livro)
    return response.data
}