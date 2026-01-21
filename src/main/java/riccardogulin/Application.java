package riccardogulin;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import riccardogulin.dao.BlogsDAO;
import riccardogulin.dao.CategoriesDAO;
import riccardogulin.dao.DocumentsDAO;
import riccardogulin.dao.UsersDAO;
import riccardogulin.entities.Blog;
import riccardogulin.entities.Category;
import riccardogulin.entities.Document;
import riccardogulin.entities.User;
import riccardogulin.exceptions.NotFoundException;

import java.util.ArrayList;

public class Application {
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("u4d13pu");

	public static void main(String[] args) {
		EntityManager em = emf.createEntityManager();

		UsersDAO ud = new UsersDAO(em);
		DocumentsDAO dd = new DocumentsDAO(em);
		BlogsDAO bd = new BlogsDAO(em);
		CategoriesDAO cd = new CategoriesDAO(em);

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

		// ********************************************* MANY TO MANY *************************************
		Category category = new Category("Development");
		Category category1 = new Category("OOP");
		Category category2 = new Category("Frontend");
		Category category3 = new Category("Backend");
		Category category4 = new Category("Java");

//		cd.saveCategory(category);
//		cd.saveCategory(category1);
//		cd.saveCategory(category2);
//		cd.saveCategory(category3);
//		cd.saveCategory(category4);

		// Per associare ad un blog tot categorie dobbiamo creare una lista di categorie LETTE DA DB
		try {
			// 1. Cerco il blog nel DB
			Blog javaFromDB = bd.findById("a0b4829e-4793-4f23-acee-ec9af6a9f577");

			// 2. Cerco le categorie nel DB
			Category developmentFromDB = cd.findById("abe7b183-85cc-4866-a9d4-ca0e64975c39");
			Category oopFromDB = cd.findById("56ea5366-6b25-4795-b2b3-24b6a4a11aad");
			Category javaCatFromDB = cd.findById("2ad8a7c0-0a09-4547-b0e5-12171ac7aff6");
			Category backendFromDB = cd.findById("a9a47c4b-6d7a-4afd-87dc-d8cc765ab25a");

			// 3. Creo una lista con esse
			ArrayList<Category> javaCategories = new ArrayList<>();
			javaCategories.add(developmentFromDB);
			javaCategories.add(oopFromDB);
			javaCategories.add(javaCatFromDB);
			javaCategories.add(backendFromDB);

			// 4. Passo la lista al blog tramite setter
			javaFromDB.setCategories(javaCategories);

			// 5. Risalvo il blog e JPA associerà automaticamente tutti gli id delle categorie ad esso
			// bd.saveBlog(javaFromDB);

			System.out.println("Tutte le categorie del blog java");
			javaFromDB.getCategories().forEach(cat -> System.out.println(cat));

			System.out.println("Tutti i blog associati alla categoria Development sono: ");
			developmentFromDB.getBlogs().forEach(blog -> System.out.println(blog));


		} catch (NotFoundException ex) {
			System.out.println(ex.getMessage());
		}

		em.close();
		emf.close();
	}
}
