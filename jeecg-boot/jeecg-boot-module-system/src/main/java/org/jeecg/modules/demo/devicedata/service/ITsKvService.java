package org.jeecg.modules.demo.devicedata.service;

import org.jeecg.modules.demo.devicedata.entity.TsKv;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 设备遥测数据
 * @Author: jeecg-boot
 * @Date:   2019-10-22
 * @Version: V1.0
 */
public interface ITsKvService extends IService<TsKv> {

	public List<TsKv> selectByMainId(String mainId);
}
