package riccardogulin;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import riccardogulin.dao.DocumentsDAO;
import riccardogulin.dao.UsersDAO;
import riccardogulin.entities.Document;
import riccardogulin.entities.User;

public class Application {
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("u4d13pu");

	public static void main(String[] args) {
		EntityManager em = emf.createEntityManager();

		UsersDAO ud = new UsersDAO(em);
		DocumentsDAO dd = new DocumentsDAO(em);

		User aldo = new User("Aldo", "Baglio");
		User giova = new User("Giovanni", "Storti");
		User giacomo = new User("Giacomo", "Poretti");

//		ud.saveUser(aldo);
//		ud.saveUser(giova);
//		ud.saveUser(giacomo);

		// ******************************************** 1 TO 1 ************************************************
		// Document aldoDoc = new Document("1234", "Italy", aldo);
		// NON POSSO USARE DIRETTAMENTE L'OGGETTO aldo (a meno che io non lo stia anche salvando nello stesso momento) perchè è NEW E NON MANAGED
		// E' un oggetto Java "semplice" difatti non ha neanche l'ID!
		// Per poter instaurare una relazione devo prima cercare lo user nel DB!
		User aldoFromDB = ud.findById("b34d8d2f-5748-44a5-ac93-7531a240926b");
		Document aldoDoc = new Document("1234", "Italy", aldoFromDB);
		dd.saveDocument(aldoDoc);


		em.close();
		emf.close();
	}
}
