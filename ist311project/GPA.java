package ist311project;

import java.util.*;

public class GPA {
    double overallGPA;
    double semesterGPA;
    
    public GPA() {
        this.overallGPA = 0.0;
        this.semesterGPA = 0.0;
    }
    
    public void setOverallGPA(double oG) {
        this.overallGPA = oG;
    }
    
    public double getOverallGPA() {
        return overallGPA;
    }
    
    public void setSemesterGPA(double sG) {
        this.semesterGPA = sG;
    }
    
    public double getSemesterGPA() {
        return semesterGPA;
    }
    
    public double gradeToValue(float grade) {
        double qualityValue = 0;

        if (grade >= 94) {
            qualityValue = 4.0;
        } else if (grade < 94 && grade >= 90) {
            qualityValue = 3.67;
        } else if (grade < 90 && grade >= 87) {
            qualityValue = 3.33;
        } else if (grade < 87 && grade >= 84) {
            qualityValue = 3.0;
        } else if (grade < 84 && grade >= 80) {
            qualityValue = 2.67;
        } else if (grade < 80 && grade >= 77) {
            qualityValue = 2.33;
        } else if (grade < 77 && grade >= 74) {
            qualityValue = 2.0;
        } else if (grade < 74 && grade >= 70) {
            qualityValue = 1.67;
        } else if (grade < 70 && grade >= 67) {
            qualityValue = 1.33;
        } else if (grade < 67 && grade >= 64) {
            qualityValue = 1.0;
        } else if (grade < 64 && grade >= 60) {
            qualityValue = 0.67;
        } else {
            qualityValue = 0.0;
        }

        return qualityValue;
    }
    
    public void calcSemesterGPA(ArrayList<Enrollment> grades, Student s, ArrayList<Course> course, int semester) {
        double qualityPoints = 0.0;
        int credits = 0;
        
        for (Enrollment e : grades) {
            if (e.getStudentID() == s.getStudentId()) {
                if (e.getSemesterID() == semester) {
                    for (Course c : course) {
                        if (c.getCourseID() == e.getCourseID()) {
                            qualityPoints += gradeToValue(e.getGrade()) * c.getCredit();
                            credits += c.getCredit();
                        }
                    }
                }
            }
        }
        
        semesterGPA = qualityPoints / credits;
    }
    
    public void calcOverallGPA(ArrayList<Enrollment> grades, Student s, ArrayList<Course> course) {
        double totalQualityPoints = 0.0;
        int totalCredits = 0;
        int studentId = s.getStudentId();
        
        for (Enrollment e : grades) {
            if (e.getStudentID() == studentId) {
                for (Course c : course) {
                    if (c.getCourseID() == e.getCourseID()) {
                        totalQualityPoints += gradeToValue(e.getGrade()) * c.getCredit();
                        totalCredits += c.getCredit();
                    }
                }
            }
        }
        
        overallGPA = totalQualityPoints / totalCredits;
    }
    
    public String overallToString() {
        return "" + overallGPA;
    }
    
    public String semesterToString() {
        return "" + semesterGPA;
    }
}