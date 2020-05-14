package com.xuecheng.test;

import com.xuecheng.framework.utils.FastDFSUtils;
import com.xuecheng.web.ConsumerApplication;
import com.xuecheng.web.service.FastDFSClient;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author lzy
 * @version 1.0
 * @date 2020/4/20 15:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ConsumerApplication.class)
public class DemoTest {

    @Test
    public void test1() {
        try {
            File file3 = new File("C:\\Users\\LZY\\Pictures\\Saved Pictures\\1111.jpg");
            FileInputStream in_file = new FileInputStream(file3);
            // 转 MultipartFile
            MultipartFile multi = new MockMultipartFile(file3.getName(), file3.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), in_file);
            String s = FastDFSUtils.uploadPic(multi);
            System.out.println(s);
        } catch (Exception e) {
            System.out.println("失败");
            e.printStackTrace();
        }
    }
    @Autowired
    protected FastDFSClient fastDFS;
    @Test
    public void test2() throws Exception{

        File file3 = new File("C:\\Users\\LZY\\Pictures\\Saved Pictures\\1111.jpg");
        FileInputStream in_file = new FileInputStream(file3);
        // 转 MultipartFile
        MultipartFile multi = new MockMultipartFile(file3.getName(), file3.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), in_file);
        String path = fastDFS.uploadFile(multi.getBytes(), multi.getOriginalFilename());
        System.out.println(path);
    }
    @Test
    public void test3() throws Exception{

        System.out.println("11111111111111");
    }

}
