package com.xuecheng.web.FastDFS;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.xuecheng.framework.result.ResultData;
import com.xuecheng.web.service.FastDFSClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author lzy
 * @version 1.0
 * @date 2020/4/20 15:16
 */

@Controller
@RequestMapping(value = "/upload")
@Slf4j
public class FastDFSController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String PATH = System.getProperty("user.dir")
            +"/template.xlsx";
    @Autowired
    protected FastDFSClient fastDFS;
    @Value("${img.imgUrl}")
    protected String imgUrl;
   /* @Autowired
    private FastDFSUtils fastDFSUtils;*/

    @RequestMapping( value = "/excelout")
    public void excelStandardTemplateOut(HttpServletResponse response, HttpServletRequest request) throws IOException {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            //下载文件的名称
            //response.setHeader("Content-Disposition", "attachment;filename="+ new String(("template.xlsx").getBytes(), "utf-8"));
            String agent = request.getHeader("User-agent");
            if(agent.indexOf("Firefox")!=-1) {
                response.addHeader("content-Disposition", "attachment;fileName==?UTF-8?B?"+new String(Base64.encodeBase64("template.xlsx".getBytes("utf-8")))+"?=");
            }else if(agent.indexOf("Edge")!=-1) {
                response.addHeader("content-Disposition", "attachment;fileName="+ URLEncoder.encode("template.xlsx", "utf-8"));
            }else {
                response.setHeader("Content-Disposition", "attachment;filename="+ new String(("批量新增签名模板.xlsx").getBytes(), "ISO-8859-1"));
            }

            ServletOutputStream out = response.getOutputStream();
            logger.info("PATH:[{}]",PATH);
            bis = new BufferedInputStream(new FileInputStream(new File(PATH)));
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[1024];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (IOException e) {
            logger.info("下载模板失败",e);
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
    }

    //上传图片
    @RequestMapping(value = "/upload/uploadPic.do")
    public void uploadPic(@RequestParam MultipartFile pic, HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException{

        System.out.println(pic.getOriginalFilename());

        //日期格式化
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        //当前时间
        //图片的名称
        String name = df.format(new Date());

        //三位随机数
        Random r = new Random();
        for (int i = 0; i < 3; i++) {
            name += r.nextInt(10);
        }
        //获取扩展名 apache  common.io.jar中已经提供获取方法
        String ext = FilenameUtils.getExtension(pic.getOriginalFilename());
        //  /upload/ + name ".jpg" 全路径
        //相对路径
        String path = "/upload/" + name + "." + ext;
        //上传路径
        String url = request.getSession().getServletContext().getRealPath("") + path;

        //上传图片的方法
        pic.transferTo(new File(url));

        //JSON对象
        JSONObject jo = new JSONObject();
        jo.put("path", path);

        //1：对响应设置类型 JSON
        response.setContentType("application/json;charset=UTF-8");
        //2:在响应中写入JSON格式的字符串
        response.getWriter().write(jo.toString());
    }

    @ResponseBody
    @RequestMapping("/upload")
    public void upload(@RequestParam(value="file",required=false)MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception {
        log.info("上传图片名称,{}", file.getOriginalFilename());

        String path = fastDFS.uploadFile(file.getBytes(), file.getOriginalFilename());
        //显示图片Url
        String url = imgUrl + path;
        //JSON对象
        JSONObject jo = new JSONObject();
        jo.put("path", path);
        jo.put("url", url);
        //1：对响应设置类型 JSON
        response.setContentType("application/json;charset=UTF-8");
        //2:在响应中写入JSON格式的字符串
        response.getWriter().write(jo.toString());
    }
    @ResponseBody
    @RequestMapping("/uploadImg")
    public void uploadPicture(@RequestParam(value="file",required=false)MultipartFile file,HttpServletRequest request,HttpServletResponse response){
        ResultData result = ResultData.built();
        Map<String, Object> map = new HashMap<String, Object>();
        File targetFile=null;
        String url="";//返回存储路径
        int code=1;
        System.out.println(file);
        String fileName=file.getOriginalFilename();//获取文件名加后缀

        if(StringUtils.isNotBlank(fileName)){
            //	if(fileName!=null&&fileName!=""){
            String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/upload/imgs/";//存储路径
            String path = request.getSession().getServletContext().getRealPath("upload/imgs"); //文件存储位置
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
            fileName=new Date().getTime()+"_"+new Random().nextInt(1000)+fileF;//新的文件名
            //先判断文件是否存在
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fileAdd = sdf.format(new Date());
            //获取文件夹路径
            File file1 =new File(path+"/"+fileAdd);
            //如果文件夹不存在则创建
            if(!file1 .exists()  && !file1 .isDirectory()){
                file1 .mkdir();
            }
            //将图片存入文件夹
            targetFile = new File(file1, fileName);
            try {
                //将上传的文件写到服务器上指定的文件。
                file.transferTo(targetFile);
                url=returnUrl+fileAdd+"/"+fileName;
                code=0;
                result.success("图片上传成功");
                result.addResult("url", url);
            } catch (Exception e) {
                e.printStackTrace();
                result.success("系统异常，图片上传失败");
            }
        }
        writeJson(response, result);
    }
    /**
     * 输出JSON数据
     *
     * @param response
     */
    public void writeJson(HttpServletResponse response, Object obj) {
        response.setContentType("text/json;charset=utf-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        PrintWriter pw = null;
        Gson gson = new Gson();
        try {
            pw = response.getWriter();
            pw.write(gson.toJson(obj));

            pw.flush();
        } catch (Exception e) {
            logger.info("输出JSON数据异常", e);
        }finally{
            if(pw!=null){
                pw.close();
            }
        }
    }
}
