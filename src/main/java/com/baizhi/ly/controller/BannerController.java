package com.baizhi.ly.controller;

import com.alibaba.excel.EasyExcel;
import com.baizhi.ly.dao.BannerDao;
import com.baizhi.ly.entity.Banner;
import com.baizhi.ly.listener.BannerListener;
import com.baizhi.ly.service.BannerService;
import com.baizhi.ly.util.ExcelImportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private BannerDao bannerDao;

    //轮播图  分页查询
    @RequestMapping("queryByPage")
    public Map queryByPage(Integer page,Integer rows){
        Map map = bannerService.queryAllFenYe(page, rows);
        return map;
    }

    //增删改轮播图
    @RequestMapping("save")
    public Map save(Banner banner,String oper,String [] id){
        HashMap map = new HashMap();
        if("add".equals(oper)){
            String bannerId = bannerService.insertBanner(banner);
            map.put("bannerId",bannerId);
        }else  if ("edit".equals(oper)){
            String bannerId = bannerService.updateBanner(banner);
            map.put("bannerId",bannerId);
        }else {
            bannerService.deleteList(Arrays.asList(id));
        }
        return map;
    }

    //文件上传
    @RequestMapping("uploadBanner")
    public void uploadBanner(MultipartFile url, String bannerId, HttpSession session){
        System.out.println(bannerId);
        String realPath = session.getServletContext().getRealPath("/img/");
        //判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        String filename = new Date().getTime()+"_"+url.getOriginalFilename();
        //文件上传
        try {
            url.transferTo(new File(realPath,filename));
            Banner banner = new Banner();
            banner.setId(bannerId);
            banner.setUrl("/img/"+filename);
            if (url.getOriginalFilename().equals(null)||url.getOriginalFilename().equals("")||url.getOriginalFilename().equals(' ')){
            }else {
                bannerDao.updateByPrimaryKeySelective(banner);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //导出Excel表格 生成文件
    @RequestMapping("exportBanner")
    public void exportBanner(HttpServletResponse response) throws IOException {
        List<Banner> bannerList = bannerDao.selectAll();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("Banner信息"+new Date().getTime(), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), Banner.class).sheet("Banner模板").doWrite(bannerList);
    }


    //从Excel表格导入  传入数据
    @RequestMapping("importBanner")
    public String importBanner(MultipartFile bannerFile, HttpServletRequest request) throws IOException {
        String uploadPath = ExcelImportUtil.getUploadPath(bannerFile, request, "/upload/bannerExcel/");
        String realPath = request.getServletContext().getRealPath(uploadPath);
        EasyExcel.read(realPath, Banner.class, new BannerListener(bannerService)).sheet().doRead();
        return "ok";
    }


}
