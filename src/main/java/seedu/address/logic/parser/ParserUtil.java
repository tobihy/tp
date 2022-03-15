package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.pet.Address;
import seedu.address.model.pet.Appointment;
import seedu.address.model.pet.Name;
import seedu.address.model.pet.OwnerName;
import seedu.address.model.pet.Phone;
import seedu.address.model.tag.Tag;


/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String ownerName} into an {@code OwnerName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code ownerName} is invalid.
     */
    public static OwnerName parseOwnerName(String ownerName) throws ParseException {
        requireNonNull(ownerName);
        String trimmedOwnerName = ownerName.trim();
        if (!OwnerName.isValidOwnerName(trimmedOwnerName)) {
            throw new ParseException(OwnerName.MESSAGE_CONSTRAINTS);
        }
        return new OwnerName(trimmedOwnerName);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String appointment} into a {@code Appointment}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param dateTime Date and time of appointment.
     * @param location Location of appointment.
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Appointment parseAppointment(String dateTime, String location) throws ParseException {
        requireAllNonNull(dateTime, location);
        String formattedDateTime = formatDateTime(dateTime.trim());
        String trimmedLocation = location.trim();
        String appointmentDetails = formattedDateTime + " at " + trimmedLocation;

        if (!Appointment.isValidAppointment(appointmentDetails)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AppointmentCommand.MESSAGE_USAGE));
        }

        return new Appointment(appointmentDetails);
    }

    /**
     * Parses the format of the Date and Time input from "yyyy-MM-dd HH:mm"
     * to "MMM-dd-yyyy HH:mm a".
     *
     * @param input Date and time in yyyy-MM-dd HH:mm.
     * @return Formatted Date and time in MMM-dd-yyyy HH:mm a.
     * @throws ParseException If input format is invalid.
     */
    private static String formatDateTime(String input) throws ParseException {
        try {
            DateTimeFormatter formatIn = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            DateTimeFormatter formatOut = DateTimeFormatter.ofPattern("MMM-dd-yyyy HH:mm a");
            return LocalDateTime.parse(input, formatIn).format(formatOut);
        } catch (Exception e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AppointmentCommand.MESSAGE_USAGE), e);
        }
    }

}
