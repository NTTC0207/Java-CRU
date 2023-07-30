package com.okta.developer.crud.Service;

import com.okta.developer.crud.Repository.MonthRepository;
import com.okta.developer.crud.Repository.TransactionRepository;
import com.okta.developer.crud.model.Month;
import com.okta.developer.crud.model.Transaction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final MonthRepository monthRepository;
    private final UsersService usersService;


    public TransactionService(TransactionRepository repository, MonthRepository monthRepository, UsersService usersService) {
        this.repository = repository;
        this.monthRepository = monthRepository;
        this.usersService = usersService;

    }



    @Async
    public CompletableFuture<List<Transaction>> getAllTransactionByMonth(Long id,int page) {
        Pageable pageable = PageRequest.of(page -1 , 3); // Adjust page number here (0-based index)
        return CompletableFuture.completedFuture(repository.findAllByMonth_Id(id,  pageable));
    }

    @Async
    public CompletableFuture<Long> countById( Long id) {
        return CompletableFuture.completedFuture(repository.countByMonth_Id(id));
    }

    @Async
    public CompletableFuture<Transaction> getTransactionID(Long id){
        Transaction transaction= this.repository.findById(id).get();
        return CompletableFuture.completedFuture(transaction);
    }



    @Async
    public CompletableFuture<Transaction> saveTranAndUpadateMonth(Transaction transaction, @AuthenticationPrincipal OAuth2User user){

        CompletableFuture<String> userIDFuture = usersService.getSubID(user);
        String userID = userIDFuture.join();

        LocalDate transactionDate = LocalDate.now();
        LocalDate date = LocalDate.parse(transactionDate.toString(), DateTimeFormatter.ISO_LOCAL_DATE);
        int months = date.getMonthValue();
        int years = date.getYear();

        Month summary = monthRepository.findByYearAndMonthAndSub(years,months,userID);

        if(summary == null){
            Month month = new Month();
//            month.setUsers(new Users());
//            month.getUsers().setId(userId);
            month.setSub(userID);
            month.setYear(years);
            month.setMonth(months);
            month.setAchievedQuota(new BigDecimal(transaction.getAmount()));
            month.setTotal_quota(new BigDecimal( 10000));
            //add transaction to database
            monthRepository.save(month);

            Transaction trans = new Transaction();
            trans.setAmount(transaction.getAmount());
            trans.setMonth_id(month.getId());

            repository.save(trans);
        }else{
            summary.setAchievedQuota(summary.getAchievedQuota().add(new BigDecimal(transaction.getAmount())));
            if(summary.getAchievedQuota().compareTo(summary.getTotal_quota()) > 0){
                summary.setArchived(true);
                summary.setCommission(summary.getAchievedQuota().multiply(new BigDecimal(0.20)) );
            }
            monthRepository.save(summary);

            Transaction trans = new Transaction();
            trans.setAmount(transaction.getAmount());
            trans.setMonth_id(summary.getId());

            repository.save(trans);

        }

        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<Transaction> updateTransactionAmountAndMonth(Transaction updatedTransaction, @AuthenticationPrincipal OAuth2User user) {
        CompletableFuture<String> userIDFuture = usersService.getSubID(user);
        String userID = userIDFuture.join();

        LocalDate transactionDate = LocalDate.now();
        LocalDate date = LocalDate.parse(transactionDate.toString(), DateTimeFormatter.ISO_LOCAL_DATE);
        int months = date.getMonthValue();
        int years = date.getYear();

        Month summary = monthRepository.findByYearAndMonthAndSub(years, months, userID);

        if (summary == null) {
            // If the corresponding Month does not exist, create a new one and update the transaction
            Month month = new Month();
            month.setSub(userID);
            month.setYear(years);
            month.setMonth(months);
            month.setAchievedQuota(new BigDecimal(updatedTransaction.getAmount()));
            month.setTotal_quota(new BigDecimal(10000));

            // Add transaction to database
            monthRepository.save(month);

            // Update the transaction with the new month_id
            updatedTransaction.setMonth_id(month.getId());

            repository.save(updatedTransaction);
        } else {
            // If the corresponding Month exists, update the Month and the transaction
            summary.setAchievedQuota(summary.getAchievedQuota().add(new BigDecimal(updatedTransaction.getAmount())));

            if (summary.getAchievedQuota().compareTo(summary.getTotal_quota()) > 0) {
                summary.setArchived(true);
                summary.setCommission(summary.getAchievedQuota().multiply(new BigDecimal(0.20)));
            }

            // Save the updated Month
            monthRepository.save(summary);

            // Update the transaction with the month_id of the updated Month
            updatedTransaction.setMonth_id(summary.getId());

            repository.save(updatedTransaction);
        }

        return CompletableFuture.completedFuture(null);
    }


    @Async
    public CompletableFuture<Transaction> updateTrans(Long id, Transaction transaction) {
        Optional<Transaction> tranOptional = repository.findById(id);

        if (tranOptional.isPresent()) {
            Transaction tran = tranOptional.get();
            Month month = monthRepository.findById(tran.getMonth_id())
                    .orElseThrow(() -> new IllegalArgumentException("The Month with id " + tran.getMonth_id() + " is not found"));

            double oldAmount = tran.getAmount();
            double newAmount = transaction.getAmount();

            // Update the Transaction amount
            tran.setAmount(newAmount);

            // Calculate the change in the amount and update the Month's totalQuota
            double amountChange = newAmount - oldAmount;
            month.setAchievedQuota(month.getAchievedQuota().add(BigDecimal.valueOf(amountChange)));

            if (month.getAchievedQuota().compareTo(month.getTotal_quota()) > 0) {
                month.setArchived(true);
                month.setCommission(month.getAchievedQuota().multiply(new BigDecimal(0.20)));
            } else if (month.getAchievedQuota().compareTo(month.getTotal_quota()) <= 0) {
                // If the achieved quota is less than or equal to the total quota
                // Set archived to false and commission to zero
                month.setArchived(false);
                month.setCommission(BigDecimal.ZERO);
            }

            Transaction updated = repository.save(tran);
            monthRepository.save(month);

            return CompletableFuture.completedFuture(updated);
        }

        throw new IllegalArgumentException("The Transaction with id " + id + " is not found");
    }


    @Async
    public CompletableFuture<Void> deleteTran(Long id) {
        Optional<Transaction> optionalTransaction = this.repository.findById(id);

        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();
            BigDecimal transactionAmount = BigDecimal.valueOf(transaction.getAmount());

            this.repository.deleteById(id);

            Optional<Month> optionalMonth = monthRepository.findById(transaction.getMonth_id());

            if (optionalMonth.isPresent()) {
                Month month = optionalMonth.get();
                BigDecimal achievedQuota = month.getAchievedQuota().subtract(transactionAmount);
                month.setAchievedQuota(achievedQuota);

                // Set commission to zero


                // Update the archived flag based on the achieved quota
                if (achievedQuota.compareTo(month.getTotal_quota()) > 0) {
                    month.setArchived(true);
                } else {
                    month.setArchived(false);
                    month.setCommission(BigDecimal.ZERO);
                }

                monthRepository.save(month);
            }
            return CompletableFuture.completedFuture(null);
        }

        throw new IllegalArgumentException("Bonus ID " + id + " is not found");
    }







}
