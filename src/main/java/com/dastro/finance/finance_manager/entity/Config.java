package com.dastro.finance.finance_manager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String confName; // 설정 변수 Key Name

    String confValue; // 설정값

    String category; // Category

    String comment; // 해당 변수 설명

}
