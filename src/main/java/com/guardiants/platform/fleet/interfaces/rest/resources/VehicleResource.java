package com.guardiants.platform.fleet.interfaces.rest.resources;

public record VehicleResource(Long id, Long fleetId, String plate, String model,
                              String brand, int year, String status,
                              DeviceAssignmentResource deviceAssignment, Long currentLoanId) {}
