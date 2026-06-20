package com.guardiants.platform.fleet.application.commandservices;

public interface VehicleLoanCommandFailure {
    String messageKey();

    record LoanNotFound() implements VehicleLoanCommandFailure {
        public String messageKey() { return "fleet.error.loanNotFound"; }
    }

    record VehicleNotAvailable() implements VehicleLoanCommandFailure {
        public String messageKey() { return "fleet.error.vehicleNotAvailable"; }
    }

    record InvalidStatusTransition() implements VehicleLoanCommandFailure {
        public String messageKey() { return "fleet.error.invalidStatusTransition"; }
    }

    record NotAuthorizedApprover() implements VehicleLoanCommandFailure {
        public String messageKey() { return "fleet.error.notAuthorizedApprover"; }
    }
}
