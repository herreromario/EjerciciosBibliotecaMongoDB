package Biblioteca;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

// Buscar libros con año mayor que 2000 Y de más de 500 páginas

public class Ejercicio_10_Libros_Filtro_AND {

	public static void main(String[] args) {

		MongoClient mongoCliente = null;

		try {

			mongoCliente = MongoClients.create();

			MongoDatabase bd = mongoCliente.getDatabase("Biblioteca");

			MongoCollection<Document> colLibros = bd.getCollection("Libros");

			Bson filtro = Filters.and(Filters.gt("anio_publicacion", 2000), Filters.gt("paginas", 500));

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
