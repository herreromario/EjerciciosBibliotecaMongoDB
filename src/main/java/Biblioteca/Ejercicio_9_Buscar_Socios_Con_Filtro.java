package Biblioteca;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

// Buscar el nombre de los socios que han tenido prestado 446854

public class Ejercicio_9_Buscar_Socios_Con_Filtro {

	public static void main(String[] args) {

		MongoClient mongoCliente = null;

		try {

			mongoCliente = MongoClients.create();

			MongoDatabase bd = mongoCliente.getDatabase("Biblioteca");

			MongoCollection<Document> colPrestamos = bd.getCollection("Prestamos");
			MongoCollection<Document> colSocios = bd.getCollection("Socios");

			Bson filtroPrestamos = Filters.eq("libro.isbn", "446854");

			MongoCursor<Document> cursorPrestamos = colPrestamos.find(filtroPrestamos).iterator();

			try {
				while (cursorPrestamos.hasNext()) {

					Document docPrestamo = cursorPrestamos.next();

					// Obtener subdocumento socio
					Document socioDoc = (Document) docPrestamo.get("socio");
					int numSocio = socioDoc.getInteger("num_socio");

					// Buscar socio en colección Socios
					Bson filtroSocio = Filters.eq("num_socio", numSocio);

					Document docSocio = colSocios.find(filtroSocio).first();

					if (docSocio != null) {
						System.out.println(docSocio.getString("nombre"));
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
