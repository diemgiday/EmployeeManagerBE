package com.devnguyen.myshop.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Document(indexName = "employees")
public class Employee {
    @Id
    private Long id;
    @NotNull(message = "firstName must be not null")
    private String firstName;
    
    @NotNull(message = "lastName must be not null")
    @Field(type = FieldType.Text)
    private String lastName;
    
    @Field(type = FieldType.Text)
    private String position;

    @Email
    private String email;
    
    @Field(type = FieldType.Double)
    private double salary;
}
