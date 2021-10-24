package com.nikitacouponproject.bean;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @NotNull
    private Company company;
    @NotNull
    @ManyToOne
    private Category category;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private Integer amount;
    @NotNull
    private Double price;
    @NotNull
    private String image;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "coupons")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Customer> customers;

    public Coupon(Company company, Category category, String title, String description, LocalDate startDate,
                  LocalDate endDate, Integer amount, Double price, String image){
        this.company = company;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

}
