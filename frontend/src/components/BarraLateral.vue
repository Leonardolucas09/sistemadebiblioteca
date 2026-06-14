<script setup>
import Filtros from './Filtros.vue';
import InputBuscarLivros from './InputBuscarLivros.vue';
import AdicionarLivro from './AdicionarLivro.vue';

defineProps({
    isOpen: Boolean,
    isUsable: Boolean,
    mostrarAdicionar: {
        type: Boolean,
        default: true,
    },
    busca: {
        type: String,
        default: '',
    },
    categoriaId: {
        type: [String, Number],
        default: '',
    },
    editora: {
        type: String,
        default: '',
    },
    categorias: {
        type: Array,
        default: () => [],
    },
    editoras: {
        type: Array,
        default: () => [],
    },
});

const emit = defineEmits([
    'toggle',
    'close',
    'navigate',
    'livro-cadastrado',
    'update:busca',
    'update:categoriaId',
    'update:editora',
]);

const handleToggle = () => {
    emit('toggle');
};

const handleClose = () => {
    emit('close');
};

const goToTabelaLivros = () => {
    emit('navigate', '');
};

const goToEmprestimos = () => {
    emit('navigate', 'emprestimos');
};

const goToMeuPerfil = () => {
    emit('navigate', 'meuPerfil');
};

</script>

<template>
    <div v-if="isOpen" class="fixed inset-0 z-40" @click="handleClose">
    </div>
    <div class="flex justify-between items-center px-8 gap-4">
        <div class="toggle cursor-pointer" @click="handleToggle">
            <svg width="30" height="30" viewBox="0 0 23 19" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M1.5 1.5H21.5M1.5 9.2037H21.5M1.5 17.5H21.5" stroke="white" stroke-width="3"
                    stroke-linecap="round" />
            </svg>
        </div>
        <div class="flex items-center gap-12">
            <AdicionarLivro v-if="isUsable && mostrarAdicionar" @livro-cadastrado="emit('livro-cadastrado')"/>
            <InputBuscarLivros
                v-if="isUsable"
                :model-value="busca"
                @update:model-value="emit('update:busca', $event)"
            />
            <Filtros
                v-if="isUsable"
                :categoria-id="categoriaId"
                :editora="editora"
                :categorias="categorias"
                :editoras="editoras"
                @update:categoria-id="emit('update:categoriaId', $event)"
                @update:editora="emit('update:editora', $event)"
            />
        </div>
    </div>

    <div class="slide bg-black absolute py-3 px-5 top-0 z-50" @click.stop :class="{ 'slide-open': isOpen }">
        <div class="slide-header">
            <span class="text-bold text-3xl text-white">🕮</span>
            <h1 class="text-2xl text-white font-bold">Sistema de Biblitoeca</h1>
        </div>

        <nav class="slide-nav">
            <ul class="flex flex-col gap-8">
                <li>
                    <a @click="goToTabelaLivros" class="cursor-pointer">
                        <svg width="30px" height="30px" viewBox="0 0 24 24" fill="none"
                            xmlns="http://www.w3.org/2000/svg">
                            <path
                                d="M19.8978 16H7.89778C6.96781 16 6.50282 16 6.12132 16.1022C5.08604 16.3796 4.2774 17.1883 4 18.2235"
                                stroke="#FFF" stroke-width="1.5" />
                            <path d="M8 7H16" stroke="#FFF" stroke-width="1.5" stroke-linecap="round" />
                            <path d="M8 10.5H13" stroke="#FFF" stroke-width="1.5" stroke-linecap="round" />
                            <path d="M19.5 19H8" stroke="#FFF" stroke-width="1.5" stroke-linecap="round" />
                            <path
                                d="M10 22C7.17157 22 5.75736 22 4.87868 21.1213C4 20.2426 4 18.8284 4 16V8C4 5.17157 4 3.75736 4.87868 2.87868C5.75736 2 7.17157 2 10 2H14C16.8284 2 18.2426 2 19.1213 2.87868C20 3.75736 20 5.17157 20 8M14 22C16.8284 22 18.2426 22 19.1213 21.1213C20 20.2426 20 18.8284 20 16V12"
                                stroke="#FFF" stroke-width="1.5" stroke-linecap="round" />
                        </svg>
                        <p>Gestão de Livros</p>
                    </a>
                </li>
                <li>
                    <a @click="goToEmprestimos" class="cursor-pointer">
                        <svg fill="#FFF" width="30px" height="30px" viewBox="0 -64 640 640"
                            xmlns="http://www.w3.org/2000/svg">
                            <path
                                d="M488 192H336v56c0 39.7-32.3 72-72 72s-72-32.3-72-72V126.4l-64.9 39C107.8 176.9 96 197.8 96 220.2v47.3l-80 46.2C.7 322.5-4.6 342.1 4.3 357.4l80 138.6c8.8 15.3 28.4 20.5 43.7 11.7L231.4 448H368c35.3 0 64-28.7 64-64h16c17.7 0 32-14.3 32-32v-64h8c13.3 0 24-10.7 24-24v-48c0-13.3-10.7-24-24-24zm147.7-37.4L555.7 16C546.9.7 527.3-4.5 512 4.3L408.6 64H306.4c-12 0-23.7 3.4-33.9 9.7L239 94.6c-9.4 5.8-15 16.1-15 27.1V248c0 22.1 17.9 40 40 40s40-17.9 40-40v-88h184c30.9 0 56 25.1 56 56v28.5l80-46.2c15.3-8.9 20.5-28.4 11.7-43.7z" />
                        </svg>
                        <p>Empréstimos</p>
                    </a>
                </li>
                
            </ul>
        </nav>
    </div>
</template>

<style>
.slide {
    display: flex;
    flex-direction: column;
    padding: 1.5rem;
    width: 20%;
    height: 100%;
    transition: 0.5s ease;
    transform: translateX(-100%);
    overflow: hidden;
}

.slide-header {
    display: flex;
    align-items: center;
    gap: 6px;
    flex-shrink: 0;
    border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.slide-nav {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    padding-top: 8rem;
    padding-bottom: 1.5rem;
    overflow-y: auto;
}

.slide ul li a,
.fundo {
    display: flex;
    align-items: center;
    gap: 1rem;
    color: #FFF;
    font-weight: 600;
}

.slide ul li a img,
.fundo img {
    width: 30px;
    height: 30px;
    object-fit: contain;
}

.slide-open {
    transform: translateX(0);
}
</style>
