package com.ndj.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Date;
import java.util.Set;


/**
 * Created by don on 26/09/2017.
 */
@DynamoDBTable(tableName = "user")
public class User {

    private String id;

    private String name;

    private Integer numberOfPlaylists;

    private Date joinDate;

    private Date joinYear;

    private String postCode;

    private Set<String> testSet;

    public Set<String> getTestSet() {
        return testSet;
    }

    public void setTestSet(Set<String> testSet) {
        this.testSet = testSet;
    }


    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Date getJoinYear() {
        return joinYear;
    }

    public void setJoinYear(Date joinYear) {
        this.joinYear = joinYear;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @DynamoDBHashKey(attributeName = "Id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfPlaylists() {
        return numberOfPlaylists;
    }

    public void setNumberOfPlaylists(Integer numberOfPlaylists) {
        this.numberOfPlaylists = numberOfPlaylists;
    }
}
