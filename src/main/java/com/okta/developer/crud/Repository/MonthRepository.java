package com.okta.developer.crud.Repository;

import com.okta.developer.crud.model.Month;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonthRepository extends JpaRepository<Month,Long> {

    Month findByYearAndMonthAndSub(int year, int month, String Sub);


    List<Month> findBySub(String Sub);

}
