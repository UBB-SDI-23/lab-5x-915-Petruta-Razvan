package com.example.restapi.repository;

import com.example.restapi.dto.LibrariesCountDTO;
import com.example.restapi.model.Book;
import com.example.restapi.model.Library;
import com.example.restapi.model.Membership;
import com.example.restapi.model.Reader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.util.List;

public class CustomLibraryRepositoryImpl implements CustomLibraryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<LibrariesCountDTO> findLibrariesGroupByCountBooksAsc() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<LibrariesCountDTO> query = cb.createQuery(LibrariesCountDTO.class);
        Root<Library> libraryRoot = query.from(Library.class);
        Join<Library, Book> libraryBookJoin = libraryRoot.join("books", JoinType.INNER);

        query.select(cb.construct(
                LibrariesCountDTO.class,
                libraryRoot.get("ID"),
                libraryRoot.get("name"),
                cb.count(libraryBookJoin.get("title"))
        ))
                .groupBy(libraryRoot.get("ID"), libraryRoot.get("name"))
                .orderBy(cb.asc(cb.count(libraryBookJoin.get("title"))));

        return this.entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<LibrariesCountDTO> findLibrariesGroupByCountStudentReadersDesc() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<LibrariesCountDTO> query = cb.createQuery(LibrariesCountDTO.class);
        Root<Library> libraryRoot = query.from(Library.class);
        Join<Library, Membership> libraryMembershipJoin = libraryRoot.join("memberships", JoinType.INNER);
        Join<Membership, Reader> membershipBookJoin = libraryMembershipJoin.join("reader", JoinType.INNER);

        query.select(cb.construct(
                LibrariesCountDTO.class,
                libraryRoot.get("ID"),
                libraryRoot.get("name"),
                cb.count(membershipBookJoin.get("isStudent"))
        ))
                .where(cb.isTrue(membershipBookJoin.get("isStudent")))
                .groupBy(libraryRoot.get("ID"), libraryRoot.get("name"))
                .orderBy(cb.desc(cb.count(membershipBookJoin.get("isStudent"))));

        return this.entityManager.createQuery(query).getResultList();
    }
}
