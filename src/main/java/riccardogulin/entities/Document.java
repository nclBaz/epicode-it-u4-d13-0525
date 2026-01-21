package riccardogulin.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "documents")
public class Document {
	@Id
	@GeneratedValue
	@Column(name = "document_id")
	private UUID documentId;
	@Column(nullable = false)
	private LocalDate issueDate;
	@Column(nullable = false)
	private LocalDate expirationDate;
	@Column(nullable = false)
	private String country;
	@Column(nullable = false)
	private String code;

	// FOREIGN KEY
	@OneToOne
	@JoinColumn(name = "user_id", unique = true, nullable = false) // Serve per personalizzare la FK
	private User owner; // Se creo un attributo che si riferisce ad un'altra entità, è OBBLIGATORIO usare una delle annotazioni @OneTo...

	public Document() {
	}

	public Document(String code, String country, User owner) {
		this.code = code;
		this.country = country;
		this.owner = owner;
		this.issueDate = LocalDate.now();
		this.expirationDate = LocalDate.now().plusYears(10);
	}

	public UUID getDocumentId() {
		return documentId;
	}


	public LocalDate getIssueDate() {
		return issueDate;
	}


	public LocalDate getExpirationDate() {
		return expirationDate;
	}


	public String getCountry() {
		return country;
	}

	public String getCode() {
		return code;
	}


	public User getOwner() {
		return owner;
	}


	@Override
	public String toString() {
		return "Document{" +
				"documentId=" + documentId +
				", issueDate=" + issueDate +
				", expirationDate=" + expirationDate +
				", country='" + country + '\'' +
				", code='" + code + '\'' +
				", owner=" + owner +
				'}';
	}
}
