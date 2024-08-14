<template>
    <v-card class="coordinate-sender" flat>
      <v-form @submit.prevent="sendCoordinates">
        <v-row no-gutters class="justify-center">
          <v-col cols="5">
            <v-text-field
              v-model="x"
              label="X"
              type="number"
              outlined
              dense
              hide-details
              class="coordinate-input"
            ></v-text-field>
          </v-col>
          <v-col cols="5">
            <v-text-field
              v-model="y"
              label="Y"
              type="number"
              outlined
              dense
              hide-details
              class="coordinate-input ml-2"
            ></v-text-field>
          </v-col>
          <v-col cols="2" class="d-flex align-center justify-end pr-0">
            <v-btn type="submit" color="#FFFFEF" class="send-btn" icon>
              <v-icon>mdi-send</v-icon>
            </v-btn>
          </v-col>
        </v-row>
      </v-form>
    </v-card>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import axios from 'axios';
  
  const x = ref(0);
  const y = ref(0);
  
  const sendCoordinates = async () => {
    try {
      const response = await axios.post(`${import.meta.env.VITE_BACKEND_API_URL}/device/coordinate`, {
        x: parseFloat(x.value),
        y: parseFloat(y.value),
        z: 0 // Always send 0 for z coordinate
      });
      console.log('Coordinates sent successfully:', response.data);
    } catch (error) {
      console.error('Error sending coordinates:', error);
    }
  };
  </script>
  
  <style scoped>
  .coordinate-sender {
    background-color: transparent !important;
    width: 100%; /* Ensure full width */
    max-width: 350px; /* Match the width of the Map component */
    margin: 10px auto; /* Center the component and add some vertical spacing */
    padding: 0 10px; /* Add some horizontal padding */
  }
  
  .coordinate-input :deep(.v-field__outline) {
    border-color: #ffffef !important;
  }
  
  .coordinate-input :deep(.v-field__input) {
    color: #ffffef !important;
  }
  
  .coordinate-input :deep(.v-label) {
    color: #ffffef !important;
  }
  
  .v-btn.v-btn--icon {
    width: 36px;
    height: 36px;
  }
  
  .send-btn {
    background-color: #FFFFEF !important;
  }
  
  .send-btn :deep(.v-icon) {
    color: #2c4d41 !important;
  }
  </style>