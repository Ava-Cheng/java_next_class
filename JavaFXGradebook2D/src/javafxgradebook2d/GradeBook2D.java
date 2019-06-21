package javafxgradebook2d;

public class GradeBook2D {

    private int[][] grades;
    private String[] names;
    private double[] averages;

    public GradeBook2D() {//建構子1
    }

    public GradeBook2D(int[][] grades) {//建構子2
        this.grades = grades;
    }

    public GradeBook2D(int grades[][], String[] names) //建構子3
    {
        this.names = names;
        this.grades = grades;
    }

    public void setGrades(int[][] grades) {
        this.grades = grades;
    }

    public int getNumberOfScore() {
        return this.grades[0].length;
    }

    public int getNumberOfStudent() {
        return grades.length;
    }

    public String outputGrades() {
        String msg = "";
        for (int student = 0; student < grades.length; student++) {
            msg += String.format("學生序號:%2d", student + 1);
            for (int test : grades[student]) {
                msg += String.format("成績:%3d", test);
            }
            double average = getAvg(grades[student]);
            msg += String.format("平均:%7.2f\n", average);
        }
        return msg;
    }

    public double get_classAvg() {
        int total = 0;
        int count = 0;

        for (int i = 0; i < grades.length; i++) {
            for (int j = 0; j < grades[i].length; j++) {
                total += grades[i][j];
                count++;
            }
        }
        return (double) total / count;
    }

    public double getAvg(int[] setOfGrades) {
        int total = 0;

        for (int grade : setOfGrades) {
            total += grade;
        }
        return (double) total / setOfGrades.length;
    }

    public int getMinimum() {
        int lowGrade = grades[0][0];
        for (int[] studentGrades : grades) {
            for (int grade : studentGrades) {
                if (grade < lowGrade) {
                    lowGrade = grade;
                }
            }
        }

        return lowGrade;
    }

    public int getFailed() {
        int count = 0;
        for (int i = 0; i < grades.length; i++) {
            for (int j = 0; j < grades[i].length; j++) {
                if (grades[i][j] < 60) {
                    count++;
                }
            }
        }
        return count;
    }

    //計算每位學生平均，以一維陣列回傳
    public double[] getStuAverages() {
        for (int i = 0; i < grades.length; i++) {
            int total = 0;
            for (int j = 0; j < grades[i].length; j++) {
                total = total + grades[i][j];
            }
            averages[i] = (double) total / grades[i].length;
        }
        return averages;
    }

}
