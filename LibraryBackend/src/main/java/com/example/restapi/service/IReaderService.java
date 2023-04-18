package com.example.restapi.service;

import com.example.restapi.dtos.readerdtos.ReaderDTO_forAll;
import com.example.restapi.dtos.readerdtos.ReaderDTO_forOne;
import com.example.restapi.model.Reader;

import java.util.List;

public interface IReaderService {
    List<ReaderDTO_forAll> getAllReaders(Integer pageNo, Integer pageSize);

    Reader addNewReader(Reader newReader);

    ReaderDTO_forOne getReaderById(Long id);

    Reader replaceReader(Reader newReader, Long id);

    void deleteReader(Long id);

    long countAllReaders();
}
