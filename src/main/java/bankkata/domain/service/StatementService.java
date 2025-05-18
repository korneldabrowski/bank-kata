package bankkata.domain.service;

import bankkata.domain.model.Operation;

import java.util.List;

/**
 * Service for generating account statements.
 */
public interface StatementService {
    /**
     * Generates a statement from a list of operations.
     *
     * @param operations the list of operations
     * @return the formatted statement
     */
    String generateStatement(List<Operation> operations);
}
