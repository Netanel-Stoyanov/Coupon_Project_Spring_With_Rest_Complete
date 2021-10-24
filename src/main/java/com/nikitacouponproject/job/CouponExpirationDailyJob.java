package com.nikitacouponproject.job;

import com.nikitacouponproject.bean.Coupon;
import com.nikitacouponproject.exception.JobGoWrongException;
import com.nikitacouponproject.reposiory.CouponRepository;
import com.nikitacouponproject.reposiory.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CouponExpirationDailyJob implements Runnable {

    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private CustomerRepository customerRepository;


    private boolean quit;

    public CouponExpirationDailyJob() {
        quit = false;
    }

    @Transactional
    @Override
    public void run() {
        while (!quit) {
            List<Coupon> coupons = couponRepository.findAll();
            for (Coupon coupon : coupons) {
                if (coupon.getEndDate().compareTo(LocalDate.now()) <= 0) {
                    customerRepository.deleteAllCouponsById(coupon.getId());
                    couponRepository.deleteById(coupon.getId());
                }
            }
            try {
                Thread.sleep(86400000);
            } catch (InterruptedException e) {
                throw new JobGoWrongException("something interrupted the daily job");
            }
        }
    }

    public void stop() {
        quit = true;
    }

}
