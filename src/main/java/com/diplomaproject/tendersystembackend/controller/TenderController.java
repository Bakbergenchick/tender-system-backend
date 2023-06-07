package com.diplomaproject.tendersystembackend.controller;

import com.diplomaproject.tendersystembackend.model.Statement;
import com.diplomaproject.tendersystembackend.model.Tender;
import com.diplomaproject.tendersystembackend.payload.TenderDTO;
import com.diplomaproject.tendersystembackend.service.StatementService;
import com.diplomaproject.tendersystembackend.service.TenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tender")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TenderController {
    private final TenderService tenderService;
    private final StatementService statementService;

    @PostMapping("/addTender")
    public ResponseEntity<?> addTender(@RequestBody TenderDTO tenderDTO){
        Tender tender = tenderService.saveOrUpdateTender(
                tenderDTO.getTenderName(),
                tenderDTO.getDescription(),
                tenderDTO.getCategoryID());
        return new ResponseEntity<>(tender, HttpStatus.CREATED);
    }

    @PostMapping("/bidTender/{user_id}/{tender_id}")
    public ResponseEntity<?> bidTender(
            @PathVariable Long user_id,
            @PathVariable Long tender_id,
            @RequestParam(name = "total_cost") Double totalCost){
        Statement statement = statementService.createStatement(user_id, tender_id, totalCost);
        return new ResponseEntity<>(statement, HttpStatus.CREATED);
    }

    @GetMapping("/getAllBids")
    public ResponseEntity<?> allStatements(){
        return new ResponseEntity<>(statementService.getAllStatements(), HttpStatus.OK);
    }

    @GetMapping("/getTenderById/{tenderId}")
    public ResponseEntity<?> getTenderById(@PathVariable Long tenderId){
        return new ResponseEntity<>(tenderService.findByTenderId(tenderId), HttpStatus.OK);
    }

    @GetMapping("/getBidById/{bidId}")
    public ResponseEntity<?> getBidById(@PathVariable Long bidId){
        return new ResponseEntity<>(statementService.findStatementById(bidId), HttpStatus.OK);
    }


    @GetMapping("/allTenders")
    public ResponseEntity<?> allTenders(){
        return new ResponseEntity<>(tenderService.getAllTenders(), HttpStatus.OK);
    }
}
