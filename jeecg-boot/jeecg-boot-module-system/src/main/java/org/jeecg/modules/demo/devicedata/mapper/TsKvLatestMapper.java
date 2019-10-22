package org.jeecg.modules.demo.devicedata.mapper;

import java.util.List;
import org.jeecg.modules.demo.devicedata.entity.TsKvLatest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 设备数据
 * @Author: jeecg-boot
 * @Date:   2019-10-22
 * @Version: V1.0
 */
public interface TsKvLatestMapper extends BaseMapper<TsKvLatest> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<TsKvLatest> selectByMainId(@Param("mainId") String mainId);
}
