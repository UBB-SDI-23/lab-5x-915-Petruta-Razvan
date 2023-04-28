package com.example.restapi.service;

import com.example.restapi.dtos.readerdtos.MembershipDTO;
import com.example.restapi.model.membership.Membership;

import java.util.List;

public interface IMembershipService {
    Membership createMembership(Long libraryID, Long readerID);

    List<MembershipDTO> addNewMemberships(List<MembershipDTO> memberships, Long id);
}
