package com.xuecheng.web.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Component
public class FastDFSClient {
	@Autowired
	private FastConnectionPool connectionPool;

	public String uploadFile(byte[] buff, String fileName) {
		return uploadFile(buff, fileName, null, null);
	}

	public String uploadFile(byte[] buff, String fileName, Map<String, String> metaList, String groupName){
		try {
			NameValuePair[] nameValuePairs = null;
			if (metaList != null) {
				nameValuePairs = new NameValuePair[metaList.size()];
				int index = 0;
				for (Iterator<Map.Entry<String, String>> iterator = metaList.entrySet().iterator(); iterator
						.hasNext();) {
					Map.Entry<String, String> entry = iterator.next();
					String name = entry.getKey();
					String value = entry.getValue();
					nameValuePairs[index++] = new NameValuePair(name, value);
				}
			}
			/** 获取可用的tracker,并创建存储server */
			StorageClient1 storageClient = connectionPool.checkout();
			String allowImgFormat = "gif,jpg,jpeg,png";
	        if (!allowImgFormat.contains(FilenameUtils.getExtension(fileName).toLowerCase())) {
	        	return "NOIMAGE";
	        }
			String path = null;
			if (!StringUtils.isEmpty(groupName)) {
				// 上传到指定分组
				path = storageClient.upload_file1(groupName, buff, FilenameUtils.getExtension(fileName),
						nameValuePairs);
			} else {
				path = storageClient.upload_file1(buff, FilenameUtils.getExtension(fileName), nameValuePairs);
			}

			/** 上传完毕及时释放连接 */
			connectionPool.checkin(storageClient);

			return "/"+path;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Map<String, String> getFileMetadata(String fileId) {
		try {
			/** 获取可用的tracker,并创建存储server */
			StorageClient1 storageClient = connectionPool.checkout();
			NameValuePair[] metaList = storageClient.get_metadata1(fileId);
			/** 上传完毕及时释放连接 */
			connectionPool.checkin(storageClient);
			if (metaList != null) {
				HashMap<String, String> map = new HashMap<String, String>();
				for (NameValuePair metaItem : metaList) {
					map.put(metaItem.getName(), metaItem.getValue());
				}
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int deleteFile(String fileId) {
		try {
			/** 获取可用的tracker,并创建存储server */
			StorageClient1 storageClient = connectionPool.checkout();
			//Integer delete_file = client.delete_file("group1/M00/00/00/wKgRsVjtyWqAdpG9AAAweEAzRjw047.jpg");
			int i = storageClient.delete_file1(fileId);
			/** 上传完毕及时释放连接 */
			connectionPool.checkin(storageClient);
			return i;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public byte[] downloadFile(String fileId) {
		try {
			/** 获取可用的tracker,并创建存储server */
			StorageClient1 storageClient = connectionPool.checkout();

			byte[] content = storageClient.download_file1(fileId);
			/** 上传完毕及时释放连接 */
			connectionPool.checkin(storageClient);

			return content;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
		return null;
	}

	public FileInfo getFileInfo(String fileId) {

		try {
			/** 获取可用的tracker,并创建存储server */
			StorageClient1 storageClient = connectionPool.checkout();
			FileInfo fileInfo = storageClient.get_file_info1(fileId);
			/** 上传完毕及时释放连接 */
			connectionPool.checkin(storageClient);

			return fileInfo;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
		return null;
	}

}