package com.example;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;

public class MongoExample {

    public static void main(String[] args) {
        MongoClient client = new MongoClient("localhost", 27017);
        MongoDatabase myMongoDb = client.getDatabase("myMongoDb");



        // print existing databases
        client.listDatabaseNames().into(new ArrayList<>()).forEach(System.out::println);
    }
}
