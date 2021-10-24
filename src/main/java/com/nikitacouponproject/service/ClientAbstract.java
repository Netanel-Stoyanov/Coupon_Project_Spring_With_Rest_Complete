package com.nikitacouponproject.service;

import com.nikitacouponproject.filter.LoginResponse;
import com.nikitacouponproject.reposiory.CompanyRepository;
import com.nikitacouponproject.reposiory.CouponRepository;
import com.nikitacouponproject.reposiory.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public abstract class ClientAbstract {

    @Autowired
    protected CompanyRepository companyRepository;
    @Autowired
    protected CouponRepository couponRepository;
    @Autowired
    protected CustomerRepository customerRepository;


    public abstract Optional<LoginResponse> login(String email, String password);

}
