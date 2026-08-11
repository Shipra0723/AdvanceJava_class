package entity;

public class Student {
    private int id;
    private String name;
    private String email;
    private long regdno;

    public Student(int id, String name, String email, long regdNo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.regdno = regdNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getRegdno() {
        return regdno;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", regdno=" + regdno +
                '}';
    }

    public void setRegdno(long regdno) {
        this.regdno = regdno;

    }
}
