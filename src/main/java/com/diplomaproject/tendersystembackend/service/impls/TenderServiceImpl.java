package com.diplomaproject.tendersystembackend.service.impls;

import com.diplomaproject.tendersystembackend.model.Category;
import com.diplomaproject.tendersystembackend.model.Statement;
import com.diplomaproject.tendersystembackend.model.Tender;
import com.diplomaproject.tendersystembackend.model.Users;
import com.diplomaproject.tendersystembackend.model.enums.TStatus;
import com.diplomaproject.tendersystembackend.repo.CategoryRepository;
import com.diplomaproject.tendersystembackend.repo.StatementRepository;
import com.diplomaproject.tendersystembackend.repo.TenderRepository;
import com.diplomaproject.tendersystembackend.repo.UserRepository;
import com.diplomaproject.tendersystembackend.service.StatementService;
import com.diplomaproject.tendersystembackend.service.TenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TenderServiceImpl implements TenderService, StatementService {
    private final TenderRepository tenderRepository;
    private final CategoryRepository categoryRepository;
    private final StatementRepository statementRepository;
    private final UserRepository userRepository;

    @Override
    public Tender saveOrUpdateTender(String tenderName, String description, Long categoryID) {
        if (tenderRepository.findTenderByTenderName(tenderName).isPresent()){
            throw new IllegalArgumentException("Tender is already exist!");
        }
        Tender tender = new Tender();
        tender.setTenderName(tenderName);
        tender.setDescription(description);
        Category categoryOptional = categoryRepository
                .findById(categoryID)
                .orElseThrow(() -> new IllegalArgumentException("Category not found!"));
        tender.setCategory(categoryOptional);
        tender.setStatus(TStatus.OPEN);

        return tenderRepository.save(tender);
    }

    @Override
    public Statement createStatement(Long user_id, Long tender_id, Double totalCost) {

        Tender tender = tenderRepository.findById(tender_id)
                .orElseThrow(() -> new NullPointerException("Tender not found!"));
        Users userById = userRepository.findById(user_id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Statement statement = new Statement();

        statement.setUserId(userById);
        statement.setTenderId(tender);
        statement.setTotalCost(totalCost);

        return statementRepository.save(statement);
    }

    @Override
    public Statement findStatementById(Long id) {
        return null;
    }

    @Override
    public Tender findByTenderId(Long id) {
        return null;
    }

    @Override
    public List<Tender> getAllTenders() {
        return tenderRepository.findAll();
    }

    @Override
    public List<Statement> getAllStatements() {
        return statementRepository.findAll();
    }

    @Override
    public void deleteStatementById(Long id) {

    }

    @Override
    public void deleteTenderById(Long id) {

    }
}
