package com.xiaozhang.springboot.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateTime;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class OssUtils {

    private static final String endpoint = "oss-cn-shenzhen.aliyuncs.com";

    private static final String accessKeyId = "xxx";

    private static final String accessKeySecret = "xxx";

    private static final String bucketName = "labor-safe";

    private static Integer finish= 1;

    // 创建OSSClient实例。
    private static  OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

    public List<String> uploadOneFile(List<String> urls)  {

        List<String> uploadUrls=new ArrayList<>();
        Integer index=1;
        Integer nums=urls.size();
        Long start = System.currentTimeMillis();
        System.out.println("start = " + start);
        Thread uploaderThread = null;
        for (String url : urls) {
            String fileName = new DateTime().toString("yyyy/MM/dd")
                    + UUID.randomUUID().toString().replace("-", "")+index+".png";
            uploadUrls.add(fileName);
            uploaderThread = new Thread(new Fileuploader(url,start,nums,index,fileName));
            uploaderThread.start();
            index++;
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return uploadUrls;
    }
    // 文件上传器
    static class Fileuploader implements Runnable{
        private final String fileURL;
        private final Long start;
        private final Integer nums;
        private final Integer index;
        private final String fileName;
        public Fileuploader(String fileURL,Long start,Integer nums,Integer index,String fileName) {
            this.fileURL = fileURL;
            this.start = start;
            this.nums= nums;
            this.index= index;
            this.fileName= fileName;
        }
        @Override
        public void run() {
            try {
                System.out.println("线程"+index+"开始执行："+System.currentTimeMillis());
                byte[] bytesFile = Base64.decode(fileURL);
                InputStream inputStream = new ByteArrayInputStream(bytesFile);
                ossClient.putObject(bucketName, fileName, inputStream);
                System.out.println("图片" +index + "上传图片完成！");
                Long end = System.currentTimeMillis();
                Long time = end - start;
                System.out.println("time = " + time);

            } catch (Exception e) {
                System.out.println("图片" +index + "上传图片失败！");
                e.printStackTrace();
            }
            finally {
                if(finish.equals(nums))
                {
                    ossClient.shutdown();
                }
                else {
                    finish=finish+1;
                }
        }

    }
        }
    }
