package com.dr_api.dr_api.exception;

// Usamos RuntimeException para que Spring pueda manejarla sin necesidad de throws
public class PlanLimitExceededException extends RuntimeException {
    
    public PlanLimitExceededException(String message) {
        super(message);
    }
}