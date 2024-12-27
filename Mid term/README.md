Step to run this Remote Shell RPC
1. Run the start.sh (should see server.class and client.class being created)
2. Run 'java Server' to start the server
3. Run 'java Client' to start the client (You can run multiple client at the same time)
4. Default password is set as "GuessMothers" 
5. Test some terminal commands (ex: ls, pwd, ...), some command will not work due to security requiring the actual terminal

Some other notable information:
- Max client is set to 10 (changable in line 13 in the server.java)
- Password is changable at line 9 in server.java
- Can work remotely but limited to the same network. (Can work through IP such as google free IP 8.8.8.8 but not recommended)