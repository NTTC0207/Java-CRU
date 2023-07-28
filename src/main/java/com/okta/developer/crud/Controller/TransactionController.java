package com.okta.developer.crud.Controller;

import com.okta.developer.crud.Repository.TransactionRepository;
import com.okta.developer.crud.Service.TransactionService;
import com.okta.developer.crud.model.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;

    public TransactionController(TransactionService transactionService, TransactionRepository transactionRepository) {
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
    }

 
    @GetMapping("/{id}/{page}")
    public List<Transaction> getall(@PathVariable Long id,@PathVariable int page)throws ExecutionException, InterruptedException{
        return (List<Transaction>) transactionService.getAllTransactionByMonth(id,page).get();
    }

    @GetMapping("/count/{id}")
    @Async
    public CompletableFuture<Long> countById(@PathVariable Long id) {
        return transactionService.countById(id);
    }

    @PostMapping
    public CompletableFuture<Transaction> saveTran(@RequestBody Transaction transaction,@AuthenticationPrincipal OAuth2User user){
        return this.transactionService.saveTranAndUpadateMonth(transaction,user);
    }
    @PutMapping("/{id}")
    public CompletableFuture<Transaction> updateTran(@PathVariable Long id,@RequestBody Transaction transaction){
        return this.transactionService.updateTrans(id,transaction);
    }
    @DeleteMapping("{id}")
    public CompletableFuture<Void> deleteTran(@PathVariable Long id){
        return this.transactionService.deleteTran(id);
    }





}
