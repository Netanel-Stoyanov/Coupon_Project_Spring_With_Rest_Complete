package com.nikitacouponproject.reposiory;

import com.nikitacouponproject.bean.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmailAndPassword(String email, String password);
    void deleteAllCouponsById(Integer id);
    boolean existsByEmail(String email);

}
