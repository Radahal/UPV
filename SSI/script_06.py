#!/usr/bin/python

import sys

rotation=int(sys.argv[1])
fileName=sys.argv[2]
outputName = fileName.split(".")[0]+"_ciphered.txt"

file = open(fileName, "r")
lines = file.readlines()
file.close()

file = open(outputName,"w")

for line in lines:
	newLine = ""
	for character in line:
		if character:
			val = ord(character)
			val+=rotation
			val=val%128
			charac = chr(val)
			#if (val<30):
				#print (val)
			newLine+=charac
	file.write(newLine)

file.write('\n')
file.close()
