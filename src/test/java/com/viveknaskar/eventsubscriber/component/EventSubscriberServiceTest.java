package com.viveknaskar.eventsubscriber.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class EventSubscriberServiceTest {

    @Mock
    private PubSubTemplate pubSubTemplate;

    @Mock
    private MessageChannel messageChannel;

    @InjectMocks
    private EventSubscriberService eventSubscriberService;

    @BeforeEach
    private void beforeEach() {
        eventSubscriberService = new EventSubscriberService();
        ReflectionTestUtils.setField(eventSubscriberService, "subscriptionName", "pubsubdemoSubscription");
    }

    @Test
    public void messageChannelAdapter() {
        PubSubInboundChannelAdapter pubSubInboundChannelAdapter = eventSubscriberService.messageChannelAdapter(messageChannel, pubSubTemplate);
        assertNotNull(pubSubInboundChannelAdapter);
    }

    @Test
    public void pubsubInputChannel() {
        DirectChannel directChannel = (DirectChannel) eventSubscriberService.pubsubInputChannel();
        assertNotNull(directChannel);
    }

    @Test
    public void messageReceiver() {
        MessageHandler messageHandler = eventSubscriberService.messageReceiver();
        assertNotNull(messageHandler);
    }

    @Test
    public void testSubscriptionMessage() {
        String messagePayload = "Hello World!";
        MessageChannel replies = new DirectChannel();
        Message<?> mockMessage = MessageBuilder.withPayload(messagePayload.getBytes(StandardCharsets.UTF_8))
                .setReplyChannel(replies)
                .build();
        eventSubscriberService.generateReportHandler(mockMessage);
    }
}