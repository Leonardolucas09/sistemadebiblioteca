import { api } from './api.js'

const USUARIO_ATUAL_ID = 'usuarioAtualId'

export function getUsuarioAtualId() {
    return Number(localStorage.getItem(USUARIO_ATUAL_ID) || 1)
}

export function setUsuarioAtualId(usuarioId) {
    localStorage.setItem(USUARIO_ATUAL_ID, String(usuarioId))
}

export async function buscarUsuario(usuarioId = getUsuarioAtualId()) {
    const response = await api.get(`/usuarios/${usuarioId}/perfil`)
    return response.data
}

export async function buscarPerfilUsuario(usuarioId = getUsuarioAtualId()) {
    const response = await api.get(`/usuarios/${usuarioId}/perfil`)
    return response.data
}
