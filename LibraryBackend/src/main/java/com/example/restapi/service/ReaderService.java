package com.example.restapi.service;

import com.example.restapi.dtos.readerdtos.ReaderDTO_forAll;
import com.example.restapi.dtos.readerdtos.ReaderDTO_forOne;
import com.example.restapi.dtos.readerdtos.ReaderDTO_Converters;
import com.example.restapi.exceptions.ReaderNotFoundException;
import com.example.restapi.model.Reader;
import com.example.restapi.repository.library_repository.LibraryRepository;
import com.example.restapi.repository.MembershipRepository;
import com.example.restapi.repository.ReaderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReaderService implements IReaderService {
    private final ReaderRepository readerRepository;
    private final MembershipRepository membershipRepository;
    private final ModelMapper modelMapper;
    private final LibraryRepository libraryRepository;

    public ReaderService(ReaderRepository readerRepository, MembershipRepository membershipRepository, ModelMapper modelMapper, LibraryRepository libraryRepository) {
        this.readerRepository = readerRepository;
        this.membershipRepository = membershipRepository;
        this.modelMapper = modelMapper;
        this.libraryRepository = libraryRepository;
    }

    @Override
    public List<ReaderDTO_forAll> getAllReaders(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("ID"));

        return this.readerRepository.findAll(pageable).getContent().stream().map(
                reader -> ReaderDTO_Converters.convertToReaderDTO_forAll(reader,
                        this.membershipRepository.countByReaderID(reader.getID()))
        ).collect(Collectors.toList());
    }

    @Override
    public Reader addNewReader(Reader newReader) {
        return this.readerRepository.save(newReader);
    }

    @Override
    public ReaderDTO_forOne getReaderById(Long id) {
        return ReaderDTO_Converters.convertToReaderDTO_forOne(this.readerRepository.findById(id).orElseThrow(()
                -> new ReaderNotFoundException(id)), modelMapper, libraryRepository);
    }

    @Override
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

    @Override
    public void deleteReader(Long id) {
        this.readerRepository.deleteById(id);
    }

    @Override
    public long countAllReaders() {
        return this.readerRepository.count();
    }
}
