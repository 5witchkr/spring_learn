package com.example.demo.discount;

import com.example.demo.annotation.MainDiscountPolicy;
import com.example.demo.member.Grade;
import com.example.demo.member.Member;
import org.springframework.stereotype.Component;

@Component
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy {

    final static int DISCOUNT_RATE = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return DISCOUNT_RATE * price / 100;
        } else {
            return 0;
        }
    }
}