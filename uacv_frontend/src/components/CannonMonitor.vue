<template>
    <v-container class="pa-0"
        style="display: flex; flex-direction: column; justify-content: center; align-items: center;">
        <h5 class="text-h5 font-weight-bold">Turret Cam</h5>
        <div class="cam">
            <v-img :src="cannonCam" alt="Turret feed"></v-img>
        </div>
    </v-container>
</template>

<script>
import mqtt from 'mqtt';

export default {
    name: "CameraMonitor",
    data() {
        return {
            cannonCam: ''
        };
    },
    mounted() {
        const cannonTopic = 'rpi/cam/cannon';

        const client = mqtt.connect({
            protocol: 'wss',
            host: "i11c102.p.ssafy.io",
            path: "/ws",
            username: 'vue',
            password: 'ssafyi11C102!!'
        });

        client.on('connect', () => {
            console.log('Connected to MQTT Broker');
            client.subscribe(cannonTopic);
        });

        client.on('message', (topic, message) => {
            const payload = message.toString();
            this.cannonCam = 'data:image/jpeg;base64,' + payload;
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
