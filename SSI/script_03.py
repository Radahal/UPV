#!/usr/bin/python

import sys

fileName = sys.argv[1]
file = open(fileName, "r")
lines = file.readlines()
for line in lines:
	splitted=line.split(':')
	print(splitted[0],":")
	size = len(splitted)-1
	for index in range(size):
		print("\t", splitted[index+1])