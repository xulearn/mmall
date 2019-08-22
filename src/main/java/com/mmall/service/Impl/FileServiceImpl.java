package com.mmall.service.Impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service("iFileService")
@Slf4j
public class FileServiceImpl implements IFileService {

    //private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file,String path){
        String fileName = file.getOriginalFilename();
        //扩展名
        //abc.jpg
        String fileExtensionName = fileName.substring(fileName.lastIndexOf('.')+1);
        //A : abc.jpg
        //B : abc.jpg
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        log.info("开始文件上传，上传文件的文件名：{}，上传的路径是：{}：新文件名：{}",fileName,path,uploadFileName);

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path,uploadFileName);

        try {
            file.transferTo(targetFile);        //前端的文件写到服务器上
            //文件已经上传成功 ：： 上传到 tomcat服务器上 ：：在webapp下

            // 将targetfile上传到ftp服务器上
            FTPUtil.uploadFile(Lists.newArrayList(targetFile)); //已经上传到ftp服务器上

            // 上传完之后，删除upload下面的文件
            targetFile.delete();

        } catch (IOException e) {
            log.error("上传文件异常",e);
            return null;
        }
        return targetFile.getName();
    }

}
