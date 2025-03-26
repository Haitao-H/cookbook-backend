<script setup>

import { userInfoService } from '@/api/user'
import { useUserInfoStore } from '@/store/userInfo'
const userInfoStore = useUserInfoStore();

const getUserInfo = async () => {
    const result = await userInfoService();
    userInfoStore.setInfo(result.data);
}
getUserInfo();

import { ElMessage, ElMessageBox } from 'element-plus';
import { useTokenStore } from '@/store/token';
import { useRouter } from 'vue-router';
const tokenStore = useTokenStore();
const router = useRouter();
const signOut = () => {
    ElMessageBox.confirm(
        'Sign out. Continue?',
        'Warning',
        {
            confirmButtonText: 'OK',
            cancelButtonText: 'CANCEL',
            type: 'warning',
        }
    )
        .then(async () => {
            // remove pinia
            tokenStore.removeToken();
            userInfoStore.removeInfo();
            // forward to login page
            router.push('/login');
            ElMessage({
                type: 'success',
                message: 'Logout completed',
            })
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: 'Logout canceled',
            })
        })
};

</script>


<template>
    <el-container class="layout-container">
        <!-- Sidebar -->
        <el-aside width="250px" class="sidebar">
            <div class="sidebar-banner">
                <img src="/src/assets/banner.png" alt="Cookbook Logo" class="banner-img" />
            </div>
            <el-menu default-active="1" router>
                <el-menu-item index="/recipe/category">
                    <el-icon>
                        <grid />
                    </el-icon>
                    <span>Category</span>
                </el-menu-item>
                <el-menu-item index="/recipe/manage">
                    <el-icon><knife-fork /></el-icon>
                    <span>Recipes Management</span>
                </el-menu-item>
                <el-sub-menu>
                    <template #title>
                        <el-icon>
                            <user />
                        </el-icon>
                        <span>User Information</span>
                    </template>
                    <el-menu-item index="/user/info">
                        <el-icon>
                            <House />
                        </el-icon>
                        <span>Basic Information</span>
                    </el-menu-item>
                    <el-menu-item index="/user/avatar">
                        <el-icon>
                            <camera />
                        </el-icon>
                        <span>Avatar Update</span>
                    </el-menu-item>
                    <el-menu-item index="/user/updatePassword">
                        <el-icon>
                            <lock />
                        </el-icon>
                        <span>Password Update</span>
                    </el-menu-item>
                </el-sub-menu>
            </el-menu>
        </el-aside>

        <!-- Main Section -->
        <el-container>
            <!-- Header -->
            <el-header class="header">
                <div class="user-info">
                    <el-avatar :size="40"
                        :src="userInfoStore.info.avatar ? userInfoStore.info.avatar : '/src/assets/avatar.jpg'" />
                    <span class="username">Hello, <strong>{{ userInfoStore.info.username }}</strong></span>
                    <el-button type="danger" @click="signOut">Sign Out</el-button>
                </div>
            </el-header>

            <!-- Body -->
            <el-main>
                <router-view></router-view>
            </el-main>

            <!-- Footer -->
            <el-footer class="footer">
                <p>&copy; 2025 Cookbook Sharing. All rights reserved.</p>
            </el-footer>
        </el-container>
    </el-container>
</template>


<style scoped>
.layout-container {
    height: 100vh;
}

.sidebar {
    background-color: #ffe6cc;
    height: 100vh;
    padding-top: 20px;
    border-right: 2px solid #d2691e;
    box-shadow: 4px 0 8px rgba(0, 0, 0, 0.1);
}

.sidebar-banner {
    text-align: center;
    padding: 15px;
}

.banner-img {
    width: 100%;
    max-height: 250px;
    object-fit: cover;
    border-radius: 10px;
}

.el-menu {
    background-color: transparent;
    border-right: none;
}

.el-menu-item,
.el-sub-menu__title {
    color: #8b4513;
    font-weight: bold;
}

.el-menu-item:hover,
.el-sub-menu__title:hover {
    background-color: #d2691e;
    color: white;
}

.header {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    padding: 10px 20px;
    background-color: #fff8e1;
    border-bottom: 2px solid #d2691e;
}

.user-info {
    display: flex;
    align-items: center;
    gap: 10px;
}

.username {
    font-weight: bold;
    color: #8b4513;
}

.footer {
    text-align: center;
    padding: 10px;
    background-color: #fff8e1;
    border-top: 2px solid #d2691e;
    color: #8b4513;
}
</style>