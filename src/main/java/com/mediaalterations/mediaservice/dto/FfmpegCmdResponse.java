package com.mediaalterations.mediaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FfmpegCmdResponse {
    private long pid;
    private String processId;
    private int progress;
    private String duration;
    private String finalFileSize;
    private ProcessStatus status;
}
