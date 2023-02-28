package com.jsrabk.reference.app.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfiguration
{

   	@Value("${spring.elasticsearch.scheme}")
    private String scheme;	
	@Value("${spring.elasticsearch.host}")
    private String hostname;  	
   	@Value("${spring.elasticsearch.port}")
    private int port;
    @Value("${spring.elasticsearch.username}")
    private String username;
    @Value("${spring.elasticsearch.password}")
    private String password;
    
    @Bean
    public RestClient client() {
    	
//        System.out.println("scheme: "+scheme);
//        System.out.println("hostname: "+hostname);
//        System.out.println("port: "+port);
//        System.out.println("username: "+username);
//        System.out.println("password: "+password);
    	
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        HttpHost host = new HttpHost(hostname, port, scheme);

        RestClient restClient = RestClient.builder(host)
                .setHttpClientConfigCallback(new HttpClientConfigCallback() {

                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        httpClientBuilder.disableAuthCaching();
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                }).build();
        
        return restClient;
    }

    @Bean
    public ElasticsearchClient elasticsearchTemplate() {
        ElasticsearchTransport transport = new RestClientTransport(client(), new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    } 
}