package com.example.demo.member;


import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MemoryMemberRepository implements MemberRepository {

    private static final Map<Long, Member> store = new ConcurrentHashMap<>();

    @Override
    public Member findById(long memberId) {
        return store.get(memberId);
    }

    @Override
    public void save(Member member){
        store.put(member.getMemberId(), member);
    }
}
