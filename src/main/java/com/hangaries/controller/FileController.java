package com.hangaries.controller;

import com.hangaries.model.InputFile;
import com.hangaries.service.file.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private final FileService fileService;


    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "uploadFile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public List<InputFile> addFile(@RequestParam("files") MultipartFile[] files, @RequestParam("restaurantId") String restaurantId) {
        logger.info("[{}] files request received for restaurantId [{}] upload. ", files.length, restaurantId);
        return fileService.uploadFiles(files, restaurantId);
    }

}
