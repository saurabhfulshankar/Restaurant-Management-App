package com.alchemist.yoru.entity;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.lang.reflect.GenericArrayType;

@Getter
@Setter
@ToString
@Entity
@Table(name = "probability_string")
public class ProbabilityString {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String probability;

    @Column(nullable = false)
    private String tenantId;
}
