#!/usr/bin/python

import string, cgi, time
from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
from urllib2 import URLError, HTTPError, urlopen

englishDict = {"Estudiantes": "Students", "Futuros": "Future", "La Universidad": "University", "segundo": "second"}

def proxy():
	 
	class my_server(BaseHTTPRequestHandler):
		def do_GET(self):
			address="http://www.upm.es"+self.path
			url=urlopen(address)
			data=url.read()
			tipo=url.info().getheader("Content-Type")
			if (tipo.find("html") != -1):
				data=data.replace("UPV","[[[UPV]]]")
			print self.path
			self.send_response(200)
			self.send_header("Content-type", tipo )
			self.end_headers()

			for k, v in englishDict.items():
				data=data.replace(k, v)

			self.wfile.write(data)
			return 0

		def log_message(self, format, *args):
			return

	server=HTTPServer(("",8081),my_server)
	server.serve_forever()


proxy()
