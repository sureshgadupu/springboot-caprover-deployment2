package dev.fullstackcode.eis.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.fullstackcode.eis.validation.ConditionalNotNull;
import dev.fullstackcode.eis.validation.OnCreate;
import dev.fullstackcode.eis.validation.OnUpdate;
import dev.fullstackcode.eis.validation.ValidName;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;



@Schema(description="Manages employee object ")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@ConditionalNotNull(fields = "salary,email",dependsOn = "hire_date" )
public class Employee implements Serializable {


    @Schema(description="Auto generated unique id",required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Null(groups = {OnCreate.class})
    @NotNull(groups = OnUpdate.class)
    private Integer id;

    @Schema(description="FirstName should not be blank",required = true,example = "Suresh")
    @NotBlank(message = "FirstName should not be blank")
    @Size(min = 3, message = "{validation.firstNameSize}")
    @ValidName
    private String first_name;


    @Schema(description="Last name of employee",required = true,example = "Suresh")
    @NotBlank(message = "LastName should not be blank")
    @Size(min = 3,message = "LastName should be at least ${min} chars")

    private String last_name;


    @Schema(description="Gender of employee")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Schema(description="Birth date of employee")
    @PastOrPresent
    private LocalDate birth_date;


    @Schema(description="Hire date of employee")
    @PastOrPresent
    private LocalDate hire_date;


    @Schema(description="Email of employee")
    @Email
    private String email;

    @Schema(description="Salary of employee",minimum="0")
    @PositiveOrZero
    private BigDecimal salary;

    @Schema(description="Department details of employee")
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;



    public Employee() {

    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public LocalDate getHire_date() {
        return hire_date;
    }

    public void setHire_date(LocalDate hire_date) {
        this.hire_date = hire_date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }



}