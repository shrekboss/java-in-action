package org.crayzer.err.design.apidesgin.apiasyncsyncmode;

import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class FileService {

    private ExecutorService threadPool = Executors.newFixedThreadPool(2);
    //计数器，作为上传任务的ID
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    //暂存上传操作的结果，生产代码需要考虑数据持久化
    private ConcurrentHashMap<String, SyncQueryUploadTaskResponse> downloadUrl = new ConcurrentHashMap<>();

    private String uploadFile(byte[] data) {
        try {
            TimeUnit.MILLISECONDS.sleep(500 + ThreadLocalRandom.current().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "http://www.demo.com/download/" + UUID.randomUUID().toString();
    }

    private String uploadThumbnailFile(byte[] data) {
        try {
            TimeUnit.MILLISECONDS.sleep(1500 + ThreadLocalRandom.current().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "http://www.demo.com/download/" + UUID.randomUUID().toString();
    }

    public UploadResponse upload(UploadRequest request) {
        UploadResponse response = new UploadResponse();
        Future<String> uploadFile = threadPool.submit(() -> uploadFile(request.getFile()));
        Future<String> uploadThumbnailFile = threadPool.submit(() -> uploadThumbnailFile(request.getFile()));
        try {
            response.setDownloadUrl(uploadFile.get(1, TimeUnit.SECONDS));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            response.setThumbnailDownloadUrl(uploadThumbnailFile.get(1, TimeUnit.SECONDS));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public SyncUploadResponse syncUpload(SyncUploadRequest request) {
        SyncUploadResponse response = new SyncUploadResponse();
        response.setDownloadUrl(uploadFile(request.getFile()));
        response.setThumbnailDownloadUrl(uploadThumbnailFile(request.getFile()));
        return response;
    }

    //异步上传操作
    public AsyncUploadResponse asyncUpload(AsyncUploadRequest request) {
        AsyncUploadResponse response = new AsyncUploadResponse();
        //生成唯一的上传任务ID
        String taskId = "upload" + atomicInteger.incrementAndGet();
        //异步上传操作只返回任务ID
        response.setTaskId(taskId);
        //提交上传原始文件操作到线程池异步处理
        threadPool.execute(() -> {
            String url = uploadFile(request.getFile());
            //如果ConcurrentHashMap不包含Key，则初始化一个SyncQueryUploadTaskResponse，然后设置DownloadUrl
            downloadUrl.computeIfAbsent(taskId, id -> new SyncQueryUploadTaskResponse(id)).setDownloadUrl(url);
        });
        //提交上传缩略图操作到线程池异步处理
        threadPool.execute(() -> {
            String url = uploadThumbnailFile(request.getFile());
            downloadUrl.computeIfAbsent(taskId, id -> new SyncQueryUploadTaskResponse(id)).setThumbnailDownloadUrl(url);
        });
        return response;
    }

    public SyncQueryUploadTaskResponse syncQueryUploadTask(SyncQueryUploadTaskRequest request) {
        SyncQueryUploadTaskResponse response = new SyncQueryUploadTaskResponse(request.getTaskId());
        response.setDownloadUrl(downloadUrl.getOrDefault(request.getTaskId(), response).getDownloadUrl());
        response.setThumbnailDownloadUrl(downloadUrl.getOrDefault(request.getTaskId(), response).getThumbnailDownloadUrl());
        return response;
    }
}
