package org.example.api.functiontest;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 日志表
 * </p>
 *
 * @author leo
 * @since 2017-11-20
 */
@Data
@TableName("sys_log")
@EqualsAndHashCode(callSuper = true)
public class SysLog extends Model<SysLog> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		System.out.println(LocalDate.parse("2024-01-01"));
	}
	/**
	 * 编号
	 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 日志类型
	 */

	private String type;

	/**
	 * 日志标题
	 */

	private String title;

	/**
	 * 创建者
	 */

	private String createBy;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;

	/**
	 * 操作IP地址
	 */
	private String remoteAddr;

	/**
	 * 用户代理
	 */
	private String userAgent;

	/**
	 * 请求URI
	 */
	private String requestUri;

	/**
	 * 操作方式
	 */
	private String method;

	/**
	 * 操作提交的数据
	 */
	private String params;

	/**
	 * 执行时间
	 */
	private Long time;

	/**
	 * 异常信息
	 */
	private String exception;

	/**
	 * 服务ID
	 */
	private String serviceId;

	/**
	 * 删除标记
	 */
	@TableLogic
	private String delFlag;

}
