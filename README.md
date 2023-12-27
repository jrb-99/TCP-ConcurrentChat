📡 WELCOME TO MY TCP CONCURRENT CHAT!

At the 7th week of my 14-Week Intensive Full Stack Programming Bootcamp at Code For All_, we Code Cadets were challanged to create a Chat! 💬🚀

This chat should support multiple clients connected sending messages to each other, some chat commands available for the clients to use, and it should rely on the TCP protocol
(a connection between the client and the server must be established, in order to proceed with exchanging data) 🌐🤝

To build this chat, we used our freshly aquired knowledge of network protocols (TCP, in this case), Java Sockets and Server Sockets, and Java concurrent programming
(to handle each client connected to the server I opted to use a cached thread pool, and each client connected is added to a concurrent linked queue. I found this collection
a good option, since it is thread-safe, it is better suited for situations where we have multiple threads accessing it)

To use/test this chat, the server must be running (just run the ServerMain class), after that clients are able to join (run each ClientMain class available).
In the ClientMain console, just insert an user name, and you should be connected to the server and ready to send and recieve messages from other clients.

When a user join or left the chat, the server broadcast informative messages to everyone connected. 🎉🔗

To exit the server, each client has the available command "/exit"

### 🚧 WORK IN PROGRESS 🚧 ###

- 🔧 Must fix: When a client sends a message, the server broadcast it to all users connected, including the sender. A tweak is in progress to ensure the proper
work of this chat.

Join the conversation, explore the intricacies, and let your voice be heard in this TCP Concurrent Chat experiment. Your insights and feedback are welcome! 🚀💬
