package riccardogulin.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import riccardogulin.entities.Category;
import riccardogulin.exceptions.NotFoundException;

import java.util.UUID;

public class CategoriesDAO {
	private final EntityManager em;

	public CategoriesDAO(EntityManager em) {
		this.em = em;
	}

	public void saveCategory(Category newCategory) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		em.persist(newCategory);

		transaction.commit();

		System.out.println("La categoria " + newCategory.getCategoryId() + " è stata salvata!");
	}

	public Category findById(String categoryId) {
		Category found = em.find(Category.class, UUID.fromString(categoryId));
		if (found == null) throw new NotFoundException(categoryId);
		return found;
	}
}
