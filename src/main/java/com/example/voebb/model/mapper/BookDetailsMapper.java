package com.example.voebb.model.mapper;

import com.example.voebb.model.dto.product.BookDetailsDTO;
import com.example.voebb.model.entity.BookDetails;
import com.example.voebb.model.entity.Product;
import org.springframework.stereotype.Component;


@Component
public class BookDetailsMapper {

    public static BookDetails toEntity(Product product, BookDetailsDTO dto){
        return new BookDetails(
                product.getId(),
                product,
                dto.isbn(),
                dto.edition(),
                dto.pages()
        );
    }

    public static BookDetailsDTO toDto(BookDetails bookDetails){
       return new BookDetailsDTO(
               bookDetails.getIsbn(),
               bookDetails.getEdition(),
               bookDetails.getPages()
       );

    }
}
