package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import Entity.BlockExecution;
import Entity.Exam;
import Entity.Exam_Subject;
import Entity.Score;
import Entity.Student;

public class ManagerClass {

	private static final String COMMA_DELIMITER = ",";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/quanlytuyensinh?characterEncoding=UTF-8";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "Cutmauvang456";

	public void run() {

		List<List<String>> resultReadFile = readCSVFile();
		for (Iterator<List<String>> iterator = resultReadFile.iterator(); iterator.hasNext();) {
			List<String> list = (List<String>) iterator.next();                
			if (isValidData(list))
				System.out.println(list);
		}

		initializeExams();

		runWriterLogs();
		
		insertExamSubject();
		
		executeInsertInitializeExams();

		System.out.println("Block A0 candidates");
		for (Iterator<List<String>> iterator = resultReadFile.iterator(); iterator.hasNext();) {
			List<String> list = (List<String>) iterator.next();
			if (list.get(9).equals("A0")) {
				double ToanMark = Double.parseDouble(list.get(10));
				double LyMark = Double.parseDouble(list.get(11));
				double HoaMark = Double.parseDouble(list.get(12));
				double totalA = ToanMark + LyMark + HoaMark;

				if (totalA >= 12)
					System.out.println(list);
			}
		}
		System.out.println();

		System.out.println("Block B0 candidates");
		for (Iterator<List<String>> iterator = resultReadFile.iterator(); iterator.hasNext();) {
			List<String> list = (List<String>) iterator.next();
			if (list.get(9).equals("B0")) {
				double ToanMark = Double.parseDouble(list.get(10));
				double HoaMark = Double.parseDouble(list.get(12));
				double SinhMark = Double.parseDouble(list.get(15));
				double totalB = ToanMark + HoaMark + SinhMark;

				if (totalB >= 12)
					System.out.println(list);
			}
		}
		System.out.println();

		System.out.println("Block C0 candidates");
		for (Iterator<List<String>> iterator = resultReadFile.iterator(); iterator.hasNext();) {
			List<String> list = (List<String>) iterator.next();
			if (list.get(9).equals("C0")) {
				double VanMark = Double.parseDouble(list.get(13));
				double SuMark = Double.parseDouble(list.get(16));
				double DiaMark = Double.parseDouble(list.get(17));
				double totalC = VanMark + SuMark + DiaMark;

				if (totalC >= 12)
					System.out.println(list);
			}
		}
		System.out.println();

		System.out.println("Block D0 candidates");
		for (Iterator<List<String>> iterator = resultReadFile.iterator(); iterator.hasNext();) {
			List<String> list = (List<String>) iterator.next();
			if (list.get(9).equals("D0")) {
				double ToanMark = Double.parseDouble(list.get(10));
				double VanMark = Double.parseDouble(list.get(13));
				double AnhMark = Double.parseDouble(list.get(14));
				double totalD = ToanMark + VanMark + AnhMark;

				if (totalD >= 12)
					System.out.println(list);
			}
		}

		insertCSV();
	}

	public void insertCSV() {
		List<List<String>> resultReadFile = readCSVFile();
		for (Iterator<List<String>> iterator = resultReadFile.iterator(); iterator.hasNext();) {
			List<String> list = (List<String>) iterator.next();
			if (isValidData(list)) {
				Student student = new Student(list.get(0), list.get(1), Validation.parseDate(list.get(2)), list.get(3),
						list.get(4) + "," + list.get(5), Integer.parseInt(list.get(6)));

	            double toanMark = list.get(10).equals("N/A") ? 0.0 : Double.parseDouble(list.get(10));
	            double lyMark = list.get(11).equals("N/A") ? 0.0 : Double.parseDouble(list.get(11));
	            double hoaMark = list.get(12).equals("N/A") ? 0.0 : Double.parseDouble(list.get(12));
	            double vanMark = list.get(13).equals("N/A") ? 0.0 : Double.parseDouble(list.get(13));
	            double anhMark = list.get(14).equals("N/A") ? 0.0 : Double.parseDouble(list.get(14));
	            double sinhMark = list.get(15).equals("N/A") ? 0.0 : Double.parseDouble(list.get(15));
	            double suMark = list.get(16).equals("N/A") ? 0.0 : Double.parseDouble(list.get(16));
	            double diaMark = list.get(17).equals("N/A") ? 0.0 : Double.parseDouble(list.get(17));
	            Score score1 = new Score("TOAN", toanMark, list.get(0), list.get(7));
	            Score score2 = new Score("LY", lyMark, list.get(0), list.get(7));
	            Score score3 = new Score("HOA", hoaMark, list.get(0), list.get(7));
	            Score score4 = new Score("VAN", vanMark, list.get(0), list.get(7));
	            Score score5 = new Score("ANH", anhMark, list.get(0), list.get(7));
	            Score score6 = new Score("SINH", sinhMark, list.get(0), list.get(7));
	            Score score7 = new Score("SU", suMark, list.get(0), list.get(7));
	            Score score8 = new Score("DIA", diaMark, list.get(0), list.get(7));

				try {
					Connection conn = ManagerClass.getConnection(DB_URL, USER_NAME, PASSWORD);
					PreparedStatement statement1 = conn.prepareStatement(
							"INSERT INTO student (idStudent, fullName, birthDay, gender, address, phoneNum) VALUES ( ?, ?, ?, ?, ?, ?)");
					statement1.setString(1, student.getIdStudent());
					statement1.setString(2, student.getFullName());
					SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
					java.util.Date birthDate = dateFormat.parse(list.get(2));
					statement1.setDate(3, new java.sql.Date(birthDate.getTime()));
					statement1.setString(4, student.getGender());
					statement1.setString(5, student.getAddress());
					statement1.setInt(6, student.getPhoneNum());
					statement1.execute();
					
					PreparedStatement statement2 = conn
							.prepareStatement("INSERT INTO Student_Subject (idStudent, idExam) VALUES (?, ?)");
					statement2.setString(1, list.get(0));
					statement2.setString(2, list.get(7));
					statement2.execute();
					
					
					PreparedStatement statement3 = conn
							.prepareStatement("INSERT INTO exam_subject (idExam, idSubject) VALUES (?, ?)");
					if (list.get(7).equals("TSDH-2024-KV-001")) {
						Exam_Subject es1 = new Exam_Subject(list.get(7), "TOAN");
						statement3.setString(1, es1.getIdExam());
						statement3.setString(2, es1.getIdSubject());
						statement3.execute();
						
						Exam_Subject es2 = new Exam_Subject(list.get(7), "LY");
						statement3.setString(1, es2.getIdExam());
						statement3.setString(2, es2.getIdSubject());
						statement3.execute();
						
						Exam_Subject es3 = new Exam_Subject(list.get(7), "HOA");
						statement3.setString(1, es3.getIdExam());
						statement3.setString(2, es3.getIdSubject());
						statement3.execute();
						
						Exam_Subject es4 = new Exam_Subject(list.get(7), "SINH");
						statement3.setString(1, es4.getIdExam());
						statement3.setString(2, es4.getIdSubject());
						statement3.execute();
						
					} else if (list.get(7).equals("TSDH-2024-KV-002")) {
						Exam_Subject es1 = new Exam_Subject(list.get(7), "TOAN");
						statement3.setString(1, es1.getIdExam());
						statement3.setString(2, es1.getIdSubject());
						statement3.execute();
						
						Exam_Subject es2 = new Exam_Subject(list.get(7), "LY");
						statement3.setString(1, es2.getIdExam());
						statement3.setString(2, es2.getIdSubject());
						statement3.execute();
						
						Exam_Subject es3 = new Exam_Subject(list.get(7), "HOA");
						statement3.setString(1, es3.getIdExam());
						statement3.setString(2, es3.getIdSubject());
						statement3.execute();
						
						Exam_Subject es4 = new Exam_Subject(list.get(7), "VAN");
						statement3.setString(1, es4.getIdExam());
						statement3.setString(2, es4.getIdSubject());
						statement3.execute();
						
						Exam_Subject es5 = new Exam_Subject(list.get(7), "ANH");
						statement3.setString(1, es5.getIdExam());
						statement3.setString(2, es5.getIdSubject());
						statement3.execute();
						
						Exam_Subject es6 = new Exam_Subject(list.get(7), "SINH");
						statement3.setString(1, es6.getIdExam());
						statement3.setString(2, es6.getIdSubject());
						statement3.execute();
						
						Exam_Subject es7 = new Exam_Subject(list.get(7), "SU");
						statement3.setString(1, es7.getIdExam());
						statement3.setString(2, es7.getIdSubject());
						statement3.execute();
						
						Exam_Subject es8 = new Exam_Subject(list.get(7), "DIA");
						statement3.setString(1, es8.getIdExam());
						statement3.setString(2, es8.getIdSubject());
						statement3.execute();
					}

					PreparedStatement statement4 = conn.prepareStatement(
							"INSERT INTO score (idSubject, score, idStudent, idExam) VALUES (?, ?, ?, ?)");
					
					statement4.setString(1, score1.getIdSubject());
					statement4.setDouble(2, score1.getScore());
					statement4.setString(3, score1.getIdStudent());
					statement4.setString(4, score1.getIdExam());
					statement4.execute();

					statement4.setString(1, score2.getIdSubject());
					statement4.setDouble(2, score2.getScore());
					statement4.setString(3, score2.getIdStudent());
					statement4.setString(4, score2.getIdExam());
					statement4.execute();

					statement4.setString(1, score3.getIdSubject());
					statement4.setDouble(2, score3.getScore());
					statement4.setString(3, score3.getIdStudent());
					statement4.setString(4, score3.getIdExam());
					statement4.execute();

					statement4.setString(1, score4.getIdSubject());
					statement4.setDouble(2, score4.getScore());
					statement4.setString(3, score4.getIdStudent());
					statement4.setString(4, score4.getIdExam());
					statement4.execute();

					statement4.setString(1, score5.getIdSubject());
					statement4.setDouble(2, score5.getScore());
					statement4.setString(3, score5.getIdStudent());
					statement4.setString(4, score5.getIdExam());
					statement4.execute();

					statement4.setString(1, score6.getIdSubject());
					statement4.setDouble(2, score6.getScore());
					statement4.setString(3, score6.getIdStudent());
					statement4.setString(4, score6.getIdExam());
					statement4.execute();

					statement4.setString(1, score7.getIdSubject());
					statement4.setDouble(2, score7.getScore());
					statement4.setString(3, score7.getIdStudent());
					statement4.setString(4, score7.getIdExam());
					statement4.execute();

					statement4.setString(1, score8.getIdSubject());
					statement4.setDouble(2, score8.getScore());
					statement4.setString(3, score8.getIdStudent());
					statement4.setString(4, score8.getIdExam());
					statement4.execute();
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

	public void insertExamSubject() {
		try {
			Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);
			PreparedStatement statement = conn.prepareStatement("INSERT INTO examsubject (idSubject) VALUES ( ?)");
			statement.setString(1, "TOAN");
			statement.execute();
			statement.setString(1, "LY");
			statement.execute();
			statement.setString(1, "HOA");
			statement.execute();
			statement.setString(1, "VAN");
			statement.execute();
			statement.setString(1, "ANH");
			statement.execute();
			statement.setString(1, "SINH");
			statement.execute();
			statement.setString(1, "SU");
			statement.execute();
			statement.setString(1, "DIA");
			statement.execute();

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initializeExams() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		dateFormat.setLenient(false);
		try {

			Exam exam1 = new Exam("TSDH-2024-KV-001", dateFormat.parse("04/15/2024"), dateFormat.parse("04/20/2024"),
					"Ky thi tuyen sinh dot 1 nam 2024");
			exam1.addBlock(new BlockExecution("A0", dateFormat.parse("04/15/2024")));
			exam1.addBlock(new BlockExecution("B0", dateFormat.parse("04/16/2024")));

			Exam exam2 = new Exam("TSDH-2024-KV-002", dateFormat.parse("07/15/2024"), dateFormat.parse("07/20/2024"),
					"Ky thi tuyen sinh dot 2 nam 2024");
			exam2.addBlock(new BlockExecution("A0", dateFormat.parse("07/15/2024")));
			exam2.addBlock(new BlockExecution("B0", dateFormat.parse("07/16/2024")));
			exam2.addBlock(new BlockExecution("C0", dateFormat.parse("07/16/2024")));
			exam2.addBlock(new BlockExecution("D0", dateFormat.parse("07/16/2024")));

			System.out.println("Exam 1: " + exam1.toString());
			System.out.println("Exam 2: " + exam2.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void runWriterLogs() {
		try {
			List<List<String>> resultReadFile = readCSVFile();
			List<String[]> dataLines = new ArrayList<>();

			for (List<String> list : resultReadFile) {
				if (!isValidData(list)) {
					String[] dataArray = list.toArray(new String[0]);
					dataLines.add(dataArray);
				}
			}

			if (!dataLines.isEmpty()) {
				writerLogs(dataLines);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Map<String, String>> executeQuery(String SQLstatement) {
		List<Map<String, String>> list = new ArrayList<>();
		try {
			Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQLstatement);
			while (rs.next()) {
				Map<String, String> sv = new HashMap<>();
				sv.put("code", String.valueOf(rs.getInt("ID")));
				sv.put("name", rs.getString("NAME"));
				list.add(sv);
			}
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void executeInsertInitializeExams() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		try {
			Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO exam (idExam, startDate, endDate, description) VALUES (?, ?, ?, ?)");
			statement.setString(1, "TSDH-2024-KV-001");

			java.util.Date startDate = dateFormat.parse("04/15/2024");
			java.util.Date endDate = dateFormat.parse("04/20/2024");

			statement.setDate(2, new java.sql.Date(startDate.getTime()));
			statement.setDate(3, new java.sql.Date(endDate.getTime()));
			statement.setString(4, "Ky thi tuyen sinh dot 1 nam 2024");

			statement.execute();

			java.util.Date Date1 = dateFormat.parse("04/15/2024");
			java.util.Date eDate1 = dateFormat.parse("04/16/2024");

			PreparedStatement blockStatement1 = conn
					.prepareStatement("INSERT INTO blockexecution (idBlock, executionDate) VALUES (?, ?)");
			blockStatement1.setString(1, "A0");
			blockStatement1.setDate(2, new java.sql.Date(Date1.getTime()));
			blockStatement1.execute();

			blockStatement1.setString(1, "B0");
			blockStatement1.setDate(2, new java.sql.Date(eDate1.getTime()));
			blockStatement1.execute();

			PreparedStatement statement2 = conn
					.prepareStatement("INSERT INTO Exam (idExam, startDate, endDate, description) VALUES (?, ?, ?, ?)");
			statement2.setString(1, "TSDH-2024-KV-002");
			java.util.Date startDate2 = dateFormat.parse("07/15/2024");
			java.util.Date endDate2 = dateFormat.parse("07/20/2024");
			statement2.setDate(2, new java.sql.Date(startDate2.getTime()));
			statement2.setDate(3, new java.sql.Date(endDate2.getTime()));
			statement2.setString(4, "Ky thi tuyen sinh dot 2 nam 2024");

			statement2.execute();

			PreparedStatement blockStatement2 = conn
					.prepareStatement("INSERT INTO blockexecution (idBlock, executionDate) VALUES (?, ?)");
			blockStatement2.setString(1, "A0");
			blockStatement2.setDate(2, new java.sql.Date(startDate2.getTime()));

			java.util.Date Date2 = dateFormat.parse("07/16/2024");

			blockStatement2.setString(1, "B0");
			blockStatement2.setDate(2, new java.sql.Date(Date2.getTime()));
			blockStatement2.execute();

			blockStatement2.setString(1, "C0");
			blockStatement2.setDate(2, new java.sql.Date(Date2.getTime()));
			blockStatement2.execute();

			blockStatement2.setString(1, "D0");
			blockStatement2.setDate(2, new java.sql.Date(Date2.getTime()));
			blockStatement2.execute();

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isValidData(List<String> data) {
		String dateFormat = "MM/DD/YYYY";
		if (data.size() != 18) {
			return false;
		}

		if (!Validation.isValidDate(data.get(2), dateFormat) || !Validation.isValidDate(data.get(8), dateFormat)) {
			System.out.println(data.get(8));
			return false;
		}

		if (!Validation.isAgeValid(data.get(2), data.get(8))) {
			return false;
		}

		if (!Validation.isValidGender(data.get(3))) {
			return false;
		}

		String examCode = data.get(7);
		String block = data.get(9);

		if (!Validation.isValidExamCode(examCode) || !Validation.isValidBlock(block)
				|| !Validation.isValidSubject(block, data.get(10), data.get(11), data.get(12), data.get(13),
						data.get(14), data.get(15), data.get(16), data.get(17))) {
			return false;
		}

		return true;
	}

	public List<List<String>> readCSVFile() {
		BufferedReader br = null;
		List<List<String>> results = new ArrayList<>();
		try {
			String line;
			File starting = new File(System.getProperty("user.dir"), "ket_qua_thi_tuyen_sinh.csv");
			br = new BufferedReader(new FileReader(starting));
			while ((line = br.readLine()) != null) {
				results.add(parseCsvLine(line));
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException crunchifyException) {
				crunchifyException.printStackTrace();
			}
		}
		return results;
	}

	private void writerLogs(List<String[]> dataLines) throws IOException {

		File csvOutputFile = new File(System.getProperty("user.dir"), "loi_ket_qua_thi.csv");
		try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
			dataLines.stream().map(this::convertToCSV).forEach(pw::println);
		} finally {
			csvOutputFile.exists();
		}
	}

	private String convertToCSV(String[] data) {
		return Stream.of(data).map(this::escapeSpecialCharacters).collect(Collectors.joining(","));
	}

	private String escapeSpecialCharacters(String data) {
		if (data == null) {
			throw new IllegalArgumentException("Input data cannot be null");
		}
		String escapedData = data.replaceAll("\\R", " ");
		if (data.contains(",") || data.contains("\"") || data.contains("'")) {
			data = data.replace("\"", "\"\"");
			escapedData = "\"" + data + "\"";
		}
		return escapedData;
	}

	/**
	 * 
	 * @param dbURL
	 * @param userName
	 * @param password
	 * @return
	 */
	private static Connection getConnection(String dbURL, String userName, String password) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, userName, password);

		} catch (Exception ex) {
			System.out.println("connect failure!");
			ex.printStackTrace();
		}
		return conn;
	}

	private static List<String> parseCsvLine(String csvLine) {
		List<String> result = new ArrayList<String>();
		if (csvLine != null) {
			String[] splitData = csvLine.split(COMMA_DELIMITER);
			for (int i = 0; i < splitData.length; i++) {
				result.add(splitData[i]);
			}
		}
		return result;
	}

}
