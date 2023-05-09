# 2.2.1 : Create trace summary

| Transistion | Server main #0 | Client-1 #1 | Client-2 #2 | Finalizers #3-5 | Worker-1 #6 | Worker-2 #7 |
|---|---|---|---|---|---|---|
|0|init, call accept| | | | | |
|1| |init, connect| | | | |
|2|finish accept, start worker| | | | | |
|3-4|call accept| | | | | |
|5| |write, call readLine| | | | |
|6| | |init, connect| | | |
|7|accept| | | | | |
|8|start worker| | | | | |
|9|close port| | | | | |
|10|shut down| | | | | |
|11-12| | | |[no source]| | |
|13-18| | | | |register worker| |
|19-21| | | | |readLine| |
|22-29| | | | |sendAll| |
|30| |close outputstream| | | | |
|31| | | |[no source]| | |
|32| | |sends data and recieves data| | | |
|33-35| | | | |IOException| |
|36-45| | | | | |register worker|
|46-50| | | | |remove worker, sendAll| |
|51| | | | | |check workers array|
|52| | | | |check workers array| |
|53-54| | | | | |workers array is null, set it to this|
|55| | | | |check workers array| |


# 2.2.2 : Describe what went wrong

## Key 1 : Transistion 0-10
The chat server initiliazes with new socket to listen in on and creates a Worker array to accept maximum "maxServ" connections. Two worker threads are started to server the clients making the connections. There are two clients that makes a connection to the server and server keeps the count with the variable "n" that is incremented for each connection. Server than closes the connections and shuts down.

## Key 2 : Transition 13-29
The server has created a new thread (Thread 1) which starts its main loop (run()). The tread adds itself to the workers array which contains information relating to client 1 which connected. Thread 1 checks the outputstream to see if the client has sent any messages. If so it will create an new InputStream from BufferedReader so that it can transmitt all the information the client has sent to other connected clients. Client 1 has sent 5 lines and Thread 1 will read all those lines and execute sendAll for respective lines. Client 1 closes it's outputstream.

## Key 3 : Transition 30-32 
Client 1 then recieves 3 of the messages. Thread 2 creates input and output stream object to transmit data and writes its ID and "Hello World!" to the stream then flushes it. Reads data from the inputstream and prints it to the screen.  

## Key 4 : Transition 33-35
Worker 1 tries to send data to all clients but fails and catches IOEXception then the server tries to remove worker 1 thread that caused the IOException. The IOException occurs because client 1 has closed the outputstream, and as such the pipe is no longer active when Worker 1 tries to read from the stream.

## Key 5 : Transition 36-45
Worker 2 starts executing and creates a new outputstream object then checks that the stream is active and that the worker array of itself is not set to null.

## Key 6 : Transition 46-47
Worker 1 is removed from the server and the worker array of its position is set to null. Then prints to all clients that it has quit.

## Key 7: Transistion 48-55
Worker 1 is going through the sendAll process and currently iterating over the workers array. All the while Worker 2 is initializing and also checking the workers array. This causes a data race where depending on of Worker 2 get to check it's index first, it will set the cell to a new worker which results in that Worker 1 will send information to the client connected. If Worker 1 reaches it first then data will not be sent to the client connected. The data race occurs when Worker 2 tries to set workers[index] to "this", whilst Worker 1 is checking the workers array.


# 2.2.3 : Patch the issue
The issue occurs because of a datarace between the worker threads. Both threads try to access the same array at the same time. As such, we need to block access to the array workers so that only one thread may access it at a time. To do this, we simply create a new Object called "lock" that is a field in the ChatServer class. Then, anywhere where the program tries to access the array workers, we will syncronize this with the new "lock" object. This restricts only one thread from accessing any part of the array workers. Detailed implementation can be seen in the .java file found in this same directory.