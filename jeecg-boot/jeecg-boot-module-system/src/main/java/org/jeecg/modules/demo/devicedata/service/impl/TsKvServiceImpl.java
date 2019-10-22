package org.jeecg.modules.demo.devicedata.service.impl;

import org.jeecg.modules.demo.devicedata.entity.TsKv;
import org.jeecg.modules.demo.devicedata.mapper.TsKvMapper;
import org.jeecg.modules.demo.devicedata.service.ITsKvService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 设备遥测数据
 * @Author: jeecg-boot
 * @Date:   2019-10-22
 * @Version: V1.0
 */
@Service
public class TsKvServiceImpl extends ServiceImpl<TsKvMapper, TsKv> implements ITsKvService {
	
	@Autowired
	private TsKvMapper tsKvMapper;
	
	@Override
	public List<TsKv> selectByMainId(String mainId) {
		return tsKvMapper.selectByMainId(mainId);
	}
}
