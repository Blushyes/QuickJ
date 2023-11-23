package xyz.blushyes.file;

import java.io.InputStream;

/**
 * 提供文件上传的接口
 *
 * <p>
 * 示例：
 * <pre>
 * quick-file:
 *   strategy-impl: xyz.blushyes.file.AliyunOSS
 *   aliyun-oss:
 *     endpoint: @aliyun.oss.endpoint@
 *     bucket-name: @aliyun.oss.bucket-name@
 *     access-key-id: @aliyun.oss.access-key-id@
 *     access-key-secret: @aliyun.oss.access-key-secret@
 * </pre>
 */
public interface QuickFIle {
    /**
     * 上传文件
     * @param file 文件流，可以通过 {@link java.io.FileInputStream} 来获取文件流。
     * @param contentType 文件类型，如 image/png
     * @param storagePath 存储路径，如 /images/avatar.png
     * @return 文件在存储上的路径，如 https://example.com/images/avatar.png
     */
    String upload(InputStream file, String contentType, String storagePath);

    /**
     * 下载文件
     * @param storagePath 存储路径，如 /images/avatar.png
     * @return 文件流，可以通过 {@link java.io.FileInputStream} 来获取文件流。
     */
    byte[] download(String storagePath);
}
