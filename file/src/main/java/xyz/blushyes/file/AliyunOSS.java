package xyz.blushyes.file;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@ConfigurationProperties("quick-file.aliyun-oss")
public class AliyunOSS implements QuickFIle {
    private String endpoint;
    private String bucketName;

    private String accessKeyId;

    private String accessKeySecret;

    private OSS ossClient;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    private OSS getOssClient() {
        return Optional.ofNullable(ossClient).orElse(new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret));
    }

    protected void connectAndExecute(ConnectionFunc connectionFunc) {
        try {
            connectionFunc.run();
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
            throw new RuntimeException(oe);
        } catch (Throwable ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
            throw new RuntimeException(ce);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    @Override
    public String upload(InputStream file, String contentType, String storagePath) {
        // 创建OSSClient实例。
        OSS ossClient = getOssClient();
        List<String> properties = Arrays.asList(Strings.EMPTY, Strings.EMPTY);

        connectAndExecute(() -> {
            // 自定义数据
            properties.set(0, UUID.randomUUID().toString());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(contentType);
            objectMetadata.setContentDisposition("inline;filename=" + properties.get(0));
            properties.set(1, properties.get(0));
            properties.set(0, storagePath + "/" + properties.get(0));

            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, properties.get(0), file, objectMetadata);
            // 创建PutObject请求。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
        });
        return properties.get(1);
    }

    @Override
    public byte[] download(String storagePath) {
        OSS ossClient = getOssClient();
        AtomicReference<byte[]> bytes = new AtomicReference<>(new byte[0]);
        connectAndExecute(() -> {
            // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
            OSSObject ossObject = ossClient.getObject(bucketName, storagePath);

            // 读取文件内容。
            BufferedInputStream inputStream = new BufferedInputStream(ossObject.getObjectContent());
            bytes.set(inputStream.readAllBytes());
            // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            inputStream.close();
            // ossObject对象使用完毕后必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            ossObject.close();
        });
        return bytes.get();
    }

    @FunctionalInterface
    public interface ConnectionFunc {
        void run() throws OSSException, Throwable;
    }
}