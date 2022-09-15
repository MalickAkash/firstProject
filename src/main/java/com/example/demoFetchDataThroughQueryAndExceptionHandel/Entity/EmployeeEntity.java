package com.example.demoFetchDataThroughQueryAndExceptionHandel.Entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;


@Data
@NoArgsConstructor
@Validated
@Entity
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long empId;
    @NotNull
    private String empFirstName;
    @NotNull
    private String empLastName;

    private Long empAge;
    @NotNull
    private String empCity;

    public EmployeeEntity(String empFirstName, String empLastName, Long empAge, String empCity) {
        this.empFirstName = empFirstName;
        this.empLastName = empLastName;
        this.empAge = empAge;
        this.empCity = empCity;
    }
}
