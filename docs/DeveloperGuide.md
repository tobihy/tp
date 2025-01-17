---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* This project is adapted from a generic application called AddressBook-Level3 (AB3) (from https://se-education.org) as the starting point.

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/se-edu/addressbook-level3/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** has two classes called [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.


**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PetListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Pet` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

How the `Logic` component works:
1. When `Logic` is called upon to execute a command, it uses the `AddressBookParser` class to parse the user command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `AddCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to add a pet).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

The Sequence Diagram below illustrates the interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores WoofAreYou data i.e., all `Pet` objects (which are contained in a `UniquePetList` object).
* stores the currently 'selected' `Pet` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Pet>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Pet` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Pet` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both WoofAreYou data and user preference data in json format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Sorting feature

#### Proposed Implementation

The proposed sorting mechanism is facilitated by `SortCommand` class. It extends `Command`
and takes in a field that the user wishes to sort WoofAreYou by. The field is parsed by
`SortCommandParser`.

The primary sorting operation that takes place in the SortCommand class is sortPetList. This operation is exposed
in the `Model` interface as Model#sortPetList().

Currently, pet list can only be sorted by pet name or owner name. Each class implements the `Comparable` interface so that
they can be compared and sorted alphabetically.

The following sequence diagram shows how the sort operation works:
![SortSequenceDiagram](images/SortSequenceDiagram.png)

The following activity diagram summarizes what happens when a user executes a new sort command:
![SortActivityDiagram](images/SortActivityDiagram.png)

#### Design considerations:

* **Alternative 1 (current choice):** Currently the comparator classes for both owner name and pet name are generated
  within the `sortPetList` method in `UniquePetList`.
    * Pros: Easy to implement.
    * Cons: May be confusing to edit if there are more comparator classes in the future.

* **Alternative 2:** Generate a new package containing the various comparator classes.
    * Pros: Cleaner code. Better for future scalability.
    * Cons: Contributes to more lines of code. Harder to set up initially.


### \[Proposed\] Charge feature

#### Proposed Implementation

The proposed charge mechanism is facilitated by `ChargeCommand` class. It extends `Command`. It takes in a pet and month the user would like to charge. These fields are parsed by `ChargeCommandParser`. Additionally, it implements the following operations:

* `ChargeCommand#generateSuccessMessage()` — Generates a message containing the total amount chargeable to be shown to the user.
* `ChargeCommand#execute()` — Fetches attendance details of a pet and computes a month's total amount chargeable.
* `ChargeCommand#equals()` — Checks if a `ChargeCommand` equates another.

Given below is an example usage scenario and how the charge mechanism behaves at each step.

Step 1. The user executes command `charge 1 /m03` to compute amount chargeable to the pet at index 1 in March. The `charge` command is parsed by `ChargeCommandParser` which then sends the pet index and month to create a new `ChargeCommand` instance.


The following sequence diagram shows how the charge operation works:

![ChargeSequenceDiagram](images/ChargeSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `ChargeCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.


#### Design considerations:

**Aspect: How charge executes:**

* **Alternative 1 (current choice):** Gets attendance from a pet's attendanceHashMap and compute charge.
    * Pros: Saves memory.
    * Cons: May have performance issues in terms of recomputing the same pet's monthly charge.

* **Alternative 2:** Save charge as an attribute for each pet.
    * Pros: Easier to get charge.
    * Cons: Will use more memory and require more code to maintain it.


### \[Proposed\] Appointment feature

#### Proposed Implementation

The proposed appointment feature is facilitated by the `AppointmentCommand` class which extends `Command` class. The
`AppointmentCommand` takes in a valid mandatory index which specifics the pet that the command is to be used on,
followed by either **one** prefix (*clear*) or **two** prefixes (*dateTime* and *location*) based on the objective the
user is trying to accomplish. The fields are parsed by `AppointmentCommandParser` class.

Appointment feature can be used to accomplish the following 2 tasks:
1. Add and store pet's appointment details. (*dateTime and Location prefixes*)
2. Clear and delete pet's appointment details. (*clear prefix*)

The operation of updating the pet's appointment details and updating the pet filter list are done by methods in the
Model interface as Model#setPet() and Model#updateFilterPetList() respectively.

The following sequence diagram below illustrates the interactions between the `Logic` component and `Model` component
for the `execute("app 1 clear")` API call:
![AppointmentSequenceDiagram](images/AppointmentSequenceDiagram.png)

The following activity diagram summarizes what happens when a user executes a new Appointmentcommand:
![AppointmentActivityDiagram](images/AppointmentActivityDiagram.png)

#### Design considerations:

* **Alternative 1 (current choice):** Currently the appointment command is responsible for both the adding and clearing
of appointment details to / from a pet. These 2 tasks follow a similar command format and are differentiated only
by the prefixes / augments.
    * Pros: Easy and simple to implement.
    * Cons: User may struggle to get familiar with the command.

* **Alternative 2:** Add a new `clear` command to clear and delete contents of variables based on input field.
    * Pros: Better for future scalability.
    * Cons: Complex implementation. More lines of code. Harder to set up initially.

### \[Proposed\] Filter feature

#### Proposed Implementation
The proposed filter mechanism is facilitated by `FilterCommand` class.
It extends `Command` and takes in a field that the user wishes to filter WoofAreYou by followed by
a given filter word. The field is parsed by `FilterCommandParser`. A filter word will follow after the keyword to
indicate what the user wants to filter out specifically.

Currently, pet list can be filtered by date, owner name and tag. Users can only filter WoofAreYou by one field at
a time only. `FilterCommandParser` ensure this by throwing a `ParseException` when more than one filter field is
entered.

`FilterCommandParser` parses the arguments and classifies the fields into the three different classes as represented by
`DateContainsFilterDatePredicate`, `OwnerNameContainsFilterWordPredicate` and `TagContainsFilterWordPredicate` classes.
Each class extends the `FilterByContainsFilterWordPredicate` class, which implements the `Predicate<Pet>` interface,
in order for `FindCommand` to handle different fields appropriately and consequently test each pet differently for a
match in the specified field.

`FindCommand` then updates WoofAreYou using one of the three classes (`Predicates`). Each class has a different
way of testing `Pet`. If user filters by date, test will go through attendance of pet and determines if pet is present
on the specified date (entered as `filterWord` by user). If user filters by owner name, test will go through owner name
of pet and finds a partial/ full match with `filterWord` provided. Similarly, if user filters by tag, test will go
through the tag of the pets and find a match using the `filterWord` provided.

The following sequence diagram shows how the filter operation works when `filter byTag/ beagle` is called:

![FilterSequenceDiagram](images/FilterSequenceDiagram.png)

The following activity diagram summarizes what happens when a user executes a new `filter` command:

![FilterActivityDiagram](images/FilterActivityDiagram.png)

#### Design considerations:

* **Alternative 1 (current choice):** Currently each filter field extends its own `FilterByContainsFilterWordPredicate` class.
  * Pros: Easy to implement and increases flexibility when testing using a `Predicate`.
  * Cons: May generate a lot more classes if filter fields were to expand in the future.

* **Alternative 2:** Generate a new package containing the various predicate and methods to identify different fields.
  * Pros: Cleaner code. Better for future scalability.
  * Cons: Requires more lines of code. Harder to set up initially. Risk being messy if not careful.


### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current WoofAreYou state in its history.
* `VersionedAddressBook#undo()` — Restores the previous WoofAreYou state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone WoofAreYou state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial WoofAreYou state, and the `currentStatePointer` pointing to that single WoofAreYou state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th pet in WoofAreYou. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of WoofAreYou after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted WoofAreYou state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new pet. The `add` command also calls `Model#commitAddressBook()`, causing another modified WoofAreYou state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the WoofAreYou state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the pet was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous WoofAreYou state, and restores the WoofAreYou to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores WoofAreYou to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest WoofAreYou state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify WoofAreYou, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all WoofAreYou states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entirety of WoofAreYou.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the pet being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

### \[Proposed\] Attendance feature

#### Proposed Implementation
The proposed attendance feature is facilitated by `AttendanceCommand`. `AttendanceCommand` consists of two subclasses,
`PresentAttendanceCommand` and `AbsentAttendanceCommand`, which allows users to either mark a pet as present or absent
on a particular day. Initially, user input, which includes the index of the pet, date, as well as pick-up and drop-off
time (if applicable), is parsed by the `PresentAttendanceCommandParser` or `AbsentAttendanceCommandParser` classes into
the command classes above. The command classes are then passed on to the Model component for execution.

The data from the input is stored into the `AttendanceHashMap` class in pets, which consists of mappings of dates to
`Attendance` objects. The class hence acts as an "attendance sheet", and is the main repository of data within the
Model component that facilitates `Attendance` functionalities.

The operation of updating the pet's attendance details and updating the GUI to reflect such changes are done by methods
in the Model interface as `Model#setPet()` and `Model#updateFilterPetList()` respectively. `Attendance` GUI is also
supported by the methods in `AttendanceTag`, `TransportTag` and `AttendanceUtil` classes.

The following sequence diagram below models the interactions between the Logic as well as the Model components to
update the backend and frontend of the application.

![AbsentAttendanceSequenceDiagram](images/AbsentAttendanceSequenceDiagram.png)

The activity diagram below illustrates the workflow of attendance commands.

![AttendanceActivityDiagram](images/AttendanceActivityDiagram.png)

#### Design considerations:

**Aspect: Attendance data within `Model` component**

* **Alternative 1 (current choice):** Attendance entries in every pets' HashMaps.
  * Pros: Better OOP and performance.
  * Cons: Higher memory usage.
* **Alternative 2:** All attendance entries in a single HashMap.
  * Pros: Lesser memory usage, easier to implement.
  * Cons: May have performance issues due to nested data structure.


### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* Pet daycare centre owners and employees
* has a need to manage administrative details of pets on a daily basis
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: manage administrative details of pets faster than a typical mouse/GUI driven app


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​           | I want to …​                                                        | So that I can…​                                                        |
|----------|-------------------|---------------------------------------------------------------------|------------------------------------------------------------------------|
| `* * *`  | pet daycare owner | retrieve pet owner's contact                                        | contact pet owners                                                     |
| `* * *`  | pet daycare owner | tag different types of pets                                         | easily differentiate between the types of pets                         |
| `* * *`  | pet daycare owner | track when pets require pickup or drop-off                          | schedule the school bus for each day                                   |
| `* * *`  | pet daycare owner | track the different food preferences required by different pets     | make sure the pets are served the right foods                          |
| `* * *`  | pet daycare owner | track the attendanceEntry of pets                                   | charge pet owners the correct amount depending on pets attendanceEntry |
| `* * *`  | pet daycare owner | add pets in the daycare to system                                   | I have a consolidated information sheet                                |
| `* * *`  | pet daycare owner | retrieve the pets addresses                                         | inform the school bus driver correctly                                 |
| `* * *`  | pet daycare owner | find pets bu their ID                                               | retrieve the pet information accordingly                               |
| `* * *`  | pet daycare owner | delete pet's information from the system                            | information of pets that are in the system will be up to date          |
| `* *`    | pet daycare owner | tabulate the monthly charge of each pets                            | bill owners accordingly                                                |
| `* *`    | pet daycare owner | track the times that the pets will arrive                           | plan out my manpower allocation for the day                            |
| `* *`    | pet daycare owner | track the weight of pets                                            | inform the owner of any changes in weight                              |
| `* *`    | pet daycare owner | track pets' grooming appointments                                   | remember to bring them for grooming                                    |
| `* *`    | pet daycare owner | track the allergies that each pet has                               | avoid giving them food they may be allergic to                         |
| `* *`    | pet daycare owner | order pets chronologically by there name                            | easily search for their name in the system                             |
| `* *`    | pet daycare owner | order pets' appointments chronologically                            | know what is the next appointment I should take note of                |
| `* *`    | pet daycare owner | keep track of pets' birthdays                                       | throw a celebration with their friends                                 |
| `* *`    | pet daycare owner | alert when it is time to feed the pets                              | ensure pets are well-fed and healthy                                   |
| `* *`    | pet daycare owner | keep track of basic logistics like leash and waste bags             | replace when they are running low                                      |
| `* *`    | pet daycare owner | alert one day before appointment                                    | remember a particular pet's appointment schedule                       |
| `* *`    | pet daycare owner | alert when pets arrive                                              | prepare relevant logistics needed to take care of the pet              |
| `*`      | pet daycare owner | track the vet appointments of pets                                  | make sure pets do not miss their medical appointments                  |
| `*`      | pet daycare owner | sort the pets by their type                                         | order their necessary supplies accordingly                             |
| `*`      | pet daycare owner | track the medicine that pets need to take                           | i can feed them medicine appropriately                                 |
| `*`      | pet daycare owner | change the attendanceEntry of pets anytime I want                   | I can allow for last minute scheduling                                 |
| `*`      | pet daycare owner | update pet's information                                            |                                                                        |
| `*`      | pet daycare owner | update pet owner's information                                      |                                                                        |
| `*`      | pet daycare owner | access the previous attendanceEntry of pets                         | update owners if they were to enquire                                  |
| `*`      | pet daycare owner | find the number of pets present in the daycare fo each day          | arrange the necessary manpower                                         |
| `*`      | pet daycare owner | get a list of pets which will be staying overnight in the daycare   | arrange the necessary manpower                                         |

### Use cases

(For all use cases below, the **System** is the `WoofAreYou` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Add a pet**

**MSS**

1.  User adds a pet with pet details
2.  System shows confirmation message that pet details are added

Use case ends.


**Extensions**

* 1a. User keyed in wrong command format.

    * 1a1. System shows an error message.

      Use case resumes at step 1.

**Use case: Delete a pet**

**MSS**

1.  User deletes a pet with pet ID
2.  System shows confirmation message that pet details are deleted

Use case ends.


**Extensions**

* 1a. User keyed in missing/ invalid pet ID.

    * 1a1. System shows an error message.

      Use case resumes at step 1.

**Use case: Get pet ID**

**MSS**

1.  User get pet ID with name of pet.
2.  System shows a list of pet IDs with the specified name.

Use case ends.


**Extensions**

* 1a. User keyed in invalid pet name.

    * 1a1. System shows empty list.

      Use case resumes at step 1.


**Use case: Get pet dietary requirements**

**MSS**

1.  User keys in pet ID.
2.  System shows the dietary requirement of the pet with specified ID.

Use case ends.


**Extensions**

* 1a. User keyed in invalid pet ID.

    * 1a1. System shows an error message.

      Use case resumes at step 1.


**Use case: Get pet owner details**

**MSS**

1.  User keys in pet ID.
2.  System shows the pet owner's details of the pet with specified ID.

Use case ends.


**Extensions**

* 1a. User keyed in invalid pet ID.

    * 1a1. System shows an error message.

      Use case resumes at step 1.


**Use case: Get pet pickup and drop-off time**

**MSS**

1.  User keys in pet ID.
2.  System shows the pickup and drop-off time of the pet with specified ID.

Use case ends.


**Extensions**

* 1a. User keyed in invalid pet ID.

    * 1a1. System shows an error message.

      Use case resumes at step 1.

**Use case: Exit**

**MSS**

1.  User keys exit.
2.  System terminates the program.

Use case ends.


### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to hold up to 1000 pets without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a pet

1. Deleting a pet while all pets are being shown

   1. Prerequisites: List all pets using the `list` command. Multiple pets in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No pet is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
