package com.example.shopping_application.mapper;

import com.example.shopping_application.dto.categoryDto.CategoryDto;
import com.example.shopping_application.dto.imageDto.ImageDto;
import com.example.shopping_application.dto.productDto.CreateProductRequestDto;
import com.example.shopping_application.dto.productDto.CreateProductResponseDto;
import com.example.shopping_application.dto.productDto.ProductDto;
import com.example.shopping_application.entity.Category;
import com.example.shopping_application.entity.Image;
import com.example.shopping_application.entity.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductMapper {
    public static Product map(CreateProductRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Product product = new Product();

        product.setName(dto.getName());
        product.setProductCode(dto.getProductCode());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        List<ImageDto> images = dto.getImages();
        List<Image> images1 = new ArrayList<>();
        for (ImageDto image : images) {
            images1.add(ImageMapper.imageDtoToImage(image));
        }
        if (images1 != null) {
            product.setImages(new ArrayList<Image>(images1));
        }

        List<CategoryDto> categories = dto.getCategories();
        List<Category> categories1 = new ArrayList<>();
        for (CategoryDto category : categories) {
            categories1.add(CategoryMapper.dtoToCategory(category));
        }
        if (categories1 != null) {
            product.setCategories(new ArrayList<Category>(categories1));
        }

        return product;
    }

    public static CreateProductResponseDto mapToResponseDto(Product entity) {
        if (entity == null) {
            return null;
        }

        CreateProductResponseDto createProductResponseDto = new CreateProductResponseDto();

        createProductResponseDto.setId(entity.getId());
        createProductResponseDto.setName(entity.getName());
        createProductResponseDto.setProductCode(entity.getProductCode());
        createProductResponseDto.setDescription(entity.getDescription());
        createProductResponseDto.setPrice(entity.getPrice());
        List<Image> list = entity.getImages();
        List<ImageDto> imageDtos = new ArrayList<>();
        for (Image image : list) {
            imageDtos.add(ImageMapper.imageToImageDto(image));
        }
        if (imageDtos != null) {
            createProductResponseDto.setImages(imageDtos);
        }
        List<Category> list1 = entity.getCategories();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : list1) {
            categoryDtos.add(CategoryMapper.categoryToDto(category));
        }
        if (categoryDtos != null) {
            createProductResponseDto.setCategories(categoryDtos);
        }

        return createProductResponseDto;
    }

    public static CreateProductRequestDto mapToRequestDto(Product entity) {
        if (entity == null) {
            return null;
        }

        CreateProductRequestDto createProductRequestDto = new CreateProductRequestDto();

        createProductRequestDto.setName(entity.getName());
        createProductRequestDto.setProductCode(entity.getProductCode());
        createProductRequestDto.setDescription(entity.getDescription());
        createProductRequestDto.setPrice(entity.getPrice());
        List<Image> list = entity.getImages();
        List<ImageDto> imageDtos = new ArrayList<>();
        for (Image image : list) {
            imageDtos.add(ImageMapper.imageToImageDto(image));
        }
        if (imageDtos != null) {
            createProductRequestDto.setImages(imageDtos);
        }
        List<Category> list1 = entity.getCategories();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : list1) {
            categoryDtos.add(CategoryMapper.categoryToDto(category));
        }
        if (categoryDtos != null) {
            createProductRequestDto.setCategories(categoryDtos);
        }
        return createProductRequestDto;
    }

    public static ProductDto mapToDto(Product entity) {
        if (entity == null) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        productDto.setId(entity.getId());
        productDto.setName(entity.getName());
        productDto.setProductCode(entity.getProductCode());
        productDto.setDescription(entity.getDescription());
        productDto.setPrice(entity.getPrice());
        List<Image> list = entity.getImages();
        List<ImageDto> imageDtos = new ArrayList<>();
        for (Image image : list) {
            imageDtos.add(ImageMapper.imageToImageDto(image));
        }
        if (imageDtos != null) {
            productDto.setImages(imageDtos);
        }
        List<Category> list1 = entity.getCategories();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : list1) {
            categoryDtos.add(CategoryMapper.categoryToDto(category));
        }
        if (categoryDtos != null) {
            productDto.setCategories(categoryDtos);
        }

        return productDto;
    }

    public static Set<CreateProductResponseDto> mapToDto(Set<Product> entity) {
        if (entity == null) {
            return null;
        }
        Set<CreateProductResponseDto> productResponseDtoSet = new HashSet<>();
        for (Product product : entity) {
            CreateProductResponseDto productResponseDto = new CreateProductResponseDto();
            productResponseDto.setId(product.getId());
            productResponseDto.setName(product.getName());
            productResponseDto.setProductCode(product.getProductCode());
            productResponseDto.setDescription(product.getDescription());
            productResponseDto.setPrice(product.getPrice());
            List<Image> list = product.getImages();
            List<ImageDto> imageDtos = new ArrayList<>();
            for (Image image : list) {
                imageDtos.add(ImageMapper.imageToImageDto(image));
            }
            if (imageDtos != null) {
                productResponseDto.setImages(imageDtos);
            }
            List<Category> list1 = product.getCategories();
            List<CategoryDto> categoryDtos = new ArrayList<>();
            for (Category category : list1) {
                categoryDtos.add(CategoryMapper.categoryToDto(category));
            }
            if (categoryDtos != null) {
                productResponseDto.setCategories(categoryDtos);
            }

            productResponseDtoSet.add(productResponseDto);
        }
        return productResponseDtoSet;
    }
}

