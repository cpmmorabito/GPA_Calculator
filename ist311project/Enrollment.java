package ist311project;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Enrollment")
@NamedQueries({
    @NamedQuery(name = "Enrollment.findAll", query = "SELECT e FROM Enrollment e"),
    @NamedQuery(name = "Enrollment.findById", query = "SELECT e FROM Enrollment e WHERE e.id = :id"),
    @NamedQuery(name = "Enrollment.findByStudentID", query = "SELECT e FROM Enrollment e WHERE e.studentID = :studentID"),
    @NamedQuery(name = "Enrollment.findByStudentIDandCourseID", query = "SELECT e FROM Enrollment e WHERE e.studentID = :studentID AND e.courseID = :courseID"),
    @NamedQuery(name = "Enrollment.findByStudentIDandSemesterID", query = "SELECT e FROM Enrollment e WHERE e.studentID = :studentID AND e.semesterID = :semesterID"),
    @NamedQuery(name = "Enrollment.findByCourseID", query = "SELECT e FROM Enrollment e WHERE e.courseID = :courseID"),
    @NamedQuery(name = "Enrollment.findByGrade", query = "SELECT e FROM Enrollment e WHERE e.grade = :grade"),
    @NamedQuery(name = "Enrollment.findBySemesterID", query = "SELECT e FROM Enrollment e WHERE e.semesterID = :semesterID")})
public class Enrollment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "courseID")
    private String courseID;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "grade")
    private Float grade;
    @Basic(optional = false)
    @Column(name = "studentID")
    private int studentID;
    @Basic(optional = false)
    @Column(name = "semesterID")
    private int semesterID;

    public Enrollment() {
    }

    public Enrollment(Long id) {
        this.id = id;
    }

    public Enrollment(Long id, int studentID, String courseID, int semesterID) {
        this.id = id;
        this.studentID = studentID;
        this.courseID = courseID;
        this.semesterID = semesterID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }
    
    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getSemesterID() {
        return semesterID;
    }

    public void setSemesterID(int semesterID) {
        this.semesterID = semesterID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Enrollment)) {
            return false;
        }
        Enrollment other = (Enrollment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gpacalculator.Enrollment[ id=" + id + " ]";
    }
}