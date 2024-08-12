<template>
    <v-container class="pa-0">
        <v-row>
            <!-- <v-col cols="12">
                <h5 class="text-h5 font-weight-bold">CAM1</h5>
                <div class="cam">
                    <v-img :src="camera1Src" alt="Camera 1 feed" width="640" height="480"></v-img>
                    <div class="details">
                        <p>{{ speed }} km/h</p>
                        <p>{{ distance }} km</p>
                    </div>
                </div>
            </v-col> -->
            <v-col cols="12">
                <h5 class="text-h5 font-weight-bold">CAM2</h5>
                <div class="cam">
                    <!-- <v-img :src="camera2Src" alt="Camera 2 feed" width="640" height="480"></v-img> -->
                    <v-img :src="camera2Src" alt="Camera 2 feed"></v-img>
                    <div class="details">
                        <!-- <p>{{ speed }} km/h</p> -->
                        <p>잔탄 {{ ammo }} / 10</p>
                    </div>
                </div>
            </v-col>
        </v-row>
    </v-container>
</template>
  
<script>
import mqtt from 'mqtt';

// export default {
//   name: "Cam_canon",
//   props: {
//     ammo: Number,
//   },
// };
// //
export default {
    name: "CameraMonitor",
    props: {
        ammo: Number,
    },
    data() {
        return {
            camera1Src: '',
            camera2Src: ''
        };
    },
    mounted() {
        const chassisTopic = 'rpi/cam/chassis';
        const cannonTopic = 'rpi/cam/cannon';

        // const client = mqtt.connect('ws://192.168.100.104:9001');
        const client = mqtt.connect({
            protocol: 'ws',
            host: "i11c102.p.ssafy.io",
            port: 15675,
            path:"/ws",
            username: 'vue',
            password: 'ssafyi11C102!!'
        });

        client.on('connect', () => {
            console.log('Connected to MQTT Broker');
            client.subscribe(chassisTopic);
            client.subscribe(cannonTopic);
        });

        client.on('message', (topic, message) => {
            // console.log(typeof message);
            const payload = message.toString();
            // console.log(typeof payload);

            if (topic === chassisTopic) {
                this.camera1Src = 'data:image/jpeg;base64,' + payload;
                // console.log(this.camera1Src)
                // console.log(payload)
            } else if (topic === cannonTopic) {
                this.camera2Src = 'data:image/jpeg;base64,' + payload;
            }
        });
    }
};
</script>

  
<style scoped>
h1 {
    text-align: center;
}
</style>


<style scoped>
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
