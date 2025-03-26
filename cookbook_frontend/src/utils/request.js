import axios from "axios";
// const baseURL = 'http://locahost:8080';
const baseURL = '/api';
const instance = axios.create({ baseURL });

import { useTokenStore } from "@/store/token";
import { ElMessage } from "element-plus";


instance.interceptors.request.use(
    (config) => {
        const tokenStore = useTokenStore();
        // with token?
        if (tokenStore.token) {
            config.headers.Authorization = tokenStore.token
        }
        return config;
    },
    (err) => {
        Promise.reject(err)
    }
)


import router from '@/router'
instance.interceptors.response.use(
    result => {
        return result.data;
    },
    err => {
        // 401 -> msg & login
        if(err.response.status === 401){
            ElMessage.error('Please login')
            router.push('/login')
            
        }else{
            ElMessage.error('Server Error')
        }
        
    }
)

export default instance;