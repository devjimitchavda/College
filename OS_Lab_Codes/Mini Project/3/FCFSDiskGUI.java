import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FCFSDiskGUI extends JFrame {
    private JTextField tfSize;
    private JTextArea taInputArray;
    private JTextField tfInitialPosition;
    private JTextArea taOutput;
    
    public FCFSDiskGUI() {
        setTitle("FCFS Disk Scheduling GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));
        
        JLabel lblSize = new JLabel("Size of array:");
        tfSize = new JTextField();
        JLabel lblInputArray = new JLabel("Disk request array:");
        taInputArray = new JTextArea();
        taInputArray.setLineWrap(true);
        JLabel lblInitialPosition = new JLabel("Initial position of the head:");
        tfInitialPosition = new JTextField();
        
        inputPanel.add(lblSize);
        inputPanel.add(tfSize);
        inputPanel.add(lblInputArray);
        inputPanel.add(new JScrollPane(taInputArray));
        inputPanel.add(lblInitialPosition);
        inputPanel.add(tfInitialPosition);
        
        JButton btnCalculate = new JButton("Calculate");
        btnCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateFCFS();
            }
        });
        
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(btnCalculate, BorderLayout.CENTER);
        
        taOutput = new JTextArea();
        taOutput.setEditable(false);
        mainPanel.add(new JScrollPane(taOutput), BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void calculateFCFS() {
        try {
            int size = Integer.parseInt(tfSize.getText());
            String[] inputStrings = taInputArray.getText().split("\\s+");
            int[] arr = new int[size];
            for (int i = 0; i < size; i++) {
                arr[i] = Integer.parseInt(inputStrings[i]);
            }
            int head = Integer.parseInt(tfInitialPosition.getText());
            
            StringBuilder output = new StringBuilder();
            output.append("Total number of seek operations = ").append(calculateTotalSeekOperations(arr, head)).append("\n");
            output.append("Seek Sequence is:\n");
            for (int i : arr) {
                output.append(i).append("\n");
            }
            taOutput.setText(output.toString());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Array size and number of elements in the array don't match.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private int calculateTotalSeekOperations(int[] arr, int head) {
        int finalCount = 0;
        int distance, currentTrack;
        for (int i = 0; i < arr.length; i++) {
            currentTrack = arr[i];
            distance = Math.abs(currentTrack - head);
            finalCount += distance;
            head = currentTrack;
        }
        return finalCount;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FCFSDiskGUI().setVisible(true);
            }
        });
    }
}