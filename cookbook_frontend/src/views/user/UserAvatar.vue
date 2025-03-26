<script setup>
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import { Plus, Upload } from '@element-plus/icons-vue';
import { useTokenStore } from '@/store/token';
import { useUserInfoStore } from '@/store/userInfo';
const userInfoStore = useUserInfoStore();
const tokenStore = useTokenStore();
const imgUrl = ref(userInfoStore.info.avatar);
const uploadRef = ref();

const uploadSuccess = (result) => {
    imgUrl.value = result.data
}

import { userAvatarUpdateService } from '@/api/user'
const updateAvatar = async () => {
    let result = await userAvatarUpdateService(imgUrl.value);
    ElMessage.success(result.message ? result.message : 'Updated successfully!');
    userInfoStore.info.avatar = imgUrl.value;
}

</script>


<template>
    <el-card>
        <div class="card-header">Update Avatar</div>

        <el-upload ref="uploadRef" class="avatar-uploader" :show-file-list="false" :auto-upload="true"
            action="/api/upload" name="file" :headers="{ 'Authorization': tokenStore.token }"
            :on-success="uploadSuccess">
            <el-image v-if="imgUrl" :src="imgUrl" class="avatar" />
            <el-image v-else src="/src/assets/avatar.jpg" width="278" />
        </el-upload>
        <br />
        <el-button type="primary" :icon="Plus" size="large" @click="uploadRef.$el.querySelector('input').click()">
            SELECT
        </el-button>
        <el-button type="success" :icon="Upload" size="large" @click="updateAvatar">
            UPLOAD
        </el-button>

    </el-card>
</template>


<style scoped></style>