<script setup>
import { ref } from 'vue'
import Cabecalho from '../components/Cabecalho.vue'
import BarraLateral from '../components/BarraLateral.vue'
import ModalAdicionarLivro from '../Models/ModalAdicionarLivro.vue';

defineEmits(['navigate']);

const sidebarAberta = ref(false)

const toggleSidebar = () => {
    sidebarAberta.value = !sidebarAberta.value;
};

const fecharSidebar = () => {
    sidebarAberta.value = false;
};

const livros = [
    {
        id: 1,
        titulo: 'Duna',
        autor: 'Frank Herbert',
        editora: 'Intrinseca',
        paginas: 659,
        data: 1966,
        estoque: '10',
        categoria: 'Livro',
    },

    {
        id: 2,
        titulo: 'Harry Potter e a Pedra Filosofal',
        autor: 'Jk Rowling',
        editora: 'Intrinseca',
        paginas: 101,
        data: 1982,
        estoque: '0',
        categoria: 'Livro',
    }, 
    {
        id: 3,
        titulo: 'Duna',
        autor: 'Frank Herbert',
        editora: 'Intrinseca',
        paginas: 659,
        data: 1966,
        estoque: '10',
        categoria: 'Livro',
    },

    {
        id: 4,
        titulo: 'Harry Potter e a Pedra Filosofal',
        autor: 'Jk Rowling',
        editora: 'Intrinseca',
        paginas: 101,
        data: 1982,
        estoque: '0',
        categoria: 'Livro',
    },
    {
        id: 5,
        titulo: 'Duna',
        autor: 'Frank Herbert',
        editora: 'Intrinseca',
        paginas: 659,
        data: 1966,
        estoque: '10',
        categoria: 'Livro',
    },

    {
        id: 6,
        titulo: 'Harry Potter e a Pedra Filosofal',
        autor: 'Jk Rowling',
        editora: 'Intrinseca',
        paginas: 101,
        data: 1982,
        estoque: '0',
        categoria: 'Livro',
    },
    {
        id: 7,
        titulo: 'Duna',
        autor: 'Frank Herbert',
        editora: 'Intrinseca',
        paginas: 659,
        data: 1966,
        estoque: '10',
        categoria: 'Livro',
    },

    {
        id: 8,
        titulo: 'Harry Potter e a Pedra Filosofal',
        autor: 'Jk Rowling',
        editora: 'Intrinseca',
        paginas: 101,
        data: 1982,
        estoque: '0',
        categoria: 'Livro',
    },
    {
        id: 9,
        titulo: 'Harry Potter e a Pedra Filosofal',
        autor: 'Jk Rowling',
        editora: 'Intrinseca',
        paginas: 101,
        data: 1982,
        estoque: '0',
        categoria: 'Livro',
    },
    {
        id: 10,
        titulo: 'Harry Potter e a Pedra Filosofal',
        autor: 'Jk Rowling',
        editora: 'Intrinseca',
        paginas: 101,
        data: 1982,
        estoque: '0',
        categoria: 'Livro',
    }
]

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

    <h2 class="text-3xl text-white font-bold mt-3 text-center">
        Tabela de Livros
    </h2>

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
                    <th class="linha">Em Estoque</th>
                    <th class="linha">Categoria</th>
                </tr>
            </thead>

            <tbody>
                <template v-for="livro in livros" :key="livro.id">
                    <tr>
                        <td class="linha">{{ livro.id }}</td>
                        <td class="linha">{{ livro.titulo }}</td>
                        <td class="linha">{{ livro.autor }}</td>
                        <td class="linha">{{ livro.editora }}</td>
                        <td class="linha">{{ livro.paginas }}</td>
                        <td class="linha">{{ livro.data }}</td>
                        <td class="linha">{{ livro.estoque > 0 ? livro.estoque : 'N/A' }}</td>
                        <td class="linha">{{ livro.categoria }}</td>
                    </tr>
                </template>
            </tbody>
        </table>
    </div>

    <ModalAdicionarLivro />
</template>

<style>
.linha {
    padding: 24px;
}
</style>