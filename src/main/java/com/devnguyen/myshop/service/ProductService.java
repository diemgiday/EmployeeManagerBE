package com.devnguyen.myshop.service;

import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import com.devnguyen.myshop.model.entity.Product;
import com.devnguyen.myshop.repository.ProductRepo;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.FunctionBoostMode;
import co.elastic.clients.elasticsearch._types.query_dsl.FunctionScoreMode;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import lombok.RequiredArgsConstructor;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final ElasticsearchClient elasticsearchClient;

    public Iterable <Product> findAll(){
        return productRepo.findAll();
    }

    public void add(Product product){
        productRepo.save(product);
    }

    public Product edit(Product product) throws Exception {
        Product existingProduct = productRepo.findById(product.getId())
                .orElseThrow(() -> new Exception("Product not found"));

        existingProduct.setName(product.getName());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setPrice(product.getPrice());

        return productRepo.save(existingProduct);
    }

    public void detele(Long id){
        productRepo.deleteById(id);
    }

    //suggest products use boost elasticsearch
     public List<Product> suggestProducts(String name, String category) throws Exception {
        SearchResponse<Product> searchResponse = elasticsearchClient.search(s -> s
            .index("products")
            .query(q -> q
                .bool(b -> b
                    .should(sh -> sh
                        .match(m -> m
                            .field("name")
                            .query(name)
                            .boost(2.0f)
                        )
                    )
                    .should(sh -> sh
                        .match(m -> m
                            .field("category")
                            .query(category)
                            .boost(1.0f)
                        )
                    )
                )
            ), Product.class
        );

        return searchResponse.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }
    //suggest products use weight elasticsearch
     public List<Product> suggestProductsWithWeight(String name) throws IOException {
        SearchResponse<Product> searchResponse = elasticsearchClient.search(s -> s
            .index("products")
            .query(q -> q
                .functionScore(fs -> fs
                    .query(bq -> bq
                        .bool(b -> b
                            .must(m -> m
                                .match(t -> t
                                    .field("name")
                                    .query(name)
                                )
                            )
                        )
                    )
                    .functions(fn -> fn
                        .weight(5.0)  // Weight for category "smartphone"
                        .filter(f -> f
                            .term(t -> t
                                .field("category")
                                .value("smartphone")
                            )
                        )
                    )
                    .functions(fn -> fn
                        .weight(10.0)  // Weight for price range
                        .filter(f -> f
                            .range(r -> r
                                .field("price")
                                .gte(JsonData.of(500))
                                .lte(JsonData.of(1000))
                            )
                        )
                    )
                    .scoreMode(FunctionScoreMode.Sum)
                    .boostMode(FunctionBoostMode.Multiply)
                )
            ), Product.class
        );

        return searchResponse.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }
}


