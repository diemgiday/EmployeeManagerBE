package com.devnguyen.myshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.devnguyen.myshop.model.entity.Employee;
import com.devnguyen.myshop.repository.EmployeeRepo;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void delete(Long id) {
        employeeRepo.deleteById(id);
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

    public List<Employee> findByName(String name) throws IOException {
        Query query = Query.of(q -> q.queryString(qs -> qs.query(name)));
        SearchResponse<Employee> searchResponse = elasticsearchClient.search(s -> s
                .index("employees")
                .query(query),
            Employee.class);
            System.out.println(searchResponse.toString());

        return getSearchHits(searchResponse);
    }

    public List<Employee> findBySalaryRange(double minSalary, double maxSalary) throws IOException {
        Query query = Query.of(q -> q.range(r -> r
                .field("salary")
                .gte(JsonData.of(minSalary))
                .lte(JsonData.of(maxSalary))
        ));
        SearchResponse<Employee> searchResponse = elasticsearchClient.search(s -> s
                .index("employees")
                .query(query),
            Employee.class);
        return getSearchHits(searchResponse);
    }

    private List<Employee> getSearchHits(SearchResponse<Employee> searchResponse) {
        return searchResponse.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }
    
}
