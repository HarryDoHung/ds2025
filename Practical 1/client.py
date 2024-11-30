import socket

HOST = '127.0.0.1' 
PORT = 1234         
BUFFER_SIZE = 1024  
OUTPUT_FILE = 'received_file.txt'

def start_client():
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client_socket.connect((HOST, PORT))
    print(f"Connected to server at {HOST}:{PORT}")

    with open(OUTPUT_FILE, 'wb') as file:
        while True:
            data = client_socket.recv(BUFFER_SIZE)
            if not data:  
                break
            file.write(data)  

    print(f"File received and saved as {OUTPUT_FILE}")
    client_socket.close()

if __name__ == "__main__":
    start_client()
