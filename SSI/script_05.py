#!/usr/bin/python

import sys, collections

existence = dict()

for line in sys.stdin:
	line = line[0:-1]
	if line in existence:
		existence[line]+=1
	else:
		existence[line]=1

print(existence)

file = open('statistics.txt', 'w')
#existence= collections.OrderedDict(sorted(existence.items()))
#existence= sorted(existence.items())
for key in sorted(existence):
	print key, "   => ", "*"*existence[key]
	toFile = str(key)+" "+str(existence[key])+"\r\n"
	file.write(toFile)
file.close()
