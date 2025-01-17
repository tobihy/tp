package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.pet.Pet;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Pet> filteredPets;
    private Predicate<Pet> lastUsedPredicate;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);
        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.versionedAddressBook = new VersionedAddressBook(this.addressBook);
        filteredPets = new FilteredList<>(this.addressBook.getPetList());
        this.lastUsedPredicate = PREDICATE_SHOW_ALL_PETS;
    }

    /**
     * Initializes a ModelManager with the given addressBook, userPrefs and predicate.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs, Predicate<Pet> predicate) {
        requireAllNonNull(addressBook, userPrefs);
        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs
                + " and predicate " + predicate);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.versionedAddressBook = new VersionedAddressBook(this.addressBook);
        filteredPets = new FilteredList<>(this.addressBook.getPetList());
        this.lastUsedPredicate = predicate;
        updateFilteredPetList(lastUsedPredicate);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPet(Pet pet) {
        requireNonNull(pet);
        return addressBook.hasPet(pet);
    }

    @Override
    public void deletePet(Pet target) {
        addressBook.removePet(target);
        this.versionedAddressBook.commit(this.getAddressBook());
    }

    @Override
    public void addPet(Pet pet) {
        addressBook.addPet(pet);
        this.versionedAddressBook.commit(this.getAddressBook());
        updateFilteredPetList();
    }

    @Override
    public void setPet(Pet target, Pet editedPet) {
        requireAllNonNull(target, editedPet);
        addressBook.setPet(target, editedPet);
        this.versionedAddressBook.commit(this.getAddressBook());
    }

    /** Method that sorts the pet list via the sortPets() command in addressBook. **/
    @Override
    public void sortPetList(String field) {
        requireNonNull(field);
        addressBook.sortPets(field);
        this.versionedAddressBook.commit(this.getAddressBook());
    }


    //============= Undo Command accessors ================//
    @Override
    public ReadOnlyAddressBook undo() throws Exception {
        return versionedAddressBook.undo();
    }

    //=========== Filtered Pet List Accessors =============================================================

    @Override
    public Predicate<Pet> getLastUsedPredicate() {
        return lastUsedPredicate;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Pet} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Pet> getFilteredPetList() {
        return filteredPets;
    }

    @Override
    public void updateFilteredPetList(Predicate<Pet> predicate) {
        requireNonNull(predicate);
        this.lastUsedPredicate = predicate;
        filteredPets.setPredicate(lastUsedPredicate);
    }

    @Override
    public void updateFilteredPetList() {
        filteredPets.setPredicate(lastUsedPredicate);
    }

    @Override
    public void updateFilteredPetListToFullPetList() {
        this.lastUsedPredicate = PREDICATE_SHOW_ALL_PETS;
        filteredPets.setPredicate(lastUsedPredicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPets.equals(other.filteredPets);
    }

}
