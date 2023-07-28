package com.okta.developer.crud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "appeal")
@AllArgsConstructor
@NoArgsConstructor
public class Appeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "month_id_1")
    private Long monthId1;
    @Column(name="month_id_2")
    private Long monthId2;
    @Column(name = "get_bonus")
    private boolean getBonus;

    @Column(name = "appeal")
    private boolean appeal;

    @Column(name = "approved")
    private boolean approved;

}
