---
layout: page
title: Shaine's Project Portfolio Page
---

### Project: WoofAreYou

WoofAreYou is a desktop administrative manager used by pet daycare owners to aid their day-to-day administrative duties.
The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project:


* **Undo**: Added the ability for users to undo previous commands.
    * What it does: Allows pet daycare owners to undo accidental commands in the event that they made a typo in one of
      the fields and need to revert the application back to the previous state.
    * Justification: The undo feature is a user-oriented feature that aims to provide pet daycare owners with a seamless
      experience when using WoofAreYou. Instead of going through the cumbersome process of deleting and adding
      in the event of a typo, pet daycare owners can instead simply undo and revert the petBook to the previous state.
    * Highlights: The implementation of `undo` is also extendable for other modifications such as the `redo` command.
    * Credits: This enhancement was inspired from the implementation of the UndoCommand that has been suggested
      in the original Developer Guide.
* **Sort**: Added the ability for users to sort the pet list.
  * What it does: Allows pet daycare owners to sort the pet list by name or owner name.
  * Justification: This command makes it easier for pet daycare owners to locate and find pet entries that they may be
    looking for. Instead of having to scour through the entire pet list to find Woofie, they can simply sort the pet
    list and look for Woofie at the end of the list. This feature is also very intuitive as it is a feature that is
    commonly employed in many applications. (For eg. **Spotify**: Listeners can sort music by
    alphabetical order.)
  * Highlights: The implementation is easily extensible as shown by how my teammate Dinesh managed to extend on it
    to sort other fields such as `pick-up` and `drop-off` time as well.
* **Diet**: Added the ability for users to add dietary restrictions to a particular pet.
    * What it does: Allows pet daycare owners to indicate a pet's dietary restrictions.
    * Justification: The diet feature is useful as it helps pet daycare owners to easily identify certain dietary
      restrictions that a particular pet has. This will help reduce situations where the pet is fed food that it should
      not be eating, creating a healthy and safe environment for the pets!
    * Credits: This enhancement is extended from the RemarkCommand tutorial that was done at the start of
      the tP iteration.
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s2.github.io/tp-dashboard/?search=soloplxya&breakdown=true)
* **Project management**: Helped maintain the team repository by **enabling assertions** in the `build.gradle` file.
  [View PR](https://github.com/AY2122S2-CS2103T-T13-1/tp/pull/90)
* **Enhancements to existing features**:
  * Previously, certain user inputs were not fully reflected on the GUI of the application as the content was too long.
    In this [PR](https://github.com/AY2122S2-CS2103T-T13-1/tp/pull/107), a modificiation that I made was to set a
    character limit to the user input so that the details keyed in will be able to fit in the GUI nicely.
* **Documentation**:
  * Updated the **Developer Guide** by describing the implementation of the SortCommand.
    Additionally, I also helped to add sequence diagrams and activity diagrams to visually illustrate the
    entire implementation of the SortCommand. [View PR](https://github.com/AY2122S2-CS2103T-T13-1/tp/pull/79)
  * Updated the **User Guide** to include the description of the features that I have implemented.
    [View PR](https://github.com/AY2122S2-CS2103T-T13-1/tp/pull/87)
* **Community**:
  * Reviewed teammates pull requests by:
    * Suggesting fixes for issues that teammates' were experiencing:
      [PR](https://github.com/AY2122S2-CS2103T-T13-1/tp/pull/164)
    * Spotting documentation errors in the code that needed to be corrected:
      [PR](https://github.com/AY2122S2-CS2103T-T13-1/tp/pull/74)

