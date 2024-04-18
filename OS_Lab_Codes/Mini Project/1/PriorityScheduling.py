import heapq

# Definition of Process class
class Process:
    def __init__(self, processID, arrivalTime, priority, burstTime):
        self.processID = processID
        self.arrivalTime = arrivalTime
        self.priority = priority
        self.burstTime = burstTime
        self.tempburstTime = burstTime
        self.responsetime = -1
        self.outtime = 0
        self.intime = -1

# Function to insert a process into the heap
def insert(Heap, value, currentTime):
    value.intime = currentTime[0]
    heapq.heappush(Heap, (value.priority, value))

# Function to extract the process with the highest priority from the heap
def extract_minimum(Heap, currentTime):
    _, min_process = heapq.heappop(Heap)
    if min_process.responsetime == -1:
        min_process.responsetime = currentTime[0] - min_process.arrivalTime
    return min_process

# Function responsible for executing the highest priority process extracted from the heap
def scheduling(Heap, array, n, currentTime):
    if not Heap:
        return

    min_process = extract_minimum(Heap, currentTime)
    min_process.outtime = currentTime[0] + 1
    print(f"Process ID = {min_process.processID}, Current Time = {currentTime[0]}")

    # If the process is not yet finished, insert it back into the Heap
    if min_process.burstTime > 0:
        insert(Heap, min_process, currentTime)
        return

    for i in range(n):
        if array[i].processID == min_process.processID:
            array[i] = min_process
            break

# Function responsible for managing the entire execution of processes based on arrival time
def priority(array, n):
    array.sort(key=lambda x: x.arrivalTime)
    total_waiting_time = 0
    total_turnaround_time = 0
    inserted_process = 0
    heap = []
    currentTime = [array[0].arrivalTime]
    total_response_time = 0

    # Calculating the total burst time of the processes
    total_burst_time = sum(process.burstTime for process in array)

    # Inserting the processes into Heap according to arrival time
    while True:
        if inserted_process != n:
            for i in range(n):
                if array[i].arrivalTime == currentTime[0]:
                    inserted_process += 1
                    array[i].intime = -1
                    array[i].responsetime = -1
                    insert(heap, array[i], currentTime)

        scheduling(heap, array, n, currentTime)
        currentTime[0] += 1
        if not heap and inserted_process == n:
            break

    for process in array:
        total_response_time += process.responsetime
        total_waiting_time += (process.outtime - process.intime - process.tempburstTime)
        total_turnaround_time += (process.outtime - process.intime)
        total_burst_time += process.burstTime

    print(f"Average waiting time = {total_waiting_time / n}")
    print(f"Average response time = {total_response_time / n}")
    print(f"Average turn around time = {total_turnaround_time / n}")

# Driver code
if __name__ == "__main__":
    n = int(input("Enter the number of processes: "))
    processes = []
    for i in range(n):
        print(f"Enter details for process {i + 1}:")
        processID = int(input("Process ID: "))
        arrivalTime = int(input("Arrival Time: "))
        priority = int(input("Priority: "))
        burstTime = int(input("Burst Time: "))
        processes.append(Process(processID, arrivalTime, priority, burstTime))
    priority(processes, n)
