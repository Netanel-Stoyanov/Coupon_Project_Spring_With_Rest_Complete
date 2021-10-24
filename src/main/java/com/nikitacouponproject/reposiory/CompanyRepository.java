package com.nikitacouponproject.reposiory;

import com.nikitacouponproject.bean.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Optional<Company> findByEmailAndPassword(String email, String password);
    boolean existsByName(String name);
    boolean existsByEmail(String email);
}
