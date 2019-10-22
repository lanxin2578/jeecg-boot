package org.jeecg.modules.demo.device.service.impl;

import org.jeecg.modules.demo.device.entity.AttrKv;
import org.jeecg.modules.demo.device.mapper.AttrKvMapper;
import org.jeecg.modules.demo.device.service.IAttrKvService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 设备属性表
 * @Author: jeecg-boot
 * @Date:   2019-10-22
 * @Version: V1.0
 */
@Service
public class AttrKvServiceImpl extends ServiceImpl<AttrKvMapper, AttrKv> implements IAttrKvService {
	
	@Autowired
	private AttrKvMapper attrKvMapper;
	
	@Override
	public List<AttrKv> selectByMainId(String mainId) {
		return attrKvMapper.selectByMainId(mainId);
	}
}
