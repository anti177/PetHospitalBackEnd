package com.example.pethospitalbackend.util;

import com.aliyun.oss.*;
import com.aliyun.oss.model.*;
import com.example.pethospitalbackend.config.OSSConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

@Component
public class OSSUtil {
    private static final Logger logger = LoggerFactory.getLogger(OSSUtil.class);

    @Autowired
    private OSSConfig ossConfig;

    public void createBucket(String bucketName, CannedAccessControlList acl) {
        // 创建OSSClient实例,构造函数参数为自己的OSS帐号信息
        OSS ossClient =  new OSSClientBuilder().build(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());

        /* 通过一个Bucket对象来创建 */
        ossClient.createBucket(bucketName);
        CreateBucketRequest bucketObj = new CreateBucketRequest(bucketName);// 构造函数入参为Bucket名称，可以为空
        bucketObj.setCannedACL(acl);// 设置bucketObj访问权限acl
        ossClient.createBucket(bucketObj);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    // 上传文件
    public String uploadFile(String bucketName, MultipartFile fileupload, String filename) throws OSSException, ClientException{

        // 创建OSSClient实例
        OSS ossClient =  new OSSClientBuilder().build(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
        try {
            if(!ossClient.doesBucketExist(bucketName)) {
                // 容器不存在，就创建
                createBucket(bucketName,CannedAccessControlList.PublicRead);
            }

            PutObjectResult result=ossClient.putObject(bucketName, filename, new ByteArrayInputStream(fileupload.getBytes()));
            ossClient.setBucketAcl(bucketName,CannedAccessControlList.PublicRead);
            // 设置URL过期时间为10年 3600l* 1000*24*365*10
            Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
            URL url=ossClient.generatePresignedUrl(bucketName,filename,expiration);


            if(result != null) {
                return url.toString();
            }
        }catch (OSSException | ClientException oe) {
            //上传失败
            logger.error("[uploadFile Ex], fileupload: {}", SerialUtil.toJsonStr(fileupload.getName()), oe);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }

        return null;
    }

    // 下载文件
    public Boolean downloadFile(String bucketName, String filename, String destinationPath) throws OSSException, ClientException{
        // 创建OSSClient实例
        OSS ossClient =  new OSSClientBuilder().build(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());

        if(!ossClient.doesBucketExist(bucketName)){
            // 关闭OSSClient。
            ossClient.shutdown();
            logger.error("[uploadFile Ex], bucketName: {} doesn't exist", SerialUtil.toJsonStr(bucketName));
            return false;
        }

        ObjectMetadata objectMetadata = ossClient.getObject(new GetObjectRequest(bucketName, filename), new File(destinationPath+filename));

        // 关闭OSSClient。
        ossClient.shutdown();

        if(objectMetadata != null)
        return true;
        else {
            logger.error("[downloadFile Ex], filename: {}, destinationPath: {}", SerialUtil.toJsonStr(filename),SerialUtil.toJsonStr(destinationPath));
            return false;
        }
    }

    public Boolean deleteFile(String bucketName, String filename){
        // 创建OSSClient实例
        OSS ossClient =  new OSSClientBuilder().build(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());

        if(!ossClient.doesBucketExist(bucketName)){
            // 关闭OSSClient。
            ossClient.shutdown();
            logger.error("[uploadFile Ex], bucketName: {} doesn't exist", SerialUtil.toJsonStr(bucketName));
            return false;
        }

        // 删除文件。
        ossClient.deleteObject(bucketName, filename);

        // 关闭OSSClient。
        ossClient.shutdown();
        return true;
    }

}
