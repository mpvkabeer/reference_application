package com.jsrabk.reference.app.ui.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import com.google.protobuf.Timestamp;
import com.jsrabk.reference.app.api.model.Product;
import com.jsrabk.reference.app.api.product.repository.ProductESRepository;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

@Controller
public class ProductUIController {

    @Autowired
    private ProductESRepository productESRepository;

    @GetMapping("/product_store")
    public String viewHomePage(Model model) {
    	try {
    		model.addAttribute("listProductDocuments",productESRepository.searchAllDocuments());
		} catch(Exception e) {
			e.printStackTrace();
		}        
        return "product_store";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute("product") Product product) {
    	try {
	    	 productESRepository.createOrUpdateDocument(product);
	    	//Thread.sleep(800); //TODO: Remove this if your elasticsearch service is performing well
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") String id, Model model) {

    	try {
	        Product product = productESRepository.getDocumentById(id);
	        model.addAttribute("product", product);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	
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
    public String deleteProduct(@PathVariable(value = "id") String id) {
    	try {
	        this.productESRepository.deleteDocumentById(id);
		} catch(Exception e) {
			e.printStackTrace();
		}
    	return "redirect:/";
    }
}

