<script setup>
defineProps({
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
})

const emit = defineEmits(['update:categoriaId', 'update:editora'])
</script>

<template>
    <div class="bg-white rounded-xl p-2 flex flex-col">
        <span class="mr-5 font-bold">
            Filtrar por...
        </span>
        <div class="flex gap-4">
            <div class="flex flex-col border-2 border-gray-500 rounded-md p-2">
                <label class="text-sm font-semibold" for="categoria-select">Categoria</label>
                <select
                    id="categoria-select"
                    class="text"
                    :value="categoriaId"
                    @change="emit('update:categoriaId', $event.target.value)"
                >
                    <option value="">Todas</option>
                    <option v-for="categoria in categorias" :key="categoria.id" :value="categoria.id">
                        {{ categoria.nome }}
                    </option>
                </select>
            </div>
            <div class="flex flex-col border-2 border-gray-500 rounded-md p-2">
                <label class="text-sm font-semibold" for="editora-select">Editora</label>
                <select
                    id="editora-select"
                    class="text"
                    :value="editora"
                    @change="emit('update:editora', $event.target.value)"
                >
                    <option value="">Todas</option>
                    <option v-for="nomeEditora in editoras" :key="nomeEditora" :value="nomeEditora">
                        {{ nomeEditora }}
                    </option>
                </select>
            </div>
        </div>
    </div>
</template>
