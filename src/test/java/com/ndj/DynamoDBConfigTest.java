package com.ndj;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.ndj.config.DynamoDBConfig;
import com.ndj.model.User;
import org.junit.Before;
import org.junit.Test;
import org.socialsignin.spring.data.dynamodb.core.DynamoDBTemplate;

import java.util.UUID;

/**
 * Created by don on 26/09/2017.
 */
public class DynamoDBConfigTest {

    private DynamoDBTemplate dynamoDBTemplate;
    private DynamoDBConfig dynamoDBConfig;

    @Before
    public void setUp() {
        dynamoDBConfig = new DynamoDBConfig();
        AmazonDynamoDB amazonDynamoDB = dynamoDBConfig.getDynamoDBInstance();
        this.dynamoDBTemplate = new DynamoDBTemplate(amazonDynamoDB);
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
