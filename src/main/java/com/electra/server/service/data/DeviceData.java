package com.electra.server.service.data;

import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class DeviceData {
    private Optional<Long> id;
    private String deviceKey;
    private Optional<List<UserData>> userDataList;

    public Optional<List<String>> getLinkedUsers() {
        return this.userDataList.map(userData -> userData.stream().map(UserData::getEmail).collect(Collectors.toList()));
    }

}
