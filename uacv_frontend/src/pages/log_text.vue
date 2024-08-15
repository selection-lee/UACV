<!-- log_text.vue -->
<template>
  <v-app>
    <Navbar />

    <Appbar />

    <v-main>
      <v-container>
        <div class="d-flex justify-center align-center my-4">
          <h3 class="text-h5 font-weight-bold mr-3">LOG</h3>
        </div>

        <v-data-table :headers="headers" :items="filteredItems" class="elevation-1">
          <template v-slot:top>
            <v-toolbar flat>
              <v-toolbar-title style="color: #FFFFEF;">기록보기</v-toolbar-title>
              <v-divider inset vertical class="mx-4" />

              <div class="filter-container">
                <v-btn
                  icon
                  @click="toggleMenu"
                  class="filter-button"
                  ref="filterButton"
                >
                  <v-icon style="color: #FFFFEF;">mdi-filter</v-icon>
                </v-btn>

                <div v-if="menu" class="filter-menu" ref="filterMenu">
                  <v-list>
                    <v-list-item v-for="option in filterOptions" :key="option" @click="applyFilter(option)">
                      <v-list-item-content>{{ option }}</v-list-item-content>
                    </v-list-item>
                  </v-list>
                </div>
              </div>
            </v-toolbar>
          </template>
        </v-data-table>

      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import Appbar from '@/components/appbar.vue';
import Navbar from '@/components/navbar.vue';
import { useUserStore } from '@/stores/user';

const store = useUserStore()

export default {
  data() {
    return {
      headers: [
        { text: "시간", value: "time" },
        { text: "구분", value: "type" },
        { text: "내용", value: "content" },
      ],
      filterOptions: ["소리인식", "발사기록", "센서인식"],
      items: [],
      filterOption: null,
      menu: false,
    };
  },

  computed: {
    filteredItems() {
      if (this.filterOption) {
        return this.items.filter(item => item.type === this.filterOption);
      }
      return this.items;
    },
  },


  methods: {
    async fetchSoundLogs() {
      try {
        const headers = {
          Authorization: `Bearer ${sessionStorage.getItem("token")}`
        }

        const response = await fetch(`${import.meta.env.VITE_BACKEND_API_URL}/sound-logs`, { headers });
        const data = await response.json();

        this.items = data.map(log => ({
          time: new Date(log.receivedAt).toLocaleString('ko-KR'),
          type: "소리인식",
          content: `총 소리 인식 (${log.soundType}로 추정)`,
          receivedAt: new Date(log.receivedAt), // 정렬을 위해 Date 객체 추가
        }))
        .sort((a, b) => b.receivedAt - a.receivedAt); // 날짜 기준 내림차순 정렬;

      } catch (error) {
        console.error('Error fetching sound logs:', error);
      }
    },
    expandDrawer() {
      this.mini = false;
      this.drawerWidth = 150;
    },
    collapseDrawer() {
      this.mini = true;
      this.drawerWidth = 56;
    },
    logout() {
      this.$router.push("/");
    },
    applyFilter(option) {
      this.filterOption = option;
      this.menu = false;
    },
    toggleMenu() {
      this.menu = !this.menu;
      if (this.menu) {
        this.$nextTick(() => {
          const button = this.$refs.filterButton.$el;
          const menu = this.$refs.filterMenu;
          const rect = button.getBoundingClientRect();
          menu.style.top = `${rect.bottom}px`;
          menu.style.left = `${rect.left}px`;
        });
      }
    },
  },
  mounted() {
    this.fetchSoundLogs();
    // Close the menu when clicking outside
    document.addEventListener('click', (e) => {
      if (this.menu && !this.$el.contains(e.target)) {
        this.menu = false;
      }
    });
  },
  components: {
    Appbar,
    Navbar,
    NavigationItem: {
      props: {
        icon: String,
        title: String,
        to: String,
        mini: Boolean,
      },
      template: `
        <v-list-item :to="to" @click="$emit('click')">
          <v-list-item-icon v-if="icon">
            <v-icon>{{ icon }}</v-icon>
          </v-list-item-icon>
          <v-list-item-content v-if="title && !mini">
            <v-list-item-title>{{ title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      `,
    },
  },
};
</script>

<style scoped>
.v-toolbar-title span {
  font-size: 24px;
  font-weight: bold;
  color: #FFFFEF;
}

.v-toolbar {
  background-color: #004d40;
  text-align: center;
}

.v-pagination {
  justify-content: center;
}

.filter-container {
  position: relative;
  z-index: 1000; /* 컨테이너의 z-index를 높임 */
}

.filter-button {
  z-index: 1001; /* 버튼의 z-index를 컨테이너보다 약간 높게 설정 */
}

.filter-menu {
  position: fixed;
  background-color: white;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  z-index: 1002; /* 메뉴의 z-index를 가장 높게 설정 */
}

/* 전체 앱에 대한 스타일 */
:deep(.v-application) {
  position: relative;
  z-index: 1;
}

/* 데이터 테이블에 대한 스타일 */
:deep(.v-data-table) {
  position: relative;
  z-index: 2;
}
</style>