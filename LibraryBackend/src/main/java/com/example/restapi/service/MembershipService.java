package com.example.restapi.service;

import com.example.restapi.dto.MembershipDTO;
import com.example.restapi.exceptions.LibraryNotFoundException;
import com.example.restapi.exceptions.ReaderNotFoundException;
import com.example.restapi.model.Library;
import com.example.restapi.model.Membership;
import com.example.restapi.model.MembershipKey;
import com.example.restapi.model.Reader;
import com.example.restapi.repository.LibraryRepository;
import com.example.restapi.repository.MembershipRepository;
import com.example.restapi.repository.ReaderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MembershipService {
    private final MembershipRepository membershipRepository;
    private final LibraryRepository libraryRepository;
    private final ReaderRepository readerRepository;

    public MembershipService(MembershipRepository membershipRepository, LibraryRepository libraryRepository, ReaderRepository readerRepository) {
        this.membershipRepository = membershipRepository;
        this.libraryRepository = libraryRepository;
        this.readerRepository = readerRepository;
    }

    public Membership createMembership(Long libraryID, Long readerID) {
        // check for library and reader existence
        Library library =  this.libraryRepository.findById(libraryID).orElseThrow(() -> new LibraryNotFoundException(libraryID));
        Reader reader = this.readerRepository.findById(readerID).orElseThrow(() -> new ReaderNotFoundException(readerID));

        // start date: current date
        // membership will last 365 days
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(365);

        // create the key
        MembershipKey key = new MembershipKey();
        key.setLibraryID(libraryID);
        key.setReaderID(readerID);

        // create the membership
        Membership membership = new Membership();
        membership.setID(key);
        membership.setStartDate(startDate);
        membership.setEndDate(endDate);
        membership.setLibrary(library);
        membership.setReader(reader);
        this.membershipRepository.save(membership);

        // add the membership to the sets of library and reader
        library.addMembership(membership);
        reader.addMembership(membership);

        return membership;
    }

    public List<MembershipDTO> addNewMemberships(List<MembershipDTO> memberships, Long id) {
        this.libraryRepository.findById(id).map(
                library -> {
                    for (MembershipDTO membership: memberships) {
                        if (this.membershipRepository.findById(new MembershipKey(id, membership.getReaderID())).isEmpty()) {
                            Reader reader = this.readerRepository.findById(membership.getReaderID()).orElseThrow(() -> new ReaderNotFoundException(membership.getReaderID()));

                            MembershipKey key = new MembershipKey();
                            key.setLibraryID(id);
                            key.setReaderID(reader.getID());

                            Membership newMembership = new Membership();
                            newMembership.setID(key);
                            newMembership.setStartDate(membership.getStartDate());
                            newMembership.setEndDate(membership.getEndDate());
                            newMembership.setLibrary(library);
                            newMembership.setReader(reader);
                            this.membershipRepository.save(newMembership);

                            library.addMembership(newMembership);
                            reader.addMembership(newMembership);
                        }
                    }
                    return library;
                }
        );

        return memberships;
    }
}
