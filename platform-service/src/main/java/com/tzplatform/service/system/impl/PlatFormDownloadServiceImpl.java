package com.tzplatform.service.system.impl;

import com.tzplatform.service.system.PlatFormDownloadService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service(value = "platFormDownloadService")
public class PlatFormDownloadServiceImpl implements PlatFormDownloadService {


    @Value("${register.doc.path}")
    private String fileRegisterInfodocPath;

    private final String registerfileName = "platform_registerinfo";


    @Override
    public ResponseEntity<byte[]> downloadRegisterDoc(String fileName) throws IOException {
        File file = new File(PlatFormDownloadServiceImpl.class.getClassLoader().getResource("").getPath() + File.separator + fileRegisterInfodocPath);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", registerfileName + ".doc");
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }
}
