QuickChat – PROG5121 POE

A Java-based chat application developed for the PROG5121 Portfolio of Evidence. This project demonstrates the use of object-oriented programming, decision structures, loops, arrays, file handling, JSON storage, and unit testing with JUnit.

Overview

QuickChat allows users to register, log in, send messages, store or discard them, generate message hashes and IDs, save data to JSON, delete messages, search through stored messages, and display reports.

The project meets the requirements for Part 1, Part 2 and Part 3 of the PROG5121 assessment. All tasks are completed with full functionality and unit tests.

Features
Part 1: Registration and Login

Username validation

Password complexity checking

South African cellphone number validation (AI-generated regex)

User registration

Login verification

Clear system messaging

All Part 1 unit tests implemented and passed

Part 2: Sending Messages

Menu-driven message system using JOptionPane

Auto-generated message IDs (10 digits)

Auto-generated message hashes

Length validation for messages

Options to send, store, or disregard messages

Total number of sent messages tracked

Messages written to a JSON file

All Part 2 unit tests implemented and passed

Part 3: Arrays and Reporting

Parallel arrays for storing message data

Ability to search messages by ID or recipient

Display of all sent messages

Longest message detection

Deleting messages using hashes (including JSON updates)

Loading saved messages from JSON

Summary message report

All Part 3 unit tests implemented and passed

Technologies Used

Java

Swing (JOptionPane)

org.json library

JUnit 5

GitHub Actions (for automated testing)

How to Run

Clone the repository

Open the project in NetBeans or any Java IDE

Ensure org.json is added to your project libraries

Run Main.java

Follow the prompts to register, log in, and begin using the QuickChat system

Folder Structure
/src
   /Login.java
   /Message.java
   /Main.java
   /MessageTest.java

QuickChat/messages.json     (auto-generated)
.github/workflows/TestJava.yml

Unit Testing

All tests for Part 1, Part 2 and Part 3 are included in the MessageTest and LoginTest classes.
GitHub Actions automatically runs the tests on every commit.

AI Assistance Attribution

Regex for cellphone validation and JSON handling was generated with assistance from ChatGPT (OpenAI, 2025). Full APA references are included in the code comments as required by the POE instructions.