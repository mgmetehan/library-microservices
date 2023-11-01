package com.mgmetehan.libraryservice.service;

import com.mgmetehan.libraryservice.dto.LibraryDto;
import com.mgmetehan.libraryservice.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LibraryService {
    private final LibraryRepository libraryRepository;


}
