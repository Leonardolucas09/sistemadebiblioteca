<script setup>
import { onMounted, ref } from 'vue'
import bookByteLogo from '../assets/bookByte_logo.png'
import userIcon from '../assets/avatarGenerico.png'
import { buscarUsuario } from '../services/usuarioService.js'

const nomeUsuario = ref('Usuario')

onMounted(async () => {
    try {
        const usuario = await buscarUsuario()
        nomeUsuario.value = usuario.nome || nomeUsuario.value
    } catch (error) {
        console.error(error)
    }
})
</script>

<template>
    <header class="flex p-7 justify-between items-center">
        <span>
            <img :src="bookByteLogo" alt="BookByte Logo" class="logo" />
        </span>

        <div class="flex justify-end items-center gap-4">
            <h2 class="text-white item-center font-semibold text-lg">
                Ola, {{ nomeUsuario }}
            </h2>
            <img :src="userIcon" alt="User Icon" class="user-icon" />
        </div>
    </header>
</template>
