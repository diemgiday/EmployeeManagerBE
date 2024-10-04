package com.devnguyen.myshop.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.devnguyen.myshop.model.entity.Product;

@Repository
public interface ProductRepo extends ElasticsearchRepository<Product, Long>{

}
