<template>
  <v-app>
    <Navbar />

    <v-app-bar app>
      <v-toolbar-title>
        <br>
        <v-img src="@/assets/logo.png" height="100" contain />
        <span class="ml-3">UACV</span>
      </v-toolbar-title>
      <v-spacer />
    </v-app-bar>

    <v-main>
      <v-container>
        <!-- Title and Buttons -->
        <div class="d-flex justify-center align-center my-4">
          <h3 class="text-h5 font-weight-bold mr-3">기록보기</h3>
          <v-btn icon :to="{ path: '/log_text' }">
            <v-icon>mdi-text</v-icon>
          </v-btn>
          <v-btn icon :to="{ path: '/log_cam' }">
            <v-icon>mdi-video</v-icon>
          </v-btn>
        </div>

        <!-- Sound Records -->
        <v-card class="mb-4 pa-4">
          <h4 class="text-h5 font-weight-bold">소리기록</h4>
          <v-row>
            <v-col v-for="(item, index) in soundRecords" :key="index" cols="12" sm="6" md="3">
              <v-card class="mx-auto" max-width="344">
                <v-img :src="item.image" height="200px"></v-img>
                <v-card-title>{{ item.date }}</v-card-title>
              </v-card>
            </v-col>
          </v-row>
          <div class="text-right">
            <v-btn text>더보기</v-btn>
          </div>
        </v-card>

        <!-- Firing Records -->
        <v-card class="pa-4">
          <h4 class="text-h5 font-weight-bold">발사기록</h4>
          <v-row>
            <v-col v-for="(item, index) in firingRecords" :key="index" cols="12" sm="6" md="3">
              <v-card class="mx-auto" max-width="344">
                <v-img :src="item.image" height="200px"></v-img>
                <v-card-title>{{ item.date }}</v-card-title>
              </v-card>
            </v-col>
          </v-row>
          <div class="text-right">
            <v-btn text>더보기</v-btn>
          </div>
        </v-card>
      </v-container>
    </v-main>
  </v-app>
</template>

<script setup>

const soundRecords = [
  { image: "@/assets/cam.jpg", date: "2024.07.26 11:08:25" },
  { image: "@/assets/cam.jpg", date: "2024.07.25 16:14:20" },
  { image: "@/assets/cam.jpg", date: "2024.07.25 16:12:59" },
  { image: "@/assets/cam.jpg", date: "2024.07.25 13:25:59" },
]

const firingRecords = [
  { image: "@/assets/cam.jpg", date: "2024.07.26 13:01:54" },
  { image: "@/assets/cam.jpg", date: "2024.07.25 18:27:02" },
  { image: "@/assets/cam.jpg", date: "2024.07.25 17:12:38" },
  { image: "@/assets/cam.jpg", date: "2024.07.25 12:48:08" },
]
</script>

<style scoped>
.v-toolbar-title span {
  font-size: 24px;
  font-weight: bold;
  color: #fff;
}

.v-toolbar {
  background-color: #004d40;
}

.v-card {
  background-color: #093028;
  color: #fff;
}
</style>