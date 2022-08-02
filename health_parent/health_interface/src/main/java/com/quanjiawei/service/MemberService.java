package com.quanjiawei.service;

import com.quanjiawei.pojo.Member;

public interface MemberService {
    Member findByTelephone(String telephone);
    void add(Member member);
}
