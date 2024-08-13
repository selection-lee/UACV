<template>
    <v-container class="pa-0">
        <v-row>
            <v-col cols="12">
                <h5 class="text-h5 font-weight-bold">Chassis Cam</h5>
                <div class="cam">
                    <v-img :src="chassisCam" alt="Chassis feed"></v-img>
                    <div class="details">
                        <p>{{ speed }} km/h</p>
                        <p>{{ distance }} km</p>
                    </div>
                </div>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import mqtt from 'mqtt';

export default {
    name: "CameraMonitor",
    props: {
        speed: Number,
        distance: Number,
    },
    data() {
        return {
            chassisCam: ''
        };
    },
    mounted() {
        const chassisTopic = 'rpi/cam/chassis';

        const client = mqtt.connect({
            protocol: 'wss',
            host: "i11c102.p.ssafy.io",
            path: "/ws",
            username: 'vue',
            password: 'ssafyi11C102!!'
        });

        client.on('connect', () => {
            console.log('Connected to MQTT Broker');
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
