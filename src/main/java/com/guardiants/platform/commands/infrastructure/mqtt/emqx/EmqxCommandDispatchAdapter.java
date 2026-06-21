package com.guardiants.platform.commands.infrastructure.mqtt.emqx;

import com.guardiants.platform.commands.application.internal.outboundservices.mqtt.CommandDispatchPort;
import com.guardiants.platform.commands.domain.model.events.CommandAckReceivedEvent;
import com.guardiants.platform.commands.domain.model.valueobjects.CommandType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * EMQX MQTT command dispatch adapter (dev stub).
 *
 * <p>In production this publishes to the EMQX topic {@code vehicles/{vehicleId}/commands} and
 * the device ACK arrives asynchronously via {@link #onAckReceived}. In dev it logs the dispatch
 * and simulates the ACK immediately by publishing a {@link CommandAckReceivedEvent}.</p>
 */
@Slf4j
@Component
public class EmqxCommandDispatchAdapter implements CommandDispatchPort {

    private final ApplicationEventPublisher eventPublisher;

    public EmqxCommandDispatchAdapter(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void dispatch(Long commandId, Long vehicleId, CommandType type) {
        log.info("MQTT STUB — dispatching {} to vehicleId={} commandId={}",
                type, vehicleId, commandId);
        simulateAck(commandId, vehicleId, true);
    }

    private void simulateAck(Long commandId, Long vehicleId, boolean success) {
        log.info("MQTT STUB — simulating ACK for commandId={} success={}", commandId, success);
        eventPublisher.publishEvent(new CommandAckReceivedEvent(commandId, vehicleId, success));
    }

    /** Called when a real MQTT ACK message arrives from the device. */
    public void onAckReceived(String topic, byte[] payload) {
        // TODO: parse topic and payload to extract commandId + success flag
        log.info("MQTT ACK received on topic={}", topic);
    }
}
