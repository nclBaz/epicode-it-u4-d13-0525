package riccardogulin.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class Category {
	@Id
	@GeneratedValue
	@Column(name = "category_id")
	private UUID categoryId;
	@Column(nullable = false)
	private String name;

	// MANY TO MANY BIDIREZIONALE
	@ManyToMany(mappedBy = "categories")
	private List<Blog> blogs;

	public Category() {
	}

	public Category(String name) {
		this.name = name;
	}

	public List<Blog> getBlogs() {
		return blogs;
	}

	public UUID getCategoryId() {
		return categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Category{" +
				"categoryId=" + categoryId +
				", name='" + name + '\'' +
				'}';
	}
}
