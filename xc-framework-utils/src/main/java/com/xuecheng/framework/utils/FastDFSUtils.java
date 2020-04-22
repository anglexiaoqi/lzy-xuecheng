package com.xuecheng.framework.utils;

import org.apache.commons.io.FilenameUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传图片到分布式文件系统
 * @author lzy
 *
 */

public class FastDFSUtils {
	//上传图片  返回路径
		public static String uploadPic(MultipartFile pic) throws Exception{
			//Spring 提供 帮助动态 获取 classpath 下的文件路径
			// 初始化文件资源
	        ClientGlobal.initByProperties("application.properties");
			/*fastdfs.connect_timeout_in_seconds=5
			fastdfs.network_timeout_in_seconds=30
			fastdfs.charset=UTF-8
			fastdfs.http_anti_steal_token=false
			fastdfs.http_secret_key=FastDFS1234567890
			fastdfs.http_tracker_http_port=80
			fastdfs.tracker_servers=192.168.5.233:22122*/
			/*ClientGlobal.setG_connect_timeout(10);
			ClientGlobal.setG_charset("UTF-8");
			ClientGlobal.setG_anti_steal_token(false);
			ClientGlobal.setG_network_timeout(30);
			ClientGlobal.setG_secret_key("FastDFS1234567890");
			ClientGlobal.setG_tracker_http_port(80);
			//Tracker服务器列表 
			InetSocketAddress[] trackerServers = new InetSocketAddress[1];
	        trackerServers[0] = new InetSocketAddress("192.168.5.233", 22122);
	        
	        ClientGlobal.setG_tracker_group(new TrackerGroup(trackerServers));
	        
	        System.out.println("============"+ClientGlobal.getG_tracker_group().tracker_servers[0].getHostName());*/
			//tracker客户端
			TrackerClient trackerClient = new TrackerClient();
			//与Tracker连接
			TrackerServer trackerServer = trackerClient.getConnection();
			//创建一个NUll值 的Stage的服务端
			StorageServer storageServer = null;
			
			//创建Stoage的客户端 
			StorageClient1 storageClient1 = new StorageClient1(trackerServer,storageServer);
			//上传时的文件名 获取文件的扩展名
			String ext = FilenameUtils.getExtension(pic.getOriginalFilename());
			
			NameValuePair[] meta_list = new NameValuePair[3];
			
			//图片原始名 
			meta_list[0] = new NameValuePair("filename",pic.getOriginalFilename());
			//图片大小
			meta_list[1] = new NameValuePair("filelength",String.valueOf(pic.getSize()));
			//图片扩展名
			meta_list[2] = new NameValuePair("ext",ext);
			
			//上传图片
			//http://192.168.200.128/group1/M00/00/01/wKjIgFWOYc6APpjAAAD-qk29i78248.jpg
			String path = storageClient1.upload_file1(pic.getBytes(), ext, meta_list);
			
			return path;
		}

}
