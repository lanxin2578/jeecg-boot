package org.jeecg.modules.demo.device.service;

import org.jeecg.modules.demo.device.entity.AttrKv;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 设备属性表
 * @Author: jeecg-boot
 * @Date:   2019-10-22
 * @Version: V1.0
 */
public interface IAttrKvService extends IService<AttrKv> {

	public List<AttrKv> selectByMainId(String mainId);
}
