package Biblioteca;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

// Mostrar el título y el autor de los libros de la editorial «planeta» 
// ordenados por año de edición

public class Ejercicio_20_Mostrar_Ordenados {

	public static void main(String[] args) {

		MongoClient mongoCliente = null;

		try {

			mongoCliente = MongoClients.create();

			MongoDatabase bd = mongoCliente.getDatabase("Biblioteca");

			MongoCollection<Document> colLibros = bd.getCollection("Libros");

			Bson filtro = Filters.eq("editorial", "Planeta");

			MongoCursor<Document> cursor = colLibros.find(filtro).sort(Sorts.ascending("anio_publicacion")).iterator();

			try {

				while (cursor.hasNext()) {
					Document doc = cursor.next();
					String titulo = doc.getString("titulo");

					Document docAutor = (Document) doc.get("autor");
					String nombreAutor = docAutor.getString("nombre");

					System.out.println(titulo + "\n" + nombreAutor + "\n");
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
