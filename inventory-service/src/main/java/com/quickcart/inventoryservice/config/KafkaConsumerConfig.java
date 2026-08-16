package com.quickcart.inventoryservice.config;

import com.quickcart.inventoryservice.event.InventoryCommitEvent;
import com.quickcart.inventoryservice.event.OrderPlacedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;


    // ==========================================
    // OrderPlacedEvent Consumer Factory
    // ==========================================

    @Bean
    public ConsumerFactory<String, OrderPlacedEvent> orderConsumerFactory() {

        Map<String, Object> properties = new HashMap<>();

        properties.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers
        );

        properties.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "inventory-group"
        );

        properties.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class
        );

        JsonDeserializer<OrderPlacedEvent> deserializer =
                new JsonDeserializer<>(OrderPlacedEvent.class);

        deserializer.addTrustedPackages("*");

        // Don't use producer's Java class name
        deserializer.setUseTypeHeaders(false);

        return new DefaultKafkaConsumerFactory<>(
                properties,
                new StringDeserializer(),
                deserializer
        );
    }


    // ==========================================
    // InventoryCommitEvent Consumer Factory
    // ==========================================

    @Bean
    public ConsumerFactory<String, InventoryCommitEvent>
    inventoryCommitConsumerFactory() {

        Map<String, Object> properties = new HashMap<>();

        properties.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers
        );

        properties.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "inventory-group"
        );

        properties.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class
        );

        JsonDeserializer<InventoryCommitEvent> deserializer =
                new JsonDeserializer<>(InventoryCommitEvent.class);

        deserializer.addTrustedPackages("*");

        // Don't use producer's Java class name
        deserializer.setUseTypeHeaders(false);

        return new DefaultKafkaConsumerFactory<>(
                properties,
                new StringDeserializer(),
                deserializer
        );
    }


    // ==========================================
    // Order Listener Factory
    // ==========================================

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderPlacedEvent>
    orderKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, OrderPlacedEvent>
                factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(orderConsumerFactory());

        return factory;
    }


    // ==========================================
    // Inventory Listener Factory
    // Used by commit + rollback
    // ==========================================

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, InventoryCommitEvent>
    inventoryKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, InventoryCommitEvent>
                factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(inventoryCommitConsumerFactory());

        return factory;
    }
}