package Entity;

public class Score {
	private String idSubject;
	private double score;
	private String idStudent;
	private String idExam;

	public Score(String idSubject, double score, String idStudent, String idExam) {
		this.idSubject = idSubject;
		this.score = score;
		this.idStudent = idStudent;
		this.idExam = idExam;
	}

	public String getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(String idSubject) {
		this.idSubject = idSubject;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(String idStudent) {
		this.idStudent = idStudent;
	}

	public String getIdExam() {
		return idExam;
	}

	public void setIdExam(String idExam) {
		this.idExam = idExam;
	}

}
