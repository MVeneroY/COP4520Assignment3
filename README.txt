Miguel Venero Yupanqui
Professor Parra
COP 4520
April 13, 2023

################################### Part 1 ####################################

To compile: 
    $ cd part1
    $ javac Runner.java
To run:
    $ java Runner 

OR to compile and run:
    $ cd part1; make run

Proof of Correctness:

The approach I used includes a lazy linked list implementation, that doesn't 
remove items from the list, but instead marks them to be ignored during search
and remove operations. The threads would alternate tasks in a cyclic manner, 
and skip the 'add to the list' task if all the gifts are already in the list.
The threads keep alternating tasks until all the tasks have are in the out 
location, which means that all the gifts end up having a thank you note
attached to them. This, besides the fact that the list's add and pop operations
are locked, guarantees the program's correctness.

Proof of Efficiency:

The program is efficient, as the list add operation makes use of a tail node to
avoid having to traverse the list. Then, the pop operation removes from the front,
avoiding once again having to traverse the list to remove an item. On top of that,
the pop operation is a lazy remove operation, so it marks off the node that will
be removed. Marked off nodes will be ignored during add() and search(), and the
search() operation will start from a virtualhead node. This node will be the first
node that is not marked off, to avoid traversing through nodes that have been
lazily removed.

However, considering the large memory usage of the program for linked lists of large 
sizes, I have had trouble running the program for a n_gifts = 500,000, since it 
threw a OutOfMemoryError error*.  

Experimental Evaluation: (no print statements)

50 gifts            9ms
500 gifts           42ms
5,000 gifts         214ms
50,000 gifts        6867ms
500,000 gifts       Time

* times obtained from running on a 10 core, 16gb ram machine

################################### Part 2 ####################################

To compile: 
    $ cd part2
    $ javac Runner.java
To run:
    $ java Runner 

OR to compile and run:
    $ cd part2; make run

Proof of Correctness:

The approach I used consisted on each sensor thread collecting its own data,
including the top 5 recorded temperatures, top 5 lowest temperatures, and the 
10 minute interval with the greatest difference. Then, once the sensors have 
collected their data, they would get access to the report object, comparing 
their data with the data on the report. If a sensor has data that would overcome
the data on the report, such as a higher recorded temperature, the sensor would 
overwrite that specific record. This approach is correct as it uses locks to 
avoid multiple sensors from writing to the report object at the same time.

Proof of Efficiency:

This approach is efficient as it doesn't really depend on any time complexity
heavy algorithms, and the write operations on the report object don't have any
big processing times either.

Experimental Evaluation:

8 n_threads     527ms

* times obtained from running on a 10 core, 16gb ram machine