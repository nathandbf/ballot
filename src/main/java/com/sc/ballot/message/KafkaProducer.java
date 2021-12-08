package com.sc.ballot.message;

import com.sc.ballot.constant.ConstanteConfig;
import com.sc.ballot.constant.ConstanteMsgLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    public void sendMessage(String msg) {

        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(ConstanteConfig.KAFKA_TOPIC, msg);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info(ConstanteMsgLog.MSG_SUCESSO.formatted(msg));
            }
            @Override
            public void onFailure(Throwable ex) {
                logger.error(ConstanteMsgLog.MSG_FAIL.formatted(msg,ex.getMessage()));
            }
        });
    }
}
