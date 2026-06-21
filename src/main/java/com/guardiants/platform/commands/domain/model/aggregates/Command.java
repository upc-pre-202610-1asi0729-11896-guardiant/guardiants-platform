package com.guardiants.platform.commands.domain.model.aggregates;

import com.guardiants.platform.commands.domain.model.commands.*;
import com.guardiants.platform.commands.domain.model.valueobjects.*;
import com.guardiants.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "commands")
public class Command extends AbstractDomainAggregateRoot<Command> {

    @Column(nullable = false)
    private Long vehicleId;

    @Column(nullable = false)
    private Long issuedByUserId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CommandType type;

    private Long triggeredByAlertId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private CommandStatus status;

    @Column(nullable = false)
    private Instant issuedAt;

    private Instant dispatchedAt;
    private Instant acknowledgedAt;
    private Instant completedAt;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private CommandResult result;

    /** Constructor — ENGINE_BLOCK */
    public Command(IssueEngineBlockCommand command) {
        this.vehicleId = command.vehicleId();
        this.issuedByUserId = command.issuedByUserId();
        this.type = CommandType.ENGINE_BLOCK;
        this.triggeredByAlertId = command.triggeredByAlertId();
        this.status = CommandStatus.ISSUED;
        this.issuedAt = Instant.now();
    }

    /** Constructor — ENGINE_UNBLOCK */
    public Command(IssueEngineUnblockCommand command) {
        this.vehicleId = command.vehicleId();
        this.issuedByUserId = command.issuedByUserId();
        this.type = CommandType.ENGINE_UNBLOCK;
        this.status = CommandStatus.ISSUED;
        this.issuedAt = Instant.now();
    }

    /** Reconstruction constructor used by persistence assemblers. */
    public Command(Long vehicleId, Long issuedByUserId, CommandType type, Long triggeredByAlertId,
                   CommandStatus status, Instant issuedAt, Instant dispatchedAt,
                   Instant acknowledgedAt, Instant completedAt, CommandResult result) {
        this.vehicleId = vehicleId;
        this.issuedByUserId = issuedByUserId;
        this.type = type;
        this.triggeredByAlertId = triggeredByAlertId;
        this.status = status;
        this.issuedAt = issuedAt;
        this.dispatchedAt = dispatchedAt;
        this.acknowledgedAt = acknowledgedAt;
        this.completedAt = completedAt;
        this.result = result;
    }

    public void markDispatched() {
        this.status = CommandStatus.DISPATCHED;
        this.dispatchedAt = Instant.now();
    }

    public void markAcknowledged() {
        this.status = CommandStatus.ACKNOWLEDGED;
        this.acknowledgedAt = Instant.now();
    }

    public void complete(CommandResult result) {
        this.status = CommandStatus.COMPLETED;
        this.result = result;
        this.completedAt = Instant.now();
    }

    public void fail(CommandResult result) {
        this.status = CommandStatus.FAILED;
        this.result = result;
        this.completedAt = Instant.now();
    }

    public boolean isPending()       { return status == CommandStatus.ISSUED
                                            || status == CommandStatus.DISPATCHED; }
    public boolean isAcknowledged()  { return status == CommandStatus.ACKNOWLEDGED; }
    public boolean isCompleted()     { return status == CommandStatus.COMPLETED
                                            || status == CommandStatus.FAILED; }
    public boolean wasTriggeredAutomatically() { return triggeredByAlertId != null; }
}
