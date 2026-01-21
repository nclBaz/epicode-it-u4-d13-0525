package riccardogulin.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue // Se non specifico una strategy, verrà usata la modalità AUTO
	@Column(name = "user_id")
	private UUID userId;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String surname;

	// 1 TO 1 BIDIREZIONALE
	@OneToOne(mappedBy = "owner")
	// Se inserisco un attributo anche da questo lato (con obbligatorietà di usare @OneToOne(mappedBy)
	// la relazione diventa BIDIREZIONALE.
	// Il vantaggio è quello di poter usare un getter anche da questo lato, che mi permetterà, una volta letto un utente dal DB,
	// di ottenere le info del suo documento facendo utenteFromDB.getDocument();
	// mappedBY = "owner" <-- "owner" si riferisce al nome dell'attributo nell'altra classe (non nome di colonna)
	// N.B. LA BIDIREZIONALITA' NON E' OBBLIGATORIA!!!!
	// N.B. LA BIDEREZIONALITA' NON IMPLICA LA CREAZIONE DI UN'ULTERIORE COLONNA!
	private Document document;

	// 1 TO MANY BIDIREZIONALE
	@OneToMany(mappedBy = "author")
	private List<Blog> blogs;

	public User() {
	}

	public User(String name, String surname) {
		this.name = name;
		this.surname = surname;
	}

	public List<Blog> getBlogs() {
		return blogs;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public UUID getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return "User{" +
				"userId=" + userId +
				", name='" + name + '\'' +
				", surname='" + surname + '\'' +
				//", document=" + document + //<-- Occhio al loop infinito dovuto alla BIDIREZIONALITA'
				'}';
	}
}
