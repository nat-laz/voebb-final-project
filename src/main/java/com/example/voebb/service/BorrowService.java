package com.example.voebb.service;

import com.example.voebb.model.entity.Borrow;

public interface BorrowService {

    Borrow createBorrow(Long clientId, Long itemId);
}
