<script setup>
import { ref, onMounted } from 'vue'
import Cabecalho from '../components/Cabecalho.vue'
import BarraLateral from '../components/BarraLateral.vue'
import { listarLivros } from '../services/livroService.js';

defineProps({
    livro: {
        type: Object,
        required: true
    }
});

defineEmits(['navigate']);

const livroAberto = ref(null)


const sidebarAberta = ref(false);

const toggleSidebar = () => {
    sidebarAberta.value = !sidebarAberta.value;
};

const fecharSidebar = () => {
    sidebarAberta.value = false;
};

function abrirLivro(id) {
    livroAberto.value =
        livroAberto.value === id ? null : id
}

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
onMounted(() => {
    carregarLivros()
})
</script>

<template>
    <Cabecalho />
    <BarraLateral
        :isOpen="sidebarAberta"
        :isUsable="true"
        @toggle="toggleSidebar"
        @close="fecharSidebar"
        @navigate="($event) => $emit('navigate', $event)"

    />

    <section>
        <h2 class="text-3xl text-white font-bold mt-3 text-center">
            Empréstimos
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
                <tr class="uppercase border-b border-white">
                    <th class="linha">ID</th>
                    <th class="linha">Título</th>
                    <th class="linha">Autor</th>
                    <th class="linha">Editora</th>
                    <th class="linha">Páginas</th>
                    <th class="linha">Data de Lançamento</th>
                    <th class="linha">Em Estoque</th>
                    <th class="linha">Categoria</th>
                </tr>
            </thead>

            <tbody>
                <template v-for="livro in livros" :key="livro.id">
                    <tr
                        @click="abrirLivro(livro.id)"
                        class="cursor-pointer hover:bg-zinc-800 transition"
                    >
                        <td class="linha">{{ livro.id }}</td>
                        <td class="linha">{{ livro.titulo }}</td>
                        <td class="linha">{{ livro.autor }}</td>
                        <td class="linha">{{ livro.editora }}</td>
                        <td class="linha">{{ livro.paginas }}</td>
                        <td class="linha">{{ livro.data }}</td>
                        <td class="linha">{{ livro.estoque }}</td>
                        <td class="linha">{{ livro.categoria }}</td>
                    </tr>

                    <tr
                        v-if="livroAberto === livro.id"
                        class="bg-zinc-900"
                    >
                        <td colspan="8" class="p-4">
                            <h2 class="font-bold mb-3">
                                Cópias de {{ livro.titulo }}
                            </h2>

                            <div class="flex flex-col gap-3">
                                <label
                                    v-for="copia in livro.copias"
                                    :key="copia"
                                    class="flex items-center gap-2"
                                >
                                    <input v-if="false"
                                        type="checkbox"
                                        class="w-4 h-4"
                                        disabled=""
                                    />
                                    <input v-else
                                        type="checkbox"
                                        class="w-4 h-4"
                                        disabled=""
                                        checked
                                    />

                                    <div class="flex items-center">
                                        <div class="flex items-center gap-4">
                                            <span>{{ copia }}</span>
                                            <span> — </span>

                                            <div v-if="true" 
                                                class="text-base text-zinc-400 gap-4 flex items-center">
                                                Alugado em:
                                                <span class="font-bold underline">xx/yy/zzzz</span>
                                                <span> | </span>
                                                <span class="font-bold italic">Nome Pessoa</span>
                                            </div>
                                            <div v-else 
                                                class="text-base text-zinc-400 gap-4 flex items-center">
                                                <span class="font-bold italic">Disponível</span>
                                            </div>

                                            <button v-if="true" 
                                                class="font-bold text-zinc-700 bg-yellow-500 ml-10 p-2 rounded-md">
                                                Devolver
                                            </button>
                                            <button v-else 
                                                class="font-bold text-white bg-blue-500 ml-10 p-2 rounded-md">
                                                Alugar
                                            </button>

                                        </div>
                                    </div>
                                </label>
                            </div>
                        </td>
                    </tr>
                </template>
            </tbody>
        </table>
    </div>
</template>

<style>
.linha {
    padding: 24px;
}
</style>