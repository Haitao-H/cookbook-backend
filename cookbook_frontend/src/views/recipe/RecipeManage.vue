<script setup>
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
    Delete,
    Edit,
    Plus
} from '@element-plus/icons-vue'

const category = ref([])
const categoryId = ref('')
const recipe = ref([])

const total = ref(10)
const currentPage = ref(1)
const pageSize = ref(10)
const background = ref(true)
const disabled = ref(false)

const handleSizeChange = (size) => {
    pageSize.value = size;
    recipeList();
}

const handleCurrentChange = (pageNum) => {
    currentPage.value = pageNum;
    recipeList();
}


// get category in serarch bar
import { recipeCategoryListService, recipeListService, recipeAddService, recipeUpdateService, recipeDeleteService } from '@/api/recipe'
const recipeCategoryList = async () => {
    let result = await recipeCategoryListService();
    category.value = result.data;
}
recipeCategoryList();

// get all recipes
const recipeList = async () => {
    let params = {
        pageNo: currentPage.value,
        pageSize: pageSize.value,
        categoryId: categoryId.value ? categoryId.value : null
    }
    let result = await recipeListService(params);

    total.value = result.data.total;
    recipe.value = result.data.items;

    let categoryMap = new Map();
    for (let i = 0; i < category.value.length; i++) {
        categoryMap.set(category.value[i].id, category.value[i].name)
    }
    for (let i = 0; i < recipe.value.length; i++) {
        recipe.value[i].categoryName = categoryMap.get(recipe.value[i].categoryId);
    }

}
recipeList();

// add new post drawer
const visibleDrawer = ref(false)
const recipeModel = ref({
    title: '',
    ingredients: '',
    instructions: '',
    categoryId: '',
    coverImage: null,
})

// coverImage handler
import { useTokenStore } from '@/store/token'
const tokenStore = useTokenStore();
const uploadHandler = (result) => {
    recipeModel.value.coverImage = result.data
    console.log(result.data)
}

const clearData = () => {
    recipeModel.value.title = ''
    recipeModel.value.ingredients = ''
    recipeModel.value.instructions = ''
    recipeModel.value.categoryId = ''
    recipeModel.value.coverImage = null
}


// add new recipe
const postRecipe = async () => {
    if (!recipeModel.value.title || !recipeModel.value.categoryId || !recipeModel.value.ingredients || !recipeModel.value.instructions) {
        ElMessage.error('All fields are required!');
        return;
    }
    let result = await recipeAddService(recipeModel.value)
    ElMessage.success(result.message ? result.message : 'Recipe posted successfully');
    visibleDrawer.value = false;
    recipeList();
};

// update recipe
const updateRecipe = async () => {
    console.log(recipeModel.value)

    let result = await recipeUpdateService(recipeModel.value)
    ElMessage.success(result.message ? result.message : 'Recipe updated successfully');
    recipeList();
    visibleDrawer.value = false;
}

// delete recipe
const deleteRecipe = async (row) => {
    ElMessageBox.confirm(
        'Delete this recipe. Continue?',
        'Warning',
        {
            confirmButtonText: 'OK',
            cancelButtonText: 'CANCEL',
            type: 'warning',
        }
    )
        .then(async () => {
            let result = await recipeDeleteService(row.id)
            ElMessage({
                type: 'success',
                message: 'Delete completed',
            })
            recipeList();
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: 'Delete canceled',
            })
        })
}

const title = ref('')
const showDialog = (row) => {
    visibleDrawer.value = true
    recipeModel.value.title = row.title;
    recipeModel.value.categoryId = row.categoryId
    recipeModel.value.ingredients = row.ingredients;
    recipeModel.value.instructions = row.instructions;
    recipeModel.value.coverImage = row.coverImage;

    recipeModel.value.id = row.id;
}

</script>


<template>
    <el-card>
        <div class="card-header">
            <span>Recipe Management</span>
            <el-button type="primary" @click="visibleDrawer = true, title = 'Post New Recipe', clearData()">ADD</el-button>
        </div>

        <div style="margin-block: 20px;">
            <hr>
        </div>

        <!-- search bar -->
        <el-form :inline="true">
            <el-form-item label="Category">
                <el-select v-model="categoryId" placeholder="PLEASE SELECT" clearable style="width: 200px;">
                    <el-option v-for="item in category" :key="item.id" :label="item.name" :value="item.id" />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="recipeList">SEARCH</el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="default" @click="categoryId = ''; recipeList()">RESET</el-button>
            </el-form-item>
        </el-form>

        <!-- recipe list -->
        <el-table :data="recipe" style="width: 100%">
            <el-table-column type="index" label="#" />
            <el-table-column prop="title" label="Title" />
            <el-table-column prop="categoryName" label="Category" />
            <el-table-column prop="createdAt" label="Creation Time" />
            <el-table-column label="Action" width="180">
                <template #default="{ row }">
                    <el-button type="primary" :icon="Edit" circle @click="showDialog(row); title = 'Update Recipe'" />
                    <el-button type="danger" :icon="Delete" circle @click="deleteRecipe(row)" />
                </template>
            </el-table-column>
        </el-table>

        <el-pagination class="el-p" v-model:current-page="currentPage" v-model:page-size="pageSize"
            :page-sizes="[5, 10, 15, 20]" :size="pageSize" :disabled="disabled" :background="background"
            layout="total, sizes, prev, pager, next, jumper" :total=total @size-change="handleSizeChange"
            @current-change="handleCurrentChange" />


        <!-- drawer -->
        <el-drawer v-model="visibleDrawer" :title="title" size="50%" :with-header="true">
            <el-form :model="recipeModel" label-width="120px">
                <el-form-item label="Title" required>
                    <el-input v-model="recipeModel.title" placeholder="Enter recipe title" />
                </el-form-item>

                <el-form-item label="Category" required>
                    <el-select v-model="recipeModel.categoryId" placeholder="Select category">
                        <el-option v-for="element in category" :key="element.id" :label="element.name"
                            :value="element.id">
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="Ingredients" required>
                    <el-input v-model="recipeModel.ingredients" type="textarea"
                        placeholder="List ingredients separated by commas" />
                </el-form-item>

                <el-form-item label="Instructions" required>
                    <el-input v-model="recipeModel.instructions" type="textarea"
                        placeholder="Describe how to prepare the dish" />
                </el-form-item>

                <el-form-item label="Cover Image">
                    <el-upload class="upload-demo" action="/api/upload" name="file"
                        :headers="{ 'Authorization': tokenStore.token }" :auto-upload="true"
                        :on-success="uploadHandler">
                        <el-image v-if="recipeModel.coverImage" :src="recipeModel.coverImage" class="avatar" />
                        <el-button type="primary">Upload Cover Image</el-button>
                    </el-upload>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary"
                        @click="title == 'Post New Recipe' ? postRecipe() : updateRecipe()">COMFIRMED</el-button>
                </el-form-item>

            </el-form>
        </el-drawer>
    </el-card>

</template>

<style scoped>
.recipe-card {
    padding: 20px;
    background-color: #fffaf0;
    border-radius: 12px;
}

.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 20px;
    font-weight: bold;
    color: #d2691e;
}

.divider {
    margin: 20px 0;
    border-bottom: 2px solid #d2691e;
}

.search-form .select-box {
    width: 200px;
}

.recipe-table {
    margin-top: 20px;
}

.el-pagination {
    margin-top: 20px;
    justify-content: center;
}

.drawer-form .avatar {
    width: 100px;
    height: 100px;
    border-radius: 8px;
}
</style>
