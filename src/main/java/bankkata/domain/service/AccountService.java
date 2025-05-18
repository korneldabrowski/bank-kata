package bankkata.domain.service;

import bankkata.domain.exception.AccountNotFoundException;
import bankkata.domain.model.Account;
import bankkata.domain.model.AccountId;
import bankkata.domain.model.Money;
import bankkata.domain.repository.AccountRepository;

/**
 * Service for performing operations on accounts.
 */
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Deposits money into an account.
     *
     * @param accountId the ID of the account
     * @param amount    the amount to deposit
     * @return the updated account
     * @throws AccountNotFoundException if the account is not found
     */
    public Account deposit(AccountId accountId, Money amount) {
        Account account = findAccount(accountId);
        account.deposit(amount);
        return accountRepository.save(account);
    }

    /**
     * Withdraws money from an account.
     *
     * @param accountId the ID of the account
     * @param amount    the amount to withdraw
     * @return the updated account
     * @throws AccountNotFoundException if the account is not found
     */
    public Account withdraw(AccountId accountId, Money amount) {
        Account account = findAccount(accountId);
        account.withdraw(amount);
        return accountRepository.save(account);
    }

    /**
     * Gets an account by its ID.
     *
     * @param accountId the ID of the account
     * @return the account
     * @throws AccountNotFoundException if the account is not found
     */
    public Account getAccount(AccountId accountId) {
        return findAccount(accountId);
    }

    private Account findAccount(AccountId accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId.value()));
    }
}
