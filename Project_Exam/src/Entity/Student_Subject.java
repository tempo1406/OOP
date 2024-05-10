package Entity;

public class Student_Subject {
	private String idStudent;
	private String idExam;
	public Student_Subject(String idStudent, String idExam) {
		this.idStudent = idStudent;
		this.idExam = idExam;
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
