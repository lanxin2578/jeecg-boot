package org.jeecg.modules.demo.device.service;

import org.jeecg.modules.demo.device.entity.AttrKv;
import org.jeecg.modules.demo.device.entity.Device;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 设备表
 * @Author: jeecg-boot
 * @Date:   2019-10-22
 * @Version: V1.0
 */
public interface IDeviceService extends IService<Device> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(Device device, List<AttrKv> attrKvList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(Device device, List<AttrKv> attrKvList);
	
	/**
	 * 删除一对多
	 */
	public void delMain(String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);
	
}
