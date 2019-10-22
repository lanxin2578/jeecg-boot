package org.jeecg.modules.demo.device.mapper;

import java.util.List;
import org.jeecg.modules.demo.device.entity.AttrKv;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 设备属性表
 * @Author: jeecg-boot
 * @Date:   2019-10-22
 * @Version: V1.0
 */
public interface AttrKvMapper extends BaseMapper<AttrKv> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<AttrKv> selectByMainId(@Param("mainId") String mainId);
}
