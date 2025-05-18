package bankkata.domain.repository;

import bankkata.domain.model.Account;
import bankkata.domain.model.AccountId;

import java.util.Optional;

/**
 * Repository interface for account persistence operations.
 */
public interface AccountRepository {
    /**
     * Finds an account by its ID.
     *
     * @param accountId the account ID
     * @return the account wrapped in an Optional
     */
    Optional<Account> findById(AccountId accountId);

    /**
     * Saves an account to the repository.
     *
     * @param account the account to save
     * @return the saved account
     */
    Account save(Account account);
}
