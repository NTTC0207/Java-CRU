package com.okta.developer.crud.Repository;

import com.okta.developer.crud.model.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findAllByMonth_Id(Long monthId, Pageable pageable);
    long countByMonth_Id(Long monthId);



}
