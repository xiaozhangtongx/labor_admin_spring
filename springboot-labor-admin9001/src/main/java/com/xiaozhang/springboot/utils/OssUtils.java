package com.xiaozhang.springboot.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.thread.ExecutorBuilder;
import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * @author: xiaozhangtx
 * @ClassName: OssUtils
 * @Description: TODO Oss工具
 * @date: 2023/5/5 20:34
 * @Version: 1.0
 */
@Component
@Slf4j
@Scope("prototype")
public class OssUtils {

    private static final String ENDPOINT = "oss-cn-beijing.aliyuncs.com";
    private static final String ACCESS_KEY_ID = "LTAI5tJukUY5o6zeMX18XQXG";
    private static final String ACCESS_KEY_SECRET = "i6e0yova1mHN8ZXFOusZRFMAzEiA20";
    private static final String BUCKET_NAME = "laboradmin";
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;


    public List<String> uploadImages(List<String> imageList) {

        List<String> urls = new ArrayList<>();

        // 遍历图片数组，提交上传任务
        for (String imageData : imageList) {
            byte[] data = Base64.decode(imageData);

            // 如果图片大小超过 5MB，进行拦截
            if (data.length > MAX_FILE_SIZE) {
                System.out.println("Image is too large (size: " + data.length + "), skipped.");
                continue;
            }

            // 创建上传任务
            String imageName = "img/" + new DateTime().toString("yyyy/MM/dd")
                    + UUID.randomUUID().toString().replace("-", "") + "image_" + System.currentTimeMillis() + ".png";
            InputStream inputStream = new ByteArrayInputStream(data);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/png");
            metadata.setContentLength(data.length);

            uploadImage(imageName, inputStream, metadata);

            String imageUrl = "https://" + BUCKET_NAME + "." + ENDPOINT + "/" + imageName;
            urls.add(imageUrl);

        }
        return urls;
    }

    private void uploadImage(String imageName, InputStream inputStream, ObjectMetadata metadata) {
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        // 设置失败请求重试次数，默认值为3次。
        conf.setMaxErrorRetry(5);
        // 创建线程池，用于多线程上传
        ExecutorService executor = ExecutorBuilder.create()
                .setCorePoolSize(5)
                .setMaxPoolSize(10)
                .setWorkQueue(new LinkedBlockingQueue<>(100))
                .build();

        PutObjectRequest putRequest = new PutObjectRequest(BUCKET_NAME, imageName, inputStream, metadata);
        try {
            // 提交上传任务到线程池
            executor.submit(() -> {
                try {
                    // 开始上传任务
                    OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);

                    ossClient.putObject(putRequest);

                    ossClient.shutdown();
                    // 输出图片 URL
                    String imageUrl = "https://" + BUCKET_NAME + "." + ENDPOINT + "/" + imageName;
                    log.info("Image uploaded successfully: " + imageUrl);
                } catch (Exception e) {
                    log.error("Error uploading image: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            log.error("Error processing image: " + e.getMessage());
        }
        // 关闭线程池和 OSSClient 对象
        executor.shutdown();
    }

}