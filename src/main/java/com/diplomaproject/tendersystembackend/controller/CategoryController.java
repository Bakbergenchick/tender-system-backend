package com.diplomaproject.tendersystembackend.controller;

import com.diplomaproject.tendersystembackend.model.Statement;
import com.diplomaproject.tendersystembackend.model.Tender;
import com.diplomaproject.tendersystembackend.payload.TenderDTO;
import com.diplomaproject.tendersystembackend.service.CategoryService;
import com.diplomaproject.tendersystembackend.service.StatementService;
import com.diplomaproject.tendersystembackend.service.TenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/getAll")
    public ResponseEntity<?> allCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }
}
