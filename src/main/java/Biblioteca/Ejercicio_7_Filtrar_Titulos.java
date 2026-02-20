package Biblioteca;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

// Buscar título de libros de autor que se apellida Follet

public class Ejercicio_7_Filtrar_Titulos {

	public static void main(String[] args) {

		MongoClient mongoCliente = null;

		try {

			// Crear la conexión
			mongoCliente = MongoClients.create();

			// Obtener la base de datos
			MongoDatabase bd = mongoCliente.getDatabase("Biblioteca");

			// Obtener la colección
			MongoCollection<Document> colLibros = bd.getCollection("Libros");

			// Creo el filtro (Se puede acceder a propiedades)
			Bson filtro = Filters.eq("autor.apellido", "Follet");

			// Creo el cursor
			MongoCursor<Document> cursor = colLibros.find(filtro).iterator();

			try {
				while (cursor.hasNext()) {
					Document doc = cursor.next();
					System.out.println(doc.getString("titulo"));
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
