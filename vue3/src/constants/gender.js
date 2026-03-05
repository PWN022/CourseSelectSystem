/**
 * 性别常量
 */
export const GENDER = {
  MALE: 'M',
  FEMALE: 'F'
}

/**
 * 性别标签映射
 */
export const GENDER_LABELS = {
  [GENDER.MALE]: '男',
  [GENDER.FEMALE]: '女'
}

/**
 * 性别选项（用于表单）
 */
export const GENDER_OPTIONS = [
  { label: '男', value: GENDER.MALE },
  { label: '女', value: GENDER.FEMALE }
]

/**
 * 根据性别代码获取中文标签
 * @param {string} genderCode 性别代码 (M/F)
 * @returns {string} 中文标签
 */
export function getGenderLabel(genderCode) {
  return GENDER_LABELS[genderCode] || '未知'
}
