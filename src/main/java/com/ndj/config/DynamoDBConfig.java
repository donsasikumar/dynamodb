package com.ndj.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.socialsignin.spring.data.dynamodb.core.DynamoDBTemplate;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by don on 26/09/2017.
 */
@Configuration
@EnableDynamoDBRepositories(basePackages = "com.ndj")
public class DynamoDBConfig {

    String AWSAccessKeyId = "" ;
    String AWSSecretKey = "" ;

    @Bean
    public AmazonDynamoDB getDynamoDBInstance() {
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
           .withCredentials( getCredentialProvider())
           .withEndpointConfiguration(initializeEndpoint("http://localhost:8000/","")).build();
        return amazonDynamoDB;
    }

    @Bean
    public DynamoDBTemplate getDynamoDBTemplate(){
        return new DynamoDBTemplate(getDynamoDBInstance());
    }

    @Bean
    public DynamoDBMapper getDynamoDBMapper(){
        return new DynamoDBMapper(getDynamoDBInstance());
    }


    @Bean
    public AwsClientBuilder.EndpointConfiguration initializeEndpoint(String url, String region){
        return new AwsClientBuilder.EndpointConfiguration(url, region);
    }

    @Bean
    public AWSCredentialsProvider getCredentialProvider(){
        return new AWSCredentialsProvider() {
            public AWSCredentials getCredentials() {
                return new BasicAWSCredentials(AWSAccessKeyId, AWSSecretKey);
            }

            public void refresh() {

            }
        };
    }



}
