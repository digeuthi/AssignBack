package com.example.board.service.implement;


import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.board.service.FileService;

@Service
public class FileServiceImplement implements FileService {

    @Value("${file.path}") 
    private String FILE_PATH;
    @Value("${file.url}") 
    private String FILE_URL;

    @Override
    public String upload(MultipartFile file) {
        //검증
        if(file.isEmpty()) return null;

        //파일명 가져오기
        String originalFileName = file.getOriginalFilename();
        // 확장자 가져오기
        int extensionIndex = originalFileName.lastIndexOf("."); //.이 있는 위치의 인덱스 가져오기
        String extension = originalFileName.substring(extensionIndex); //. 이후의 확장자 가져오는것

        // 파일의 새로운 이름 지정 //여러 사람들이 이미지를 올리면 그때 마다 파일 이름이 여러개일것. 이거 설정
        String uuid = UUID.randomUUID().toString();
        String saveName = uuid + extension; //새로운 파일 이름이 생성됨.

        // 파일저장 경로 지정
        String savaPath = FILE_PATH + saveName;

        try {
            //파일 저장
            file.transferTo(new File(savaPath));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
        //클라이언트가 해당 파일에 접근하기 위한 url
        String fileUrl = FILE_URL + saveName;
        return fileUrl;
    }

    @Override
    public Resource getFile(String fileName) {
        
        Resource file = null; //참조변수 만들기

        try {
            // 파일을 url로 가져오기
            String url = "file:" + FILE_PATH + fileName;
            file = new UrlResource(url);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return file;
    }
    
}
