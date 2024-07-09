package com.electra.server.exception;

public class DeviceNotFoundException extends RuntimeException {

    public DeviceNotFoundException(String message) {
        super("Device " + message + " not found");
    }

}