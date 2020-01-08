package com.baizhi.ly.controller;

import com.baizhi.ly.dao.ChapterDao;
import com.baizhi.ly.entity.Chapter;
import com.baizhi.ly.service.ChapterService;
import com.baizhi.ly.util.HttpUtil;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private ChapterDao chapterDao;

    //根据专辑id查询所有章节
    @RequestMapping("queryBypageChapter")
    public Map queryBypage(Integer page,Integer rows,String albumId){
        Map map = chapterService.queryByPage(page, rows, albumId);
        return map;
    }

    //章节增删改
    @RequestMapping("save")
    public Map save(Chapter chapter, String oper, String [] id,String albumId){
        HashMap map = new HashMap();
        if("add".equals(oper)){
            String chapterId = chapterService.insertChapter(chapter,albumId);
            map.put("chapterId",chapterId);
        }else  if ("edit".equals(oper)){
            String chapterId = chapterService.updateChapter(chapter);
            map.put("chapterId",chapterId);
        }else {
            chapterService.deleteList(Arrays.asList(id));
        }
        return map;
    }

    //音频上传
    @RequestMapping("uploadChapter")
    public void uploadChapter(MultipartFile url, String chapterId, HttpSession session, HttpServletRequest request) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        String realPath = session.getServletContext().getRealPath("/upload/music/");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String http = HttpUtil.getHttp(url, request, "/upload/music/");
        Chapter chapter = new Chapter();
        chapter.setId(chapterId);
        chapter.setUrl(http);
        // 计算文件大小
        Double size = Double.valueOf(url.getSize() / 1024 / 1024);
        chapter.setSize(size);
        // 计算音频时长
        // 使用三方计算音频时间工具类 得出音频时长
        String[] split = http.split("/");
        // 获取文件名
        String name = split[split.length - 1];
        // 通过文件获取AudioFile对象 音频解析对象
        AudioFile read = AudioFileIO.read(new File(realPath, name));
        // 通过音频解析对象 获取 头部信息 为了信息更准确 需要将AudioHeader转换为MP3AudioHeader
        MP3AudioHeader audioHeader = (MP3AudioHeader) read.getAudioHeader();
        // 获取音频时长 秒
        int trackLength = audioHeader.getTrackLength();
        String time = trackLength / 60 + "分" + trackLength % 60 + "秒";
        chapter.setTime(time);
        if (url.getOriginalFilename().equals(null)||url.getOriginalFilename().equals("")||url.getOriginalFilename().equals(' ')){
        }else {
            chapterDao.updateByPrimaryKeySelective(chapter);
        }
    }

    //音频下载
    @RequestMapping("downloadChapter")
    public void downloadChapter(String url, HttpServletResponse response, HttpSession session) throws IOException {
        // 处理url路径 找到文件
        String[] split = url.split("/");
        String realPath = session.getServletContext().getRealPath("/upload/music/");
        String name = split[split.length-1];
        File file = new File(realPath, name);
        // 调用该方法时必须使用 location.href 不能使用ajax ajax不支持下载
        // 通过url获取本地文件
        response.setHeader("Content-Disposition", "attachment; filename="+name);
        ServletOutputStream outputStream = response.getOutputStream();
        FileUtils.copyFile(file,outputStream);
        // FileUtils.copyFile("服务器文件",outputStream)
        // FileUtils.copyFile();
    }
}
