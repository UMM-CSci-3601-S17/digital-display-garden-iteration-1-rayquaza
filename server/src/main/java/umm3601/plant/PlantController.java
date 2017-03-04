package umm3601.plant;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Iterator;

import static com.mongodb.client.model.Filters.eq;


public class PlantController {

    private MongoCollection<Document> plantCollection;

    public PlantController(String databaseName) {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase(databaseName);
        plantCollection = db.getCollection("plants");
    }

    public String getPlant(String id) {

        FindIterable<Document> jsonPlant;
        String returnVal;
        try {

            jsonPlant = plantCollection.find(eq("_id", new ObjectId(id)));

            Iterator<Document> iterator = jsonPlant.iterator();

            if (iterator.hasNext()) {
                returnVal = iterator.next().toJson();
            } else {
                returnVal = "null";
            }

        } catch (IllegalArgumentException e) {
            returnVal = "null";
        }

        return returnVal;

    }

    // does this need to be synchronized?
//    public synchronized boolean likePlant(String id) {
//        Document searchDocument = new Document();
//        searchDocument.append("_id", new ObjectId(id));
//
//        Document updateDocument = new Document();
//        updateDocument.append("$inc");
//
//        plantCollection.findOneAndUpdate(searchDocument, updateDocument);
//        return false;
//    }

}
