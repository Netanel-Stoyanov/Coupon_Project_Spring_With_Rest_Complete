package com.nikitacouponproject.controller;

import com.nikitacouponproject.bean.Category;
import com.nikitacouponproject.bean.Coupon;
import com.nikitacouponproject.bean.Customer;
import com.nikitacouponproject.filter.LoginResponse;
import com.nikitacouponproject.service.CustomerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerFacade customerFacade;

    @PostMapping(path = "cu/login")
    public ResponseEntity<LoginResponse> login(@RequestParam String email, @RequestParam String password){
        return ResponseEntity.ok(customerFacade.login(email, password).get());
    }

    @PostMapping(path = "/customer/purchasecoupon")
    public ResponseEntity<Void> purchaseCoupon(@RequestBody Coupon coupon){
        customerFacade.purchaseCoupon(coupon);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/customer/getAllCustomerCoupon")
    public ResponseEntity<List<Coupon>> getAllCustomerCoupons(){
        return ResponseEntity.ok(customerFacade.getCustomerCoupons());
    }

    @GetMapping(path = "/customer/getAllCustomerCoupon/category")
    public ResponseEntity<List<Coupon>> getAllCustomerCoupons(@RequestBody Category category){
        return ResponseEntity.ok(customerFacade.getCustomerCoupons(category));
    }

    @GetMapping(path = "/customer/getAllCustomerCoupon/{maxPrice}")
    public ResponseEntity<List<Coupon>> getAllCustomerCoupons(@PathVariable double maxPrice){
        return ResponseEntity.ok(customerFacade.getCustomerCoupons(maxPrice));
    }

    @GetMapping(path = "/customer/details")
    public ResponseEntity<Customer> getCompanyDetails(){
        return ResponseEntity.ok(customerFacade.getCustomerDetails().get());
    }

}
