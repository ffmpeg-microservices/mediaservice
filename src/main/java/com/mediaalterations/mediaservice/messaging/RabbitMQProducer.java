package com.mediaalterations.mediaservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mediaalterations.mediaservice.dto.FfmpegCmdResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.progress}")
    private String exchange;

    @Value("${rabbitmq.queue.progress.routingKey}")
    private String ffmpegProgressRoutingKey;

    public void publishFfmpegProcessProgress(FfmpegCmdResponse event) {
        log.info("Publishing Ffmpeg process progress: pid={} processId={} progress={}", event.getPid(),
                event.getProcessId(), event.getProgress());
        rabbitTemplate.convertAndSend(
                exchange,
                ffmpegProgressRoutingKey,
                event);
    }
}
