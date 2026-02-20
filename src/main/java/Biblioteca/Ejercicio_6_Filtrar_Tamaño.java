package Biblioteca;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

// Buscar el préstamo con dos libros

public class Ejercicio_6_Filtrar_Tamaño {

	public static void main(String[] args) {

		MongoClient mongoCliente = null;

		try {

			// Crear la conexión
			mongoCliente = MongoClients.create();

			// Obtener la base de datos
			MongoDatabase bd = mongoCliente.getDatabase("Biblioteca");

			// Obtener la colección
			MongoCollection<Document> colPrestamos = bd.getCollection("Prestamos");

			// Crear el filtro de tamañó
			Bson filtro = Filters.size("libro", 2);

			// Aplicar el filtro a un documento
			Document doc = colPrestamos.find(filtro).first();

			// Comprobar que el documento existe
			if (doc != null) {
				System.out.println(doc.toJson());
			} else {
				System.out.println("No se encontró el préstamo.");
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
