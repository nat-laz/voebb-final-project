package com.example.voebb.model.dto.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemFilters {
    private Long itemId;
    private Long productTypeId;
    private Long statusId;
    private Long libraryId;
}