package com.okta.developer.crud.dto;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionPostDto {


    private Long month_id;
    @Column(updatable = false)
    private LocalDate date;

    private Double amount;

    @PrePersist
    public void prePersist() {
        this.date = LocalDate.now();
    }
}
