package com.example.demo.member;

public interface MemberRepository {
    Member findById(long memberId);
    void save(Member member);
}
