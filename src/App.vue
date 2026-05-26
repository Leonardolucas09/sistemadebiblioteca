<script setup>
import { ref } from "vue";
import BarraLateral from "./components/BarraLateral.vue";
import Cabecalho from "./components/Cabecalho.vue";
import TabelaLivros from "./components/TabelaLivros.vue";
import Emprestimos from "./components/Emprestimos.vue";

const sidebarAberta = ref(false);

const toggleSidebar = () => {
  sidebarAberta.value = !sidebarAberta.value;
};

const fecharSidebar = () => {
  sidebarAberta.value = false;
};
</script>

<template>
  <Cabecalho />
  <BarraLateral
    :isOpen="sidebarAberta"
    @toggle="toggleSidebar"
    @close="fecharSidebar"
  />
  <!-- <TabelaLivros/> -->
  <Emprestimos/>
</template>
