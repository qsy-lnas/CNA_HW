# -*- coding: utf-8 -*-
"""
Created on Mon Oct 11 15:46:09 2021

@author: hsc
"""

from socket import *
serverPort = 12000
serverSocket = socket(AF_INET,SOCK_STREAM)
serverSocket.bind(('',serverPort))
serverSocket.listen(1)
print('The server is ready to receive')
i = 0
while True:
     connectionSocket, addr = serverSocket.accept()
     if connectionSocket != None:
          while True:
               sentence = connectionSocket.recv(1024).decode()
               print('From client[%d]:'%i, sentence)
               capitalizedSentence = sentence.upper()
               connectionSocket.send(capitalizedSentence.encode())
               if sentence == 'quit': 
                    connectionSocket.close()
                    print('Client[%d] disconnected'%i)
                    i += 1
                    break
     connectionSocket.close()