package me.minikuma.batch.chunk.processor;

import me.minikuma.batch.domain.ProductDto;
import me.minikuma.batch.domain.Product;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;

public class FileItemProcessor implements ItemProcessor<ProductDto, Product> {
    @Override
    public Product process(ProductDto item) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Product productEntity = modelMapper.map(item, Product.class);
        return productEntity;
    }
}
