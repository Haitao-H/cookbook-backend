<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
const isLogin = ref(true)

const formData = ref({
    username: '',
    email: '',
    password: '',
    repassword: ''
})

// input rule check
const checkRepassword = (rule, value, callback) => {
    if (value == '') {
        callback(new Error('Repassword is requured'));
    } else if (value != formData.value.password) {
        callback(new Error('Two inputs don\'t match'));
    } else {
        callback()
    }
}

const rules = {
    username: [
        { required: true, message: 'username is required', trigger: 'blur' }],
    email: [
        { required: true, message: 'email is required', trigger: 'blur' }],
    password: [
        { required: true, message: 'password is required', trigger: 'blur' }, {
            min: 5, max: 16, message: 'length should between 5~16'
        }],
    repassword: [
        { validator: checkRepassword, trigger: 'blur' }]
}


// submit button handler
import {useRouter} from 'vue-router'
const router = useRouter();

import { userSignupService, userLoginService } from '@/api/user.js'
const signupFunction = async () => {
    let result = await userSignupService(formData.value)
    console.log(result.message)
    if (result.code === 0) {
        // alert(result.message ? result.message : 'Signup successfully!')
        ElMessage.success(result.message ? result.message : 'Signup successfully!')
    } else {
        // alert(result.message ? result.message : 'Sign up failed!')
        ElMessage.error(result.message ? result.message : 'Sign up failed!')
    }
}

import { useTokenStore } from '@/store/token'
const tokenStore = useTokenStore()
const loginFunction = async () => {
    let result = await userLoginService(formData.value)
    if (result.code === 0) {
        // alert(result.message ? result.message : 'Login successfully!')
        ElMessage.success(result.message ? result.message : 'Login successfully!')
        // store token in pinia
        tokenStore.setToken(result.data)
        router.push('/');
    } else {
        // alert(result.message ? result.message : 'Login failed!')
        ElMessage.error(result.message ? result.message : 'Login failed!')
    }
}


// message box handler


</script>


<template>
    <el-row class="login-page">
        <el-col :span="12" class="bg"></el-col>
        <el-col :span="6" :offset="3" class="auth-container">
            <el-card class="auth-card">
                <h2>{{ isLogin ? 'Login' : 'Sign Up' }}</h2>

                <el-form :model="formData" :rules="rules" label-width="100px">
                    <el-form-item label="Username" prop="username">
                        <el-input placeholder="Enter username" v-model="formData.username" />
                    </el-form-item>
                    <el-form-item label="Email" prop="email" v-if="!isLogin">
                        <el-input placeholder="Enter email" v-model="formData.email" />
                    </el-form-item>
                    <el-form-item label="Password" prop="password">
                        <el-input type="password" placeholder="Enter password" v-model="formData.password" />
                    </el-form-item>
                    <el-form-item label="Repassword" prop="repassword" v-if="!isLogin">
                        <el-input type="password" placeholder="Reenter email" v-model="formData.repassword" />
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="isLogin ? loginFunction() : signupFunction()">{{ isLogin ?
                            'Login' :
                            'Sign Up'
                            }}</el-button>
                    </el-form-item>
                </el-form>
                <p>
                    {{ isLogin ? "Don't have an account?" : "Already have an account?" }}
                    <el-button type="text" @click="isLogin = !isLogin">{{ isLogin ? 'Sign Up' : 'Login' }}</el-button>
                </p>
            </el-card>
        </el-col>
    </el-row>
</template>


<style scoped>
.bg {
    background: url('/src/assets/loginBg.png') no-repeat center center;
    background-size: cover;
}

.auth-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
}

.auth-card {
    width: 90%;
    padding: 20px;
    text-align: center;
}
</style>