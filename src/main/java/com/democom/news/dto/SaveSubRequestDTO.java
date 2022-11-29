package com.democom.news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveSubRequestDTO {
    Long idAuthor;
    Long idReader;
}
