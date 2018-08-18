package config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import config.Configuration;

public class MongoFactory {
    public final MongoClient build(final Configuration configuration) {

        String DATABASE_URL = "mongodb://tluser:VMubKliZCVb93yxp@cluster0-shard-00-00-8zrna.mongodb.net:27017,cluster0-shard-00-01-8zrna.mongodb.net:27017,cluster0-shard-00-02-8zrna.mongodb.net:27017/database?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin";
        String COLLECTION = "kerala";

        final MongoClientURI mongoClientURI = /*System.getenv("DATABASE").isEmpty() ?*/
                new MongoClientURI(DATABASE_URL) /*:
                new MongoClientURI(System.getenv("DATABASE"))*/;

        return new MongoClient(mongoClientURI);
    }
}
