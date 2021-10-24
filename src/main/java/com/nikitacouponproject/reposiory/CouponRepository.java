package com.nikitacouponproject.reposiory;

import com.nikitacouponproject.bean.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    List<Coupon> findAllByCompanyId(int id);
    List<Coupon> findCouponsByCustomersId(Integer id);
    boolean existsByTitle(String name);
    void deleteAllByCompanyId(Integer id);
}
