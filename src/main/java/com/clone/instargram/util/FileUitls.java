package com.clone.instargram.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;

public class FileUitls {
//CommonUtils.buildFileName(category, multipartFile.getOriginalFilename());
    public static String buildFileName(String category, String filename){
        String randomId = UUID.randomUUID().toString();
        System.out.println("파일 이름 : " + category + filename + "/" + randomId);
        return category + filename + "/" + randomId;
    }

    public static String getFilenameInUrl(String url){
        return decodeFilename(url.split("https://dong-example.s3.ap-northeast-2.amazonaws.com/")[1]);
    }

    private static String decodeFilename(String filename){
        try {
            filename = URLDecoder.decode(filename, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return filename;
    }
}
