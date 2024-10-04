package com.devnguyen.myshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.devnguyen.myshop.model.entity.Product;
import com.devnguyen.myshop.service.ProductService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<Iterable<Product>> getAllProducts(){
        Iterable<Product> products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @PostMapping()
    public String addProduct(Product product){
        productService.add(product);
        return "Add new product successfull";
    }
    @PutMapping()
    public ResponseEntity<?> editProduct(@RequestBody Product product) {
        try {
            Product updatedProduct = productService.edit(product);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }        
    @DeleteMapping("/{id}")
    public String deleteProduct(@RequestParam Long id){
        productService.detele(id);
        return "Deleted product " + id;
    } 
    @GetMapping("/suggest-products1")
    public List<Product> suggestProducts(@RequestParam String name, @RequestParam String category) throws Exception{
        return productService.suggestProducts(name, category);
    }
    @GetMapping("/suggest-products2")
    public List<Product> suggestProductsWithWeight(@RequestParam String name) throws IOException{
        return productService.suggestProductsWithWeight(name);
    }
}
