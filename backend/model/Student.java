package backend.model;

public class Student {
    private int studentId;
    private int userId;
    private String name;
    private String rollNumber;
    private String department;
    private String year;
    private String contact;
    private String email;

    public Student() {
    }

    public Student(int studentId, int userId, String name, String rollNumber,
            String department, String year, String contact, String email) {
        this.studentId = studentId;
        this.userId = userId;
        this.name = name;
        this.rollNumber = rollNumber;
        this.department = department;
        this.year = year;
        this.contact = contact;
        this.email = email;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
