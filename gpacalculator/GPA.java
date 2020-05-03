/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gpacalculator;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class GPA {
    double overallGPA;
    double semesterGPA;
    EntityManager manager;
    
    public GPA() {
        this.overallGPA = 0.0;
        this.semesterGPA = 0.0;
        manager = (EntityManager) Persistence.createEntityManagerFactory("IST311ProjectD3").createEntityManager();
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
    
    public void calcSemesterGPA(int semester, int studentID) {
        double qualityPoints = 0.0;
        int credits = 0;
        
        Query query = manager.createNamedQuery("Enrollment.findByStudentIDandSemesterID");
        query.setParameter("studentID", studentID); //first is variable name second is the actual value being searched
        query.setParameter("semesterID", semester);
        List<Enrollment> data = query.getResultList();

        for (Enrollment d : data) {
            Query query_course = manager.createNamedQuery("Course.findByCourseID");
            query_course.setParameter("courseID", d.getCourseID());
            Course c = (Course) query_course.getSingleResult();
            qualityPoints += gradeToValue(d.getGrade()) * c.getCredit();
            credits += c.getCredit();
        }
        
//        for (Enrollment e : grades) {
//            if (e.getStudentID() == s.getStudentId()) {
//                if (e.getSemesterID() == semester) {
//                    for (Course c : course) {
//                        if (c.getCourseID() == e.getCourseID()) {
//                            qualityPoints += gradeToValue(e.getGrade()) * c.getCredit();
//                            credits += c.getCredit();
//                        }
//                    }
//                }
//            }
//        }
        
        semesterGPA = qualityPoints / credits;
    }
    
    public void calcOverallGPA(int stuID) {
        double totalQualityPoints = 0.0;
        int totalCredits = 0;
        
        Query query_main = manager.createNamedQuery("Enrollment.findByStudentID");
        query_main.setParameter("studentID", stuID); //first is variable name second is the actual value being searched;
        List<Enrollment> data = query_main.getResultList();

        for (Enrollment d : data) {
            Query query_course = manager.createNamedQuery("Course.findByCourseID");
            query_course.setParameter("courseID", d.getCourseID());
            Course c = (Course) query_course.getSingleResult();
            totalQualityPoints += gradeToValue(d.getGrade()) * c.getCredit();
            totalCredits += c.getCredit();
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
