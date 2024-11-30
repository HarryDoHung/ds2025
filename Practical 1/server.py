import socket

HOST = '127.0.0.1'  
PORT = 1234         
BUFFER_SIZE = 1024  
FILE_TO_SEND = 'testing_file.txt'

def start_server():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind((HOST, PORT))
    server_socket.listen(1) 
    print(f"Server listening on {HOST}:{PORT}")

    conn, addr = server_socket.accept()
    print(f"Connection established with {addr}")

    try:
        with open(FILE_TO_SEND, 'rb') as file:
            while (data := file.read(BUFFER_SIZE)):
                conn.sendall(data) 
    except FileNotFoundError:
        print(f"Error: {FILE_TO_SEND} not found.")
    finally:
        print("File transfer complete.")
        conn.close()
        server_socket.close()

if __name__ == "__main__":
    start_server()
