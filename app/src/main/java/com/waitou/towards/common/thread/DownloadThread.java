package com.waitou.towards.common.thread;

import com.blankj.utilcode.util.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by waitou on 17/3/27.
 * 下载
 */

public class DownloadThread implements Runnable {

    private int                    id;
    private File                   file;
    private String                 url;
    private DownloadThreadListener listener;
    private OkHttpClient           client;

    public static DownloadThread get(int id, String url, String path, DownloadThreadListener listener) {
        return new DownloadThread(id, url, path, listener);
    }

    private DownloadThread(int id, String url, String path, DownloadThreadListener listener) {
        this.id = id;
        this.url = url;
        this.listener = listener;
        FileUtils.createFileByDeleteOldFile(path);
        file = FileUtils.getFileByPath(path);
    }

    public DownloadThread setClient(OkHttpClient client) {
        this.client = client;
        return this;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        FileOutputStream fos = null;
        ResponseBody body = null;
        try {
            Request request = new Request.Builder()
                    .url(url).build();
            Response response = client.newCall(request).execute();
            body = response.body();
            final long total;
            if (body != null) {
                total = body.contentLength();
                inputStream = body.byteStream();
                fos = new FileOutputStream(file);
                byte[] buffer = new byte[2 * 1024];
                int len;
                int bufferOffset = 0;
                while ((len = inputStream.read(buffer)) != -1) {
                    bufferOffset += len;
                    fos.write(buffer, 0, len);
                    if (listener != null) {
                        listener.onDownloading(id, bufferOffset * 1.0f / total, bufferOffset >= total, file);
                    }
                }
                fos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) inputStream.close();
                if (fos != null) fos.close();
                if (body != null) body.close();
                ThreadPoolProxyFactory.createNormalThreadPoolProxy().remove(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        ThreadPoolProxyFactory.createNormalThreadPoolProxy().execute(this);
    }

    public interface DownloadThreadListener {
        void onDownloading(int id, float progress, boolean isCompleted, File file);
    }
}
