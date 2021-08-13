# FuzzyKSQL
Data Agnostic library which allows to extend the default KSQL language with fuzzy operations.

# Environment details

* Tested System: Linux Ubuntu 20.04 LTS (but any other system supporting docker would work)
* Necessary software: Java (1.8+), Docker

# Description of the repository

* FuzzyProject - the main project, where the data-agnostic library for KSQL was implemented. The main project of this repository. The generated JAR file should be added to the KSQL Server on which the queries are about to be ran.

* AGVProducer - data producer. Simulator which generates 2 independent streams of data. The data payloads are based on the Autonomously Guided Vehicles - since some of the parameters may seem odd.

* Script files - files concerning the environment setup as well as the list of tested queries.

