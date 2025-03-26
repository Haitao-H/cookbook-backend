<script setup>
import { ref } from 'vue';
import { ElMessage } from 'element-plus';

const passwordData = ref({
    oldPassword: '',
    newPassword: '',
    confirmPassword: '',
});

const validateConfirmPassword = (rule, value, callback) => {
    if (value !== passwordData.value.newPassword) {
        callback(new Error('Passwords do not match'));
    } else {
        callback();
    }
};

const rules = {
    oldPassword: [
        { required: true, message: 'Current password is required', trigger: 'blur' }
    ],
    newPassword: [
        { required: true, message: 'New password is required', trigger: 'blur' }
    ],
    confirmPassword: [
        { required: true, message: 'Please confirm your password', trigger: 'blur' },
        { validator: validateConfirmPassword, trigger: 'blur' },
    ],
};

const clearData = () => {
    passwordData.value = {
    oldPassword: '',
    newPassword: '',
    confirmPassword: ''
  }
}

import { userPasswordUpdateService } from '@/api/user';
const updatePassword = async () => {
    let result = await userPasswordUpdateService(passwordData.value);
    ElMessage.success(result.message ? result.message : 'Updated successfully!');
    clearData();
};
</script>


<template>
    <el-card class="card">
        <div class="card-header">Update Password</div>

        <el-form :model="passwordData" :rules="rules" label-width="150px" class="password-form">
            <el-form-item label="Current Password" prop="oldPassword">
                <el-input v-model="passwordData.oldPassword" type="password" placeholder="Enter current password"
                    show-password />
            </el-form-item>

            <el-form-item label="New Password" prop="newPassword">
                <el-input v-model="passwordData.newPassword" type="password" placeholder="Enter new password"
                    show-password />
            </el-form-item>

            <el-form-item label="Confirm Password" prop="confirmPassword">
                <el-input v-model="passwordData.confirmPassword" type="password" placeholder="Confirm new password"
                    show-password />
            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="updatePassword">SUBMIT</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>

<style scoped>
.card {
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

.password-form .el-input {
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