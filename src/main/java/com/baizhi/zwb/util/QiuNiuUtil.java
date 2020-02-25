package com.baizhi.zwb.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

/*
* 主要用来上传视频，下载视频和删除视频
*/
public class QiuNiuUtil {

    //上传视频到七牛云
    public static void upload(MultipartFile file,String fileName) throws IOException {
        //将文件转换为字节数组
        byte[] bytes = file.getBytes();

        //构造一个带指定 Region 对象的配置类   参数：机房（华东，...）
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "1dCrj4u261Bu0OPOBb-go-RcsRHwcs1L2-UJNoib";
        String secretKey = "67Msb_flsg0RtwbyND-1TYdxYJNeHUrP2Es3ymA4";
        String bucket = "yingxzwb";//上传空间名
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
       // String localFilePath = "";//此处不要了，用字节数组传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = fileName; //指定文件名：时间戳+原文件名
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(bytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    //上传文件到七牛云
    public static void upload(String filePath,String fileName) throws IOException {
        //构造一个带指定 Region 对象的配置类   参数：机房（华东，...）
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "1dCrj4u261Bu0OPOBb-go-RcsRHwcs1L2-UJNoib";
        String secretKey = "67Msb_flsg0RtwbyND-1TYdxYJNeHUrP2Es3ymA4";
        String bucket = "yingxzwb";//上传空间名
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        // String localFilePath = "";//此处不要了，用字节数组传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = fileName; //指定文件名：时间戳+原文件名
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(filePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    //删除视频
     //fileName:  文件名
    public static void delete(String fileName){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        String accessKey = "1dCrj4u261Bu0OPOBb-go-RcsRHwcs1L2-UJNoib";
        String secretKey = "67Msb_flsg0RtwbyND-1TYdxYJNeHUrP2Es3ymA4";
        String bucket = "yingxzwb";
        //...其他参数参考类注释
        //String bucket = "184-video";  //存储空间的名字
        //String key = "人民的名义.mp4";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, fileName);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }
}
