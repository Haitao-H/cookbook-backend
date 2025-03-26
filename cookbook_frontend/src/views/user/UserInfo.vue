<script setup>
import { ref } from 'vue';
import { useUserInfoStore } from '@/store/userInfo';
const userInfoStore = useUserInfoStore();
const userInfo = ref({
    ...userInfoStore.info
})

const rules = {
    username: [
        { required: true, message: 'username is required', trigger: 'blur' }],
    email: [
        { required: true, message: 'email is required', trigger: 'blur' }],
}

import { userInfoUpdateService } from '@/api/user';
import { ElMessage } from 'element-plus';
const updateUser = async () => {
    let result = await userInfoUpdateService(userInfo.value);
    ElMessage.success(result.message ? result.messge : 'Updated successfully!'
    )
    // update pinia data
    userInfoStore.setInfo(userInfo.value);

}
</script>


<template>
    <el-card class="user-info-card">
        <div class="card-header">Update Basic Information</div>

        <el-form :model="userInfo" :rules="rules" label-width="120px" class="user-form">
            <el-form-item label="Username" prop="username">
                <el-input v-model="userInfo.username" placeholder="Enter your username" />
            </el-form-item>

            <el-form-item label="Email" prop="email">
                <el-input v-model="userInfo.email" placeholder="Enter your email" />
            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="updateUser">UPDATE</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>

<style scoped>
.user-info-card {
    padding: 20px;
    background-color: #fffaf0;
    border-radius: 12px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
}

.card-header {
    font-size: 22px;
    font-weight: bold;
    margin-bottom: 20px;
    color: #d2691e;
}

.user-form .el-input {
    width: 100%;
}

.el-button {
    margin-top: 20px;
    background-color: #d2691e;
    border-color: #d2691e;
}

.el-button:hover {
    background-color: #b25a1e;
    border-color: #b25a1e;
}
</style>