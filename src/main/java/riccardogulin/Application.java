package riccardogulin;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import riccardogulin.dao.BlogsDAO;
import riccardogulin.dao.DocumentsDAO;
import riccardogulin.dao.UsersDAO;
import riccardogulin.entities.Blog;
import riccardogulin.entities.Document;
import riccardogulin.entities.User;
import riccardogulin.exceptions.NotFoundException;

public class Application {
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("u4d13pu");

	public static void main(String[] args) {
		EntityManager em = emf.createEntityManager();

		UsersDAO ud = new UsersDAO(em);
		DocumentsDAO dd = new DocumentsDAO(em);
		BlogsDAO bd = new BlogsDAO(em);

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
		try {
			User aldoFromDB = ud.findById("b34d8d2f-5748-44a5-ac93-7531a240926b");
			Document aldoDoc = new Document("1234", "Italy", aldoFromDB);
			// dd.saveDocument(aldoDoc);
			System.out.println(aldoFromDB.getDocument());

		} catch (NotFoundException ex) {
			System.out.println(ex.getMessage());
		}


		// ******************************************** 1 TO MANY ************************************************
		try {
			// Blog react = new Blog("React", "Bello ma non quanto Java", giova); // giova non è managed quindi non va bene!
			User giovaFromDB = ud.findById("f454737b-7c0e-48e1-8bf9-56a3e87060b8");
			Blog react = new Blog("React", "Bello ma non quanto Java", giovaFromDB); // giovaFromDB  è managed e va bene!
			// bd.saveBlog(react);

			Blog java = new Blog("Java", "Bellissimo", giovaFromDB); // giovaFromDB  è managed e va bene!
			// bd.saveBlog(java);

		} catch (NotFoundException ex) {
			System.out.println(ex.getMessage());
		}

		try {
			Blog javaFromDB = bd.findById("07729e88-151a-46b7-9785-d9e6b0bec257");
			System.out.println(javaFromDB);
			User giovaFromDB = ud.findById("f454737b-7c0e-48e1-8bf9-56a3e87060b8");
			giovaFromDB.getBlogs().forEach(blog -> System.out.println(blog));


		} catch (NotFoundException ex) {
			System.out.println(ex.getMessage());
		}

		em.close();
		emf.close();
	}
}
