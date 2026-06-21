package com.guardiants.platform.commands.application.internal.outboundservices.mqtt;

import com.guardiants.platform.commands.domain.model.valueobjects.CommandType;

public interface CommandDispatchPort {
    void dispatch(Long commandId, Long vehicleId, CommandType type);
}
