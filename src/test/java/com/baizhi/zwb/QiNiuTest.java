package com.baizhi.zwb;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;

public class QiNiuTest {

    @Test
    public void test(){
        //构造一个带指定 Region 对象的配置类   参数：机房（华东，...）
        Configuration cfg = new Configuration(Region.region1());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "1dCrj4u261Bu0OPOBb-go-RcsRHwcs1L2-UJNoib";//1dCrj4u261Bu0OPOBb-go-RcsRHwcs1L2-UJNoib
        String secretKey = "67Msb_flsg0RtwbyND-1TYdxYJNeHUrP2Es3ymA4";//67Msb_flsg0RtwbyND-1TYdxYJNeHUrP2Es3ymA4
        String bucket = "yingx-zwb";//存储空间的名字
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "E:\\aaa.jpg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "aaa.jpg"; //指定文件名
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
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
}
