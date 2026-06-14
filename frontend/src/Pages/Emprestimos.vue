<script setup>
import { computed, onMounted, ref } from 'vue'
import Cabecalho from '../components/Cabecalho.vue'
import BarraLateral from '../components/BarraLateral.vue'
import { listarCategorias } from '../services/categoriaService.js'
import { listarLivros, listarEditoras } from '../services/livroService.js'
import { alugarLivro, devolverEmprestimo, listarEmprestimos } from '../services/emprestimoService.js'
import { getUsuarioAtualId } from '../services/usuarioService.js'

defineEmits(['navigate'])

const livroAberto = ref(null)
const sidebarAberta = ref(false)
const livros = ref([])
const emprestimos = ref([])
const categorias = ref([])
const editoras = ref([])
const carregando = ref(false)
const salvandoId = ref(null)
const erro = ref('')
const usuarioPorLivro = ref({})
const busca = ref('')
const categoriaId = ref('')
const editora = ref('')

const toggleSidebar = () => {
    sidebarAberta.value = !sidebarAberta.value
}

const fecharSidebar = () => {
    sidebarAberta.value = false
}

function abrirLivro(id) {
    livroAberto.value = livroAberto.value === id ? null : id
}

function formatarData(data) {
    if (!data) return '-'
    return new Date(`${data}T00:00:00`).toLocaleDateString('pt-BR')
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
            emprestimoAtivo,
            isAlugado: Boolean(emprestimoAtivo),
            nomeUsuario: emprestimoAtivo?.nomeUsuario,
            usuarioId: emprestimoAtivo?.usuarioId,
            dataEmprestimo: emprestimoAtivo?.dataEmprestimo,
            dataDevolucao: emprestimoAtivo?.dataPrevistaDevolucao,
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

async function carregarDados() {
    carregando.value = true
    erro.value = ''

    try {
        const [livrosData, categoriasData, editorasData] = await Promise.all([
            listarLivros(),
            listarCategorias(),
            listarEditoras(),
        ])

        livros.value = livrosData
        categorias.value = categoriasData
        editoras.value = editorasData

        try {
            emprestimos.value = await listarEmprestimos()
        } catch (error) {
            emprestimos.value = []
            erro.value = 'Nao foi possivel carregar os emprestimos. Os livros foram exibidos como disponiveis.'
            console.error(error)
        }
    } catch (error) {
        erro.value = 'Erro ao carregar os dados. Verifique se o backend esta no ar.'
        console.error(error)
    } finally {
        carregando.value = false
    }
}

async function alugar(livro) {
    const usuarioId = Number(usuarioPorLivro.value[livro.id] || getUsuarioAtualId())

    if (!usuarioId) {
        erro.value = 'Informe o ID do usuario antes de alugar.'
        return
    }

    salvandoId.value = livro.id
    erro.value = ''

    try {
        await alugarLivro(livro.id, usuarioId)
        await carregarDados()
    } catch (error) {
        erro.value = error.response?.data?.message || error.response?.data || 'Erro ao alugar o livro.'
        console.error(error)
    } finally {
        salvandoId.value = null
    }
}

async function devolver(livro) {
    if (!livro.emprestimoAtivo?.id) return

    salvandoId.value = livro.id
    erro.value = ''

    try {
        await devolverEmprestimo(livro.emprestimoAtivo.id)
        await carregarDados()
    } catch (error) {
        erro.value = error.response?.data?.message || error.response?.data || 'Erro ao devolver o livro.'
        console.error(error)
    } finally {
        salvandoId.value = null
    }
}

onMounted(() => {
    carregarDados()
})
</script>

<template>
    <Cabecalho />
    <BarraLateral
        v-model:busca="busca"
        v-model:categoria-id="categoriaId"
        v-model:editora="editora"
        :isOpen="sidebarAberta"
        :isUsable="true"
        :mostrarAdicionar="false"
        :categorias="categorias"
        :editoras="editoras"
        @toggle="toggleSidebar"
        @close="fecharSidebar"
        @navigate="($event) => $emit('navigate', $event)"
    />

    <section>
        <h2 class="text-3xl text-white font-bold mt-3 text-center">
            Emprestimos
        </h2>

        <button type="button" @click="carregarDados"
            class="block mx-auto mt-4 px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition">
            Recarregar
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
                <tr class="uppercase border-b border-white">
                    <th class="linha">ID</th>
                    <th class="linha">ISBN</th>
                    <th class="linha">Titulo</th>
                    <th class="linha">Autor</th>
                    <th class="linha">Categoria</th>
                    <th class="linha">Editora</th>
                    <th class="linha">Status</th>
                    <th class="linha">Emprestimo</th>
                    <th class="linha">Devolucao</th>
                    <th class="linha">Usuario</th>
                </tr>
            </thead>

            <tbody v-if="!carregando && livrosFiltrados.length > 0">
                <template v-for="livro in livrosFiltrados" :key="livro.id">
                    <tr
                        @click="abrirLivro(livro.id)"
                        class="cursor-pointer hover:bg-zinc-800 transition"
                    >
                        <td class="linha">{{ livro.id }}</td>
                        <td class="linha">{{ livro.isbn || '-' }}</td>
                        <td class="linha">{{ livro.titulo }}</td>
                        <td class="linha">{{ livro.nomeAutor || '-' }}</td>
                        <td class="linha">{{ livro.nomeCategoria || '-' }}</td>
                        <td class="linha">{{ livro.editora || '-' }}</td>
                        <td class="linha">{{ livro.isAlugado ? 'Alugado' : 'Disponivel' }}</td>
                        <td class="linha">{{ formatarData(livro.dataEmprestimo) }}</td>
                        <td class="linha">{{ formatarData(livro.dataDevolucao) }}</td>
                        <td class="linha">{{ livro.nomeUsuario || '-' }}</td>
                    </tr>

                    <tr v-if="livroAberto === livro.id" class="bg-zinc-900">
                        <td colspan="10" class="p-4">
                            <div class="flex flex-wrap items-center gap-4">
                                <span class="font-bold">{{ livro.titulo }}</span>

                                <template v-if="livro.isAlugado">
                                    <span class="text-zinc-400">
                                        Alugado por {{ livro.nomeUsuario || `usuario ${livro.usuarioId}` }}
                                        ate {{ formatarData(livro.dataDevolucao) }}
                                    </span>
                                    <button
                                        type="button"
                                        @click.stop="devolver(livro)"
                                        :disabled="salvandoId === livro.id"
                                        class="font-bold text-zinc-900 bg-yellow-500 p-2 rounded-md disabled:opacity-60">
                                        {{ salvandoId === livro.id ? 'Salvando...' : 'Devolver' }}
                                    </button>
                                </template>

                                <template v-else>
                                    <input
                                        type="number"
                                        v-model="usuarioPorLivro[livro.id]"
                                        placeholder="ID do usuario atual"
                                        class="text-white bg-gray-700 p-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
                                    />
                                    <button
                                        type="button"
                                        @click.stop="alugar(livro)"
                                        :disabled="salvandoId === livro.id"
                                        class="font-bold text-white bg-blue-500 p-2 rounded-md disabled:opacity-60">
                                        {{ salvandoId === livro.id ? 'Salvando...' : 'Alugar' }}
                                    </button>
                                </template>
                            </div>
                        </td>
                    </tr>
                </template>
            </tbody>
        </table>
    </div>
</template>

<style>
.tabela-biblioteca {
    table-layout: fixed;
    width: min(1500px, calc(100vw - 64px));
}

.linha {
    padding: 16px;
    width: 130px;
    max-width: 130px;
    min-height: 64px;
    vertical-align: middle;
    white-space: normal;
    overflow-wrap: anywhere;
    word-break: break-word;
}

.linha:nth-child(1) {
    width: 64px;
    max-width: 64px;
}

.linha:nth-child(2),
.linha:nth-child(7) {
    width: 120px;
    max-width: 120px;
}

.linha:nth-child(3) {
    width: 230px;
    max-width: 230px;
}

.linha:nth-child(8),
.linha:nth-child(9) {
    width: 125px;
    max-width: 125px;
}
</style>
