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
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.test.util.ReflectionTestUtils;

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
    void messageChannelAdapter() {
        PubSubInboundChannelAdapter pubSubInboundChannelAdapter = eventSubscriberService.messageChannelAdapter(messageChannel, pubSubTemplate);
        assertNotNull(pubSubInboundChannelAdapter);
    }

    @Test
    void pubsubInputChannel() {
        DirectChannel directChannel = (DirectChannel) eventSubscriberService.pubsubInputChannel();
        assertNotNull(directChannel);
    }

    @Test
    void messageReceiver() {
        MessageHandler messageHandler = eventSubscriberService.messageReceiver();
        assertNotNull(messageHandler);
    }
}