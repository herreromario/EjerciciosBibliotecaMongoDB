package Biblioteca;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

// Obtener la lista de socios

public class Ejercicio_3_Listar_Todos {

	public static void main(String[] args) {

		MongoClient mongoCliente = null;

		try {

			// Crear conexión
			mongoCliente = MongoClients.create();

			// Obtener la base de datos
			MongoDatabase bd = mongoCliente.getDatabase("Biblioteca");

			// Obtener la colección
			MongoCollection<Document> colSocios = bd.getCollection("Socios");

			// Crear un cursor para la colección
			MongoCursor<Document> cursor = colSocios.find().iterator();

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
