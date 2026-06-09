<setup script>

</setup>

<template>
    <div class="bg-white rounded-xl p-2 flex flex-col">
        <span class="mr-5 font-bold">
            Filtrar por...
        </span>
        <div class="flex gap-4">
            <div class="flex flex-col border-2 border-gray-500 rounded-md p-2 ">
                <label class="text-sm font-semibold" for="categoria-select">Categoria</label>
                <select class="text ">
                    <option value="">Selecione</option>
                    <option value="categoria-artigo">Artigos</option>
                    <option value="categoria-livros">Livros</option>
                    <option value="categoria-quadrinhos">Quadrinhos</option>
                </select>
            </div>
            <div class="flex flex-col  border-2 border-gray-500 rounded-md p-2 ">
                <label class="text-sm font-semibold" for="categoria-select">Data de Lançamento</label>
                <input type="date" id="" value="2026-05-18" max="2026-06-31">
            </div>
        </div>

    </div>
</template>