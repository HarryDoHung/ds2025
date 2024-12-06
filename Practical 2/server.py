from xmlrpc.server import SimpleXMLRPCServer

HOST = '127.0.0.1'
PORT = 1234
BUFFER_SIZE = 1024
FILE_TO_SEND = 'testing_file.txt'

def send_file():
    with open(FILE_TO_SEND, 'rb') as file:
        while (data := file.read(BUFFER_SIZE)):
            yield data

server = SimpleXMLRPCServer((HOST, PORT))
print(f"RPC Server running on {HOST}:{PORT}")

server.register_function(send_file, "send_file")
server.serve_forever()