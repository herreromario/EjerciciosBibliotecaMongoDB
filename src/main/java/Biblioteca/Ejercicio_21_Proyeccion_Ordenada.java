package Biblioteca;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

// Mostrar el título y la editorial (proyección) de los libros ordenados por número de
// páginas.

public class Ejercicio_21_Proyeccion_Ordenada {

	public static void main(String[] args) {

		MongoClient mongoCliente = null;

		try {

			mongoCliente = MongoClients.create();

			MongoDatabase bd = mongoCliente.getDatabase("Biblioteca");

			MongoCollection<Document> colLibros = bd.getCollection("Libros");

			MongoCursor<Document> cursorLibros = colLibros.find().projection(
					Projections.fields(Projections.include("titulo", "editorial", "paginas"), Projections.excludeId()))
					.sort(Sorts.descending("paginas")).iterator();

			try {

				while (cursorLibros.hasNext()) {

					Document docLibro = cursorLibros.next();
					System.out.println(docLibro.toJson());
				}

			} finally {
				cursorLibros.close();
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
