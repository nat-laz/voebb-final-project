package com.example.voebb.service;

import com.example.voebb.model.dto.borrow.GetBorrowingsDTO;
import com.example.voebb.model.entity.Borrow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BorrowService {

    Borrow createBorrow(Long clientId, Long itemId);

    Page<GetBorrowingsDTO> getFilteredBorrowings(Long userId, Long itemId, Long libraryId, Pageable pageable);
}
