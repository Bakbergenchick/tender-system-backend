package com.diplomaproject.tendersystembackend.service.impls;

import com.diplomaproject.tendersystembackend.model.Category;
import com.diplomaproject.tendersystembackend.repo.CategoryRepository;
import com.diplomaproject.tendersystembackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Category> getAllCategories() {
        return repository.findAll();
    }
}
