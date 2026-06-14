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
                <li>
                    <a @click="goToMeuPerfil" class="cursor-pointer">
                        <svg width="30px" height="30px" viewBox="0 0 20 20" version="1.1"
                            xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">

                            <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                <g id="Dribbble-Light-Preview" transform="translate(-140.000000, -2159.000000)"
                                    fill="#FFF">
                                    <g id="icons" transform="translate(56.000000, 160.000000)">
                                        <path
                                            d="M100.562548,2016.99998 L87.4381713,2016.99998 C86.7317804,2016.99998 86.2101535,2016.30298 86.4765813,2015.66198 C87.7127655,2012.69798 90.6169306,2010.99998 93.9998492,2010.99998 C97.3837885,2010.99998 100.287954,2012.69798 101.524138,2015.66198 C101.790566,2016.30298 101.268939,2016.99998 100.562548,2016.99998 M89.9166645,2004.99998 C89.9166645,2002.79398 91.7489936,2000.99998 93.9998492,2000.99998 C96.2517256,2000.99998 98.0830339,2002.79398 98.0830339,2004.99998 C98.0830339,2007.20598 96.2517256,2008.99998 93.9998492,2008.99998 C91.7489936,2008.99998 89.9166645,2007.20598 89.9166645,2004.99998 M103.955674,2016.63598 C103.213556,2013.27698 100.892265,2010.79798 97.837022,2009.67298 C99.4560048,2008.39598 100.400241,2006.33098 100.053171,2004.06998 C99.6509769,2001.44698 97.4235996,1999.34798 94.7348224,1999.04198 C91.0232075,1998.61898 87.8750721,2001.44898 87.8750721,2004.99998 C87.8750721,2006.88998 88.7692896,2008.57398 90.1636971,2009.67298 C87.1074334,2010.79798 84.7871636,2013.27698 84.044024,2016.63598 C83.7745338,2017.85698 84.7789973,2018.99998 86.0539717,2018.99998 L101.945727,2018.99998 C103.221722,2018.99998 104.226185,2017.85698 103.955674,2016.63598"
                                            id="profile_round-[#1342]">

                                        </path>
                                    </g>
                                </g>
                            </g>
                        </svg>
                        <p>Meu Perfil</p>
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
