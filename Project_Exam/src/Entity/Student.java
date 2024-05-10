package Entity;

import java.util.Date;

public class Student {
    private String idStudent;
    private String fullName;
    private Date birthDay;
    private String gender;
    private String address;
    private int phoneNum;

    public Student(String idStudent, String fullName, Date birthDay, String gender, String address, int phoneNum) {
		this.idStudent = idStudent;
		this.fullName = fullName;
		this.birthDay = birthDay;
		this.gender = gender;
		this.address = address;
		this.phoneNum = phoneNum;
	}

	public String getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(String idStudent) {
		this.idStudent = idStudent;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(int phoneNum) {
		this.phoneNum = phoneNum;
	}

	@Override
	public String toString() {
		return "Student [idStudent=" + idStudent + ", fullName=" + fullName + ", birthDay=" + birthDay + ", gender="
				+ gender + ", address=" + address + ", phoneNum=" + phoneNum + "]";
	}

    
    
    
}

