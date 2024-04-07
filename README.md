#  Java PDA Expression Parser

This Java project implements a Pushdown Automaton (PDA) for parsing expressions and if statements. It utilizes a stack-based approach to process input strings and validate whether they conform to predefined grammar rules.

## ⚙️ Features

- Parses expressions following predefined grammar rules.
- Handles if statements with nested expressions.
- Supports logging to trace PDA transitions (optional).

## 📝 Usage

1. Ensure you have Java installed on your system.
2. Clone this repository to your local machine.
3. Open the project in your preferred Java IDE.
4. Compile and run the `App.java` file.
5. You can uncomment `ExpressionParser.zeroesAndOnes();` in `App.java` to test parsing for zero-one sequences.

## 📦 Components

### `Edge` Class

Represents an edge in the PDA, defining the character, pop character, and push character.

### `State` Class

Defines a state in the PDA, with methods to add transitions and manage transitions based on input characters.

### `Transition` Class

Represents a transition in the PDA, consisting of a state and an edge.

### `PDA` Class

Implements the Pushdown Automaton functionality, including methods to add/remove items from the stack, run the PDA, and manage logging.

### `ExpressionParser` Class

Contains methods to parse expressions and if statements using the PDA.

### `App` Class

Main class to run the PDA expression parser.

## Example

To parse an if statement, you can use the following code snippet:

```java
import Core.ExpressionParser;

public class App {
    public static void main(String[] args) throws Exception {
        ExpressionParser.ifStatement();
    }
}
```
