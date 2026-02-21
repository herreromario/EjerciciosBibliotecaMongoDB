package Biblioteca;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

// Mostra el nombre del socio 2

public class Ejercicio_13_Buscar_Nombre_Socio {

	public static void main(String[] args) {

		MongoClient mongoCliente = null;

		try {

			mongoCliente = MongoClients.create();

			MongoDatabase bd = mongoCliente.getDatabase("Biblioteca");

			MongoCollection<Document> colSocios = bd.getCollection("Socios");

			Bson filtro = Filters.eq("num_socio", 2);

			Document doc = colSocios.find(filtro).first();

			if (doc != null) {
				String nombre = doc.getString("nombre");
				System.out.println(nombre);
			} else {
				System.out.println("No se encontró al socio.");
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
