import socket
import sys

HOST = '192.168.1.20'
PORT = 9000

def listen():
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    print("socket created")

    try:
        s.bind((HOST, PORT))
        print("Socket Bind Success!")
    except socket.error as err:
        #print("Bind Failed, Error Code: " + str(err[0]) + ", Message: " + str(err[1]))
        sys.exit()

    s.listen(10)
    print("Socket is now listening")

    while(1):
        conn, addr = s.accept()
        print("Connect with " + addr[0] + ":" + str(addr[1]))
        buf = conn.recv(1024).decode()
        print(buf)
        if(buf == "terminate"):
            s.close()
def main():
    listen()

if __name__ == '__main__':
    main()
