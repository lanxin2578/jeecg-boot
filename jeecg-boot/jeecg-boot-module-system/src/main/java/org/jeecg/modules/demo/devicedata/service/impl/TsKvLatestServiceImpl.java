package org.jeecg.modules.demo.devicedata.service.impl;

import org.jeecg.modules.demo.devicedata.entity.TsKvLatest;
import org.jeecg.modules.demo.devicedata.mapper.TsKvLatestMapper;
import org.jeecg.modules.demo.devicedata.service.ITsKvLatestService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 设备数据
 * @Author: jeecg-boot
 * @Date:   2019-10-22
 * @Version: V1.0
 */
@Service
public class TsKvLatestServiceImpl extends ServiceImpl<TsKvLatestMapper, TsKvLatest> implements ITsKvLatestService {
	
	@Autowired
	private TsKvLatestMapper tsKvLatestMapper;
	
	@Override
	public List<TsKvLatest> selectByMainId(String mainId) {
		return tsKvLatestMapper.selectByMainId(mainId);
	}
}
