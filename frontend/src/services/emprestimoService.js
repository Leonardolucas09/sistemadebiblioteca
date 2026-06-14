import { api } from './api.js'

export async function listarEmprestimos(filtros = {}) {
    const response = await api.get('/emprestimos', { params: filtros })
    return response.data
}

export async function alugarLivro(livroId, usuarioId) {
    const response = await api.post('/emprestimos', { livroId, usuarioId })
    return response.data
}

export async function devolverEmprestimo(emprestimoId) {
    const response = await api.patch(`/emprestimos/${emprestimoId}/devolver`)
    return response.data
}
