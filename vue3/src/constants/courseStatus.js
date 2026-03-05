/**
 * 选课状态常量
 */
export const COURSE_STATUS = {
  PENDING: '待审批',
  APPROVED: '已通过', 
  REJECTED: '已拒绝',
  DROP_PENDING: '退课待审批'
}

/**
 * 状态标签映射
 */
export const STATUS_LABELS = {
  [COURSE_STATUS.PENDING]: '待审批',
  [COURSE_STATUS.APPROVED]: '已通过',
  [COURSE_STATUS.REJECTED]: '已拒绝',
  [COURSE_STATUS.DROP_PENDING]: '退课待审批'
}

/**
 * 状态颜色映射
 */
export const STATUS_COLORS = {
  [COURSE_STATUS.PENDING]: 'warning',
  [COURSE_STATUS.APPROVED]: 'success',
  [COURSE_STATUS.REJECTED]: 'danger',
  [COURSE_STATUS.DROP_PENDING]: 'info'
}

/**
 * 根据状态获取标签
 * @param {string} status 状态值
 * @returns {string} 状态标签
 */
export function getStatusLabel(status) {
  return STATUS_LABELS[status] || status || '未知'
}

/**
 * 根据状态获取颜色
 * @param {string} status 状态值
 * @returns {string} 颜色类型
 */
export function getStatusColor(status) {
  return STATUS_COLORS[status] || 'info'
}
