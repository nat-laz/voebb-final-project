package com.example.voebb.service;

import com.example.voebb.model.entity.ItemLocation;
import com.example.voebb.model.entity.Library;
import com.example.voebb.model.entity.ProductItem;

public interface ItemLocationService {

    ItemLocation createLocation(ProductItem item, String note, Library library);
}
