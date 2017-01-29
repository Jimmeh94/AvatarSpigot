package avatar.util.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import avatar.util.misc.UUIDUtils;

import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class MongoUtils {
    private MongoClient client;
    private MongoDatabase database;
    private String username, password, ip;

    public void openConnection(){
        client = new MongoClient(new MongoClientURI("mongodb://" + username + ":" + password + ip));
        if(database == null){
            database = getDatabase();
        }
    }

    public boolean isConnected(){
        return client != null;
    }

    public MongoDatabase getDatabase(){
        return client.getDatabase("db name");
    }

    public void close() {
        client.close();
        client = null;
        database = null;
    }

    public MongoUtils(String string, String string1, String string2) {
        username = string;
        password = string1;
        ip = string2;
    }

    public Document fetchPlayerData(UUID uuid){
        FindIterable<Document> iterable = null;
        if (!isConnected())
            openConnection();
        if (database != null) {
            iterable = database.getCollection("players").find(eq("uuid", UUIDUtils.getRawUUID(uuid)));
            Document document = iterable.first();
            return document;
        }
        return null;
    }

    public Document fetchEmbeddedDocument(Document document, String field) { //for reading a single, non-embedded integer
        return (Document)document.get(field);
    }
}
