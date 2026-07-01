package com.campus.lostfound.controller;

import com.campus.lostfound.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传接口
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${file.upload-path}")
    private String uploadPath;

    /**
     * 图片上传
     * POST /file/upload
     * 返回访问URL： /api/upload/xxx.jpg
     */
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return Result.error("上传文件为空");
        }
        File dir = new File(uploadPath);
        if (!dir.exists()) dir.mkdirs();

        String original = file.getOriginalFilename();
        String suffix = (original != null && original.contains("."))
                ? original.substring(original.lastIndexOf("."))
                : ".jpg";
        String fileName = UUID.randomUUID().toString().replace("-", "") + suffix;

        File dest = new File(dir, fileName);
        file.transferTo(dest);

        // 返回前端可访问的URL，前端通过 Vue 代理访问 /api/upload/**
        
// 返回完整 URL，前端跨域时也能正常加载图片
String baseUrl = System.getenv("BASE_URL");
if (baseUrl == null || baseUrl.isEmpty()) {
    baseUrl = "";
}
String url = baseUrl + "/api/upload/" + fileName;
return Result.success(url);
    }
}
