package com.viveknaskar.eventsubscriber;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class EventSubscriberServiceTest {

    private EventSubscriberService eventSubscriberService;
    private PubSubTemplate pubSubTemplate = mock(PubSubTemplate.class);
    private MessageChannel messageChannel = mock(MessageChannel.class);

    @BeforeEach
    private void beforeEach() {
        eventSubscriberService = new EventSubscriberService();
        eventSubscriberService.setSubscriptionName("pubsubdemoSubscription");
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