package com.example;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.flapdoodle.embedmongo.MongoDBRuntime;
import de.flapdoodle.embedmongo.MongodExecutable;
import de.flapdoodle.embedmongo.MongodProcess;
import de.flapdoodle.embedmongo.config.MongodConfig;
import de.flapdoodle.embedmongo.distribution.Version;
import de.flapdoodle.embedmongo.runtime.Network;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class AppLiveTest {
    private static final String DB_NAME = "myMongoDb";
    private MongodExecutable mongodExe;
    private MongodProcess mongod;
    private MongoClient mongo;
    private MongoDatabase db;
    private MongoCollection<Document> collection;

    @Before
    public void setup() throws Exception {
        // Creating Mongodbruntime instance
        MongoDBRuntime runtime = MongoDBRuntime.getDefaultInstance();

        // Creating MongodbExecutable
        mongodExe = runtime.prepare(new MongodConfig(Version.V1_6, 12345, Network.localhostIsIPv6()));

        // Starting Mongodb
        mongod = mongodExe.start();
        mongo = new MongoClient("localhost", 12345);

        // Creating DB
        db = mongo.getDatabase(DB_NAME);

        // Creating collection Object and adding values
        collection = db.getCollection("customers");
    }

    @After
    public void teardown() throws Exception {
        mongod.stop();
        mongodExe.cleanup();
    }

    @Test
    public void testAddressPersistance() {
        Document document = new Document("name", "John")
                .append("contact", new Document("phone", "228-555-0149"))
                .append("stars", 3)
                .append("categories", Arrays.asList("Bakery", "Coffee", "Pastries"));

        collection.insertOne(document);
        FindIterable<Document> documents = collection.find();


        assertEquals(documents.iterator().next().get("name"), "John");
    }
}
