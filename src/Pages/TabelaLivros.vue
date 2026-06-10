<script setup>
import { onMounted, ref } from 'vue'
import Cabecalho from '../components/Cabecalho.vue'
import BarraLateral from '../components/BarraLateral.vue'
import ModalAdicionarLivro from '../Models/ModalAdicionarLivro.vue';
import { listarLivros } from '../services/livroService.js';

defineProps({
    livro: {
        type: Object,
        required: true
    }
});

defineEmits(['navigate']);

const sidebarAberta = ref(false)

const toggleSidebar = () => {
    sidebarAberta.value = !sidebarAberta.value;
};

const fecharSidebar = () => {
    sidebarAberta.value = false;
};

const livros = ref([])
const carregando = ref(false)
const erro = ref('')

async function carregarLivros() {
    carregando.value = true
    erro.value = ''

    try {
        livros.value = await listarLivros()
    } catch (error) {
        erro.value = 'Erro ao carregar os livros. Por favor. Verifique se o backend está no ar.'
        console.error(error)
    } finally {
        carregando.value = false
    }
}

const livrosLista = ref(null)

function atualizarLivros() {
    livrosLista.value?.carregarLivros()
}

onMounted(() => {
    carregarLivros()
})

</script>

<template>
    <Cabecalho />
    <BarraLateral :isOpen="sidebarAberta" :isUsable="true" @toggle="toggleSidebar" @close="fecharSidebar"
        @navigate="($event) => $emit('navigate', $event)" />

        
    <section>
        <h2 class="text-3xl text-white font-bold mt-3 text-center">
            Tabela de Livros
        </h2>
        <button type="button" @click="carregarLivros"
            class="block mx-auto mt-4 px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition">
            Recarregar Livros
        </button>
    </section>    

    <p v-if="carregando" class="text-center text-white mt-4">
        Carregando livros...
    </p>
    <p v-else-if="erro" class="text-center text-red-500 mt-4">
        {{ erro }}
    </p>
    <p v-else-if="livros.length === 0" class="text-center text-white mt-4">
        Nenhum livro encontrado.
    </p>
    

    <div class="overflow-x-auto rounded-2xl" style="max-height: 70vh;">
        <table class="table-auto border-collapse border border-white rounded-md text-white mx-auto mt-10">
            <thead class="sticky top-0 bg-black">
                <tr class="uppercase border-white">
                    <th class="linha">ID</th>
                    <th class="linha">Título</th>
                    <th class="linha">Autor</th>
                    <th class="linha">Editora</th>
                    <th class="linha">Páginas</th>
                    <th class="linha">Data de Lançamento</th>
                    <th class="linha">Disponível</th>
                    <th class="linha">Categoria</th>
                </tr>
            </thead>

            <tbody v-if="!carregando && !erro && livros.length > 0">
                <tr v-for="livro in livros" :key="livro.id" class="hover:bg-zinc-800 transition">
                    <td class="linha">{{ livro.id }}</td>
                    <td class="linha">{{ livro.titulo }}</td>
                    <td class="linha">{{ livro.autor }}</td>
                    <td class="linha">{{ livro.editora }}</td>
                    <td class="linha">{{ livro.paginas }}</td>
                    <td class="linha">{{ livro.data }}</td>
                    <td class="linha">{{ livro.disponivel ? 'Sim' : 'Não' }}</td>
                    <td class="linha">{{ livro.categoria }}</td>
                </tr>
            </tbody>
        </table>
    </div>

    <ModalAdicionarLivro @livro-cadastrado="atualizarLivro"/>
</template>

<style>
.linha {
    padding: 24px;
}
</style>