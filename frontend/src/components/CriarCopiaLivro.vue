<script setup>
    import { ref } from 'vue';
    import ModalCriarCopiaLivro from '../Models/ModalNovaSenha.vue';
    
    const modalRef = ref(null);
    
    const abrirModal = () => {
        modalRef.value.openModal();
    };
</script>

<template>
    <div>
        <button class="bg-blue-500 text-white px-4 rounded-md text-lg font-bold cursor-pointer h-14" 
            @click="abrirModal"
        >
            Criar Cópia de Livro
        </button>
        <ModalCriarCopiaLivro ref="modalRef" />
    </div>
</template>