<template>
  <div id="app">
    <div class="welcome-message">
      <p class="welcome-text">안녕하세요, <span class="user-name"></span>성진 님!</p>
      <p class="description">오늘도 즐거운 강아지 산책하세요.</p>
    </div>
    <div class="brand" >
      PUPFIT
    </div>

    <NavBar ></NavBar>
    <div class="router-view-wrapper">
      <router-view></router-view>
    </div>

<!--    <footer class="footer">-->
<!--      <p>&copy; 2023 Leafy. All rights reserved.</p>-->
<!--    </footer>-->
  </div>

</template>

<script setup>
import SiteHeader from "./components/SiteHeader.vue";
// import SiteFooter from "@/components/SiteFooter.vue";
import NavBar from "@/components/NavBar.vue";

</script>


<style scoped>
html,
body {
  height: 100%;
  margin: 0;
}

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  position: relative;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.router-view-wrapper {
  flex-grow: 1;
  display: flex;
  margin: auto;
}

.welcome-text {
  font-size: 0.9rem;
  font-weight: bold;
}

.brand {
  font-size: 2.5em;
  font-weight: bold;
  color: #1c1904;
  padding-top: 30px;
  padding-bottom: 20px;
  text-align: center;
}

.welcome-message {
  margin-bottom: 1rem;
  position: absolute;
  top: 0px;
  right: 20px;
  text-align: right;
}

.footer {
  background-color: #8FBC8F;
  color: white;
  padding: 1rem;
  font-size: 0.9rem;
  text-align: center;
  bottom: 0;
  width: 100%;
}
</style>
