package org.jeecg.modules.demo.device.service.impl;

import org.jeecg.modules.demo.device.entity.Device;
import org.jeecg.modules.demo.device.entity.AttrKv;
import org.jeecg.modules.demo.device.mapper.AttrKvMapper;
import org.jeecg.modules.demo.device.mapper.DeviceMapper;
import org.jeecg.modules.demo.device.service.IDeviceService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 设备表
 * @Author: jeecg-boot
 * @Date:   2019-10-22
 * @Version: V1.0
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {

	@Autowired
	private DeviceMapper deviceMapper;
	@Autowired
	private AttrKvMapper attrKvMapper;
	
	@Override
	@Transactional
	public void saveMain(Device device, List<AttrKv> attrKvList) {
		deviceMapper.insert(device);
		if(attrKvList!=null && attrKvList.size()>0) {
			for(AttrKv entity:attrKvList) {
				//外键设置
				entity.setDeviceId(device.getId());
				attrKvMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(Device device,List<AttrKv> attrKvList) {
		deviceMapper.updateById(device);
		
		//1.先删除子表数据
		attrKvMapper.deleteByMainId(device.getId());
		
		//2.子表数据重新插入
		if(attrKvList!=null && attrKvList.size()>0) {
			for(AttrKv entity:attrKvList) {
				//外键设置
				entity.setDeviceId(device.getId());
				attrKvMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		attrKvMapper.deleteByMainId(id);
		deviceMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			attrKvMapper.deleteByMainId(id.toString());
			deviceMapper.deleteById(id);
		}
	}
	
}
