package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/file")
@Slf4j
public class FIleController {


    @PostMapping("/upload")
    public String uploadFile(HttpServletRequest request){
        String user = request.getParameter("user");
        String age = request.getParameter("age"
        );
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> pics = multipartRequest.getFiles("pics");
        return  user + "," + age + "," + pics.size();
    }
}
