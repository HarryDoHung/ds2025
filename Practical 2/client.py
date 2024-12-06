import xmlrpc.client

HOST = '127.0.0.1'
PORT = 1234
OUTPUT_FILE = 'received_file.txt'

server = xmlrpc.client.ServerProxy(f"http://{HOST}:{PORT}")
print(f"Connected to RPC server at {HOST}:{PORT}")

with open(OUTPUT_FILE, 'wb') as file:
    for chunk in server.send_file():
        file.write(chunk)

print("File received and saved successfully.")