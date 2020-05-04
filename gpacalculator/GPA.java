package gpacalculator;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author User
 * 
 * Model class for storing and calculating GPA for the given user
 * This class instantiates when the program starts and recalculates
 * the GPA every time the window is open so the user can see the 
 * effects of grade changes on their GPA
 * 
 */

public class GPA {
    
    private double overallGPA;
    private double semesterGPA;
    EntityManager manager;
    
    // Constructor that also creates EntityManager for the class
    public GPA() {
        this.overallGPA = 0.0;
        this.semesterGPA = 0.0;
        manager = (EntityManager) Persistence.createEntityManagerFactory("IST311ProjectD3").createEntityManager();
    }
    
    // Setters and getters for overallGPA and semesterGPA
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
    
    // Method takes the float value from the database and converts it into it's
    // grade point equivalent for calculation
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
    
    // Method calculates the GPA for the given semester and student
    // Stores the result in variable semesterGPA
    public void calcSemesterGPA(int semester, int studentID) {
        double qualityPoints = 0.0;
        int credits = 0;
        
        // Gets data from database for all of the classes the given student
        // took during the given semester
        Query query = manager.createNamedQuery("Enrollment.findByStudentIDandSemesterID");
        query.setParameter("studentID", studentID); //first is variable name second is the actual value being searched
        query.setParameter("semesterID", semester);
        List<Enrollment> data = query.getResultList();

        // Goes through list of Enrollment data and gets the course information
        // for each entry
        for (Enrollment d : data) {
            Query query_course = manager.createNamedQuery("Course.findByCourseID");
            query_course.setParameter("courseID", d.getCourseID());
            Course c = (Course) query_course.getSingleResult();
            
            // Stores two variables for data calculation based on the courses
            // pulled from the database
            qualityPoints += gradeToValue(d.getGrade()) * c.getCredit();
            credits += c.getCredit();
        }
        
        // Calculates GPA and stores in it semesterGPA
        semesterGPA = qualityPoints / credits;
    }
    
    // Method calculates the GPA for the given student over all semesters
    // Stores the result in variable overallGPA
    public void calcOverallGPA(int stuID) {
        double totalQualityPoints = 0.0;
        int totalCredits = 0;
        
        // Gets data from database for all of the classes the given student took
        Query query_main = manager.createNamedQuery("Enrollment.findByStudentID");
        query_main.setParameter("studentID", stuID);
        List<Enrollment> data = query_main.getResultList();

        // Goes through list of Enrollment data and gets the course information
        // for each entry
        for (Enrollment d : data) {
            Query query_course = manager.createNamedQuery("Course.findByCourseID");
            query_course.setParameter("courseID", d.getCourseID());
            Course c = (Course) query_course.getSingleResult();
            
            // Stores two variables for data calculation based on the courses
            // pulled from the database
            totalQualityPoints += gradeToValue(d.getGrade()) * c.getCredit();
            totalCredits += c.getCredit();
        }
        
        // Calculates GPA and stores in it overallGPA        
        overallGPA = totalQualityPoints / totalCredits;
    }
    
}
