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

// Mostrar el título de los libros que tiene prestados el socio con nombre «Javier García»

public class Ejercicio_19_Titulo_Libros_Prestados_Socio {

	public static void main(String[] args) {

		MongoClient mongoCliente = null;

		try {

			// Creo la conexion
			mongoCliente = MongoClients.create();

			// Accedo a la base de datos
			MongoDatabase bd = mongoCliente.getDatabase("Biblioteca");

			// Accedo a las colecciones
			MongoCollection<Document> colLibros = bd.getCollection("Libros");
			MongoCollection<Document> colPrestamos = bd.getCollection("Prestamos");
			MongoCollection<Document> colSocios = bd.getCollection("Socios");

			// Creo el filtro para encontrar al socio
			Bson filtroSocio = Filters.eq("nombre", "Javier García");

			// Creo el documento del socio
			Document docSocio = colSocios.find(filtroSocio).first();

			if (docSocio == null) {
				System.out.println("No existe el socio.");
				return;
			}

			// Me guardo su numero de socio
			int numeroSocio = docSocio.getInteger("num_socio");

			
			// Creo un filtro para buscar al socio en la lista de préstamos
			Bson filtroPrestamo = Filters.eq("socio.num_socio", numeroSocio);

			// Creo el cursor de prestamos que recorrerá los préstamos en busca del socio
			MongoCursor<Document> cursorPrestamos = colPrestamos.find(filtroPrestamo).iterator();
			
			// Creo una lista set para almacenar los isbn de los libros prestados sin repetir el mismo
			Set<String> listaISBN = new HashSet<>();

			try {

				while (cursorPrestamos.hasNext()) {

					Document docPrestamo = cursorPrestamos.next();

					// Creo una lista de los Documentos libro prestados
					List<Document> listaLibros =
					        docPrestamo.getList("libro", Document.class);

					
					// Guardo el isbn de cada libro en la lista
					for (Document document : listaLibros) {
						listaISBN.add(document.getString("isbn"));
					}
				}

			} finally {
				cursorPrestamos.close();
			}

			if (!listaISBN.isEmpty()) {

				// Creo un filtro que comprueba si el isbn se encuentra en la lista
				Bson filtroLibros = Filters.in("isbn", listaISBN);
				
				// Creo un cursor en busca de los libros con esos isbn
				MongoCursor<Document> cursorLibros = colLibros.find(filtroLibros).iterator();

				try {

					while (cursorLibros.hasNext()) {
						Document docLibro = cursorLibros.next();
						String titulo = docLibro.getString("titulo");
						System.out.println(titulo);
					}

				} finally {
					cursorLibros.close();
				}
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
