import { api } from './api.js'

export async function listarCategorias() {
    const response = await api.get('/categorias')
    return response.data.map((categoria) => ({
        id: categoria.id,
        nome: categoria.nome,
    }))
}
