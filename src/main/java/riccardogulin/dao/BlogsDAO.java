package riccardogulin.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import riccardogulin.entities.Blog;
import riccardogulin.exceptions.NotFoundException;

import java.util.UUID;

public class BlogsDAO {
	private final EntityManager em;

	public BlogsDAO(EntityManager em) {
		this.em = em;
	}

	public void saveBlog(Blog newBlog) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		em.persist(newBlog);

		transaction.commit();

		System.out.println("L'articolo " + newBlog.getBlogId() + " è stato salvato!");
	}

	public Blog findById(String blogId) {
		Blog found = em.find(Blog.class, UUID.fromString(blogId));
		if (found == null) throw new NotFoundException(blogId);
		return found;
	}
}
