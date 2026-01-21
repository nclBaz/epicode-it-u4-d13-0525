package riccardogulin.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "blogs")
public class Blog {
	@Id
	@GeneratedValue
	@Column(name = "blog_id")
	private UUID blogId;
	private String title;
	private String content;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User author;

	@ManyToMany
	@JoinTable(name = "blogs_categories",
			joinColumns = @JoinColumn(name = "blog_id", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "category_id", nullable = false))
	// @JoinTable non è di per se obbligatoria, però non usandola JPA solitamente fa un po' di pasticci
	// meglio usarla per scegliere noi come sarà fatta la JUNCTION TABLE
	private List<Category> categories;

	public Blog() {
	}

	public Blog(String title, String content, User author) {
		this.title = title;
		this.content = content;
		this.author = author;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public UUID getBlogId() {
		return blogId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getAuthor() {
		return author;
	}


	@Override
	public String toString() {
		return "Blog{" +
				"blogId=" + blogId +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", author=" + author +
				", categories=" + categories +
				'}';
	}
}
