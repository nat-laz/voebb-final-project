package com.example.voebb.service.impl;

import com.example.voebb.model.dto.borrow.GetBorrowingsDTO;
import com.example.voebb.model.entity.Borrow;
import com.example.voebb.model.entity.CustomUser;
import com.example.voebb.model.entity.ProductItem;
import com.example.voebb.repository.BorrowRepo;
import com.example.voebb.repository.CustomUserRepo;
import com.example.voebb.repository.ProductItemRepo;
import com.example.voebb.service.BorrowService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {

    private final BorrowRepo borrowRepo;
    private final CustomUserRepo customUserRepo;
    private final ProductItemRepo productItemRepo;

    @Transactional
    @Override
    public Borrow createBorrow(Long clientId, Long itemId) {
        CustomUser customUser = customUserRepo.findById(clientId).orElseThrow();
        ProductItem item = productItemRepo.findById(itemId).orElseThrow();

        Borrow b = new Borrow();
        b.setCustomUser(customUser);
        b.setItem(item);
        b.setStartDate(LocalDate.now());
        b.setDueDate(LocalDate.now().plusWeeks(2));

        return borrowRepo.save(b);
    }

    @Override
    public Page<GetBorrowingsDTO> getFilteredBorrowings(Long userId, Long itemId, Long libraryId, Pageable pageable) {
        Page<GetBorrowingsDTO> rawResults = borrowRepo.findFilteredBorrows(userId, itemId, libraryId, pageable);

        return rawResults.map(dto -> new GetBorrowingsDTO(
                dto.borrowId(),
                dto.userId(),
                dto.customUserFullName(),
                dto.itemId(),
                dto.itemTitle(),
                dto.startDate(),
                dto.dueDate(),
                dto.returnDate(),
                dto.extendsCount(),
                calculateBorrowStatus(dto.dueDate(), dto.returnDate())
        ));
    }

    private String calculateBorrowStatus(LocalDate dueDate, LocalDate returnDate) {
        if (returnDate != null) return "Returned";
        if (dueDate.isBefore(LocalDate.now())) return "Overdue";
        return "Active";
    }
}
