/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author fkash
 */
@Entity
@Table(name = "Couse")
@NamedQueries({
    @NamedQuery(name = "Couse.findAll", query = "SELECT c FROM Couse c"),
    @NamedQuery(name = "Couse.findByCouseId", query = "SELECT c FROM Couse c WHERE c.couseId = :couseId"),
    @NamedQuery(name = "Couse.findByCredit", query = "SELECT c FROM Couse c WHERE c.credit = :credit"),
    @NamedQuery(name = "Couse.findByCourseName", query = "SELECT c FROM Couse c WHERE c.courseName = :courseName")})
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "couseId")
    private Long courseId;
    @Column(name = "credit")
    private BigInteger credit;
    @Column(name = "courseName")
    private String courseName;

    public Course() {
    }

    public Course(Long couseId) {
        this.courseId = couseId;
    }

    public Long getCouseId() {
        return courseId;
    }

    public void setCouseId(Long couseId) {
        this.courseId = couseId;
    }

    public BigInteger getCredit() {
        return credit;
    }

    public void setCredit(BigInteger credit) {
        this.credit = credit;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (courseId != null ? courseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Course)) {
            return false;
        }
        Course other = (Course) object;
        if ((this.courseId == null && other.courseId != null) || (this.courseId != null && !this.courseId.equals(other.courseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "example.Course[ couseId=" + couseId + " ]";
    }
    
}
