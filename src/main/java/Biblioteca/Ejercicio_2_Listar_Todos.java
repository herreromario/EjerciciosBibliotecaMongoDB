package Biblioteca;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

// Buscar todos los libros

public class Ejercicio_2_Listar_Todos {

	public static void main(String[] args) {

		MongoClient mongoCliente = null;

		try {

			// Crear conexión
			mongoCliente = MongoClients.create();

			// Obtener la base de datos
			MongoDatabase bd = mongoCliente.getDatabase("Biblioteca");

			// Obtener la colección
			MongoCollection<Document> colLibros = bd.getCollection("Libros");

			// Crear un cursor para la colección
			MongoCursor<Document> cursor = colLibros.find().iterator();

			// Recorrer la colección entera

			try {
				while (cursor.hasNext()) {
					System.out.println(cursor.next().toJson());
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
