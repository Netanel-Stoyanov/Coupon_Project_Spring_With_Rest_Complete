package com.nikitacouponproject.controller;

import com.nikitacouponproject.bean.Category;
import com.nikitacouponproject.bean.Company;
import com.nikitacouponproject.bean.Coupon;
import com.nikitacouponproject.filter.LoginResponse;
import com.nikitacouponproject.service.CompanyFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompanyController{

    @Autowired
    private CompanyFacade companyFacade;

    @PostMapping(path = "/co/login/")
    public ResponseEntity<LoginResponse> login(@RequestParam String email, @RequestParam String password){
        return ResponseEntity.ok(companyFacade.login(email, password).get());
    }

    @PostMapping(path = "/company/addcoupon")
    public ResponseEntity<Void> addCoupon(@RequestBody Coupon coupon){
        companyFacade.addCoupon(coupon);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/company/deletecoupon/{couponId}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable int couponId){
        companyFacade.deleteCoupon(couponId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/company/updatecoupon")
    public ResponseEntity<Void> updateCoupon(@RequestBody Coupon coupon){
        companyFacade.updateCoupon(coupon);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/company/getallcompanycoupons")
    public ResponseEntity<List<Coupon>> getAllCompanyCoupons(){
        return ResponseEntity.ok(companyFacade.getCompanyCoupons());
    }

    @GetMapping(path = "/company/getallcompanycoupons/category")
    public ResponseEntity<List<Coupon>> getAllCompanyCouponsByCategory(@RequestBody Category category){
        return ResponseEntity.ok(companyFacade.getCompanyCoupons(category));
    }

    @GetMapping(path = "/company/getallcompanycoupons/{maxPrice}")
    public ResponseEntity<List<Coupon>> getAllCompanyCouponsByMaxPrice(@PathVariable double maxPrice){
        return ResponseEntity.ok(companyFacade.getCompanyCoupons(maxPrice));
    }

    @GetMapping(path = "/company/details")
    public ResponseEntity<Company> getCompanyDetails(){
        return ResponseEntity.ok(companyFacade.getCompanyDetails().get());
    }


}
