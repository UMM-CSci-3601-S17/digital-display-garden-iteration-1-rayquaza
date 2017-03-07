package umm3601.plant;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

public class PlantControllerSpec {

    private PlantController plantController;
    private static String databaseName = "data-for-testing-only";
    private static String plantCollection = "plants";
    private static String commentCollection = "comments";


    @Before
    public void clearAndPopulateDB() throws IOException {

        // Get a connection to the database
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> plantDocuments = db.getCollection(plantCollection);

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

    /**
     * The method fetches a single plant from the database. Because the
     * getPlant(String) method in PlantController has side effects, we
     * use this method to inspect the DB manually during testing.
     *
     * Note that this method does no sanity checking on the query.
     *
     * @param targetId a string specifying an ID of the plant
     * @return a Document straight from the DB
     */
    private Document getPlantById(String targetId) {

        // All this hassle is to test the db contents to see if they actually changed
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> plantDocuments = db.getCollection(plantCollection);
        Document searchDoc = new Document();
        searchDoc.append("_id", new ObjectId(targetId));

        Iterator<Document> iterator = plantDocuments.find(searchDoc).iterator();
        return iterator.next();
    }

    @Test
    public void getPlantReturnsNonEmptyString() {
        // This test is mostly just to make sure we are building the mock database correctly

        assertNotEquals("", plantController.getPlant("58b8f2565fbad0fc7a89f858"));

    }

    @Test
    public void getPlantReturnsNullWhenTheIdIsNotFound() {

        assertEquals("null", plantController.getPlant("000000000000000000000000"));

    }

    @Test
    public void getPlantReturnsNullWhenTheIdIsMalformed() {

        assertEquals("null", plantController.getPlant("oh snap!"));

    }

    @Test
    public void getPlantReturnsObjectWithProperNames() {

        String jsonAsString = plantController.getPlant("58b8f2565fbad0fc7a89f74b");
        Document doc = Document.parse(jsonAsString);
        assertEquals("commonName should be Arctotis", "Arctotis", doc.getString("commonName"));
        assertEquals("cultivar should be hybrid Orange", "hybrid Orange", doc.getString("cultivar"));

    }

    @Test
    public void getPlantUpdatesPageViews() {

        String targetId = "58b8f2565fbad0fc7a89f85a";

        plantController.getPlant(targetId);

        Document resultDoc = getPlantById(targetId);

        assertEquals("The pageViews should be 1", 1, ((Document) resultDoc.get("metadata")).get("pageViews"));

    }

    @Test
    public void getPlantReturnsDocumentWithCorrectKeys() {

        String jsonAsString = plantController.getPlant("58b8f2565fbad0fc7a89f746");
        Document doc = Document.parse(jsonAsString);

        Set<String> expectedKeys = new HashSet(Arrays.asList("commonName", "cultivar", "_id"));
        Set<String> actualKeys = doc.keySet();

        assertEquals(expectedKeys, actualKeys);

    }

    @Test
    public void incrementMetadataReturnsFalseWhenIdIsNotFound() {

        assertFalse(plantController.incrementMetadata("000000000000000000000000", ""));

    }

    @Test
    public void incrementMetadataReturnsFalseWhenIdIsMalformed() {

        assertFalse(plantController.incrementMetadata("I'm not hexadecimal!", ""));

    }

    @Test
    public void incrementMetadataReturnsTrueWhenIdIsFound() {

        // Note that the field doesn't matter as long is it is non-empty
        assertTrue(plantController.incrementMetadata("58b8f2565fbad0fc7a89f858", "foobar"));

    }

    @Test(expected = com.mongodb.MongoCommandException.class)
    public void incrementMetadataThrowsExceptionWhenIdIsFoundAndFieldIsEmpty() {

        // some argue that you shouldn't have tests like this, but it makes for
        // nice documentation, so whatever
        plantController.incrementMetadata("58b8f2565fbad0fc7a89f858", "");

    }

    @Test
    public void incrementMetadataIncrementsMetadata() {

        String targetId = "58b8f2565fbad0fc7a89f85a";

        plantController.incrementMetadata(targetId, "likes");
        plantController.incrementMetadata(targetId, "likes");
        plantController.incrementMetadata(targetId, "likes");

        Document resultDoc = getPlantById(targetId);

        assertEquals("The likes should be 3", 3, ((Document) resultDoc.get("metadata")).get("likes"));

    }

    @Test
    public void storePlantCommentReturnsFalseWhenStringIsNotJson() {
        Boolean worked = plantController.storePlantComment("PlantId: HexadecimalNonsense, Comment: Your method of identifying plants is ridiculous!");

        assertFalse(worked);
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
