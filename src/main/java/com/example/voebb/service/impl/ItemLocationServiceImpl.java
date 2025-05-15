package com.example.voebb.service.impl;

import com.example.voebb.model.entity.ItemLocation;
import com.example.voebb.model.entity.Library;
import com.example.voebb.model.entity.ProductItem;
import com.example.voebb.service.ItemLocationService;
import org.springframework.stereotype.Service;

@Service
public class ItemLocationServiceImpl implements ItemLocationService {

    @Override
    public ItemLocation createLocation(ProductItem item, String note, Library library) {
        ItemLocation location = new ItemLocation();
        location.setLibrary(library);
        location.setNote(note);

        // Set up bidirectional link with ProductItem
        item.addLocation(location);

        return location;
    }
}
