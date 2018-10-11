#!/usr/bin/python

import sys

for index in range(len(sys.argv[1:])):
	print index,": =>",sys.argv[index+1]
