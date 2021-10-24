package com.nikitacouponproject.service;

import com.nikitacouponproject.bean.Category;
import com.nikitacouponproject.bean.Company;
import com.nikitacouponproject.bean.Coupon;
import com.nikitacouponproject.exception.AlreadyExistsException;
import com.nikitacouponproject.exception.LoginException;
import com.nikitacouponproject.exception.NotExistsException;
import com.nikitacouponproject.filter.LoginResponse;
import com.nikitacouponproject.service.security.UserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class CompanyFacade extends ClientAbstract {

    private UserDetails userDetails;

    @Override
    public Optional<LoginResponse> login(String email, String password) {
        Optional<Company> optionalUser = companyRepository.findByEmailAndPassword(email, password);
        if (optionalUser.isPresent()) {
            LocalDateTime localDateTime = LocalDateTime.now();
            Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

            String jws = Jwts.builder().setIssuer("coupon-system").setSubject("login-proses")
                    .claim("name", optionalUser.get().getEmail())
                    .claim("user-id", optionalUser.get().getId())
                    .setIssuedAt(Date.from(Instant.ofEpochSecond(instant.getEpochSecond())))
                    .setExpiration(Date.from(Instant.ofEpochSecond(instant.plusSeconds(31556926l).getEpochSecond())))
                    .signWith(
                            Keys.hmacShaKeyFor("sakjdfhaskkljsdnfkjnrnskjnfkldnsfkljsdkljndskljnsdfkjsdnkjsdfnkjsdnsdkjlnsdklnsdjhf8wrkadjh9832oaweh".getBytes()))
                    .compact();


            return Optional.of(new LoginResponse(optionalUser.get().getEmail(), jws));
        }
        throw new LoginException("your email or password not match");
    }

    /**
     * adds the coupon to the database if the company does not already have a coupon
     * with this title
     *
     * @param coupon
     * @throws AlreadyExistsException - if the company has an existing coupon with
     *                                the same title
     */
    public void addCoupon(Coupon coupon) throws AlreadyExistsException {
        if (coupon != null) {
            synchronized (Lock.coupon()) {
                if (couponRepository.existsByTitle(coupon.getTitle())) {
                    throw new AlreadyExistsException(coupon.getTitle(), "coupon title");
                } else {
                    couponRepository.save(coupon);
                }
            }
        }
    }


    /**
     * deletes the coupon from the database and all its purchases
     *
     * @param couponId
     */
    public void deleteCoupon(int couponId) {
        synchronized (Lock.coupon()) {
            couponRepository.deleteById(couponId);
        }
    }

    public void updateCoupon(Coupon coupon) {
        if (coupon != null) {
            Optional<Coupon> lastCoupon = couponRepository.findById(coupon.getId());
            if (lastCoupon.isEmpty()) {
                throw new NotExistsException("no such a coupon exists");
            } else {
                synchronized (Lock.coupon()) {
                    couponRepository.save(coupon);
                }
            }
        }
    }

    /**
     * @return List<Coupon> - all the coupons of the company that logged in
     */
    public List<Coupon> getCompanyCoupons() {
        return couponRepository.findAllByCompanyId(userDetails.getId());
    }

    /**
     * @param category
     * @return List<Coupon> - all the coupons of the company that logged in and are
     * of the same category
     */
    public List<Coupon> getCompanyCoupons(Category category) {
        if (category == null) {
            return new ArrayList<Coupon>();
        }
        return getCompanyCoupons().stream().filter(coupon -> coupon.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    /**
     * @param maxPrice
     * @return List<Coupon> - all the coupons of the company that logged in and have
     * a price lower or equal to the entered maxPrice
     */

    public List<Coupon> getCompanyCoupons(double maxPrice) {
        return getCompanyCoupons().stream().filter(coupon -> coupon.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    /**
     * @return optional of the company that logged in if it still exists in the
     * database, otherwise an empty optional
     */
    public Optional<Company> getCompanyDetails() {
        return companyRepository.findById(userDetails.getId());
    }
}
