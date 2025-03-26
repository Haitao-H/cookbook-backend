import request from '@/utils/request.js'
import { useTokenStore } from '@/store/token.js'

// query all recipe categories
export const recipeCategoryListService = () => {
    // const tokenStore = useTokenStore();
    // return request.get('/category', { headers: { 'Authorization': tokenStore.token } })
    return request.get('/category')
}

export const recipeCategoryAddService = (categoryData) => {
    return request.post('/category', categoryData)
}

export const recipeCategoryUpdateService = (categoryData) => {
    return request.put('/category', categoryData)

}

export const recipeCategoryDeleteService = (id) => {
    return request.delete('/category?id=' + id)
}


// query all recipes
export const recipeListService = (params) => {
    return request.get('/recipe', { params: params })
}

// add new recipe
export const recipeAddService = (recipeData) => {
    return request.post('/recipe', recipeData)
}

// update new recipe
export const recipeUpdateService = (recipeData) => {
    console.log(recipeData);
    
    return request.put('/recipe', recipeData)
}

// delete recipe
export const recipeDeleteService = (id) => {
    return request.delete('/recipe?id=' + id)
}