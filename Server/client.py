import socket
import sys
import cv2 as cv

HOST = '192.168.1.20'
PORT = 9000

def send(input):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind((HOST,PORT))
    s.connect((HOST,PORT))
    s.sendto(input.encode(),(HOST,PORT))
    s.close()


def main():
    send("handshake")
    while 1:
        send(input(">"))

if __name__ == '__main__':
    main()
