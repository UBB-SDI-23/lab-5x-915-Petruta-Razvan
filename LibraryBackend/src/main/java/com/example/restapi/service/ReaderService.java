package com.example.restapi.service;

import com.example.restapi.dto.DTOConverters;
import com.example.restapi.dto.ReaderDTO_forAll;
import com.example.restapi.exceptions.ReaderNotFoundException;
import com.example.restapi.model.Reader;
import com.example.restapi.repository.MembershipRepository;
import com.example.restapi.repository.ReaderRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReaderService {
    private final ReaderRepository readerRepository;
    private final MembershipRepository membershipRepository;

    public ReaderService(ReaderRepository readerRepository, MembershipRepository membershipRepository) {
        this.readerRepository = readerRepository;
        this.membershipRepository = membershipRepository;
    }

    public List<ReaderDTO_forAll> getAllReaders(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("ID"));

        return this.readerRepository.findAll(pageable).getContent().stream().map(
                reader -> DTOConverters.convertToReaderDTO_forAll(reader,
                        this.membershipRepository.countByReaderID(reader.getID()))
        ).collect(Collectors.toList());
    }

    public Reader addNewReader(Reader newReader) {
        return this.readerRepository.save(newReader);
    }

    public Reader getReaderById(Long id) {
        return this.readerRepository.findById(id).orElseThrow(() -> new ReaderNotFoundException(id));
    }

    public Reader replaceReader(Reader newReader, Long id) {
        return this.readerRepository.findById(id)
                .map(reader -> {
                    reader.setName(newReader.getName());
                    reader.setGender(newReader.getGender());
                    reader.setStudent(newReader.isStudent());
                    reader.setBirthDate(newReader.getBirthDate());
                    reader.setEmail(newReader.getEmail());
                    return this.readerRepository.save(reader);
                })
                .orElseGet(() -> {
                    newReader.setID(id);
                    return this.readerRepository.save(newReader);
                });
    }

    public void deleteReader(Long id) {
        this.readerRepository.deleteById(id);
    }

    public long countAllReaders() {
        return this.readerRepository.count();
    }
}
