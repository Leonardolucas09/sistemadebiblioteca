<script setup>
    import { ref } from 'vue';
    import ModalNovaSenha from '../Models/ModalNovaSenha.vue';
    import Cabecalho from '../components/Cabecalho.vue';
    import BarraLateral from '../components/BarraLateral.vue';
    
    const sidebarAberta = ref(false);

    const toggleSidebar = () => {
        sidebarAberta.value = !sidebarAberta.value;
    };

    const fecharSidebar = () => {
        sidebarAberta.value = false;
    };

    defineEmits(['navigate']);

    const modalRef = ref(null);
    
    const abrirModal = () => {
        modalRef.value.openModal();
    };


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
                        Informações Pessoais
                    </h2>
                    <p class="text-white mb-2">Nome: </p>
                    <p class="text-white mb-2">Email: </p>
                </div>
                
                <div>
                    <h2 class="text-2xl text-white font-bold mb-4">
                        Últimos Empréstimos
                    </h2>
                    <p class="text-white mb-2">Livro: </p>
                    <p class="text-white mb-2">Data de Devolução: </p>
                </div>
            </div>
                

            <a @click="abrirModal" class="inline text-blue-500 hover:underline cursor-pointer">
                <span>Deseja alterar a sua senha?</span>
            </a>
        </div>
        <ModalNovaSenha ref="modalRef" />
    </div>
</template>