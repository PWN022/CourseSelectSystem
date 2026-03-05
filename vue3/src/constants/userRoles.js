/**
 * 用户角色常量
 */
export const USER_ROLES = {
  ADMIN: 'ADMIN',
  TEACHER: 'TEACHER',
  STUDENT: 'STUDENT'
}

/**
 * 角色标签映射
 */
export const ROLE_LABELS = {
  [USER_ROLES.ADMIN]: '管理员',
  [USER_ROLES.TEACHER]: '教师',
  [USER_ROLES.STUDENT]: '学生'
}

/**
 * 角色颜色映射（用于标签显示）
 */
export const ROLE_COLORS = {
  [USER_ROLES.ADMIN]: 'danger',
  [USER_ROLES.TEACHER]: 'warning',
  [USER_ROLES.STUDENT]: 'success'
}

/**
 * 角色选项（用于表单下拉选择）
 */
export const ROLE_OPTIONS = [
  { label: ROLE_LABELS[USER_ROLES.ADMIN], value: USER_ROLES.ADMIN },
  { label: ROLE_LABELS[USER_ROLES.TEACHER], value: USER_ROLES.TEACHER },
  { label: ROLE_LABELS[USER_ROLES.STUDENT], value: USER_ROLES.STUDENT }
]

/**
 * 根据角色代码获取中文标签
 * @param {string} roleCode 角色代码 (ADMIN/TEACHER/STUDENT)
 * @returns {string} 中文标签
 */
export function getRoleLabel(roleCode) {
  return ROLE_LABELS[roleCode] || '未知角色'
}

/**
 * 根据角色代码获取标签颜色
 * @param {string} roleCode 角色代码 (ADMIN/TEACHER/STUDENT)
 * @returns {string} 颜色类型
 */
export function getRoleColor(roleCode) {
  return ROLE_COLORS[roleCode] || 'info'
}

/**
 * 检查是否为管理员角色
 * @param {string} roleCode 角色代码
 * @returns {boolean}
 */
export function isAdmin(roleCode) {
  return roleCode === USER_ROLES.ADMIN
}

/**
 * 检查是否为教师角色
 * @param {string} roleCode 角色代码
 * @returns {boolean}
 */
export function isTeacher(roleCode) {
  return roleCode === USER_ROLES.TEACHER
}

/**
 * 检查是否为学生角色
 * @param {string} roleCode 角色代码
 * @returns {boolean}
 */
export function isStudent(roleCode) {
  return roleCode === USER_ROLES.STUDENT
}

/**
 * 检查是否有管理权限（管理员或教师）
 * @param {string} roleCode 角色代码
 * @returns {boolean}
 */
export function hasManagementPermission(roleCode) {
  return [USER_ROLES.ADMIN, USER_ROLES.TEACHER].includes(roleCode)
}
