package com.example.ticket.config;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.LoggingErrorHandler;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.JavaUtils;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.AlwaysRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.backoff.ExponentialBackOff;

@Configuration
public class KafkaTopicConfig {
    @Value(value = "${kafka.admin.bootstrapAddress}")
    private String bootstrapAddress;

    private static final Logger log = LoggerFactory.getLogger(KafkaTopicConfig.class);

    // @Bean
    // public LoggingErrorHandler errorHandler() {
    //   return new LoggingErrorHandler();
    // }

    @Bean
    public SeekToCurrentErrorHandler errorHandler(DeadLetterPublishingRecoverer deadLetterPublishingRecoverer) {
      var backOff = new ExponentialBackOff(1000, 1.5);
      backOff.setMaxElapsedTime((long)(1000 * Math.pow(1.5,4)));
      var handler = new SeekToCurrentErrorHandler(deadLetterPublishingRecoverer);
      return handler;
    }

    @Bean
    public DeadLetterPublishingRecoverer publisher(KafkaOperations<?,?> operations) {
      return new DeadLetterPublishingRecoverer(operations);
    }

    @Bean
	public RecordMessageConverter converter() {
        return new StringJsonMessageConverter();
	}
    
    @Bean
    public KafkaAdmin kafkaAdmin() {

        log.info("\n\n\n\n*************************************************************\nbootstrapAddress:::    " + bootstrapAddress);

        
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topicTicketBooked() {
        return new NewTopic("ticketBooked", 1, (short) 1);
    }

    @Bean
    public NewTopic topicEventPublished() {
        return new NewTopic("eventPublished", 1, (short) 1);
    }

    @Bean
    public NewTopic topicEventPublishedTopic() {
        return new NewTopic("testTopic", 1, (short) 1);
    }
}

