package me.minikuma.batch.chunk.processor;

import me.minikuma.batch.domain.Product;
import me.minikuma.batch.domain.ProductEntity;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;

public class FileItemProcessor implements ItemProcessor<Product, ProductEntity> {
    @Override
    public ProductEntity process(Product item) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        ProductEntity productEntity = modelMapper.map(item, ProductEntity.class);
        return productEntity;
    }
}
