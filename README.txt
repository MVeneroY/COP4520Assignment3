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
Proof of Efficiency:
Experimental Evaluation: (no print statements)

50 gifts            9ms
500 gifts           42ms
5,000 gifts         214ms
50,000 gifts        6867ms
500,000 gifts       Time

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