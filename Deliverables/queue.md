# Bounded Queue

## Queue-do_wait

| Transistion | Main | Thread-1 | Thread-2 |
| --- | --- | --- | --- |
| 0-3 | Initialization of Queue class with data object and producer/consumer indexes. Initiate a Worker class that creates and spin off threads. |  |  |
| 4-5 |  | Producer that adds data to the queue until it is full. Then notifyAll() to wake up waiting threads to remove data. |  |
| 6 |  | Consumer and calls waitForData to block until enough elements are available in the queue though it is still full. Then calls wait to release lock and goes to sleep.  |  |
| 7 |  |  | Producer that calls wait and blocks until space is available in the queue. |

## Queue-notify

| Transistion | Main | Thread-1 | Thread-2 | Thread-3 | Thread-4 |
| --- | --- | --- | --- | --- | --- |
| 0-44 | Initialization of Queue class with data object and producer/consumer indexes. Initiate Producer and Consumer classes that creates and spin off threads. |  |  |  |  |
| 45 | | Producer that tries add data to the queue but it has no lock. |  |  |  |
| 46-51 |  |  | Consumer that takes lock and tries to remove data from queue, call waitForData to block until enough elements are available in the queue. Calls wait to release lock and goes to sleep. |  |  |
| 52-53 |  |  |  | Producer that tries to add data to queue but waits on lock. |  |
| 54-56 |  |  |  |  | Consumer tries to removes data from the queue then calls waitForData to block until enough elements are available in the queue. Calls wait to release lock and goes to sleep. |
| 57-59 |  | Producer that adds data to the queue until it is full then call notify to wake up one waiting thread. |  |  |  |
| 60 |  |  |  | Producer that tries to add data to the queue but it is full so call wait to go to sleep and wait for consumer. |  |

## QueueTest
In this simpler version in queue-notify the do-while loop is changed to a while loop which means that the check on (c + len > p) will always have to be true before a thread calls on wait instead of at least once in the do-while. Meaning that the consumer always have to wait for producer to add data and as the threads exeucute in the order put() then remove() the wait will never be reached.
Also in this simpler version there is only a notify signal that will be received by the other thread that is waiting and not a notifyAll.


