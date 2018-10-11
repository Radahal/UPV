#!/usr/bin/python

import sys
import optparse
import random

parser = optparse.OptionParser()
parser.add_option('-n', action="store", dest="n")
parser.add_option('-m', action="store", dest="m")
parser.add_option('-M', action="store", dest="M")

(options,args) = parser.parse_args(sys.argv[1:])

if (options.n == None or options.m == None or options.M == None):
	n = 25
	m = 0
	M = 100
else:
	n = int(options.n)
	m = int(options.m)
	M = int(options.M)

for index in range(n):
	print (random.randint(m,M))
