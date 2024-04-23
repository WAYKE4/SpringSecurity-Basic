package com.boot.springboot.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @SequenceGenerator(name = "userSeqGen", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "userSeqGen")
    private Long id;

    // obyazn bit not null @NotNull
    // obyazn bit null  @Null
    //@Size(min = 1) - razmer stroki
    //@Email
    //@AssertFalse // podtverdi 4to tam true
    //@Digits(integer = 1, fraction =  skolbko do zapyatoi i posle (5,12)
    //@Future
    //@Max(100)
    //@Min(12)
   // @Pattern(regexp = "[a-z]{10}")

    @Column(name = "username", unique = true)
    private String username;


    @Column(name = "user_password")
    private String userPassword;


    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp created;

    @Column(name = "changed")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp changed;

    @NotNull
    @Min(18)
    @Column(name = "age")
    private Integer age;
}