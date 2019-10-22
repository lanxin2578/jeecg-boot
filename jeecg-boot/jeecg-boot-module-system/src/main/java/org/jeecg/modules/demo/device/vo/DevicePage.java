package org.jeecg.modules.demo.device.vo;

import java.util.List;
import org.jeecg.modules.demo.device.entity.Device;
import org.jeecg.modules.demo.device.entity.AttrKv;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/**
 * @Description: 设备表
 * @Author: jeecg-boot
 * @Date:   2019-10-22
 * @Version: V1.0
 */
@Data
public class DevicePage {
	
	/**主键*/
	private String id;
	/**设备名称*/
	@Excel(name = "设备名称", width = 15)
	private String name;
	/**设备状态*/
	@Excel(name = "设备状态", width = 15)
	private String status;
	/**删除标识*/
	@Excel(name = "删除标识", width = 15)
	private String delFlag;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
	private String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
	private String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**所属部门*/
	@Excel(name = "所属部门", width = 15)
	private String sysOrgCode;
	
	@ExcelCollection(name="设备属性表")
	private List<AttrKv> attrKvList;	
	
}
