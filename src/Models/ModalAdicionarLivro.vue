<script setup>
    import { computed, onMounted, ref } from 'vue';
    import { listarCategorias } from '../services/categoriaService.js';
    
    const showModal = ref(false);
    
    const openModal = () => {
        showModal.value = true;
    };
    
    const closeModal = () => {
        showModal.value = false;
    };
    
    defineExpose({ openModal, closeModal });

    const emits = defineEmits(['livro-cadastrado'])
    const tentouEnviar = ref(false)
    const livro = ref({
        titulo: '',
        autor: '',
        isbn: '',
        editora: '',
        paginas: null,
        anoPublicacao: null,
        categoriaId: ''
    });

    const categorias = ref([])
    const carregandoCategorias = ref(false)
    const erroCategorias = ref('')
    const salvando = ref(false)
    const erroSalvar = ref('')
    const mensagemSucesso = ref('')

    function limparFormulario() {
        livro.value = {
            titulo: '',
            autor: '',
            isbn: '',
            editora: '',
            paginas: null,
            anoPublicacao: null,
            categoriaId: ''
        }
        tentouEnviar.value = false
    }

    function validarFormulario() {
        tentouEnviar.value = true
        return formularioValido.value
    }

    const erros = computed(() => {
        const errosAtuais = {}
        if (!livro.value.titulo) {
            errosAtuais.titulo = 'O título é obrigatório.'
        }
        if (!livro.value.autor) {
            errosAtuais.autor = 'O autor é obrigatório.'
        }
        if (!livro.value.isbn) {
            errosAtuais.isbn = 'O ISBN é obrigatório.'
        }
        if (!livro.value.editora) {
            errosAtuais.editora = 'A editora é obrigatória.'
        }
        if (!livro.value.paginas || livro.value.paginas <= 0) {
            errosAtuais.paginas = 'O número de páginas deve ser maior que zero.'
        }
        if (!livro.value.anoPublicacao || livro.value.anoPublicacao <= 0) {
            errosAtuais.anoPublicacao = 'O ano de publicação deve ser maior que zero.'
        }
        if (!livro.value.categoriaId) {
            errosAtuais.categoriaId = 'A categoria é obrigatória.'
        }
        return errosAtuais
    })

    const formularioValido = computed(() => {
        return Object.keys(erros.value).length === 0
    })

    async function salvarLivro() {
        erroFormulario.value = ''
        mensagemSucesso.value = ''

        if (!validarFormulario()) {
            erroFormulario.value = 'Por favor, corrija os erros no formulário antes de salvar.'
            return
        }

        salvando.value = true

        try {
            await cadastrarProduto({
                titulo: livro.value.titulo,
                autor: livro.value.autor,
                isbn: livro.value.isbn,
                editora: livro.value.editora,
                paginas: Number(livro.value.paginas),
                anoPublicacao: livro.value.anoPublicacao,
                categoriaId: Number(livro.value.categoriaId),
            })
            mensagemSucesso.value = 'Livro cadastrado com sucesso!'
            limparFormulario()
            emits('livro-cadastrado')
        } catch (error) {
            erroFormulario.value = 'Erro ao cadastrar o livro. Por favor, tente novamente.'
            console.error(error)
        } finally {
            salvando.value = false
        }
    }

    async function carregarCategorias() {
        carregandoCategorias.value = true
        erroCategorias.value = ''

        try {
            categorias.value = await listarCategorias()
        } catch (error) {
            erroCategorias.value = 'Erro ao carregar as categorias. Por favor, tente novamente.'
            console.error(error)
        } finally {
            carregandoCategorias.value = false
        }
    }

    onMounted(() => {
        carregarCategorias()
    })


</script>

<template> 
    <div v-if="showModal" class="fixed inset-0 flex items-center justify-center z-50">
        <div class="bg-slate-950 p-8 rounded-lg w-1/2">
            <div class="flex justify-between items-center mb-6">
                <h2 class="text-3xl text-white font-bold">
                    Adicionar Livro
                </h2>
                <button @click="closeModal" class="text-white text-2xl font-bold cursor-pointer">
                    ×
                </button>
            </div>

            <div class="flex flex-col gap-4">
                <input type="text" v-model="livro.titulo" placeholder="Título" class="w-full text-white bg-gray-700 p-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500">
                <input type="text" v-model="livro.autor" placeholder="Autor" class="w-full text-white bg-gray-700 p-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500">
                <input type="text" v-model="livro.isbn" placeholder="ISBN" class="w-full text-white bg-gray-700 p-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500">
                <input type="text" v-model="livro.editora" placeholder="Editora" class="w-full text-white bg-gray-700 p-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500">
                <input type="number" v-model="livro.paginas" placeholder="Páginas" class="w-full text-white bg-gray-700 p-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500">
                <input type="number" v-model="livro.anoPublicacao" placeholder="Ano de Publicação" class="w-full text-white bg-gray-700 p-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500">


                <p v-if="carregandoCategorias" class="text-white">
                    Carregando categorias...
                </p>
                <p v-else-if="erroCategorias" class="text-red-500">
                    {{ erroCategorias }}
                </p>
                <select v-else v-model="livro.categoriaId" class="w-full text-white bg-gray-700 p-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500">
                    <option value="">Selecione a Categoria</option>
                    <option v-for="categoria in categorias" :key="categoria.id" :value="categoria.id">
                        {{ categoria.nome }}
                    </option>
                </select>
                
                <p v-if="erroFormulario" class="text-red-500 mt-4">
                    {{ erroFormulario }}
                </p>
                <p v-if="mensagemSucesso" class="text-green-500 mt-4">
                    {{ mensagemSucesso }}
                </p>

                <button type="submit" :disabled="salvando || carregandoCategorias" class="bg-green-500 text-white px-6 py-2 rounded-md text-lg font-bold cursor-pointer w-full hover:bg-green-600 transition">
                    {{ salvando ? 'Cadastrando...' : 'Cadastrar Livro' }}
                </button>
            </div>
        </div>
    </div>
</template>