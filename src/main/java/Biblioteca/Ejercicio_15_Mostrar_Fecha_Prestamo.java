package Biblioteca;

import java.util.Date;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

// Mostrar la fecha de préstamo del libro prestado al socio num_socio 1

public class Ejercicio_15_Mostrar_Fecha_Prestamo {

	public static void main(String[] args) {

		MongoClient mongoCliente = null;

		try {

			mongoCliente = MongoClients.create();

			MongoDatabase bd = mongoCliente.getDatabase("Biblioteca");

			MongoCollection<Document> colPrestamos = bd.getCollection("Prestamos");

			Bson filtro = Filters.eq("socio.num_socio", 1);

			Document doc = colPrestamos.find(filtro).first();

			if (doc != null) {

				Date fecha = doc.getDate("fecha_pres");
				System.out.println("Fecha del préstamo: " + fecha.toString());

			} else {
				System.out.println("No se ha encontrado el préstamo.");
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
