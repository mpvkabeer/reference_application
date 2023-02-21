package com.jsrabk.reference.app.ui.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.jsrabk.reference.app.api.model.Product;
import com.jsrabk.reference.app.api.product.repository.ProductESRepository;

import java.io.IOException;

@Controller
public class ProductUIController {

    @Autowired
    private ProductESRepository productESRepository;

    @GetMapping("/")
    public String viewHomePage(Model model) throws IOException {
        model.addAttribute("listProductDocuments",productESRepository.searchAllDocuments());
        return "index";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute("product") Product product) throws IOException {
    	productESRepository.createOrUpdateDocument(product);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") String id, Model model) throws IOException {

        Product product = productESRepository.getDocumentById(id);
        model.addAttribute("product", product);
        return "updateProductDocument";
    }

    @GetMapping("/showNewProductForm")
    public String showNewEmployeeForm(Model model) {
        // create model attribute to bind form data
        Product product = new Product();
        model.addAttribute("product", product);
        return "newProductDocument";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable(value = "id") String id) throws IOException {

        this.productESRepository.deleteDocumentById(id);
        return "redirect:/";
    }
}

