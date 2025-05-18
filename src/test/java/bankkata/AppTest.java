package bankkata;

import bankkata.domain.model.AccountId;
import bankkata.domain.repository.AccountRepository;
import bankkata.domain.service.AccountService;
import bankkata.domain.service.StatementService;
import bankkata.infrastructure.clock.TestClock;
import bankkata.infrastructure.formatter.TextStatementFormatter;
import bankkata.infrastructure.repository.InMemoryAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Test for the App class and the architecture wiring.
 */
class AppTest {
    @Test
    void shouldDemonstrateBasicBankAccountFunctionality() {
        // This test is just a placeholder for the real tests in AccountTest and
        // StatementPrinterTest
        Assertions.assertTrue(true);
    }

    @Test
    void shouldDemonstrateArchitecturalComponents() {
        // Test that our architectural components can be instantiated
        TestClock clock = new TestClock(LocalDate.now(), LocalTime.now());
        AccountRepository accountRepository = new InMemoryAccountRepository();
        StatementService statementService = new TextStatementFormatter();
        AccountService accountService = new AccountService(accountRepository);

        // Create and save an account
        AccountId accountId = new AccountId("test-account");
        bankkata.domain.model.Account account = new bankkata.domain.model.Account(accountId, clock);
        accountRepository.save(account);

        // Verify we can retrieve the account
        Assertions.assertTrue(accountRepository.findById(accountId).isPresent());
    }
}
