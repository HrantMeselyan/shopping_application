package com.example.shopping_application.dto.imageDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Ashot Simonyan on 11.06.23.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDto {

    private int id;
    private String image;
}
