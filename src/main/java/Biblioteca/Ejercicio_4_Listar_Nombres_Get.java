package Biblioteca;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

// Obtener el nombre de los socios (usar método get())

public class Ejercicio_4_Listar_Nombres_Get {

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

			try {
				while (cursor.hasNext()) {
					Document doc = cursor.next();
					String nombre = doc.getString("nombre");
					System.out.println("Nombre: " + nombre);
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
