package chapter32_database;

public class Classes {

    private String class_id;
    public String class_name;
    public String credit;
    public String teacher_id;

    public Classes(String class_id, String class_name, String credit, String teacher_id) {
        this.class_id = class_id;
        this.class_name = class_name;
        this.credit = credit;
        this.teacher_id = teacher_id;
    }

    public void setId(String id) {
        this.class_id = class_id;
    }

    public void setName(String name) {
        this.class_name = class_name;
    }

    public void setCredit(String phone) {
        this.credit = credit;
    }

    public void setTeacher_id(String phone) {
        this.teacher_id = teacher_id;
    }

    public String getId() {
        return class_id;
    }

    public String getName() {
        return class_name;
    }

    public String getCredit() {
        return credit;
    }

    public String getTeacher_id() {
        return teacher_id;
    }
}
