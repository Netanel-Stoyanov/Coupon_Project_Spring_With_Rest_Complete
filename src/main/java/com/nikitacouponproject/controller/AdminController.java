package com.nikitacouponproject.controller;

import com.nikitacouponproject.bean.Company;
import com.nikitacouponproject.bean.Customer;
import com.nikitacouponproject.filter.LoginResponse;
import com.nikitacouponproject.service.AdminFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private AdminFacade adminFacade;

    @PostMapping(path = "/a/login")
    public ResponseEntity<LoginResponse> login(@RequestParam String email, @RequestParam String password) {
        return ResponseEntity.ok(adminFacade.login(email, password).get());
    }

    @PostMapping(path = "/admin/addcompany")
    public ResponseEntity<Void> addCompany(@RequestBody Company company){
        adminFacade.addCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(path = "/admin/deletecompany/{companyId}")
    public ResponseEntity<Void> deleteCompany(@PathVariable  int companyId){
        adminFacade.deleteCompany(companyId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(path = "/admin/updatecompany")
    public ResponseEntity<Void> updateCompany(@RequestBody Company company){
        adminFacade.updateCompany(company);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/admin/getallcompany")
    public ResponseEntity<List<Company>> getAllCompany(){
        return ResponseEntity.ok(adminFacade.getAllCompanies());
    }

    @GetMapping(path = "/admin/getonecompany/{companyId}")
    public ResponseEntity<Company> getOneCompany(@PathVariable int companyId){
        return ResponseEntity.ok(adminFacade.getOneCompany(companyId).get());
    }

    @PostMapping(path = "/admin/addcustomer")
    public ResponseEntity<Void> addCustomer(@RequestBody Customer customer){
        adminFacade.addCustomer(customer);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/admin/deletecustomer/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int customerId){
        adminFacade.deleteCustomer(customerId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/admin/updatecustomer")
    public ResponseEntity<Void> updatecustomer(@RequestBody Customer customer){
        adminFacade.updateCustomer(customer);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/admin/getallcustomers")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return ResponseEntity.ok(adminFacade.getAllCustomers());
    }

    @GetMapping(path = "/admin/getonecustomer/{customerId}")
    public ResponseEntity<Customer> getOneCustomer(@PathVariable int customerId){
        return ResponseEntity.ok(adminFacade.getOneCustomer(customerId).get());
    }
}
