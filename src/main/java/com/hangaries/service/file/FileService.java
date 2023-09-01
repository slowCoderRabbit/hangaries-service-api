package com.hangaries.service.file;

import com.hangaries.model.InputFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface FileService {

    List<InputFile> uploadFiles(MultipartFile[] files, String restaurantId);

}
