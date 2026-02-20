package Biblioteca;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

// Buscar el registro del primer libro

public class Ejercicio_1_Listar_Primero {

	public static void main(String[] args) {

		MongoClient mongoCliente = null;

		try {

			// Crear conexión
			mongoCliente = MongoClients.create();

			// Obtener la base de datos
			MongoDatabase bd = mongoCliente.getDatabase("Biblioteca");

			// Obtener la colección
			MongoCollection<Document> colLibros = bd.getCollection("Libros");

			// Obtener el documento 	   -> primer documento
			Document doc = colLibros.find().first();

			// Imprimir el documento
			System.out.println(doc);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mongoCliente != null) {
				mongoCliente.close();
			}
		}
	}
}
