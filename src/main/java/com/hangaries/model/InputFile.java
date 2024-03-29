package com.hangaries.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
public class InputFile {

    @Id
    @GeneratedValue
    private Long id;
    private String fileName;
    private String fileUrl;

    public InputFile(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }
}




