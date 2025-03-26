import request from '@/utils/request.js'

export const userSignupService = (signupData) => {
    const params = new URLSearchParams();
    for (let key in signupData) {
        params.append(key, signupData[key])
    }
    return request.post('/user/signup', params);
}

export const userLoginService = (loginData) => {
    const params = new URLSearchParams();
    for (let key in loginData) {
        params.append(key, loginData[key])
    }
    return request.post('/user/login', params)
}


// get userinfo
export const userInfoService = () => {
    console.log(request.get('/user/userInfo'))
    return request.get('/user/userInfo')
}

// update user basic info
export const userInfoUpdateService = (userInfoData) => {
    return request.put('/user/update', userInfoData)
}

// update user avatar
export const userAvatarUpdateService = (avatarUrl) => {
    const params = new URLSearchParams();
    params.append('avatarUrl', avatarUrl)
    return request.patch('/user/updateAvatar', params)
}

// update password
export const userPasswordUpdateService = (passwordData) => {
    return request.patch('/user/updatePassword', passwordData)
}