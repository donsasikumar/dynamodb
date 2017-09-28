package com.ndj;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import com.ndj.config.DynamoDBConfig;
import com.ndj.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.socialsignin.spring.data.dynamodb.core.DynamoDBTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by don on 26/09/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("local")
public class DynamoDBConfigTest {

    private AmazonDynamoDB amazonDynamoDB;
    DynamoDBMapper dynamoDBMapper;

    @Before
    public void setUp() {
        amazonDynamoDB = new DynamoDBConfig().getDynamoDBInstance();
        this.dynamoDBTemplate = new DynamoDBTemplate(amazonDynamoDB);
        createtable();
    }

    private DynamoDBTemplate dynamoDBTemplate;



    public void createtable(){
        try {
            dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

            CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(User.class);

            tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

            amazonDynamoDB.createTable(tableRequest);


        } catch (ResourceInUseException e) {
            // Do nothing, table already created
        }
    }



    @Test
    public void testUser_CRUD() {

        User user = new User();
        user.setName("Don Sasikumar");
        user.setNumberOfPlaylists(10);
        user.setId(UUID.randomUUID().toString());

        // Save it to DB.
        dynamoDBTemplate.save(user);

        // Retrieve it from DB.
        User retrievedUser = dynamoDBTemplate.load(User.class, user.getId());

        // Verify the details on the entity.
        assert retrievedUser.getName().equals(user.getName());
        assert retrievedUser.getId().equals(user.getId());
        assert retrievedUser.getNumberOfPlaylists() == user.getNumberOfPlaylists();

        // Update the entity and save.
        retrievedUser.setNumberOfPlaylists(20);
        dynamoDBTemplate.save(retrievedUser);

        retrievedUser = dynamoDBTemplate.load(User.class, user.getId());

        assert retrievedUser.getNumberOfPlaylists() == 20;

        // Delete.
        dynamoDBTemplate.delete(retrievedUser);

        // Get again.
        assert dynamoDBTemplate.load(User.class, user.getId()) == null;

    }

}
