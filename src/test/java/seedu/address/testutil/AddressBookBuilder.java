package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.pet.Pet;

/**
 * A utility class to help with building AddressBook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPet("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Pet} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPet(Pet pet) {
        addressBook.addPet(pet);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
