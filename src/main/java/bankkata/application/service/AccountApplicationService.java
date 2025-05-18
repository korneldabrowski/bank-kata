package bankkata.application.service;

import bankkata.application.dto.OperationDto;
import bankkata.application.dto.StatementDto;
import bankkata.application.exception.ApplicationException;
import bankkata.domain.exception.AccountNotFoundException;
import bankkata.domain.exception.InvalidOperationException;
import bankkata.domain.model.Account;
import bankkata.domain.model.AccountId;
import bankkata.domain.model.Money;
import bankkata.domain.model.Operation;
import bankkata.domain.service.AccountService;
import bankkata.domain.service.StatementService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Application service for account operations.
 * This class serves as the entry point to the application business logic.
 */
public class AccountApplicationService {
    private final AccountService accountService;
    private final StatementService statementService;

    public AccountApplicationService(AccountService accountService, StatementService statementService) {
        this.accountService = accountService;
        this.statementService = statementService;
    }

    /**
     * Deposits money into an account.
     * 
     * @param accountId the ID of the account
     * @param amount    the amount to deposit
     * @throws ApplicationException if the operation fails
     */
    public void deposit(String accountId, double amount) {
        try {
            accountService.deposit(new AccountId(accountId), new Money(amount));
        } catch (AccountNotFoundException | InvalidOperationException e) {
            throw new ApplicationException("Failed to deposit: " + e.getMessage(), e);
        }
    }

    /**
     * Withdraws money from an account.
     * 
     * @param accountId the ID of the account
     * @param amount    the amount to withdraw
     * @throws ApplicationException if the operation fails
     */
    public void withdraw(String accountId, double amount) {
        try {
            accountService.withdraw(new AccountId(accountId), new Money(amount));
        } catch (AccountNotFoundException | InvalidOperationException e) {
            throw new ApplicationException("Failed to withdraw: " + e.getMessage(), e);
        }
    }

    /**
     * Gets the balance of an account.
     * 
     * @param accountId the ID of the account
     * @return the account balance
     * @throws ApplicationException if the operation fails
     */
    public double getBalance(String accountId) {
        try {
            Account account = accountService.getAccount(new AccountId(accountId));
            return account.getBalance().getAmount().doubleValue();
        } catch (AccountNotFoundException e) {
            throw new ApplicationException("Failed to get balance: " + e.getMessage(), e);
        }
    }

    /**
     * Gets a statement for an account.
     * 
     * @param accountId the ID of the account
     * @return the account statement
     * @throws ApplicationException if the operation fails
     */
    public StatementDto getStatement(String accountId) {
        try {
            Account account = accountService.getAccount(new AccountId(accountId));
            List<Operation> operations = account.getOperations();
            String formattedStatement = statementService.generateStatement(operations);

            List<OperationDto> operationDtos = operations.stream()
                    .map(op -> new OperationDto(
                            op.getType().toString(),
                            op.getTimestamp(),
                            op.getAmount().getAmount().doubleValue(),
                            op.getBalanceAfterOperation().getAmount().doubleValue()))
                    .collect(Collectors.toList());

            return new StatementDto(formattedStatement, operationDtos);
        } catch (AccountNotFoundException e) {
            throw new ApplicationException("Failed to get statement: " + e.getMessage(), e);
        }
    }
}
