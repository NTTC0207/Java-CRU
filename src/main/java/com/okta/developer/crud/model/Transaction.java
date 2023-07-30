package com.okta.developer.crud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false)
    private LocalDate date;



    private Double amount;

    @Column(name = "month_id",insertable = true, updatable = false)
    private Long month_id;

    @ManyToOne
    @JoinColumn(name = "month_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Month month;

    //    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    @JsonIgnore
//    private Users users;

    @PrePersist
    public void prePersist() {
        this.date = LocalDate.now();
    }

}
