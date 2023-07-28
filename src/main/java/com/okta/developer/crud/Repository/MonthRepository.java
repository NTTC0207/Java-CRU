package com.okta.developer.crud.Repository;

import com.okta.developer.crud.model.Month;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonthRepository extends JpaRepository<Month,Long> {

    //    @Query("SELECT m FROM Month m WHERE YEAR(m.year) = :year AND MONTH(m.month) = :month AND m.user.id = :user_Id")
//    Month findByMonthYearAndUserId(@Param("year") int year, @Param("month") int month, @Param("userId") Long userId);
    Month findByYearAndMonthAndUsersId(int year, int month, Long userId);
    List<Month> findByUsers_Id(Long userId);

    Optional<Month> findAllById(Long id);
}
