<script setup>
    import { ref } from 'vue';
    import ModalAdicionarLivro from '../Models/ModalAdicionarLivro.vue';

    const emit = defineEmits(['livro-cadastrado'])
    
    const modalRef = ref(null);
    
    const abrirModal = () => {
        modalRef.value.openModal();
    };
</script>

<template>
    <div>
        <button class=" bg-blue-600 text-white hover:bg-blue-700 transition px-4 rounded-md text-lg font-bold cursor-pointer h-14" 
            @click="abrirModal"
        >
            Adicionar Livro
        </button>
        <ModalAdicionarLivro ref="modalRef" @livro-cadastrado="emit('livro-cadastrado')" />
    </div>
</template>
