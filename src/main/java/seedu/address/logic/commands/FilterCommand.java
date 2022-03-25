package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.pet.FilterByContainsFilterWordPredicate;

/**
 * Filters all pets in address book by field provided.
 * Filter word matching is case-insensitive.
 * Fields can only be from a defined set.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all pets by "
            + "the specified field followed by filter/ search word (case-insensitive)"
            + "and displays them as a list with index numbers.\n"
            + "PARAMETERS: FIELD [FILTER WORD] \n"
            + "FIELD = { 'byDate/', 'byOwner/', 'byTag/' } \n"
            + "Filter word following 'byDate/' can be 'today' or 'dd-MM-yyyy'.\n"
            + "Example: " + COMMAND_WORD + " tag/" + "golden retriever\n"
            + "Example: " + COMMAND_WORD + " byDate/today";

    public static final String INVALID_FILTER_FIELD = "The field you are trying to filter by is invalid!";

    private final FilterByContainsFilterWordPredicate predicate;

    public FilterCommand(FilterByContainsFilterWordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPetList(predicate);
        return new CommandResult(
                String.format(Messages.FILTER_MESSAGE_SUCCESS, model.getFilteredPetList().size()));
    }

}
