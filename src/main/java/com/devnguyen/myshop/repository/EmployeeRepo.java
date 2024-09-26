package com.devnguyen.myshop.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.devnguyen.myshop.model.entity.Employee;
import java.util.Optional;


@Repository
public interface EmployeeRepo extends ElasticsearchRepository<Employee, Long>{
        Optional <Employee> findById(Long id);
}
