package com.tzplatform.service.system;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface PlatFormDownloadService {

    ResponseEntity<byte[]>  downloadRegisterDoc(String fileName) throws IOException;

}
