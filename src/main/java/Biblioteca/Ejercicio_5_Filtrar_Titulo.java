package Biblioteca;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Ejercicio_5_Filtrar_Titulo {
	public static void main(String[] args) {

		MongoClient mongoCliente = null;

		try {

			// Crear conexión
			mongoCliente = MongoClients.create();

			// Obtener la base de datos
			MongoDatabase bd = mongoCliente.getDatabase("Biblioteca");

			// Obtener la colección
			MongoCollection<Document> colLibros = bd.getCollection("Libros");

			// Crear filtro equals para comparar
			Bson filtro = Filters.eq("titulo", "El Quijote");

			// Crear un documento aplicando el filtro
			Document doc = colLibros.find(filtro).first();

			// Compruebo si existe un libro que cumpla el filtro
			if (doc != null) {
				System.out.println(doc.toJson());
			} else {
				System.out.println("No se encontró el libro");
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
