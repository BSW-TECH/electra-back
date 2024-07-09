package com.electra.server.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "DEVICES")
@AllArgsConstructor
@NoArgsConstructor
public class DeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @Column(name = "KEY")
    private String key;

    @OneToMany(mappedBy = "deviceEntity", cascade = CascadeType.ALL)
    private List<UserEntity> userEntities;

}
