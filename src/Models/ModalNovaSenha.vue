<script setup>
    import { ref } from 'vue';
    
    const showModal = ref(false);
    
    const openModal = () => {
        showModal.value = true;
    };
    
    const closeModal = () => {
        showModal.value = false;
    };
    
    defineExpose({ openModal, closeModal });
</script>

<template> 
    <div v-if="showModal" class="fixed inset-0 flex items-center justify-center z-50">
        <div class="bg-slate-950 p-8 rounded-lg w-1/2">
            <div class="flex justify-between items-center mb-6">
                <h2 class="text-3xl text-white font-bold">
                    Alterar senha
                </h2>
                <button @click="closeModal" class="text-white text-2xl font-bold cursor-pointer">
                    ×
                </button>
            </div>

            <div class="flex flex-col gap-4">
                <h2 class="text-lg text-white font-bold">
                    Você está prestes a alterar sua senha. Por favor, insira sua nova senha abaixo.
                </h2>

                <div class="flex flex-row gap-4">
                    <input type="password" placeholder="Nova senha" class="w-full text-white bg-gray-700 p-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-red-600">
                    <input type="password" placeholder="Confirmar nova senha" class="w-full text-white bg-gray-700 p-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-red-600">
                </div>
                    
                <div class="flex gap-4 justify-end mt-6">
                    <button @click="closeModal" class="bg-gray-600 text-white px-6 py-2 rounded-md text-lg font-bold cursor-pointer">
                        Cancelar
                    </button>
                    <button class="bg-green-500 text-white px-6 py-2 rounded-md text-lg font-bold cursor-pointer">
                        Salvar
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>