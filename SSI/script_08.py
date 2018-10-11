#!/usr/bin/python

import sys, urllib2
from bs4 import BeautifulSoup

url=sys.argv[1]

outputName="script_08_results.txt"


file = open(outputName,'w')

html = urllib2.urlopen(url).read()
soup = BeautifulSoup(html, 'html.parser')
[s.extract() for s in soup(['style', 'script', '[document]', 'head', 'title'])]
visible_text = soup.getText()

text = visible_text.encode('ascii', 'ignore')
text = text.split(" ")
text = sorted(list(set(text)));
out = ""
for string in text:
	if string.isalpha():
		out += string + " "
		file.write(string+"\n")
file.close()
#print(out)
