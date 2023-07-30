//package com.okta.developer.crud.Repository;
//
//import com.okta.developer.crud.model.Users;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface UsersRepository extends JpaRepository<Users,Long> {
//
//    Users findByEmail(String email);
//    Optional<Users> findByAuth(String auth);
//    @Query("SELECT u.id FROM Users u WHERE u.email = :email")
//    Optional<Long> findIdByEmail(@Param("email") String email);
//}
