package com.example.restapi.service;

import com.example.restapi.exceptions.ReaderNotFoundException;
import com.example.restapi.model.Reader;
import com.example.restapi.repository.ReaderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderService {
    private final ReaderRepository readerRepository;

    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public List<Reader> getAllReaders() {
        return this.readerRepository.findAll();
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
}
