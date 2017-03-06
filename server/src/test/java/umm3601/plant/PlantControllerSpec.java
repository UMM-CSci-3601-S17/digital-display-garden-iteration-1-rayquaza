package umm3601.plant;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlantControllerSpec {

    private PlantController plantController;
    private static String databaseName = "data-for-testing-only";
    private static String collectionName = "plants";


    @Before
    public void clearAndPopulateDB() throws IOException {

        // Get a connection to the database
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> plantDocuments = db.getCollection(collectionName);

        // clear the collection before each test
        plantDocuments.drop();

        // create test data
        List<Document> testTodos = new ArrayList<>();
        testTodos.add(Document.parse("{ \"_id\" : { \"$oid\" : \"58b8f2565fbad0fc7a89f858\" }, \"commonName\" : \"Zinnia\", \"cultivar\" : \"Profusion Double Deep Salmon\", \"gardenLocation\" : 7, \"metadata\" : { \"likes\": 0, \"dislikes\": 0, \"pageViews\": 0 } } "));
        testTodos.add(Document.parse("{ \"_id\" : { \"$oid\" : \"58b8f2565fbad0fc7a89f85a\" }, \"commonName\" : \"Zinnia\", \"cultivar\" : \"Solmar Red\", \"gardenLocation\" : \"1S\", \"metadata\" : { \"likes\": 0, \"dislikes\": 0, \"pageViews\": 0 } } "));
        testTodos.add(Document.parse("{ \"_id\" : { \"$oid\" : \"58b8f2565fbad0fc7a89f74b\" }, \"commonName\" : \"Arctotis\", \"cultivar\" : \"hybrid Orange\", \"gardenLocation\" : 9, \"metadata\" : { \"likes\": 0, \"dislikes\": 0, \"pageViews\": 0 } } "));
        testTodos.add(Document.parse("{ \"_id\" : { \"$oid\" : \"58b8f2565fbad0fc7a89f746\" }, \"commonName\" : \"Angelonia\", \"cultivar\" : \"Angelface® Super Blue\", \"gardenLocation\" : \"1S\", \"metadata\" : { \"likes\": 0, \"dislikes\": 0, \"pageViews\": 0 } } "));

        // Add the test data to the db
        plantDocuments.insertMany(testTodos);

        plantController = new PlantController(databaseName);

    }

    @Test
    public void getPlantReturnsNonEmptyString() {
        // This test is mostly just to make sure we are building the mock database correctly

        assertNotEquals("", plantController.getPlant("58b8f2565fbad0fc7a89f858"));

    }

    @AfterClass
    public static void removeTestData() {
        // We are nice people, so we clean up after ourselves

        // Get a connection to the database
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase(databaseName);

        // drop the database
        db.drop();

    }

}
