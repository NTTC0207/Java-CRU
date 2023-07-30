package com.okta.developer.crud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "month_quota")
public class Month {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sub")
    private String sub;

    @Column(name = "year",updatable = false)
    @JsonIgnore
    private int year;

    @Column(name = "month",updatable = false)
    @JsonIgnore
    private int month;

    @Column(name = "achieved_quota")
    private BigDecimal achievedQuota;

    @Column(name = "is_archived")
    private boolean isArchived;

    @Column(name = "commission")
    private BigDecimal commission;


    @Column(name="total_quota")
    private BigDecimal total_quota;

//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    @JsonIgnore
//    private Users users;

    // Transient field to store formatted date
    @Transient
    private String formattedDate;

    public void setFormattedDate() {
        this.formattedDate = String.format("%04d-%02d", year, month);
    }
    public String getFormattedDate() {
        return formattedDate;
    }



}
