package Biblioteca;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

// Buscar libros editados entre 1990 y 2000

public class Ejercicio_16_Buscar_Entre_Anios_2 {

	public static void main(String[] args) {

		MongoClient mongoCliente = null;

		try {

			mongoCliente = MongoClients.create();

			MongoDatabase bd = mongoCliente.getDatabase("Biblioteca");

			MongoCollection<Document> colLibros = bd.getCollection("Libros");

			Bson filtro = Filters.and(Filters.gt("anio_publicacion", 1989), Filters.lt("anio_publicacion", 2001));

			MongoCursor<Document> cursor = colLibros.find(filtro).iterator();

			try {

				while (cursor.hasNext()) {
					Document doc = cursor.next();
					System.out.println(doc.toJson());
				}

			} finally {
				cursor.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mongoCliente != null) {
				mongoCliente.close();
			}
		}
	}

}
