package com.clrvn.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * @Description: 文件下载工具类
 * @Author: 刘炜程
 */
@SuppressWarnings("all")
public class ResponseUtils {

    /**
     * 构建下载类
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static ResponseEntity<byte[]> buildResponseEntity(File file, String fileName) throws IOException {
        byte[] body = null;
        //获取文件
        InputStream is = new FileInputStream(file);
        body = new byte[is.available()];
        is.read(body);
        HttpHeaders headers = new HttpHeaders();

        //设置文件类型
        System.err.println("文件名" + fileName);
        //文件名解码
        headers.add("Content-Disposition", "attchement;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        //设置Http状态码
        HttpStatus statusCode = HttpStatus.OK;
        //返回数据
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
        return entity;
    }
}
