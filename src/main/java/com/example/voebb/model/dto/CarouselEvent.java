package com.example.voebb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarouselEvent {
    private String title;
    private String description;
    private String imageUrl;
    private String email;
}
