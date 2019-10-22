package org.jeecg.modules.demo.devicedata.service;

import org.jeecg.modules.demo.devicedata.entity.TsKvLatest;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 设备数据
 * @Author: jeecg-boot
 * @Date:   2019-10-22
 * @Version: V1.0
 */
public interface ITsKvLatestService extends IService<TsKvLatest> {

	public List<TsKvLatest> selectByMainId(String mainId);
}
