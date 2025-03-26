import { createRouter, createWebHistory } from 'vue-router'
import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'

import RecipeCategoryVue from '@/views/recipe/RecipeCategory.vue'
import RecipeManageVue from '@/views/recipe/RecipeManage.vue'
import UserAvatarVue from '@/views/user/UserAvatar.vue'
import UserInfoVue from '@/views/user/UserInfo.vue'
import UserUpdatePasswordVue from '@/views/user/UserUpdatePassword.vue'

const routes = [
    { path: '/login', component: LoginVue },
    {
        path: '/',
        component: LayoutVue,
        redirect: '/recipe/manage',
        children: [
            { path: '/recipe/category', component: RecipeCategoryVue },
            { path: '/recipe/manage', component: RecipeManageVue },
            { path: '/user/info', component: UserInfoVue },
            { path: '/user/avatar', component: UserAvatarVue },
            { path: '/user/updatePassword', component: UserUpdatePasswordVue },
        ]
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes: routes,
})

export default router
