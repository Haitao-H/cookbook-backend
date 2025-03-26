import { defineStore } from "pinia";
import { ref } from "vue";

export const useTokenStore = defineStore('token', () => {
    const token = ref('')

    // set token
    const setToken = (newToken) => {
        token.value = newToken;
    }

    // remove token
    const removeToken = () => {
        token.value = ''
    }

    return { token, setToken, removeToken }
}, { persist: true });