/**
 * 日期时间格式化工具函数
 */

/**
 * 格式化日期时间为 YYYY-MM-DD HH:mm:ss 格式
 * @param {string|Date} date - 日期对象或日期字符串
 * @returns {string} 格式化后的日期时间字符串
 */
export function formatDateTime(date) {
  if (!date) return '';
  
  const d = new Date(date);
  
  // 检查日期是否有效
  if (isNaN(d.getTime())) {
    return '';
  }
  
  const year = d.getFullYear();
  const month = String(d.getMonth() + 1).padStart(2, '0');
  const day = String(d.getDate()).padStart(2, '0');
  const hours = String(d.getHours()).padStart(2, '0');
  const minutes = String(d.getMinutes()).padStart(2, '0');
  const seconds = String(d.getSeconds()).padStart(2, '0');
  
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

/**
 * 格式化日期为 YYYY-MM-DD 格式
 * @param {string|Date} date - 日期对象或日期字符串
 * @returns {string} 格式化后的日期字符串
 */
export function formatDate(date) {
  if (!date) return '';
  
  const d = new Date(date);
  
  // 检查日期是否有效
  if (isNaN(d.getTime())) {
    return '';
  }
  
  const year = d.getFullYear();
  const month = String(d.getMonth() + 1).padStart(2, '0');
  const day = String(d.getDate()).padStart(2, '0');
  
  return `${year}-${month}-${day}`;
}

/**
 * 格式化时间为 HH:mm:ss 格式
 * @param {string|Date} date - 日期对象或日期字符串
 * @returns {string} 格式化后的时间字符串
 */
export function formatTime(date) {
  if (!date) return '';
  
  const d = new Date(date);
  
  // 检查日期是否有效
  if (isNaN(d.getTime())) {
    return '';
  }
  
  const hours = String(d.getHours()).padStart(2, '0');
  const minutes = String(d.getMinutes()).padStart(2, '0');
  const seconds = String(d.getSeconds()).padStart(2, '0');
  
  return `${hours}:${minutes}:${seconds}`;
}

/**
 * 获取当前日期时间的格式化字符串
 * @returns {string} 当前日期时间的格式化字符串
 */
export function getCurrentDateTime() {
  return formatDateTime(new Date());
}

/**
 * 获取当前日期的格式化字符串
 * @returns {string} 当前日期的格式化字符串
 */
export function getCurrentDate() {
  return formatDate(new Date());
}

/**
 * 计算两个日期之间的天数差
 * @param {string|Date} date1 - 第一个日期
 * @param {string|Date} date2 - 第二个日期
 * @returns {number} 天数差
 */
export function daysBetween(date1, date2) {
  const d1 = new Date(date1);
  const d2 = new Date(date2);
  
  // 检查日期是否有效
  if (isNaN(d1.getTime()) || isNaN(d2.getTime())) {
    return NaN;
  }
  
  // 转换为UTC日期，消除时区影响
  const utc1 = Date.UTC(d1.getFullYear(), d1.getMonth(), d1.getDate());
  const utc2 = Date.UTC(d2.getFullYear(), d2.getMonth(), d2.getDate());
  
  // 计算天数差
  const MS_PER_DAY = 1000 * 60 * 60 * 24;
  return Math.floor((utc2 - utc1) / MS_PER_DAY);
}

/**
 * 日期加减天数
 * @param {string|Date} date - 基准日期
 * @param {number} days - 要加减的天数，正数为加，负数为减
 * @returns {Date} 计算后的新日期
 */
export function addDays(date, days) {
  const d = new Date(date);
  
  // 检查日期是否有效
  if (isNaN(d.getTime())) {
    return new Date(NaN);
  }
  
  d.setDate(d.getDate() + days);
  return d;
}

/**
 * 获取指定日期是星期几
 * @param {string|Date} date - 日期对象或日期字符串
 * @returns {string} 星期几的中文表示
 */
export function getDayOfWeek(date) {
  const d = new Date(date);
  
  // 检查日期是否有效
  if (isNaN(d.getTime())) {
    return '';
  }
  
  const weekdays = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
  return weekdays[d.getDay()];
} 