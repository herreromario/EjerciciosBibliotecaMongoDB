package Biblioteca;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

// Mostrar el nombre de los socios que se ha llevado prestado el libro de título «El Quijote»

public class Ejercicio_18_Nombre_Socios_Libro_Prestado {

	public static void main(String[] args) {

		MongoClient mongoCliente = null;

		try {

			mongoCliente = MongoClients.create();

			MongoDatabase bd = mongoCliente.getDatabase("Biblioteca");

			MongoCollection<Document> colLibros = bd.getCollection("Libros");
			MongoCollection<Document> colPrestamos = bd.getCollection("Prestamos");
			MongoCollection<Document> colSocios = bd.getCollection("Socios");

			Bson filtroLibro = Filters.eq("titulo", "El Quijote");

			Document doc = colLibros.find(filtroLibro).first();
			
			if (doc == null) {
			    System.out.println("No existe el libro.");
			    return;
			}

			String isbn = doc.getString("isbn");

			Bson filtroPrestamo = Filters.eq("libro.isbn", isbn);

			MongoCursor<Document> cursorPrestamos = colPrestamos.find(filtroPrestamo).iterator();

			try {

				while (cursorPrestamos.hasNext()) {

					Document docPrestamo = cursorPrestamos.next();

					Document docSocioPrestamos = (Document) docPrestamo.get("socio");

					int numeroSocio = docSocioPrestamos.getInteger("num_socio");

					Bson filtroSocio = Filters.eq("num_socio", numeroSocio);

					MongoCursor<Document> cursorSocios = colSocios.find(filtroSocio).iterator();

					try {

						while (cursorSocios.hasNext()) {

							Document docSocio = cursorSocios.next();
							String nombreSocio = docSocio.getString("nombre");

							System.out.println(nombreSocio);
						}

					} finally {
						cursorSocios.close();
					}
				}

			} finally {
				cursorPrestamos.close();
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
