import sys
from PyQt5.QtWidgets import QApplication, QWidget, QLabel, QLineEdit, QPushButton, QVBoxLayout, QTableWidget, QTableWidgetItem, QTextEdit

class Process:
    def __init__(self, name, pid, priority, arrival_time, burst_time):
        self.name = name
        self.pid = pid
        self.priority = priority
        self.arrival_time = arrival_time
        self.burst_time = burst_time
        self.remaining_time = burst_time

class SchedulerApp(QWidget):
    def __init__(self):
        super().__init__()
        self.processes = []  # List to store the processes
        self.job_queue = []  # List to store the job queue
        self.init_ui()  # Initialize the user interface
        
    def init_ui(self):
        self.setWindowTitle('Preemptive Scheduling')  # Set window title
        layout = QVBoxLayout()  # Create vertical layout

        # Labels and input fields for process details
        self.name_label = QLabel("Process Name:")
        self.name_entry = QLineEdit()
        layout.addWidget(self.name_label)
        layout.addWidget(self.name_entry)

        self.id_label = QLabel("Process ID:")
        self.id_entry = QLineEdit()
        layout.addWidget(self.id_label)
        layout.addWidget(self.id_entry)

        self.priority_label = QLabel("Priority:")
        self.priority_entry = QLineEdit()
        layout.addWidget(self.priority_label)
        layout.addWidget(self.priority_entry)

        self.arrival_label = QLabel("Arrival Time:")
        self.arrival_entry = QLineEdit()
        layout.addWidget(self.arrival_label)
        layout.addWidget(self.arrival_entry)

        self.burst_label = QLabel("Burst Time:")
        self.burst_entry = QLineEdit()
        layout.addWidget(self.burst_label)
        layout.addWidget(self.burst_entry)

        # Button to add process
        self.add_button = QPushButton("Add Process")
        self.add_button.clicked.connect(self.add_process)
        layout.addWidget(self.add_button)

        # Button to sort processes
        self.sort_button = QPushButton("Sort Processes")
        self.sort_button.clicked.connect(self.sort_processes)
        layout.addWidget(self.sort_button)

        # Button to show job queue
        self.show_queue_button = QPushButton("Show Job Queue")
        self.show_queue_button.clicked.connect(self.show_job_queue)
        layout.addWidget(self.show_queue_button)

        # Table to display processes
        self.process_table = QTableWidget()
        self.process_table.setColumnCount(7)
        self.process_table.setHorizontalHeaderLabels(["Name", "ID", "Priority", "Arrival Time", "Burst Time", "Completion Time", "Waiting Time"])
        layout.addWidget(self.process_table)

        # Text edit to display job queue
        self.job_queue_text = QTextEdit()
        layout.addWidget(self.job_queue_text)

        self.setLayout(layout)

    def add_process(self):
        # Get process details from input fields
        name = self.name_entry.text()
        pid = int(self.id_entry.text())
        priority = int(self.priority_entry.text())
        arrival_time = int(self.arrival_entry.text())
        burst_time = int(self.burst_entry.text())
        
        # Create Process object and append to processes list
        process = Process(name, pid, priority, arrival_time, burst_time)
        self.processes.append(process)
        
        # Clear input fields
        self.name_entry.clear()
        self.id_entry.clear()
        self.priority_entry.clear()
        self.arrival_entry.clear()
        self.burst_entry.clear()
        
    def sort_processes(self):
        # Sort processes based on priority and arrival time
        self.processes.sort(key=lambda x: (x.priority, x.arrival_time))
        current_time = 0
        for process in self.processes:
            # Calculate completion time and waiting time
            completion_time = min(current_time + process.remaining_time, current_time + process.burst_time)
            waiting_time = current_time - process.arrival_time
            if waiting_time < 0:
                waiting_time = 0  # Set waiting time to 0 if negative
            # Insert process details into process table
            self.process_table.insertRow(self.process_table.rowCount())
            self.process_table.setItem(self.process_table.rowCount() - 1, 0, QTableWidgetItem(process.name))
            self.process_table.setItem(self.process_table.rowCount() - 1, 1, QTableWidgetItem(str(process.pid)))
            self.process_table.setItem(self.process_table.rowCount() - 1, 2, QTableWidgetItem(str(process.priority)))
            self.process_table.setItem(self.process_table.rowCount() - 1, 3, QTableWidgetItem(str(process.arrival_time)))
            self.process_table.setItem(self.process_table.rowCount() - 1, 4, QTableWidgetItem(str(process.burst_time)))
            self.process_table.setItem(self.process_table.rowCount() - 1, 5, QTableWidgetItem(str(completion_time)))
            self.process_table.setItem(self.process_table.rowCount() - 1, 6, QTableWidgetItem(str(waiting_time)))
            current_time = completion_time

    def show_job_queue(self):
        # Clear previous job queue
        self.job_queue_text.clear()
        # Initialize current time
        current_time = 0
        # Copy the list of processes
        remaining_processes = list(self.processes)
        # Iterate over remaining processes until all are processed
        while remaining_processes:
            # Find the next process to execute based on arrival time and priority
            next_process = min(remaining_processes, key=lambda x: (x.arrival_time, x.priority))
            # Remove the selected process from the list
            remaining_processes.remove(next_process)
            # Calculate the completion time for the process
            completion_time = min(current_time + next_process.remaining_time, current_time + next_process.burst_time)
            # Append the process details to the job queue
            self.job_queue.append(f"{next_process.name} -> {current_time} - {completion_time}")
            # Update the current time
            current_time = completion_time
        # Display the job queue in the text edit
        self.job_queue_text.setPlainText("\n".join(self.job_queue))

def main():
    app = QApplication(sys.argv)
    scheduler_app = SchedulerApp()
    scheduler_app.show()
    sys.exit(app.exec_())

if __name__ == '__main__':
    main()
        

"""
test cases:
Process Name: P1, Process ID: 1, Priority: 2, Arrival Time: 0, Burst Time: 5
Process Name: P2, Process ID: 2, Priority: 1, Arrival Time: 1, Burst Time: 4
Process Name: P3, Process ID: 3, Priority: 3, Arrival Time: 2, Burst Time: 3
Process Name: P4, Process ID: 4, Priority: 2, Arrival Time: 3, Burst Time: 2
Process Name: P5, Process ID: 5, Priority: 1, Arrival Time: 4, Burst Time: 1
"""