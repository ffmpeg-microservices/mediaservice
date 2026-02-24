package com.mediaalterations.mediaservice.service;

import com.mediaalterations.mediaservice.dto.ProcessDto;
import org.springframework.stereotype.Service;

public interface MediaService {
    void workOnProcess(ProcessDto process) throws Exception;

    String killProcess(String processId);
}
