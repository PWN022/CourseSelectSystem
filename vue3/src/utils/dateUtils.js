/**
 * 日期格式化工具类
 */
export default class DateUtils {
  /**
   * 格式化日期
   * @param {Date|string|number} date 日期对象/日期字符串/时间戳
   * @param {string} format 格式化模式 支持：
   * YYYY: 年
   * MM: 月
   * DD: 日
   * HH: 时
   * mm: 分
   * ss: 秒
   * @returns {string} 格式化后的日期字符串
   */
  static format(date, format = 'YYYY-MM-DD') {
    // 转换输入日期为Date对象
    const dateObj = date instanceof Date ? date : new Date(date);
    
    // 如果日期无效则返回空字符串
    if (isNaN(dateObj.getTime())) {
      return '';
    }

    // 获取日期各个部分
    const year = dateObj.getFullYear();
    const month = dateObj.getMonth() + 1;
    const day = dateObj.getDate();
    const hours = dateObj.getHours();
    const minutes = dateObj.getMinutes();
    const seconds = dateObj.getSeconds();

    // 补零函数
    const padZero = (num) => num.toString().padStart(2, '0');

    // 替换格式字符串
    return format
      .replace('YYYY', year)
      .replace('MM', padZero(month))
      .replace('DD', padZero(day))
      .replace('HH', padZero(hours))
      .replace('mm', padZero(minutes))
      .replace('ss', padZero(seconds));
  }

  /**
   * 格式化为年月 YYYY-MM
   * @param {Date|string|number} date 日期
   * @returns {string}
   */
  static formatYearMonth(date) {
    return this.format(date, 'YYYY-MM');
  }

  /**
   * 格式化为年月日 YYYY-MM-DD
   * @param {Date|string|number} date 日期
   * @returns {string}
   */
  static formatDate(date) {
    return this.format(date, 'YYYY-MM-DD');
  }

  /**
   * 格式化为完整日期时间 YYYY-MM-DD HH:mm:ss
   * @param {Date|string|number} date 日期
   * @returns {string}
   */
  static formatDateTime(date) {
    return this.format(date, 'YYYY-MM-DD HH:mm:ss');
  }
}

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