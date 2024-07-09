package com.electra.server.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "USERS")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "id")
    private RoleEntity roleEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DEVICE_ID", referencedColumnName = "id")
    private DeviceEntity deviceEntity;

}
