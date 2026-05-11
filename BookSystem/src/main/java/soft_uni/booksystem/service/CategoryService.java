package soft_uni.booksystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft_uni.booksystem.entity.Category;

import soft_uni.booksystem.repository.CategoryRepository;

import java.util.Collections;
import java.util.List;
import java.util.Random;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final Random random =  new Random() ;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

    }
    public List<Category> getRandomCategory() {
            List<Category> all = categoryRepository.findAll();
            Collections.shuffle(all);
            int count = random.nextInt(1, 4); // 1, 2, or 3 categories
            return all.subList(0, count);



    }
}
