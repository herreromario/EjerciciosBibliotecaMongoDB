package Biblioteca;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

// Mostrar el título de los libros que tienen algún préstamo 

public class Ejercicio_22_Mostrar_Libros_Prestamo {

	public static void main(String[] args) {

		MongoClient mongoCliente = null;

		try {

			mongoCliente = MongoClients.create();

			MongoDatabase bd = mongoCliente.getDatabase("Biblioteca");

			MongoCollection<Document> colPrestamsos = bd.getCollection("Prestamos");
			MongoCollection<Document> colLibros = bd.getCollection("Libros");

			MongoCursor<Document> cursorPrestamos = colPrestamsos.find().iterator();

			Set<String> listaISBN = new HashSet<>();

			try {

				while (cursorPrestamos.hasNext()) {

					Document docPrestamo = cursorPrestamos.next();

					List<Document> listaLibros = docPrestamo.getList("libro", Document.class);

					for (Document document : listaLibros) {
						String isbn = document.getString("isbn");
						listaISBN.add(isbn);
					}
				}

			} finally {
				cursorPrestamos.close();
			}

			Bson filtroLibrosPrestados = Filters.in("isbn", listaISBN);

			MongoCursor<Document> cursorLibros = colLibros.find(filtroLibrosPrestados)
					.projection(Projections.fields(Projections.include("titulo"), Projections.excludeId())).iterator();

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
