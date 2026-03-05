<template>
  <div class="course-list-container">


    <div class="search-form">
      <el-form :inline="true" :model="searchForm" ref="searchFormRef">
        <el-form-item label="课程代码">
          <el-input
            v-model="searchForm.courseCode"
            placeholder="请输入课程代码"
            clearable
          />
        </el-form-item>
        <el-form-item label="课程名称">
          <el-input
            v-model="searchForm.courseName"
            placeholder="请输入课程名称"
            clearable
          />
        </el-form-item>
        <el-form-item label="课程类型">
          <el-select
            v-model="searchForm.courseType"
            placeholder="请选择课程类型"
            clearable
          >
            <el-option
              v-for="item in courseTypes"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
          <el-button type="success" v-if="userStore.isAdmin" @click="handleAdd">添加课程</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-table
      v-loading="loading"
      :data="tableData"
      border
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="courseCode" label="课程代码" width="120" />
      <el-table-column prop="courseName" label="课程名称" width="180" />
      <el-table-column prop="credit" label="学分" width="80" />
      <el-table-column prop="hours" label="课时" width="80" />
      <el-table-column prop="courseType" label="课程类型" width="120" />
      <el-table-column prop="teacherCount" label="授课教师数" width="100" />
      <el-table-column prop="studentCount" label="选课学生数" width="100" />
      <el-table-column prop="createTime" label="创建时间">
        <template #default="scope">
          {{ formatDateTime(scope.row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="220" fixed="right">
        <template #default="scope">
          <el-button v-if="userStore.isAdmin" size="small" @click="handleEdit(scope.row)"
            >编辑</el-button
          >
          <el-button
           
            size="small"
            type="primary"
            @click="handleViewDetail(scope.row)"
            >详情</el-button
          >
          <el-button v-if="userStore.isAdmin" size="small" type="danger" @click="handleDelete(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination
        :current-page="currentPage"
        :page-size="pageSize"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <!-- 课程表单对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑课程信息' : '添加课程信息'"
      width="650px"
      destroy-on-close
    >
      <el-form
        :model="form"
        :rules="rules"
        label-width="100px"
        ref="formRef"
        style="max-height: 60vh; overflow-y: auto"
      >
        <el-form-item label="课程代码" prop="courseCode">
          <el-input v-model="form.courseCode" placeholder="请输入课程代码" />
        </el-form-item>
        <el-form-item label="课程名称" prop="courseName">
          <el-input v-model="form.courseName" placeholder="请输入课程名称" />
        </el-form-item>
        <el-form-item label="学分" prop="credit">
          <el-input-number
            v-model="form.credit"
            :min="0"
            :max="10"
            :precision="1"
            :step="0.5"
          />
        </el-form-item>
        <el-form-item label="课时" prop="hours">
          <el-input-number v-model="form.hours" :min="0" :max="200" :step="1" />
        </el-form-item>
        <el-form-item label="课程类型" prop="courseType">
          <el-select
            v-model="form.courseType"
            placeholder="请选择课程类型"
            style="width: 100%"
          >
            <el-option
              v-for="item in courseTypes"
              :key="item"
              :label="item"
              :value="item"
            />
            <el-option key="new" label="+ 添加新类型" value="new" />
          </el-select>
        </el-form-item>
        <el-form-item
          v-if="form.courseType === 'new'"
          label="新课程类型"
          prop="newCourseType"
        >
          <el-input
            v-model="form.newCourseType"
            placeholder="请输入新的课程类型"
          />
        </el-form-item>
        <el-form-item label="课程描述" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            :rows="4"
            placeholder="请输入课程描述"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 课程详情对话框 -->
    <el-dialog v-model="detailDialogVisible" title="课程详情" width="800px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="课程代码">{{
          detailForm.courseCode
        }}</el-descriptions-item>
        <el-descriptions-item label="课程名称">{{
          detailForm.courseName
        }}</el-descriptions-item>
        <el-descriptions-item label="学分">{{
          detailForm.credit
        }}</el-descriptions-item>
        <el-descriptions-item label="课时">{{
          detailForm.hours
        }}</el-descriptions-item>
        <el-descriptions-item label="课程类型">{{
          detailForm.courseType
        }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{
          formatDateTime(detailForm.createTime)
        }}</el-descriptions-item>
        <el-descriptions-item label="更新时间">{{
          formatDateTime(detailForm.updateTime)
        }}</el-descriptions-item>
        <el-descriptions-item label="课程描述" :span="2">{{
          detailForm.description
        }}</el-descriptions-item>
      </el-descriptions>

      <el-tabs v-model="activeTab" class="detail-tabs">
        <el-tab-pane label="授课教师" name="teachers">
          <el-table :data="teachersList" border style="width: 100%">
            <el-table-column prop="teacherName" label="教师姓名" />
            <el-table-column prop="semester" label="学期" />
            <el-table-column prop="createTime" label="分配时间">
              <template #default="scope">
                {{ formatDateTime(scope.row.createTime) }}
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="选课学生" name="students">
          <el-table :data="studentsList" border style="width: 100%">
            <el-table-column prop="studentNo" label="学号" />
            <el-table-column prop="studentName" label="学生姓名" />
            <el-table-column prop="teacherName" label="授课教师" />
            <el-table-column prop="semester" label="学期" />
            <el-table-column prop="createTime" label="选课时间">
              <template #default="scope">
                {{ formatDateTime(scope.row.createTime) }}
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
  </div>
</template>

<script setup>
import {onMounted, reactive, ref} from "vue";
import {ElMessageBox} from "element-plus";
import request from "@/utils/request";
import {useUserStore} from '@/store/user'

const userStore = useUserStore()
// 表格数据
const tableData = ref([]);
const loading = ref(false);
const total = ref(0);
const currentPage = ref(1);
const pageSize = ref(10);
const selectedRows = ref([]);

// 搜索表单
const searchForm = reactive({
  courseCode: "",
  courseName: "",
  courseType: "",
});
const searchFormRef = ref(null);

// 课程类型列表
const courseTypes = ref([]);

// 表单对话框
const dialogVisible = ref(false);
const isEdit = ref(false);
const formRef = ref(null);
const form = reactive({
  id: null,
  courseCode: "",
  courseName: "",
  credit: 3,
  hours: 48,
  courseType: "",
  newCourseType: "",
  description: "",
});

// 详情对话框
const detailDialogVisible = ref(false);
const detailForm = reactive({});
const activeTab = ref("teachers");
const teachersList = ref([]);
const studentsList = ref([]);

// 表单验证规则
const rules = {
  courseCode: [
    { required: true, message: "请输入课程代码", trigger: "blur" },
    { min: 3, max: 20, message: "长度在 3 到 20 个字符", trigger: "blur" },
  ],
  courseName: [
    { required: true, message: "请输入课程名称", trigger: "blur" },
    { min: 2, max: 100, message: "长度在 2 到 100 个字符", trigger: "blur" },
  ],
  credit: [{ required: true, message: "请输入学分", trigger: "change" }],
  hours: [{ required: true, message: "请输入课时", trigger: "change" }],
  courseType: [
    { required: true, message: "请选择课程类型", trigger: "change" },
  ],
  newCourseType: [
    {
      required: true,
      message: "请输入新的课程类型",
      trigger: "blur",
      validator: (rule, value, callback) => {
        if (form.courseType === "new" && !value) {
          callback(new Error("请输入新的课程类型"));
        } else {
          callback();
        }
      },
    },
  ],
};

// 初始化
onMounted(() => {
  fetchCourses();
  fetchCourseTypes();
});

// 格式化日期时间
const formatDateTime = (dateTime) => {
  if (!dateTime) return "";
  const date = new Date(dateTime);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  const hours = String(date.getHours()).padStart(2, "0");
  const minutes = String(date.getMinutes()).padStart(2, "0");
  return `${year}-${month}-${day} ${hours}:${minutes}`;
};

// 获取课程列表
const fetchCourses = async () => {
  loading.value = true;
  try {
    await request.get(
      "/course/page",
      {
        currentPage: currentPage.value,
        size: pageSize.value,
        courseCode: searchForm.courseCode,
        courseName: searchForm.courseName,
        courseType: searchForm.courseType,
      },
      {
        onSuccess: (res) => {
          tableData.value = res.records;
          total.value = res.total;
        },
      }
    );
  } catch (error) {
    console.error("获取课程列表失败:", error);
  } finally {
    loading.value = false;
  }
};

// 获取课程类型列表
const fetchCourseTypes = async () => {
  try {
    await request.get(
      "/course/types",
      {},
      {
        onSuccess: (res) => {
          courseTypes.value = res || [];
        },
      }
    );
  } catch (error) {
    console.error("获取课程类型列表失败:", error);
  }
};

// 处理查询
const handleSearch = () => {
  currentPage.value = 1;
  fetchCourses();
};

// 重置查询
const resetSearch = () => {
  searchFormRef.value.resetFields();
  currentPage.value = 1;
  fetchCourses();
};

// 处理页码变化
const handleCurrentChange = (val) => {
  currentPage.value = val;
  fetchCourses();
};

// 处理每页条数变化
const handleSizeChange = (val) => {
  pageSize.value = val;
  currentPage.value = 1;
  fetchCourses();
};

// 处理多选
const handleSelectionChange = (rows) => {
  selectedRows.value = rows;
};

// 处理添加
const handleAdd = () => {
  isEdit.value = false;
  Object.keys(form).forEach((key) => {
    form[key] = key === "credit" ? 3 : key === "hours" ? 48 : "";
  });
  dialogVisible.value = true;
};

// 处理编辑
const handleEdit = (row) => {
  isEdit.value = true;
  Object.keys(form).forEach((key) => {
    if (key !== "newCourseType") {
      form[key] = row[key];
    }
  });
  dialogVisible.value = true;
};

// 处理查看详情
const handleViewDetail = async (row) => {
  try {
    await request.get(
      `/course/${row.id}`,
      {},
      {
        onSuccess: (res) => {
          Object.assign(detailForm, res);

          // 获取授课教师列表
          fetchTeachersByCourseId(row.id);

          // 获取选课学生列表
          fetchStudentsByCourseId(row.id);

          detailDialogVisible.value = true;
        },
      }
    );
  } catch (error) {
    console.error("获取课程详情失败:", error);
  }
};

// 获取授课教师列表
const fetchTeachersByCourseId = async (courseId) => {
  try {
    await request.get(
      `/teacher-course/course/${courseId}`,
      {},
      {
        onSuccess: (res) => {
          teachersList.value = res || [];
        },
      }
    );
  } catch (error) {
    console.error("获取授课教师列表失败:", error);
  }
};

// 获取选课学生列表
const fetchStudentsByCourseId = async (courseId) => {
  try {
    await request.get(
      `/student-course/course/${courseId}`,
      {},
      {
        onSuccess: (res) => {
          studentsList.value = res || [];
        },
      }
    );
  } catch (error) {
    console.error("获取选课学生列表失败:", error);
  }
};

// 处理删除
const handleDelete = (row) => {
  ElMessageBox.confirm("确定要删除该课程吗？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      try {
        await request.delete(`/course/${row.id}`, {
          successMsg: "删除成功",
        });
        fetchCourses();
      } catch (error) {
        console.error("删除课程失败:", error);
      }
    })
    .catch(() => {});
};

// 提交表单
const submitForm = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      // 如果选择了新增课程类型
      if (form.courseType === "new" && form.newCourseType) {
        form.courseType = form.newCourseType;
      }

      try {
        if (isEdit.value) {
          await request.put("/course", form, {
            successMsg: "更新成功",
          });
        } else {
          await request.post("/course", form, {
            successMsg: "添加成功",
          });
        }
        dialogVisible.value = false;
        fetchCourses();
        fetchCourseTypes(); // 刷新课程类型列表
      } catch (error) {
        console.error("保存课程信息失败:", error);
      }
    }
  });
};
</script>

<style lang="scss" scoped>
.course-list-container {
  padding: 20px;

  .teacher-alert {
    margin-bottom: 20px;
    
    p {
      margin: 5px 0;
      &:first-child {
        margin-top: 0;
      }
      &:last-child {
        margin-bottom: 0;
      }
    }
  }

  .search-form {
    margin-bottom: 20px;
    padding: 20px;
    background-color: #fff;
    border-radius: 4px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }

  .el-table {
    margin-bottom: 20px;
  }

  .pagination-container {
    display: flex;
    justify-content: flex-end;
    margin-top: 20px;
  }

  .detail-tabs {
    margin-top: 20px;
  }
}
</style> 