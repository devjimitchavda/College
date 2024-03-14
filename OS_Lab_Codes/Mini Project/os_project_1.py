import sys
from PyQt5.QtWidgets import QApplication, QWidget, QLabel, QLineEdit, QPushButton, QVBoxLayout, QTableWidget, QTableWidgetItem

class Process:
    def __init__(self, name, burst_time, arrival_time, priority):
        self.name = name
        self.burst_time = burst_time
        self.remaining_time = burst_time
        self.arrival_time = arrival_time
        self.priority = priority
        self.finish_time = 0
        self.turnaround_time = 0
        self.waiting_time = 0

class Scheduler(QWidget):
    def __init__(self):
        super().__init__()

        self.processes = []

        self.initUI()

    def initUI(self):
        self.setWindowTitle('Preemptive Priority Scheduling')
        layout = QVBoxLayout()

        self.process_name_label = QLabel('Process Name:')
        self.process_name_input = QLineEdit()
        self.burst_time_label = QLabel('Burst Time:')
        self.burst_time_input = QLineEdit()
        self.arrival_time_label = QLabel('Arrival Time:')
        self.arrival_time_input = QLineEdit()
        self.priority_label = QLabel('Priority (0-10):')
        self.priority_input = QLineEdit()

        add_button = QPushButton('Add Process')
        add_button.clicked.connect(self.add_process)

        self.output_button = QPushButton('Show Output')
        self.output_button.clicked.connect(self.calculate_schedule)

        layout.addWidget(self.process_name_label)
        layout.addWidget(self.process_name_input)
        layout.addWidget(self.burst_time_label)
        layout.addWidget(self.burst_time_input)
        layout.addWidget(self.arrival_time_label)
        layout.addWidget(self.arrival_time_input)
        layout.addWidget(self.priority_label)
        layout.addWidget(self.priority_input)
        layout.addWidget(add_button)
        layout.addWidget(self.output_button)

        self.result_table = QTableWidget()
        self.result_table.setColumnCount(8)
        self.result_table.setHorizontalHeaderLabels(["Process Name", "Process ID", "Burst Time", "Arrival Time", "Priority", "Finish Time", "Turnaround Time", "Waiting Time"])
        layout.addWidget(self.result_table)

        self.setLayout(layout)

    def add_process(self):
        name = self.process_name_input.text()
        burst_time = int(self.burst_time_input.text())
        arrival_time = int(self.arrival_time_input.text())
        priority = int(self.priority_input.text())

        process = Process(name, burst_time, arrival_time, priority)
        self.processes.append(process)

        self.update_table_row(len(self.processes) - 1)

    def calculate_schedule(self):
        self.processes.sort(key=lambda x: (x.arrival_time, x.priority))

        current_time = 0
        total_waiting_time = 0
        total_turnaround_time = 0

        self.result_table.setRowCount(len(self.processes))

        for i, process in enumerate(self.processes):
            if process.arrival_time > current_time:
                current_time = process.arrival_time

            min_priority = 11
            min_index = -1

            for j in range(i + 1):
                if self.processes[j].arrival_time <= current_time and self.processes[j].remaining_time > 0:
                    if self.processes[j].priority < min_priority:
                        min_priority = self.processes[j].priority
                        min_index = j

            current_time += 1
            self.processes[min_index].remaining_time -= 1

            if self.processes[min_index].remaining_time == 0:
                self.processes[min_index].finish_time = current_time
                self.processes[min_index].turnaround_time = self.processes[min_index].finish_time - self.processes[min_index].arrival_time
                self.processes[min_index].waiting_time = self.processes[min_index].turnaround_time - self.processes[min_index].burst_time
                total_waiting_time += self.processes[min_index].waiting_time
                total_turnaround_time += self.processes[min_index].turnaround_time

            self.update_table_row(i)

        avg_waiting_time = total_waiting_time / len(self.processes)
        avg_turnaround_time = total_turnaround_time / len(self.processes)

        self.result_table.setHorizontalHeaderLabels(["Process Name", "Process ID", "Burst Time", "Arrival Time", "Priority", "Finish Time", "Turnaround Time", "Waiting Time"])
        self.result_table.setItem(len(self.processes), 6, QTableWidgetItem(f'{avg_turnaround_time:.2f}'))
        self.result_table.setItem(len(self.processes), 7, QTableWidgetItem(f'{avg_waiting_time:.2f}'))

    def update_table_row(self, row):
        process = self.processes[row]
        self.result_table.setItem(row, 0, QTableWidgetItem(process.name))
        self.result_table.setItem(row, 1, QTableWidgetItem(str(row + 1)))
        self.result_table.setItem(row, 2, QTableWidgetItem(str(process.burst_time)))
        self.result_table.setItem(row, 3, QTableWidgetItem(str(process.arrival_time)))
        self.result_table.setItem(row, 4, QTableWidgetItem(str(process.priority)))

if __name__ == '__main__':
    app = QApplication(sys.argv)
    scheduler = Scheduler()
    scheduler.show()
    sys.exit(app.exec_())
