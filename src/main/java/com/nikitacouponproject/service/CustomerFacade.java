package com.nikitacouponproject.service;

import com.nikitacouponproject.bean.Category;
import com.nikitacouponproject.bean.Coupon;
import com.nikitacouponproject.bean.Customer;
import com.nikitacouponproject.exception.*;
import com.nikitacouponproject.filter.LoginResponse;
import com.nikitacouponproject.service.security.UserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class CustomerFacade extends ClientAbstract {

	private UserDetails userDetails;

	@Override
	public Optional<LoginResponse> login(String email, String password) {
		Optional<Customer> optionalUser = customerRepository.findByEmailAndPassword(email, password);
		if (optionalUser.isPresent()) {
			LocalDateTime localDateTime = LocalDateTime.now();
			Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

			String jws = Jwts.builder().setIssuer("coupon-system").setSubject("login-proses")
					.claim("name", optionalUser.get().getFirstName())
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
	 * adds a coupon purchase to the database and decreases the amount left for sale
	 * by one
	 * 
	 * @param coupon
	 * @throws CouponAlreadyPurchasedException - if the customer already bought this
	 *                                         coupon in the past
	 * @throws NoMoreCouponsException          - if the amount of coupons left for
	 *                                         sale is 0 or lower
	 * @throws CouponExpiredException          - if the coupon endDate already
	 *                                         arrived
	 */
	public void purchaseCoupon(Coupon coupon)
			throws CouponAlreadyPurchasedException, NoMoreCouponsException, CouponExpiredException {
		synchronized (Lock.coupon()) {
			List<Coupon> couponsByCustomer = couponRepository.findCouponsByCustomersId(userDetails.getId());
			boolean firstPurchase = true;
			for (int i = 0; i < couponsByCustomer.size() && firstPurchase; i++) {
				if (coupon.getId().equals(couponsByCustomer.get(i).getId())) {
					firstPurchase = false;
					throw new CouponAlreadyPurchasedException();
				}
			}
			Optional<Coupon> optionalCoupon = couponRepository.findById(coupon.getId());
			if (optionalCoupon.isEmpty() || optionalCoupon.get().getAmount() <= 0) {
				throw new NoMoreCouponsException();
			}
			Coupon couponToPurchase = optionalCoupon.get();
			if (couponToPurchase.getEndDate().compareTo(LocalDate.now()) <= 0) {
				throw new CouponExpiredException();
			}

			Optional<Customer> customerById = customerRepository.findById(userDetails.getId());
			if (customerById.isPresent()){
				Customer customer = customerById.get();
				Set<Coupon> coupons = customer.getCoupons();
				coupons.add(coupon);
				customer.setCoupons(coupons);
				customerRepository.save(customer);
			}
			couponToPurchase.setAmount(couponToPurchase.getAmount() - 1);
			couponRepository.save(couponToPurchase);
		}
	}

	/**
	 * 
	 * @return List<Coupon> - a list of coupons purchased by the customer that
	 *         logged in
	 */
	public List<Coupon> getCustomerCoupons() {
		return couponRepository.findCouponsByCustomersId(userDetails.getId());
	}

	/**
	 * 
	 * @param category
	 * @return List<Coupon> - a list of coupons purchased by the customer that
	 *         logged in and are of the entered category
	 */
	public List<Coupon> getCustomerCoupons(Category category) {
		return getCustomerCoupons().stream().filter(coupon -> coupon.getCategory().equals(category))
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @param maxPrice
	 * @return List<Coupon> - a list of coupons purchased by the customer that
	 *         logged in and have a price lower or equal to the given maxPrice
	 */
	public List<Coupon> getCustomerCoupons(double maxPrice) {
		return getCustomerCoupons().stream().filter(coupon -> coupon.getPrice() <= maxPrice)
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @return an optional of the customer that logged in if still exists in the
	 *         database, otherwise- an empty optional
	 */
	public Optional<Customer> getCustomerDetails() {
		return customerRepository.findById(userDetails.getId());
	}
}
