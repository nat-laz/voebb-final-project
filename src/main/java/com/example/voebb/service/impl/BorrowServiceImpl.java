package com.example.voebb.service.impl;

import com.example.voebb.model.entity.Borrow;
import com.example.voebb.model.entity.Client;
import com.example.voebb.model.entity.ProductItem;
import com.example.voebb.repository.BorrowRepo;
import com.example.voebb.repository.ClientRepo;
import com.example.voebb.repository.ProductItemRepo;
import com.example.voebb.service.BorrowService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BorrowServiceImpl implements BorrowService {

    private final BorrowRepo borrowRepo;
    private final ClientRepo clientRepo;
    private final ProductItemRepo productItemRepo;

    @Autowired
    public BorrowServiceImpl(BorrowRepo borrowRepo, ClientRepo clientRepo, ProductItemRepo productItemRepo) {
        this.borrowRepo = borrowRepo;
        this.clientRepo = clientRepo;
        this.productItemRepo = productItemRepo;
    }

    @Transactional
    @Override
    public Borrow createBorrow(Long clientId, Long itemId) {
        Client client = clientRepo.findById(clientId).orElseThrow();
        ProductItem item = productItemRepo.findById(itemId).orElseThrow();

        Borrow b = new Borrow();
        b.setClient(client);
        b.setItem(item);
        b.setStartDate(LocalDate.now());
        b.setDueDate(LocalDate.now().plusWeeks(2));

        return borrowRepo.save(b);
    }
}
