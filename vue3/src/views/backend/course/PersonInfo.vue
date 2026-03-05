    <!-- 学生特有信息卡片 -->
    <el-card v-if="userStore.isStudent" class="info-card">
      <template #header>
        <div class="card-header">
          <span>学生信息</span>
        </div>
      </template>
      <el-form
        ref="studentFormRef"
        :model="studentForm"
        :rules="studentRules"
        label-width="100px"
        :disabled="!isEditing"
      >
        <el-form-item label="学号" prop="studentNo">
          <el-input v-model="studentForm.studentNo" disabled />
        </el-form-item>

        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
        </el-form-item>

        <el-form-item label="出生日期" prop="birthDate">
          <el-date-picker
            v-model="studentForm.birthDate"
            type="date"
            placeholder="选择出生日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>

        <el-form-item label="家庭住址" prop="address">
          <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
        </el-form-item>

        <el-form-item label="班级" prop="className">
          <el-input v-model="studentForm.className" disabled />
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 学生班主任信息卡片 -->
    <el-card v-if="userStore.isStudent && studentForm.headTeacherId" class="info-card">
      <template #header>
        <div class="card-header">
          <span>班主任信息</span>
        </div>
      </template>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="姓名">{{ studentForm.headTeacherName }}</el-descriptions-item>
        <el-descriptions-item label="职称">{{ studentForm.headTeacherTitle }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ studentForm.headTeacherPhone }}</el-descriptions-item>
        <el-descriptions-item label="电子邮箱">{{ studentForm.headTeacherEmail }}</el-descriptions-item>
      </el-descriptions>
    </el-card>

    <el-card class="password-card"> 

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form-item>
    </el-form>

    <!-- 学生表单数据 -->
    <el-form
      ref="studentFormRef"
      :model="studentForm"
      :rules="studentRules"
      label-width="100px"
      :disabled="!isEditing"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input v-model="studentForm.studentNo" disabled />
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
      </el-form-item>

      <el-form-item label="出生日期" prop="birthDate">
        <el-date-picker
          v-model="studentForm.birthDate"
          type="date"
          placeholder="选择出生日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
      </el-form-item>

      <el-form-item label="班级" prop="className">
        <el-input v-model="studentForm.className" disabled />
      </el-form