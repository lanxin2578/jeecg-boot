package org.jeecg.modules.demo.devicedata.service;

import org.jeecg.modules.demo.devicedata.entity.TsKv;
import org.jeecg.modules.demo.devicedata.entity.TsKvLatest;
import org.jeecg.modules.demo.devicedata.entity.DataDevice;
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
public interface IDataDeviceService extends IService<DataDevice> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(DataDevice dataDevice,List<TsKv> tsKvList,List<TsKvLatest> tsKvLatestList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(DataDevice dataDevice,List<TsKv> tsKvList,List<TsKvLatest> tsKvLatestList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
