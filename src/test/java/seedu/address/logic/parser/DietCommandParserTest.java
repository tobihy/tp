package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.DIET_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DIET_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIET_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PET;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DietCommand;
import seedu.address.model.pet.Diet;

public class DietCommandParserTest {

    private static final String NEG_INTEGER_MAX = String.valueOf(-(Integer.MAX_VALUE + 1));
    private static final String POS_INTEGER_MAX = String.valueOf(Integer.MAX_VALUE + 1);
    private DietCommandParser parser = new DietCommandParser();

    @Test
    public void parse_invalidValue_failure() {
        // diet with special characters
        assertParseFailure(parser, INVALID_DIET_DESC, Diet.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validValue_success() {
        String userInput = INDEX_FIRST_PET.getOneBased() + DIET_DESC_AMY;
        // valid diet - only contains alphanumeric characters and spaces
        assertParseSuccess(parser, userInput, new DietCommand(INDEX_FIRST_PET, new Diet(VALID_DIET_AMY)));
    }

    @Test
    public void parse_indexOutOfBoundsIntegerOverflow_throwsParseException() {
        // large positive number
        assertParseFailure(parser, POS_INTEGER_MAX + DIET_DESC_AMY, MESSAGE_INVALID_PET_DISPLAYED_INDEX);
        // large negative number
        assertParseFailure(parser, NEG_INTEGER_MAX + DIET_DESC_AMY, MESSAGE_INVALID_PET_DISPLAYED_INDEX);
    }
}
