package me.minikuma.batch.partitioner;

import me.minikuma.batch.domain.ProductDto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductPartitioner implements Partitioner {

    private SqlSessionFactory sqlSessionFactory;

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        List<ProductDto> productList = sqlSessionFactory
                .openSession()
                .selectList("SELECT type FROM product GROUP BY type");

        Map<String, ExecutionContext> result = new HashMap<>();

        int number = 0;

        for (ProductDto productDto : productList) {
            ExecutionContext value = new ExecutionContext();
            result.put("partition" + number, value);
            value.put("product", productDto);
            number++;
        }
        return result;
    }
}
