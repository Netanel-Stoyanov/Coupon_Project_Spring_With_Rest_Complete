package com.nikitacouponproject.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.nikitacouponproject.bean.Company;
import com.nikitacouponproject.bean.Customer;
import com.nikitacouponproject.exception.*;
import com.nikitacouponproject.filter.LoginResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@NoArgsConstructor
public class AdminFacade extends ClientAbstract {

    /**
     * checks if the email and password are the correct ones
     *
     * @param email
     * @param password
     * @return true - if email and password are correct, false otherwise
     * @throws NullVariableException - if either or both of the variables are null
     */

    public Optional<LoginResponse> login(String email, String password) throws NullVariableException {
        NullUtil.check(email, "admin email");
        NullUtil.check(password, "admin password");
        if (email.equals("admin@admin.com") && password.equals("admin")){
            LocalDateTime localDateTime = LocalDateTime.now();
            Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

            String jws = Jwts.builder().setIssuer("coupon-system").setSubject("login-proses")
                    .claim("name","admin")
                    .claim("user-id", 1)
                    .setIssuedAt(Date.from(Instant.ofEpochSecond(instant.getEpochSecond())))
                    .setExpiration(Date.from(Instant.ofEpochSecond(instant.plusSeconds(31556926l).getEpochSecond())))
                    .signWith(
                            Keys.hmacShaKeyFor("sakjdfhaskkljsdnfkjnrnskjnfkldnsfkljsdkljndskljnsdfkjsdnkjsdfnkjsdnsdkjlnsdklnsdjhf8wrkadjh9832oaweh".getBytes()))
                    .compact();
            return Optional.of(new LoginResponse("admin", jws));
        }
        throw new LoginException("the email or the password don't match");
    }

    /**
     * checks if the company's name and email don't exist in the database, if so-
     * adds the company
     *
     * @param company
     * @throws AlreadyExistsException - if name or email already exist in the
     *                                database
     */
    public void addCompany(Company company) throws AlreadyExistsException {
        if (company != null) {
            synchronized (Lock.company()) {
                if (companyRepository.existsByName(company.getName())) {
                    throw new AlreadyExistsException(company.getName(), "company name");
                } else if (companyRepository.existsByEmail(company.getEmail())) {
                    throw new AlreadyExistsException(company.getEmail(), "company email");
                } else {
                    companyRepository.save(company);
                }
            }
        }
    }


    /**
     * deletes the company with given ID from database and all coupons made by the
     * company and their purchases
     *
     * @param companyId
     */
    @Transactional
    public void deleteCompany(int companyId) {
        synchronized (Lock.company()) {
            couponRepository.deleteAllByCompanyId(companyId);
            companyRepository.deleteById(companyId);
        }
    }

    public void updateCompany(Company company) {
        if (company != null) {
            Optional<Company> optionalCompany = companyRepository.findById(company.getId());
            if (optionalCompany.isEmpty()) {
                throw new NotExistsException("this company is not exists");
            }
            synchronized (Lock.company()) {
                companyRepository.save(company);
            }
        }
    }

    /**
     * @return List<Company> all the companies in the database
     */
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    /**
     * @param companyId
     * @return optional of company if it exists, otherwise an empty optional
     */
    public Optional<Company> getOneCompany(int companyId) {
        return companyRepository.findById(companyId);
    }

    /**
     * adds the customer to the database if there is no other customer with the same
     * email
     *
     * @param customer
     * @throws AlreadyExistsException - if there is customer in the database with
     *                                the same email as the entered customer
     */
    public void addCustomer(Customer customer) throws AlreadyExistsException {
        if (customer != null) {
            synchronized (Lock.customer()) {
               if (customerRepository.existsByEmail(customer.getEmail())){
                   throw new AlreadyExistsException(customer.getEmail(), "customer email");
               }else{
                  customerRepository.save(customer);
               }
            }
        }
    }


    /**
     * deletes the customer and all the customer's purchases
     *
     * @param customerId customer id
     */
    public void deleteCustomer(int customerId) {
        synchronized (Lock.customer()) {
            customerRepository.deleteById(customerId);
        }
    }

    public void updateCustomer(Customer customer) {
        if (customer != null) {
            Optional<Customer> optionalCustomer = customerRepository.findById(customer.getId());
            if (optionalCustomer.isEmpty()) {
                throw new NotExistsException("there is no such a customer");
            }
            synchronized (Lock.customer()) {
                customerRepository.save(customer);
            }
        }
    }

    /**
     * @return List<Customer> - all the customers in the database
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * @param customerId
     * @return an optional of the customer with given id if one exists in the
     * database, otherwise- an empty optional
     */
    public Optional<Customer> getOneCustomer(int customerId) {
        return customerRepository.findById(customerId);
    }
}


