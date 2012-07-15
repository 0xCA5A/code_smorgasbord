#!/bin/bash

# General Vue on a Module
pyreverse -ASmy -k -o pdf $(ls *.py) -p general_vue

# Detailed Vue on a Module
# pyreverse -c module_one -a1 -s1 -f ALL -o pdf $(ls *.py)

#default
pyreverse -o pdf -p default $(ls *.py)

#default but with all attributes
pyreverse -o pdf -p default_all_attributes -f ALL $(ls *.py)

