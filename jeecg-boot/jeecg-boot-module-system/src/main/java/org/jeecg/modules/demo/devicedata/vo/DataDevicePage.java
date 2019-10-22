package org.jeecg.modules.demo.devicedata.vo;

import java.util.List;
import org.jeecg.modules.demo.devicedata.entity.DataDevice;
import org.jeecg.modules.demo.devicedata.entity.TsKv;
import org.jeecg.modules.demo.devicedata.entity.TsKvLatest;
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
public class DataDevicePage {
	
	/**主键*/
	private java.lang.String id;
	/**设备名称*/
	@Excel(name = "设备名称", width = 15)
	private java.lang.String name;
	/**设备状态*/
	@Excel(name = "设备状态", width = 15)
	private java.lang.String status;
	/**删除标识*/
	@Excel(name = "删除标识", width = 15)
	private java.lang.String delFlag;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
	private java.lang.String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
	private java.lang.String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
	/**所属部门*/
	@Excel(name = "所属部门", width = 15)
	private java.lang.String sysOrgCode;
	
	@ExcelCollection(name="设备遥测数据")
	private List<TsKv> tsKvList;	
	@ExcelCollection(name="设备数据")
	private List<TsKvLatest> tsKvLatestList;	
	
}
