# CPU Simulator, Spring 2024
This project was built as part of my *Computer Architecture and Organization* course. The goal was to simulate core components of a CPU — including arithmetic logic, memory, and instruction handling — in order to gain a deeper understanding of low-level computer architecture and how processors execute code.
## Project Overview
This simulator mimics key aspects of CPU behavior by implementing:
* 32-bit ALU: Performs arithmetic and logical operations such as addition, subtraction, AND, OR, etc.
* Main Memory: Simulates RAM with support for read and write operations.
* Processor Control Logic: Executes a simplified instruction cycle (fetch, decode, execute).
* Lexer and Parser: Converts readable input into machine-level operations for simulation.
These components work together to simulate how instructions are interpreted and executed at the hardware level, helping us explore instruction sets, memory access, and computational flow.
## Testing
Unit tests were written to validate correct behavior under a variety of scenarios, ensuring accurate instruction execution, memory usage, and ALU computation.
## Notes
* This project was designed for educational purposes.
* The codebase is organized into multiple modules and helper classes for clarity and modularity.
* Instruction parsing was included to improve readability and bridge the gap between human-readable input and low-level operations.
