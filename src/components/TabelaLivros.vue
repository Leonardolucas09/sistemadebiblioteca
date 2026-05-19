<script setup>
import { ref } from 'vue'

const livroAberto = ref(null)

const livros = [
    {
        id: 1,
        titulo: 'Duna',
        autor: 'Frank Herbert',
        editora: 'Intrinseca',
        paginas: 659,
        data: 1966,
        estoque: 'Sim',
        categoria: 'Livro',
        copias: [
            'Cópia 01',
            'Cópia 02',
            'Cópia 03'
        ]
    },

    {
        id: 2,
        titulo: 'Harry Potter e a Pedra Filosofal',
        autor: 'Jk Rowling',
        editora: 'Intrinseca',
        paginas: 101,
        data: 1982,
        estoque: 'Não',
        categoria: 'Livro',
        copias: [
            'Cópia A',
            'Cópia B'
        ]
    }
]

function abrirLivro(id) {
    livroAberto.value =
        livroAberto.value === id ? null : id
}
</script>

<template>
    <table
        class="table-auto border-collapse border border-white rounded-md text-white mx-auto mt-10"
    >
        <thead>
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
                                <input
                                    type="checkbox"
                                    class="w-4 h-4"
                                    disabled=""
                                />

                                <span>{{ copia }}</span>
                                — Alugado em <span>xx/yy/zzzz</span>
                                por <span>Nome Pessoa</span>
                                <button class="bg-green-500 p-2 rounded-md">
                                    Devolver
                                </button>
                            </label>
                        </div>
                    </td>
                </tr>
            </template>
        </tbody>
    </table>
</template>

<style>
.linha {
    padding: 24px;
}
</style>