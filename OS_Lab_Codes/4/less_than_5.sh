#!/bin/bash

if [ "$#" -le 5 ]; then
    echo "The number of command line arguments is less than or equal to 5."
else
    echo "The number of command line arguments is greater than 5."
fi
