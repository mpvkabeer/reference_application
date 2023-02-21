package com.jsrabk.reference.app.api.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "products")
public class Product {

    @Id
    private String id;

    @Field(type = FieldType.Text, name = "name")
    private String name;

    @Field(type = FieldType.Text, name = "description")
    private String description;

    @Field(type = FieldType.Double, name = "price")
    private double price;

    public String getId() {
    	return this.id;
    }   
    
    public void setId(String id) {
    	this.name = id;
    }
    
    public String getName() {
    	return this.name;
    }   
    
    public void setName(String name) {
    	this.name = name;
    }

    public String getDescription() {
    	return this.description;
    }
    
    public void setDescription(String description) {
    	this.description = description;
    }
    
    public Double getPrice() {
    	return this.price;
    }
    
    public void setPrice(Double price) {
    	this.price = price;
    }

}