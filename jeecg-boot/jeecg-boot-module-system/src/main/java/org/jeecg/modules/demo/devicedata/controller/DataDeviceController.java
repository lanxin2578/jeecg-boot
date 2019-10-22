package org.jeecg.modules.demo.devicedata.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.devicedata.entity.TsKv;
import org.jeecg.modules.demo.devicedata.entity.TsKvLatest;
import org.jeecg.modules.demo.devicedata.entity.DataDevice;
import org.jeecg.modules.demo.devicedata.vo.DataDevicePage;
import org.jeecg.modules.demo.devicedata.service.IDataDeviceService;
import org.jeecg.modules.demo.devicedata.service.ITsKvService;
import org.jeecg.modules.demo.devicedata.service.ITsKvLatestService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;

 /**
 * @Description: 设备表
 * @Author: jeecg-boot
 * @Date:   2019-10-22
 * @Version: V1.0
 */
@RestController
@RequestMapping("/devicedata/dataDevice")
@Slf4j
public class DataDeviceController {
	@Autowired
	private IDataDeviceService dataDeviceService;
	@Autowired
	private ITsKvService tsKvService;
	@Autowired
	private ITsKvLatestService tsKvLatestService;
	
	/**
	 * 分页列表查询
	 *
	 * @param dataDevice
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DataDevice dataDevice,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DataDevice> queryWrapper = QueryGenerator.initQueryWrapper(dataDevice, req.getParameterMap());
		Page<DataDevice> page = new Page<DataDevice>(pageNo, pageSize);
		IPage<DataDevice> pageList = dataDeviceService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param dataDevicePage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DataDevicePage dataDevicePage) {
		DataDevice dataDevice = new DataDevice();
		BeanUtils.copyProperties(dataDevicePage, dataDevice);
		dataDeviceService.saveMain(dataDevice, dataDevicePage.getTsKvList(),dataDevicePage.getTsKvLatestList());
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param dataDevicePage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DataDevicePage dataDevicePage) {
		DataDevice dataDevice = new DataDevice();
		BeanUtils.copyProperties(dataDevicePage, dataDevice);
		DataDevice dataDeviceEntity = dataDeviceService.getById(dataDevice.getId());
		if(dataDeviceEntity==null) {
			return Result.error("未找到对应数据");
		}
		dataDeviceService.updateMain(dataDevice, dataDevicePage.getTsKvList(),dataDevicePage.getTsKvLatestList());
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		dataDeviceService.delMain(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.dataDeviceService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DataDevice dataDevice = dataDeviceService.getById(id);
		if(dataDevice==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(dataDevice);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryTsKvByMainId")
	public Result<?> queryTsKvListByMainId(@RequestParam(name="id",required=true) String id) {
		List<TsKv> tsKvList = tsKvService.selectByMainId(id);
		return Result.ok(tsKvList);
	}
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryTsKvLatestByMainId")
	public Result<?> queryTsKvLatestListByMainId(@RequestParam(name="id",required=true) String id) {
		List<TsKvLatest> tsKvLatestList = tsKvLatestService.selectByMainId(id);
		return Result.ok(tsKvLatestList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param dataDevice
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DataDevice dataDevice) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<DataDevice> queryWrapper = QueryGenerator.initQueryWrapper(dataDevice, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<DataDevice> queryList = dataDeviceService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<DataDevice> dataDeviceList = new ArrayList<DataDevice>();
      if(oConvertUtils.isEmpty(selections)) {
          dataDeviceList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          dataDeviceList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<DataDevicePage> pageList = new ArrayList<DataDevicePage>();
      for (DataDevice main : dataDeviceList) {
          DataDevicePage vo = new DataDevicePage();
          BeanUtils.copyProperties(main, vo);
          List<TsKv> tsKvList = tsKvService.selectByMainId(main.getId());
          vo.setTsKvList(tsKvList);
          List<TsKvLatest> tsKvLatestList = tsKvLatestService.selectByMainId(main.getId());
          vo.setTsKvLatestList(tsKvLatestList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "设备表列表");
      mv.addObject(NormalExcelConstants.CLASS, DataDevicePage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("设备表数据", "导出人:"+sysUser.getRealname(), "设备表"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
    }

    /**
    * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<DataDevicePage> list = ExcelImportUtil.importExcel(file.getInputStream(), DataDevicePage.class, params);
              for (DataDevicePage page : list) {
                  DataDevice po = new DataDevice();
                  BeanUtils.copyProperties(page, po);
                  dataDeviceService.saveMain(po, page.getTsKvList(),page.getTsKvLatestList());
              }
              return Result.ok("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
    }

}
