package com.jsrabk.reference.app.api.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jsrabk.reference.app.api.model.Product;
import com.jsrabk.reference.app.api.product.repository.ProductESRepository;

import java.io.IOException;
import java.util.List;

@RestController
public class ProductESController {

    @Autowired
    private ProductESRepository productESRepository;

    @PostMapping("/createOrUpdateDocument")
    public ResponseEntity<Object> createOrUpdateDocument(@RequestBody Product product) throws IOException {
          String response = productESRepository.createOrUpdateDocument(product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getDocument")
    public ResponseEntity<Object> getDocumentById(@RequestParam String productId) throws IOException {
       Product product =  productESRepository.getDocumentById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/deleteDocument")
    public ResponseEntity<Object> deleteDocumentById(@RequestParam String productId) throws IOException {
        String response =  productESRepository.deleteDocumentById(productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/searchDocument")
    public ResponseEntity<Object> searchAllDocument() throws IOException {
        List<Product> products = productESRepository.searchAllDocuments();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}

