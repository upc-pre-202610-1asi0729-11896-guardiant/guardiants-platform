package com.guardiants.platform.fleet.domain.model.queries;

import com.guardiants.platform.fleet.domain.model.valueobjects.LoanStatus;

public record GetAllLoansByFleetIdQuery(Long fleetId, LoanStatus status) {}
