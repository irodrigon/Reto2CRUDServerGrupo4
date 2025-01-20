package com.tartanga.grupo4.loans;

import com.tartanga.grupo4.product.Product;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author egure
 */
@Entity
@Table(name = "loans", schema = "rovobankdb")
@NamedQueries({
    @NamedQuery(name = "getBalanceByRemaining", query = "SELECT l FROM Loan l WHERE l.amount > :balance"),
    @NamedQuery(name = "getLoansByInterestRate", query = "SELECT l FROM Loan l WHERE l.interest <= :interestRate"),
    @NamedQuery(name = "findByDatesLoan", query = "SELECT l FROM Loan l WHERE l.endDate >= :startDate AND l.endDate <= :endDate"),
})
@XmlRootElement
public class Loan extends Product implements Serializable {

    private static final long serialVersionUID = 1L;

  
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "loan_id") 
    private Long loanId;

    @Temporal(TemporalType.DATE)
    @Column(name = "creation_date", nullable = false, insertable = true, updatable = true)
    private Date creationDate;

    @Column(name = "interest", nullable = false)
    private Integer interest;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "period", nullable = false)
    private Integer period;

    // Constructor predeterminado
    public Loan() {
        this.creationDate = super.getCreationDate();
    }

    // Getters y Setters
    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Integer getInterest() {
        return interest;
    }

    public void setInterest(Integer interest) {
        this.interest = interest;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    // Métodos estándar de hashCode, equals y toString
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loanId != null ? loanId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Loan)) {
            return false;
        }
        Loan other = (Loan) object;
        return (this.loanId != null || other.loanId == null) && (this.loanId == null || this.loanId.equals(other.loanId));
    }

    @Override
    public String toString() {
        return "com.tartanga.grupo4.loans.Loan[ loanId=" + loanId + " ]";
    }
}
