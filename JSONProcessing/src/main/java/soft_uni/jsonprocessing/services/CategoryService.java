package soft_uni.jsonprocessing.services;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import soft_uni.jsonprocessing.dtos.CategoryImportDto;
import soft_uni.jsonprocessing.dtos.CategoryStatsDto;
import soft_uni.jsonprocessing.entities.Category;
import soft_uni.jsonprocessing.repository.CategoryRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemLoopException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class CategoryService {
    private final Gson gson ;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper ;

    public CategoryService(Gson gson, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.gson = gson;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public void importData() throws IOException {
        Path path = Path.of("C:\\Users\\georg\\Spring_Data\\JSONProcessing\\src\\main\\resources\\json_files\\categories.json");
        List<String> lines = Files.readAllLines(path);
        CategoryImportDto[] categoryImportDtos = gson.fromJson(String.join("", lines), CategoryImportDto[].class);
        for (CategoryImportDto categoryImportDto : categoryImportDtos) {
            String name = categoryImportDto.getName();
            if (name == null || name.length() < 3 || name.length() > 15) {
                System.out.println("Invalid category name: "+ name);
                continue;
            }
            Category category = new Category(name);

            categoryRepository.save(category);

        }
    }

    public Set<Category> getRandomCategories() {
        Random random = new Random();
        List<Category> all = categoryRepository.findAll();
        Collections.shuffle(all, random);
        int count = random.nextInt(1, Math.max(2, all.size()));
        return new HashSet<>(all.subList(0, count));
    }

    public void getCategoryStats() {
        List<CategoryStatsDto> categoryStats = this.categoryRepository.findCategoryStats();
        String json = this.gson.toJson(categoryStats);
        System.out.println(json);
    }
}
