package com.numberone.backend.domain.member.repository.custom;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Long> findAllByLocation(String targetLocation);
}
