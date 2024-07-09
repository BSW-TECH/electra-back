package com.electra.server.service.api;

public interface SessionApi {

    /**
     *
     * @return sessionId
     */
    Integer requestNewSession(String deviceKey);

    /**
     *
     * @param sessionId
     */
    void closeSession(Integer sessionId);
}
