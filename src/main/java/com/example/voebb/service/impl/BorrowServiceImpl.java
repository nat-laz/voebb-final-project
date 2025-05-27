package com.example.voebb.service.impl;

import com.example.voebb.exception.*;
import com.example.voebb.model.dto.borrow.CreateBorrowDTO;
import com.example.voebb.model.dto.borrow.GetBorrowingsDTO;
import com.example.voebb.model.entity.Borrow;
import com.example.voebb.model.entity.CustomUser;
import com.example.voebb.model.entity.ItemStatus;
import com.example.voebb.model.entity.ProductItem;
import com.example.voebb.repository.BorrowRepo;
import com.example.voebb.repository.CustomUserRepo;
import com.example.voebb.repository.ItemStatusRepo;
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
    private final CustomUserRepo userRepo;
    private final ProductItemRepo itemRepo;
    private final ItemStatusRepo statusRepo;

    @Override
    @Transactional
    public void createBorrow(CreateBorrowDTO dto) {

        if (dto.userId() == null || dto.itemId() == null) {
            throw new IllegalArgumentException("Both User ID and Item ID are required.");
        }

        CustomUser user = userRepo.findById(dto.userId())
                .orElseThrow(() -> new UserNotFoundException(dto.userId()));

        ProductItem item = itemRepo.findById(dto.itemId())
                .orElseThrow(() -> new ItemNotFoundException(dto.itemId()));

        if (!item.getStatus().getName().equalsIgnoreCase("available")) {
            throw new ItemNotAvailableException(item.getProduct().getTitle(), item.getId(), item.getStatus().getName());
        }

        if (user.getBorrowedBooksCount() >= 5) {
            throw new UserBorrowLimitExceededException(user.getId(), user.getFirstName(), user.getLastName());
        }

        if (item.getProduct() == null || item.getProduct().getType() == null) {
            throw new IllegalStateException("Item's product or product type is not properly set.");
        }

        int borrowDurationDays = item.getProduct().getType().getBorrowDurationDays();

        LocalDate startDate = LocalDate.now();
        LocalDate dueDate = startDate.plusDays(borrowDurationDays);

        Borrow borrow = new Borrow();
        borrow.setCustomUser(user);
        borrow.setItem(item);
        borrow.setStartDate(startDate);
        borrow.setDueDate(dueDate);
        borrow.setExtendsCount(0);

        borrowRepo.save(borrow);

        ItemStatus borrowedStatus = statusRepo.findByNameIgnoreCase("borrowed")
                .orElseThrow(() -> new ItemStatusNotFoundException("borrowed"));
        item.setStatus(borrowedStatus);
        itemRepo.save(item);

        user.setBorrowedBooksCount(user.getBorrowedBooksCount() + 1);
        userRepo.save(user);

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
                dto.productType(),
                dto.startDate(),
                dto.dueDate(),
                dto.returnDate(),
                dto.extendsCount(),
                calculateBorrowStatus(dto.dueDate(), dto.returnDate())
        ));
    }

    @Override
    @Transactional
    public String returnBorrow(Long borrowId) {
        Borrow borrow = borrowRepo.findById(borrowId)
                .orElseThrow(() -> new BorrowNotFoundException(borrowId));

        if (borrow.getReturnDate() != null) {
            throw new IllegalStateException("This item is already returned.");
        }


        borrow.setReturnDate(LocalDate.now());
        borrowRepo.save(borrow);

        ProductItem item = borrow.getItem();
        ItemStatus availableStatus = statusRepo.findByNameIgnoreCase("available")
                .orElseThrow(() -> new ItemStatusNotFoundException("available"));
        item.setStatus(availableStatus);
        itemRepo.save(item);

        CustomUser user = borrow.getCustomUser();

        user.setBorrowedBooksCount(Math.max(0, user.getBorrowedBooksCount() - 1));

        userRepo.save(user);

        String userFullName = user.getFirstName() + " " + user.getLastName();
        String productTitle = item.getProduct().getTitle();
        return "User with ID: [#" + user.getId() + "] " + userFullName + " successfully returned the item \"" + productTitle + "\".";
    }



    private String calculateBorrowStatus(LocalDate dueDate, LocalDate returnDate) {
        if (returnDate != null) return "Returned";
        if (dueDate.isBefore(LocalDate.now())) return "Overdue";
        return "Active";
    }
}
