package demo.command.mvc;
/**
 * The model for the commit
 * @author fernandojribeiro
 */
public class Commit {
	private String shortHash;
	private String authorName;
	private String authorEmail;
	private String authorDate;
	private String authorRelativeDate;
	private String subject;

	public Commit(String shortHash, String authorName, String authorEmail, String authorDate, String authorRelativeDate, String subject) {
		this.shortHash = shortHash;
		this.authorName = authorName;
		this.authorEmail = authorEmail;
		this.authorDate = authorDate;
		this.authorRelativeDate = authorRelativeDate;
		this.subject = subject;
	}
	
	public String getShortHash() {
		return shortHash;
	}

	public void setShortHash(String shortHash) {
		this.shortHash = shortHash;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorEmail() {
		return authorEmail;
	}

	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}

	public String getAuthorDate() {
		return authorDate;
	}

	public void setAuthorDate(String authorDate) {
		this.authorDate = authorDate;
	}

	public String getAuthorRelativeDate() {
		return authorRelativeDate;
	}

	public void setAuthorRelativeDate(String authorRelativeDate) {
		this.authorRelativeDate = authorRelativeDate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
