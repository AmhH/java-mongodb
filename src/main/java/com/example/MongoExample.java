package com.example;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Filters.eq;

public class MongoExample {

    public static void main(String[] args) {
        MongoClient client = new MongoClient("localhost", 27017);
        MongoDatabase database = client.getDatabase("myMongoDb");

        // print existing databases
        client.listDatabaseNames().into(new ArrayList<>()).forEach(System.out::println);

        database.createCollection("customers", new CreateCollectionOptions());

        System.out.println("*******Collection************");
        // print all collections in customers database
        database.listCollectionNames().into(new ArrayList<>()).forEach(System.out::println);


        // create data
        MongoCollection<Document> collection = database.getCollection("customers");
        Document document = new Document("name", "Café Con Leche")
                .append("contact", new Document("phone", "228-555-0149")
                        .append("email", "cafeconleche@example.com")
                        .append("location",Arrays.asList(-73.92502, 40.8279556)))
                .append("stars", 3)
                .append("categories", Arrays.asList("Bakery", "Coffee", "Pastries"));

        collection.insertOne(document);

        Block<Document> printBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document.toJson());
            }
        };

        // read data
        collection.find().forEach(printBlock);
        System.out.println("*******FIND PARAMETER************");
        collection.find(eq("name", "Café Con Leche"))
                .forEach(printBlock);

        // update data


        // delete data
    }
}
