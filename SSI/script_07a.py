#!/usr/bin/python

import string, cgi, time
from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
from urllib2 import URLError, HTTPError, urlopen

englishDict = {"hola": "hello", "adios": "bye", "donde": "where", "bien": "good"}

def proxy():

	class my_server(BaseHTTPRequestHandler):
		def do_GET(self):
			print (self.path)
			self.send_response(200)
			self.send_header("Content-type", "abcd" )
			self.end_headers()
			self.wfile.write(englishDict[self.path[1:]])
			return 0

		def log_message(self, format, *args):
			return

	server=HTTPServer(("",8081),my_server)
	server.serve_forever()

proxy()
