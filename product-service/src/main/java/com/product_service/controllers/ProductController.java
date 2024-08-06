package com.product_service.controllers;

import com.product_service.dto.ProductRequest;
import com.product_service.dto.ProductResponse;
import com.product_service.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void CreateProduct(@RequestBody ProductRequest productRequest){
        productService.createproduct(productRequest);
    }

    @GetMapping
    public List<ProductResponse> getAllProduct(){

        return productService.getAllProduct();

    }
//    @GetMapping
//    public void getProduct(){
//
//    }
//    @DeleteMapping
//    public void deleteProduct(){
//
//    }


}
