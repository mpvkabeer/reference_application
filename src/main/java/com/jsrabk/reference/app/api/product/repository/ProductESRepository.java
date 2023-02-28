package com.jsrabk.reference.app.api.product.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsrabk.reference.app.api.model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductESRepository {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    private final String indexName = "products";


    public String createOrUpdateDocument(Product product) throws IOException {
    	
        IndexResponse response = elasticsearchClient.index(i -> i
                .index(indexName)
                .id(product.getId()+"")
                .document(product)
                .refresh(Refresh.True)
        );
        
        if(response.result().name().equals("Created")){
            return new StringBuilder("Document has been successfully created.").toString();
        }else if(response.result().name().equals("Updated")){
            return new StringBuilder("Document has been successfully updated.").toString();
        }
        return new StringBuilder("Error while performing the operation.").toString();
    }

    public Product getDocumentById(String productId) throws IOException{
        Product product = null;
        GetResponse<Product> response = elasticsearchClient.get(g -> g
                        .index(indexName)
                        .id(productId),
                Product.class
        );

        if (response.found()) {
             product = response.source();
            System.out.println("Product name " + product.getName());
        } else {
            System.out.println ("Product not found");
        }

       return product;
    }

    public String deleteDocumentById(String productId) throws IOException {

        DeleteRequest request = DeleteRequest.of(d -> d.index(indexName).id(productId).refresh(Refresh.True));

        DeleteResponse deleteResponse = elasticsearchClient.delete(request);
        if (Objects.nonNull(deleteResponse.result()) && !deleteResponse.result().name().equals("NotFound")) {
            return new StringBuilder("Product with id " + deleteResponse.id() + " has been deleted.").toString();
        }
        System.out.println("Product not found");
        return new StringBuilder("Product with id " + deleteResponse.id()+" does not exist.").toString();

    }

    public  List<Product> searchAllDocuments() throws IOException {

    	   SearchRequest searchRequest =  SearchRequest.of(s -> s.index(indexName));
           SearchResponse<Product> searchResponse =  elasticsearchClient.search(searchRequest, Product.class);
           List<Hit<Product>> hits = searchResponse.hits().hits();
           List<Product> products = new ArrayList<>();
           for(Hit<Product> product : hits){
               products.add(product.source());
           }
           return products;
    }
}