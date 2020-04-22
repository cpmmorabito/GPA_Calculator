/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import java.io.Serializable;
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
@Table(name = "Semester")
@NamedQueries({
    @NamedQuery(name = "Semester.findAll", query = "SELECT s FROM Semester s"),
    @NamedQuery(name = "Semester.findByYear", query = "SELECT s FROM Semester s WHERE s.year = :year"),
    @NamedQuery(name = "Semester.findByCumulativeGPA", query = "SELECT s FROM Semester s WHERE s.cumulativeGPA = :cumulativeGPA"),
    @NamedQuery(name = "Semester.findByOverallGPA", query = "SELECT s FROM Semester s WHERE s.overallGPA = :overallGPA")})
public class Semester implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "year")
    private String year;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cumulativeGPA")
    private Float cumulativeGPA;
    @Column(name = "overallGPA")
    private Float overallGPA;

    public Semester() {
    }

    public Semester(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Float getCumulativeGPA() {
        return cumulativeGPA;
    }

    public void setCumulativeGPA(Float cumulativeGPA) {
        this.cumulativeGPA = cumulativeGPA;
    }

    public Float getOverallGPA() {
        return overallGPA;
    }

    public void setOverallGPA(Float overallGPA) {
        this.overallGPA = overallGPA;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (year != null ? year.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Semester)) {
            return false;
        }
        Semester other = (Semester) object;
        if ((this.year == null && other.year != null) || (this.year != null && !this.year.equals(other.year))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "example.Semester[ year = " + year + " ]";
    }
    
}