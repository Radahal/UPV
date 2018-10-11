#!/usr/bin/python

import sys
import optparse

parser = optparse.OptionParser()
parser.add_option('-a', action="store", dest="a")
parser.add_option('-b', action="store", dest="b")
parser.add_option('-d', action="store", dest="d")
parser.add_option('-e', action="store", dest="e")
parser.add_option('-f', action="store", dest="f")
parser.add_option('-r', action="store", dest="r")
(options,args) = parser.parse_args(sys.argv[1:])

if options.a:
	print "-a => ", options.a
if options.b:
	print "-b => ", options.b
if options.d:
	print "-d => ", options.d
if options.e:
	print "-e => ", options.e
if options.f:
	print "-f => ", options.f
if options.r:
	print "-r => ", options.r
