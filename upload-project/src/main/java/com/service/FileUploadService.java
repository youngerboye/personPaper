package com.service;

import com.domain.FileOutput;
import com.model.AttachmentConfig;
import com.repository.AttachmentConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author: Young
 * @description: 文件上传
 * @date: Created in 17:00 2018/9/7
 * @modified by:
 */
@Service
public class FileUploadService {

    @Autowired
    private AttachmentConfigRepository attachmentConfigRepository;

    public FileOutput upload(MultipartFile file, Integer resourceType) throws IOException {
        FileOutput fileOutput = new FileOutput();
        //获取文件名//20180926工作情况-杨正武.xlsx
        String fileName = file.getOriginalFilename();
        //获取末尾的扩展名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        AttachmentConfig attachmentConfig = attachmentConfigRepository.findByConfigTypeAndState(resourceType, 0);
        if (attachmentConfig == null) {
            fileOutput.setMessage("上传配置信息错误无法配置");
            fileOutput.setSuccess(false);
            return fileOutput;
        }

        String allowFiles = attachmentConfig.getAllowFiles();
        if (allowFiles != null) {
            if (!allowFiles.contains(suffixName)) {
                fileOutput.setMessage("上传文件类型错误");
                fileOutput.setSuccess(false);
                return fileOutput;
            }
        }

        //获取文件的大小(KB)
        Long fileSize = file.getSize() / 1024;
        if (attachmentConfig.getMaxSize() != null) {
            if (attachmentConfig.getMaxSize() < fileSize) {
                fileOutput.setMessage("文件超过最大限制");
                fileOutput.setSuccess(false);
                return fileOutput;
            }
        }
        if (attachmentConfig.getMinSize() != null) {
            if (attachmentConfig.getMinSize() > fileSize) {
                fileOutput.setMessage("文件过小");
                fileOutput.setSuccess(false);
                return fileOutput;
            }
        }

        int index = fileName.lastIndexOf("\\");
        if (index != -1) {
            fileName = fileName.substring(index + 1);
        }

        int hasCode = fileName.hashCode();
        String hex = Integer.toString(hasCode);
        String filePath = "D:\\nginx\\nginx\\html\\uploadFile";
        String returnPath = "/" + hex.charAt(0) + "/" + hex.charAt(1) + "/" + System.currentTimeMillis() + "_" + fileName;
        filePath = filePath + returnPath;

        File fileDir = new File(filePath);
        if (!fileDir.getParentFile().exists()) {
            fileDir.getParentFile().mkdirs();
        }
        InputStream inputStream = file.getInputStream();
        FileOutputStream fos = new FileOutputStream(fileDir);
        BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);
        Integer length = 0;
        byte[] buffer = new byte[1024];
        while ((length = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, length);
        }
        bos.flush();
        bos.close();
        inputStream.close();
        file.transferTo(fileDir);
        fileOutput.setFilePath(returnPath);
        fileOutput.setFileName(fileName);
        fileOutput.setSuffixName(suffixName);
        fileOutput.setFileSize(fileSize);
        fileOutput.setSuccess(true);
        fileOutput.setMessage("上传成功");
        return fileOutput;
    }

    /**
     * 压缩文件方法
     *
     * @param sourceFilePath 待压缩的文件路径
     * @param zipFilePath    :压缩后存放路径
     * @return
     */
    public FileOutput fileToZip(String sourceFilePath, String zipFilePath) throws IOException {
        String suffixName = sourceFilePath.substring(sourceFilePath.lastIndexOf("."));
        AttachmentConfig attachmentConfig = attachmentConfigRepository.findAttachmentConfigByAllowFiles(suffixName);
        FileOutput fileOutput = new FileOutput();
        File sourceFile = new File(sourceFilePath);
        FileInputStream fis;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        String zipFileName = null;
        Long lenth;
        File zipFile = null;
        if (!sourceFile.exists()) {
            fileOutput.setMessage("待压缩的文件目录不存在");
            fileOutput.setSuccess(false);
            return fileOutput;
        } else {
            try {
                zipFile = new File(zipFilePath);
                zipFileName = zipFile.getName();
                if (zipFile.exists()) {
                    fileOutput.setMessage("目录名下已存在打包文件");
                    fileOutput.setSuccess(false);
                    return fileOutput;
                } else {
                    fos = new FileOutputStream(zipFile);
                    zos = new ZipOutputStream(new BufferedOutputStream(fos));
                    byte[] bufs = new byte[1024 * 10];

                    //创建ZIP实体，并添加进压缩包
                    ZipEntry zipEntry = new ZipEntry(sourceFile.getName());
                    zos.putNextEntry(zipEntry);
                    //读取待压缩的文件并写进压缩包里
                    fis = new FileInputStream(sourceFile);
                    bis = new BufferedInputStream(fis, 1024 * 10);
                    int read;
                    while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                        zos.write(bufs, 0, read);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                bis.close();
                zos.close();
                fos.close();
            }
        }
        lenth = zipFile.length();
        fileOutput.setSuccess(true);
        fileOutput.setFilePath(zipFilePath);
        fileOutput.setFileName(zipFileName);
        fileOutput.setFileSize(lenth / 1024);
        if (attachmentConfig.getCompressMaxWidth() < fileOutput.getFileSize()) {
            fileOutput.setMessage("压缩文件大于可压缩最大宽度");
            fileOutput.setSuccess(false);
            return fileOutput;
        }
        return fileOutput;
    }
}
