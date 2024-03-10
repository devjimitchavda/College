# 5) Write a script that retrieves and displays information about the network,
# including the hostname, IP address, and a list of network interfaces.

#!/bin/bash

# Function to retrieve network information
network_info() {
    echo "Hostname:"
    hostname
    echo "IP Address:"
    hostname -I
    echo "Network Interfaces:"
    ifconfig -a | grep -o '^[a-zA-Z0-9]*'
}

# Main script
network_info
