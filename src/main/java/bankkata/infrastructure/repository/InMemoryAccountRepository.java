package bankkata.infrastructure.repository;

import bankkata.domain.model.Account;
import bankkata.domain.model.AccountId;
import bankkata.domain.repository.AccountRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of the AccountRepository interface.
 * This implementation is thread-safe using ConcurrentHashMap.
 */
public class InMemoryAccountRepository implements AccountRepository {

    private final Map<AccountId, Account> accounts = new ConcurrentHashMap<>();

    @Override
    public Optional<Account> findById(AccountId accountId) {
        return Optional.ofNullable(accounts.get(accountId));
    }

    @Override
    public Account save(Account account) {
        accounts.put(account.getId(), account);
        return account;
    }
}
