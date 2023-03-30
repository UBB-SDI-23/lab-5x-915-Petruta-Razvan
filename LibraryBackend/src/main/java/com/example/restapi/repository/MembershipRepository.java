package com.example.restapi.repository;

import com.example.restapi.model.Membership;
import com.example.restapi.model.MembershipKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, MembershipKey> {
}
