package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPets.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


public class SortCommandTest {
    private Model model;
    private Model expectedModel;

    /**
     * Initializes the model and expectedModel before running each test.
     */
    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    /**
     * Test method that checks if the pet list gets sorted after calling the sort command.
     */
    @Test
    public void execute_listIsNotFiltered_showsSortedList() {
        expectedModel.sortPetList();
        assertCommandSuccess(new SortCommand(), model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
