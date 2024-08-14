<template>
    <div class="center-container">
        <div class="loader"></div>
    </div>
</template>

<style scoped>
.center-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    background-color: #093028;
}

.loader {
    width: fit-content;
    font-size: 34px;
    font-family: 'Noto Sans KR', sans-serif;
    line-height: 1.4;
    font-weight: bold;
    color: #FBFBDE;
    background:
        linear-gradient(#FBFBDE 0 0) left, 
        linear-gradient(#FBFBDE 0 0) right; 
    background-repeat: no-repeat;
    border-right: 11px solid #093028;
    border-left: 10px solid #093028;
    background-origin: border-box;
    position: relative;
    animation: l9-0 2s infinite;
    transform: translateY(-20%);
}

.loader::before {
    content: "Loading";
}

.loader::after {
    content: "";
    position: absolute;
    top: 100%;
    left: 0;
    width: 44px;
    height: 120px;
    background:
        linear-gradient(90deg, #FBFBDE 8px, #0000 0 calc(100% - 8px), #FBFBDE 0) bottom /44px 40px,
        linear-gradient(90deg, red 8px, #0000 0 calc(100% - 8px), red 0) bottom 20px left 0/44px 12px,
        linear-gradient(#FBFBDE 0 0) bottom 6px left 0 /44px 16px,
        linear-gradient(#FBFBDE 0 0) bottom 0 left 50%/16px 32px;
    background-repeat: no-repeat;
    animation: l9-1 2s infinite;
}

@keyframes l9-0 {

    0%,
    25% {
        background-size: 50% 100%
    }

    25.1%,
    75% {
        background-size: 0 0, 50% 100%
    }

    75.1%,
    100% {
        background-size: 0 0, 0 0
    }
}

@keyframes l9-1 {
    25% {
        background-position: bottom, bottom 108px left 0, bottom 6px left 0, bottom 0 left 50%;
        left: 0
    }

    25.1% {
        background-position: bottom, bottom 20px left 0, bottom 6px left 0, bottom 0 left 50%;
        left: 0
    }

    50% {
        background-position: bottom, bottom 20px left 0, bottom 6px left 0, bottom 0 left 50%;
        left: calc(100% - 44px)
    }

    75% {
        background-position: bottom, bottom 108px left 0, bottom 6px left 0, bottom 0 left 50%;
        left: calc(100% - 44px)
    }

    75.1% {
        background-position: bottom, bottom 20px left 0, bottom 6px left 0, bottom 0 left 50%;
        left: calc(100% - 44px)
    }
}
</style>
