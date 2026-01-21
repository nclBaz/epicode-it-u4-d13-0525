package riccardogulin.entities;

import jakarta.persistence.*;

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

	public Blog() {
	}

	public Blog(String title, String content, User author) {
		this.title = title;
		this.content = content;
		this.author = author;
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
				'}';
	}
}
