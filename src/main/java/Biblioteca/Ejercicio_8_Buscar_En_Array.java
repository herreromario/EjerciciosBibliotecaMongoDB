package Biblioteca;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

// En prestamos buscar el documento del préstamos del libro con ISBN 446854

public class Ejercicio_8_Buscar_En_Array {

	public static void main(String[] args) {

		MongoClient mongoCliente = null;

		try {

			mongoCliente = MongoClients.create();
			
			MongoDatabase bd = mongoCliente.getDatabase("Biblioteca");

			MongoCollection<Document> colPrestamos = bd.getCollection("Prestamos");

			Bson filtro = Filters.eq("libro.isbn", "446854");

			Document doc = colPrestamos.find(filtro).first();

			if (doc != null) {
				System.out.println(doc.toJson());
			} else {
				System.out.println("No se ha encontrado el libro.");
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
