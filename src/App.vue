<script setup>
import { ref, computed } from "vue";
import TabelaLivros from "./Pages/TabelaLivros.vue";
import Emprestimos from "./Pages/Emprestimos.vue";

const routes = {
  "": TabelaLivros,
  "emprestimos": Emprestimos,
}

  const currentRoute = ref("");

  const navigate = (path) => {
    currentRoute.value = path;
  }

  const currentView = computed(() => {
    return routes[currentRoute.value] || TabelaLivros;
  });
</script>

<template>
    <component :is="currentView" @navigate="navigate" />
</template>
