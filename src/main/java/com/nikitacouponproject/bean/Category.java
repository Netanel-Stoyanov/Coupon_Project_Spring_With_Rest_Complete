package com.nikitacouponproject.bean;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private CategoryEnum name;
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Coupon> coupons;

    public Category(CategoryEnum name){
        this.name = name;
    }

}
