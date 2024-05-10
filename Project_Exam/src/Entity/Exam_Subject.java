package Entity;

public class Exam_Subject {
	private String idExam;
	private String idSubject;
	public Exam_Subject(String idExam, String idSubject) {
		this.idExam = idExam;
		this.idSubject = idSubject;
	}
	public String getIdExam() {
		return idExam;
	}
	public void setIdExam(String idExam) {
		this.idExam = idExam;
	}
	public String getIdSubject() {
		return idSubject;
	}
	public void setIdSubject(String idSubject) {
		this.idSubject = idSubject;
	}
	
	
}
