package Biblioteca;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

// Mostrar el título de los libros escritos por Isaac Asimov

public class Ejercicio_17_Mostrar_Titulos_Autor {

	public static void main(String[] args) {

		MongoClient mongoCliente = null;

		try {

			mongoCliente = MongoClients.create();

			MongoDatabase bd = mongoCliente.getDatabase("Biblioteca");

			MongoCollection<Document> colLibros = bd.getCollection("Libros");

			Bson filtro = Filters.and(Filters.eq("autor.nombre", "Isaac"), Filters.eq("autor.apellido", "Asimov"));

			MongoCursor<Document> cursor = colLibros.find(filtro).iterator();

			try {

				while (cursor.hasNext()) {
					Document doc = cursor.next();
					String titulo = doc.getString("titulo");
					System.out.println(titulo);
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
