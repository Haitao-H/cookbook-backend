<script setup>

import { Delete, Edit } from '@element-plus/icons-vue';
import { ref } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';

const categories = ref([
    // { "id": 2, "name": "Name1", "description": "description for name1" },
    // { "id": 3, "name": "Name2", "description": "description for name2" },
    // { "id": 4, "name": "Name3", "description": "description for name3" },

])

// get recipe category list
import { recipeCategoryListService, recipeCategoryAddService, recipeCategoryUpdateService, recipeCategoryDeleteService } from '@/api/recipe.js'
const recipeCategoryList = async () => {
    let result = await recipeCategoryListService();
    categories.value = result.data;
}
recipeCategoryList();


// new category dialog handler
const dialogVisible = ref(false)

const categoryModel = ref({
    name: '',
    description: ''
})

const rules = {
    name: [{ required: true, message: 'name is required', trigger: 'blur' }],
    description: [{ required: true, message: 'description is required', trigger: 'blur' }]
}

// clear category model data
const clearData = () => {
    categoryModel.value.name = '';
    categoryModel.value.description = ''
}

const addCategory = async () => {
    let result = await recipeCategoryAddService(categoryModel.value);
    ElMessage.success(result.message ? result.message : 'Successfully Added!')
    recipeCategoryList();
    dialogVisible.value = false;
}

const updateCategory = async () => {
    console.log(categoryModel.value);
    
    let result = await recipeCategoryUpdateService(categoryModel.value);
    ElMessage.success(result.message ? result.message : 'Successfully Updated!')
    recipeCategoryList();
    dialogVisible.value = false;
}

const deleteCategory = async (row) => {

    ElMessageBox.confirm(
        'Delete this category. Continue?',
        'Warning',
        {
            confirmButtonText: 'OK',
            cancelButtonText: 'CANCEL',
            type: 'warning',
        }
    )
        .then(async () => {
            let result = await recipeCategoryDeleteService(row.id)
            ElMessage({
                type: 'success',
                message: 'Delete completed',
            })
            recipeCategoryList();
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: 'Delete canceled',
            })
        })
}

// dialog title handle
const title = ref('')

const showDialog = (row) => {
    dialogVisible.value = true
    categoryModel.value.name = row.name;
    categoryModel.value.description = row.description

    categoryModel.value.id = row.id;
}


</script>


<template>
    <el-card>
        <div class="card-header">
            <span><strong>Recipe Category</strong></span>
            <el-button type="primary" @click="dialogVisible = true; title = 'Add New Category'; clearData()">New
                Category</el-button>
        </div>

        <div style="margin-block: 20px;">
            <hr>
        </div>

        <el-table :data="categories" style="width: 100%">
            <el-table-column type="index" label="#" />
            <el-table-column prop="name" label="Name" />
            <el-table-column prop="description" label="Description" />
            <el-table-column label="Action" width="180">
                <template #default="{ row }">
                    <el-button type="primary" :icon="Edit" circle @click="showDialog(row); title = 'Update Category'" />
                    <el-button type="danger" :icon="Delete" circle @click="deleteCategory(row)" />
                </template>
            </el-table-column>
        </el-table>
    </el-card>


    <!-- category dialog-->
    <el-dialog v-model="dialogVisible" :title="title" width="400px" center>
        <el-form label-position="top" :model="categoryModel" :rules="rules">
            <el-form-item label="Category Name" prop="name">
                <el-input v-model="categoryModel.name" placeholder="Enter category name" />
            </el-form-item>

            <el-form-item label="Description" prop="description">
                <el-input v-model="categoryModel.description" placeholder="Enter description" type="textarea" />
            </el-form-item>
        </el-form>

        <template #footer>
            <el-button @click="dialogVisible = false">CANCEL</el-button>
            <el-button type="primary"
                @click="title == 'Add New Category' ? addCategory() : updateCategory()">COMFIRMED</el-button>
        </template>
    </el-dialog>


</template>


<style scoped>
.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 20px;
    font-weight: bold;
    color: #d2691e;
}

.demo-form-inline .el-input {
    --el-input-width: 220px;
}

.demo-form-inline .el-select {
    --el-select-width: 220px;
}

.el-p {
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;
}

.card-header {
    display: flex;
    justify-content: space-between;
}
</style>