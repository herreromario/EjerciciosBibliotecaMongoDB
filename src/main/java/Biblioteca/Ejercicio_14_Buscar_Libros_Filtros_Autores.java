package Biblioteca;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

//  Buscar los libros (título, nombre autor) que sean de autores de nacionalidad americana o
// de la editorial planeta

public class Ejercicio_14_Buscar_Libros_Filtros_Autores {

	public static void main(String[] args) {

		MongoClient mongoCliente = null;

		try {

			mongoCliente = MongoClients.create();

			MongoDatabase bd = mongoCliente.getDatabase("Biblioteca");

			MongoCollection<Document> colLibros = bd.getCollection("Libros");

			Bson filtro = Filters.or(Filters.eq("autor.nacionalidad", "Americano"), Filters.eq("editorial", "Planeta"));

			MongoCursor<Document> cursor = colLibros.find(filtro).iterator();

			try {

				while (cursor.hasNext()) {

					Document doc = cursor.next();
					String titulo = doc.getString("titulo");
					
					// Mongo permite "autor.nacionalidad" en filtros,
					// pero en get() no se puede usar notación punto.
					
					Document docAutor = (Document) doc.get("autor");
					String autor = docAutor.getString("nombre");

					System.out.println("\nTitulo: " + titulo + "\nAutor: " + autor + "\n");
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
