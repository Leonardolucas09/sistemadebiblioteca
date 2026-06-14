<script setup>
import { onMounted, ref } from 'vue'
import ModalNovaSenha from '../Models/ModalNovaSenha.vue'
import Cabecalho from '../components/Cabecalho.vue'
import BarraLateral from '../components/BarraLateral.vue'
import { buscarPerfilUsuario } from '../services/usuarioService.js'

defineEmits(['navigate'])

const sidebarAberta = ref(false)
const modalRef = ref(null)
const perfil = ref(null)
const carregando = ref(false)
const erro = ref('')

const toggleSidebar = () => {
    sidebarAberta.value = !sidebarAberta.value
}

const fecharSidebar = () => {
    sidebarAberta.value = false
}

const abrirModal = () => {
    modalRef.value.openModal()
}

function formatarData(data) {
    if (!data) return '-'
    return new Date(`${data}T00:00:00`).toLocaleDateString('pt-BR')
}

async function carregarPerfil() {
    carregando.value = true
    erro.value = ''

    try {
        perfil.value = await buscarPerfilUsuario()
    } catch (error) {
        erro.value = 'Erro ao carregar o perfil.'
        console.error(error)
    } finally {
        carregando.value = false
    }
}

onMounted(() => {
    carregarPerfil()
})
</script>

<template>
    <Cabecalho />
    <BarraLateral
        :isUsable="false"
        :isOpen="sidebarAberta"
        @toggle="toggleSidebar"
        @close="fecharSidebar"
        @navigate="($event) => $emit('navigate', $event)"
    />

    <div class="flex flex-col gap-6">
        <h2 class="text-3xl text-white font-bold mt-3 text-center">
            Meu Perfil
        </h2>

        <div class="bg-gray-900 mx-140 p-6 rounded-lg ">
            <div class="flex justify-between items-center mb-6">
                <div>
                    <h2 class="text-2xl text-white font-bold mb-4">
                        Informacoes Pessoais
                    </h2>
                    <p class="text-white mb-2">Nome: {{ perfil?.nome || '-' }}</p>
                </div>

                <div>
                    <h2 class="text-2xl text-white font-bold mb-4">
                        Ultimos Emprestimos
                    </h2>
                    <p v-if="carregando" class="text-white mb-2">Carregando...</p>
                    <p v-else-if="erro" class="text-red-500 mb-2">{{ erro }}</p>
                    <template v-else>
                        <p class="text-white mb-2">Livro: {{ perfil?.ultimoEmprestimo?.tituloLivro || '-' }}</p>
                        <p class="text-white mb-2">
                            Data de Devolucao:
                            {{ formatarData(perfil?.ultimoEmprestimo?.dataPrevistaDevolucao) }}
                        </p>
                    </template>
                </div>
            </div>

            <!-- <a @click="abrirModal" class="inline text-blue-500 hover:underline cursor-pointer">
                <span>Deseja alterar a sua senha?</span>
            </a> -->
        </div>
        <!-- <ModalNovaSenha ref="modalRef" /> -->
    </div>
</template>
