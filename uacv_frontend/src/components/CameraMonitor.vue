<template>
    <v-container class="pa-0"
        style="display: flex; flex-direction: column; justify-content: center; align-items: center;">
        <h5 class="text-h5 font-weight-bold">Chassis Cam</h5>
        <div class="cam">
            <v-img :src="chassisCam" alt="Chassis feed" style="width: 290px;"></v-img>
        </div>
    </v-container>
</template>

<script>
import mqtt from 'mqtt'

export default {
    name: "CameraMonitor",
    data() {
        return {
            chassisCam: ''
        };
    },
    mounted() {
        const chassisTopic = 'rpi/cam/chassis'

        const client = mqtt.connect({
            protocol: 'wss',
            path: "/ws",
            host: import.meta.env.VITE_MQTT_HOST,
            username: import.meta.env.VITE_MQTT_USERNAME,
            password: import.meta.env.VITE_MQTT_PASSWORD
        });

        client.on('connect', () => {
            client.subscribe(chassisTopic);
        });

        client.on('message', (topic, message) => {
            const payload = message.toString();
            this.chassisCam = 'data:image/jpeg;base64,' + payload;
        });
    }
};
</script>


<style scoped>
h1 {
    text-align: center;
}

.cam {
    text-align: center;
}

.cam-image {
    width: 100%;
    max-width: 600px;
    height: auto;
}

.details {
    margin-top: 10px;
}
</style>
