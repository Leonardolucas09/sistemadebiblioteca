<script setup>
import { computed, onMounted, ref } from 'vue'
import BarraLateral from '../components/BarraLateral.vue'
import { listarCategorias } from '../services/categoriaService.js'
import { listarEmprestimos } from '../services/emprestimoService.js'
import { listarEditoras, listarLivros } from '../services/livroService.js'

defineEmits(['navigate'])

const sidebarAberta = ref(false)
const livros = ref([])
const emprestimos = ref([])
const categorias = ref([])
const editoras = ref([])
const carregando = ref(false)
const erro = ref('')
const busca = ref('')
const categoriaId = ref('')
const editora = ref('')

const toggleSidebar = () => {
    sidebarAberta.value = !sidebarAberta.value
}

const fecharSidebar = () => {
    sidebarAberta.value = false
}

async function carregarLivros() {
    carregando.value = true
    erro.value = ''

    try {
        const livrosData = await listarLivros()
        livros.value = livrosData

        try {
            emprestimos.value = await listarEmprestimos()
        } catch (error) {
            emprestimos.value = []
            erro.value = 'Nao foi possivel carregar os emprestimos. Status e devolucao podem ficar indisponiveis.'
            console.error(error)
        }
    } catch (error) {
        erro.value = 'Erro ao carregar os livros. Verifique se o backend esta no ar.'
        console.error(error)
    } finally {
        carregando.value = false
    }
}

async function carregarFiltros() {
    try {
        const [categoriasData, editorasData] = await Promise.all([
            listarCategorias(),
            listarEditoras(),
        ])

        categorias.value = categoriasData
        editoras.value = editorasData
    } catch (error) {
        console.error(error)
    }
}

const emprestimosAtivosPorLivro = computed(() => {
    return emprestimos.value
        .filter((emprestimo) => !emprestimo.devolvido)
        .reduce((mapa, emprestimo) => {
            mapa[emprestimo.livroId] = emprestimo
            return mapa
        }, {})
})

const livrosComEmprestimo = computed(() => {
    return livros.value.map((livro) => {
        const emprestimoAtivo = emprestimosAtivosPorLivro.value[livro.id]

        return {
            ...livro,
            isAlugado: Boolean(emprestimoAtivo),
            dataDevolucao: emprestimoAtivo?.dataPrevistaDevolucao,
            nomeUsuario: emprestimoAtivo?.nomeUsuario,
        }
    })
})

const livrosFiltrados = computed(() => {
    const termoBusca = busca.value.trim().toLowerCase()
    const categoriaSelecionada = categoriaId.value ? Number(categoriaId.value) : null

    return livrosComEmprestimo.value.filter((livro) => {
        const atendeBusca = !termoBusca
            || livro.titulo?.toLowerCase().includes(termoBusca)
            || livro.nomeAutor?.toLowerCase().includes(termoBusca)
            || String(livro.isbn || '').includes(termoBusca)

        const atendeCategoria = !categoriaSelecionada || livro.categoriaId === categoriaSelecionada
        const atendeEditora = !editora.value || livro.editora === editora.value

        return atendeBusca && atendeCategoria && atendeEditora
    })
})

function formatarData(data) {
    if (!data) return '-'
    return new Date(`${data}T00:00:00`).toLocaleDateString('pt-BR')
}

onMounted(() => {
    carregarLivros()
    carregarFiltros()
})
</script>

<template>
    <BarraLateral
        v-model:busca="busca"
        v-model:categoria-id="categoriaId"
        v-model:editora="editora"
        :isOpen="sidebarAberta"
        :isUsable="true"
        :categorias="categorias"
        :editoras="editoras"
        @toggle="toggleSidebar"
        @close="fecharSidebar"
        @navigate="($event) => $emit('navigate', $event)"
        @livro-cadastrado="() => { carregarLivros(); carregarFiltros() }"
    />

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
    <p v-else-if="livrosFiltrados.length === 0" class="text-center text-white mt-4">
        Nenhum livro encontrado.
    </p>

    <div class="overflow-x-auto rounded-2xl" style="max-height: 70vh;">
        <table class="tabela-biblioteca border-collapse border border-white rounded-md text-white mx-auto mt-10">
            <thead class="sticky top-0 bg-black">
                <tr class="uppercase border-white">
                    <th class="linha">ID</th>
                    <th class="linha">Titulo</th>
                    <th class="linha">Autor</th>
                    <th class="linha">Editora</th>
                    <th class="linha">Paginas</th>
                    <th class="linha">Status</th>
                    <th class="linha">Devolucao</th>
                    <th class="linha">Usuario</th>
                    <th class="linha">Categoria</th>
                </tr>
            </thead>

            <tbody v-if="!carregando && !erro && livrosFiltrados.length > 0">
                <tr v-for="livro in livrosFiltrados" :key="livro.id" class="hover:bg-zinc-800 transition">
                    <td class="linha">{{ livro.id }}</td>
                    <td class="linha">{{ livro.titulo }}</td>
                    <td class="linha">{{ livro.nomeAutor || '-' }}</td>
                    <td class="linha">{{ livro.editora }}</td>
                    <td class="linha">{{ livro.numeroPaginas }}</td>
                    <td class="linha">{{ livro.isAlugado ? 'Alugado' : 'Disponivel' }}</td>
                    <td class="linha">{{ formatarData(livro.dataDevolucao) }}</td>
                    <td class="linha">{{ livro.nomeUsuario || '-' }}</td>
                    <td class="linha">{{ livro.nomeCategoria || '-' }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<style>
.tabela-biblioteca {
    table-layout: fixed;
    width: min(1400px, calc(100vw - 64px));
}

.linha {
    padding: 16px;
    width: 140px;
    max-width: 140px;
    min-height: 64px;
    vertical-align: middle;
    white-space: normal;
    overflow-wrap: anywhere;
    word-break: break-word;
}

.linha:nth-child(1) {
    width: 72px;
    max-width: 72px;
}

.linha:nth-child(2) {
    width: 240px;
    max-width: 240px;
}

.linha:nth-child(5),
.linha:nth-child(6) {
    width: 110px;
    max-width: 110px;
}
</style>
