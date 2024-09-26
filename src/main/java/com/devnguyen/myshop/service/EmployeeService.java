package com.devnguyen.myshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.devnguyen.myshop.model.entity.Employee;
import com.devnguyen.myshop.repository.EmployeeRepo;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired 
    private EmployeeRepo employeeRepo;
    @Autowired
    private ElasticsearchClient elasticsearchClient;

    public Employee save(Employee employee) {
        return employeeRepo.save(employee);
    }

    public Iterable<Employee> findAll(){
        return employeeRepo.findAll();
    }

    public Optional<Employee> findById(Long id) {
        return employeeRepo.findById(id);
        
    }

    public Employee addEmployee(Employee employee){
        return employeeRepo.save(employee);
    }

    public List<Employee> searchByText(String keyword) throws Exception{
        SearchRequest searchRequest = new SearchRequest
            .Builder()
            .index("employees")
            .query(q -> q.multiMatch(m -> m
                .fields("firstName", "lastName", "position")
                .query(keyword)))
            .build();

        SearchResponse<Employee> searchResponse = elasticsearchClient.search(searchRequest, Employee.class);

        List<Employee> employees = new ArrayList<>();

        for (Hit<Employee> hit : searchResponse.hits().hits()) {
            employees.add(hit.source());
        }

        return employees;
    }

    
}
