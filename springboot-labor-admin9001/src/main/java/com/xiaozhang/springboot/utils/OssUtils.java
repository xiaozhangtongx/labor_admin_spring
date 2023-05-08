package com.xiaozhang.springboot.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.thread.ExecutorBuilder;
import com.aliyun.oss.*;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
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
    private static final int PART_SIZE = 5 * 1024 * 1024;


    public List<String> uploadImages(List<String> imageList) {

        List<String> urls = new ArrayList<>();

        // 遍历图片数组，提交上传任务
        for (String imageData : imageList) {
            String[] dataArray = imageData.split(",");
            byte[] data = Base64.decode(dataArray[1]);

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

    public String uploadFiles(String name, String type, long size, String url, MultipartFile file) throws IOException {

        // 生成唯一文件名
        String fileName = "file/" + new DateTime().toString("yyyy/MM/dd")
                + UUID.randomUUID().toString().replace("-", "") + "file_" + System.currentTimeMillis() + "." + name.split("\\.")[1];

        uploadFile(size, file, fileName, type);

        // 生成文件url
        String fileUrl = "https://" + BUCKET_NAME + "." + ENDPOINT + "/" + fileName;

        return fileUrl;
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


    private void uploadFile(long size, MultipartFile file, String fileName, String type) throws IOException {

        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);

        // 判断文件大小是否超过30MB
        if (size > 30 * 1024 * 1024) {
            throw new IOException("上传文件大于30MB");
        }

        // 直接上传小文件
        if (size <= PART_SIZE) {
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, fileName, file.getInputStream());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(type);
            putObjectRequest.setMetadata(objectMetadata);
            ossClient.putObject(putObjectRequest);
        } else {
// 创建OSSClient实例。
            try {
                // 创建InitiateMultipartUploadRequest对象。
                InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(BUCKET_NAME, fileName);

                // 初始化分片。
                InitiateMultipartUploadResult upresult = ossClient.initiateMultipartUpload(request);
                // 返回uploadId，它是分片上传事件的唯一标识。您可以根据该uploadId发起相关的操作，例如取消分片上传、查询分片上传等。
                String uploadId = upresult.getUploadId();

                // partETags是PartETag的集合。PartETag由分片的ETag和分片号组成。
                List<PartETag> partETags = new ArrayList<PartETag>();
                // 每个分片的大小，用于计算文件有多少个分片。单位为字节。

                // 根据上传的数据大小计算分片数。以本地文件为例，说明如何通过File.length()获取上传数据的大小。
                long fileLength = size;
                int partCount = (int) (fileLength / PART_SIZE);
                if (fileLength % PART_SIZE != 0) {
                    partCount++;
                }
                // 遍历分片上传。
                for (int i = 0; i < partCount; i++) {
                    long startPos = i * PART_SIZE;
                    long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : PART_SIZE;
                    UploadPartRequest uploadPartRequest = new UploadPartRequest();
                    uploadPartRequest.setBucketName(BUCKET_NAME);
                    uploadPartRequest.setKey(fileName);
                    uploadPartRequest.setUploadId(uploadId);
                    // 设置上传的分片流。
                    // 以本地文件为例说明如何创建FIleInputstream，并通过InputStream.skip()方法跳过指定数据。
                    InputStream instream = file.getInputStream();
                    instream.skip(startPos);
                    uploadPartRequest.setInputStream(instream);
                    // 设置分片大小。除了最后一个分片没有大小限制，其他的分片最小为100 KB。
                    uploadPartRequest.setPartSize(curPartSize);
                    // 设置分片号。每一个上传的分片都有一个分片号，取值范围是1~10000，如果超出此范围，OSS将返回InvalidArgument错误码。
                    uploadPartRequest.setPartNumber(i + 1);
                    // 每个分片不需要按顺序上传，甚至可以在不同客户端上传，OSS会按照分片号排序组成完整的文件。
                    UploadPartResult uploadPartResult = ossClient.uploadPart(uploadPartRequest);
                    // 每次上传分片之后，OSS的返回结果包含PartETag。PartETag将被保存在partETags中。
                    partETags.add(uploadPartResult.getPartETag());
                }


                // 创建CompleteMultipartUploadRequest对象。
                // 在执行完成分片上传操作时，需要提供所有有效的partETags。OSS收到提交的partETags后，会逐一验证每个分片的有效性。当所有的数据分片验证通过后，OSS将把这些分片组合成一个完整的文件。
                CompleteMultipartUploadRequest completeMultipartUploadRequest =
                        new CompleteMultipartUploadRequest(BUCKET_NAME, fileName, uploadId, partETags);

                // 完成分片上传。
                CompleteMultipartUploadResult completeMultipartUploadResult = ossClient.completeMultipartUpload(completeMultipartUploadRequest);
                System.out.println(completeMultipartUploadResult.getETag());
            } catch (OSSException oe) {
                System.out.println("Caught an OSSException, which means your request made it to OSS, "
                        + "but was rejected with an error response for some reason.");
                System.out.println("Error Message:" + oe.getErrorMessage());
                System.out.println("Error Code:" + oe.getErrorCode());
                System.out.println("Request ID:" + oe.getRequestId());
                System.out.println("Host ID:" + oe.getHostId());
            } catch (ClientException ce) {
                System.out.println("Caught an ClientException, which means the client encountered "
                        + "a serious internal problem while trying to communicate with OSS, "
                        + "such as not being able to access the network.");
                System.out.println("Error Message:" + ce.getMessage());
            } finally {
                if (ossClient != null) {
                    ossClient.shutdown();
                }
            }
        }

    }

}