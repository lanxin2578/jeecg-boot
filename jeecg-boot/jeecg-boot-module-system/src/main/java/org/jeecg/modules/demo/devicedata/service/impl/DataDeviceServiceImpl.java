package org.jeecg.modules.demo.devicedata.service.impl;

import org.jeecg.modules.demo.devicedata.entity.DataDevice;
import org.jeecg.modules.demo.devicedata.entity.TsKv;
import org.jeecg.modules.demo.devicedata.entity.TsKvLatest;
import org.jeecg.modules.demo.devicedata.mapper.TsKvMapper;
import org.jeecg.modules.demo.devicedata.mapper.TsKvLatestMapper;
import org.jeecg.modules.demo.devicedata.mapper.DataDeviceMapper;
import org.jeecg.modules.demo.devicedata.service.IDataDeviceService;
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
public class DataDeviceServiceImpl extends ServiceImpl<DataDeviceMapper, DataDevice> implements IDataDeviceService {

	@Autowired
	private DataDeviceMapper dataDeviceMapper;
	@Autowired
	private TsKvMapper tsKvMapper;
	@Autowired
	private TsKvLatestMapper tsKvLatestMapper;
	
	@Override
	@Transactional
	public void saveMain(DataDevice dataDevice, List<TsKv> tsKvList,List<TsKvLatest> tsKvLatestList) {
		dataDeviceMapper.insert(dataDevice);
		if(tsKvList!=null && tsKvList.size()>0) {
			for(TsKv entity:tsKvList) {
				//外键设置
				entity.setDeviceId(dataDevice.getId());
				tsKvMapper.insert(entity);
			}
		}
		if(tsKvLatestList!=null && tsKvLatestList.size()>0) {
			for(TsKvLatest entity:tsKvLatestList) {
				//外键设置
				entity.setDeviceId(dataDevice.getId());
				tsKvLatestMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(DataDevice dataDevice,List<TsKv> tsKvList,List<TsKvLatest> tsKvLatestList) {
		dataDeviceMapper.updateById(dataDevice);
		
		//1.先删除子表数据
		tsKvMapper.deleteByMainId(dataDevice.getId());
		tsKvLatestMapper.deleteByMainId(dataDevice.getId());
		
		//2.子表数据重新插入
		if(tsKvList!=null && tsKvList.size()>0) {
			for(TsKv entity:tsKvList) {
				//外键设置
				entity.setDeviceId(dataDevice.getId());
				tsKvMapper.insert(entity);
			}
		}
		if(tsKvLatestList!=null && tsKvLatestList.size()>0) {
			for(TsKvLatest entity:tsKvLatestList) {
				//外键设置
				entity.setDeviceId(dataDevice.getId());
				tsKvLatestMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		tsKvMapper.deleteByMainId(id);
		tsKvLatestMapper.deleteByMainId(id);
		dataDeviceMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			tsKvMapper.deleteByMainId(id.toString());
			tsKvLatestMapper.deleteByMainId(id.toString());
			dataDeviceMapper.deleteById(id);
		}
	}
	
}
