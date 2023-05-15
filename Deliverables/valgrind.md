# Defect report of Valgrind trace

### Task description
Analyze a defect version of pattern-search program __grep__ to identify what is causing the bug and then fix the issue. The following command was executed where the string "test" is piped in as argument to grep and the pattern to search for is "es": 

echo test | valgrind -s ./src/grep -e es




### Overview of the error trace
Valgrind detected in total three errors and 36 bytes of data was leaked when inspecting the memory. The first error was caused by invalid write operations while the other two was caused by a invalid read operations.

#### Error 1
Invalid read of 1 bytes that occured at line 952 in the main program grep.c where a compiler pointer is set to one of the two search functions. The program argument and its length is passed into ```(*compile) PARAMS((char *, size_t))``` which then causes an error in the regex_compile function of regex.c.

Anledning??


#### Error 2
Invalid write of 1 byte that occured at line 844 in the main program grep.c where the program tries to do the following write operation: ```keys[keycc++] = '\n'```. Looking at the previous rows of execution we can see that the program have expanded the allocated memory of keys and then performed a __strcpy__ to fill the newly allocated memory. The error then occurs when trying to write a newline character to the end of the string. 

Anledning??

#### Error 3
Invalid write of 1 byte that occured at line 842 in the main program grep.c where the program tries to copy a string into the allocated memory of keys: ```strcpy(&keys[keycc], optarg)```. 


### Which data is uninitialized? Look at the type of data used (a string), and how it is being used.




### How are C strings encoded? 




### What is a common error when allocating memory for a C string?