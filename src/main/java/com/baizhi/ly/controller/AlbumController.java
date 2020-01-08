package com.baizhi.ly.controller;

import com.baizhi.ly.dao.AlbumDao;
import com.baizhi.ly.entity.Album;
import com.baizhi.ly.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private AlbumDao albumDao;

    //分页查询专辑
    @RequestMapping("queryByPageAlbum")
    public Map queryByPage(Integer page,Integer rows){
        Map map = albumService.queryByPage(page, rows);
        return map;
    }

    //增删改专辑
    @RequestMapping("save")
    public Map save(Album album, String oper, String [] id){
        HashMap map = new HashMap();
        if("add".equals(oper)){
            String albumId = albumService.insertAlbum(album);
            map.put("albumId",albumId);
        }else  if ("edit".equals(oper)){
            String albumId = albumService.updateAlbum(album);
            map.put("albumId",albumId);
        }else {
            albumService.deleteList(Arrays.asList(id));
        }
        return map;
    }

    //文件上传
    @RequestMapping("uploadAlbum")
    public void uploadAlbum(MultipartFile cover, String albumId, HttpSession session){
        String realPath = session.getServletContext().getRealPath("/upload/img/");
        //判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        String filename = new Date().getTime()+"_"+cover.getOriginalFilename();
        //文件上传
        try {
            cover.transferTo(new File(realPath,filename));
            Album album = new Album();
            album.setId(albumId);
            album.setCover("/upload/img/"+filename);
            if (cover.getOriginalFilename().equals(null)||cover.getOriginalFilename().equals("")||cover.getOriginalFilename().equals(' ')){
            }else {
                albumDao.updateByPrimaryKeySelective(album);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
