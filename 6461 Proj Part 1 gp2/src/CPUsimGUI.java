import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import static javax.swing.BorderFactory.createLineBorder;

//GUI and its various components and buttons
public class CPUsimGUI extends JFrame {
    private JPanel mainPanel;
    private JButton runButton;
    private JButton resetButton;
    private JButton loadButton;
    private JButton exitButton;
    private JPanel buttonPanel;
    private JPanel registerValue;
    private JTextPane Input;
    private JPanel inputPanel;
    private JPanel show;
    private JLabel PC;
    private JLabel MAR;
    private JLabel MBR;
    private JLabel IR;
    private JTextField pcVal;
    private JLabel gpr0;
    private JLabel gpr1;
    private JLabel gpr2;
    private JLabel gpr3;
    private JTextField marVal;
    private JTextField mbrVal;
    private JTextField irVal;
    private JTextField gpr0Val;
    private JTextField gpr1Val;
    private JTextField gpr2Val;
    private JTextField gpr3Val;
    private JTextField ixr1Val;
    private JTextField ixr2Val;
    private JTextField ixr3Val;
    private JLabel ixr1;
    private JLabel ixr2;
    private JLabel ixr3;
    private JLabel mfr;
    private JTextField mfrVal;
    private JTextPane output;
    private JPanel memPanel;
    private JScrollPane memPanScroll;
    private JTable usedMem;
    private JTextField input;

    Instruction data = new Instruction();
    //GUI buttons and their actions
    public CPUsimGUI(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        //set the text fields uneditable
        gpr0Val.setEditable(false);
        gpr1Val.setEditable(false);
        gpr2Val.setEditable(false);
        gpr3Val.setEditable(false);
        ixr1Val.setEditable(false);
        ixr2Val.setEditable(false);
        ixr3Val.setEditable(false);
        marVal.setEditable(false);
        mbrVal.setEditable(false);
        irVal.setEditable(false);
        mfrVal.setEditable(false);
        pcVal.setEditable(false);


        //background colors
        gpr0Val.setBackground(Color.decode("#D6FAFF"));
        gpr1Val.setBackground(Color.decode("#D6FAFF"));
        gpr2Val.setBackground(Color.decode("#D6FAFF"));
        gpr3Val.setBackground(Color.decode("#D6FAFF"));
        ixr1Val.setBackground(Color.decode("#D6FAFF"));
        ixr2Val.setBackground(Color.decode("#D6FAFF"));
        ixr3Val.setBackground(Color.decode("#D6FAFF"));
        marVal.setBackground(Color.decode("#D6FAFF"));
        mbrVal.setBackground(Color.decode("#D6FAFF"));
        irVal.setBackground(Color.decode("#D6FAFF"));
        mfrVal.setBackground(Color.decode("#D6FAFF"));
        pcVal.setBackground(Color.decode("#D6FAFF"));


        //show.setBorder(createLoweredBevelBorder());
        show.setBorder(createLineBorder(new Color(255,255,255),20));
        registerValue.setAlignmentX(20);

        Input.setSize(new Dimension(400,20));

        //set output uneditable by users
        output.setEditable(false);

        setMemoryPanel();
        usedMem.setVisible(true);

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String instruction = Input.getText();
                int i = 0;
                if (!instruction.equals("")) {
                    String[] Instruction = instruction.split("\n");
                    for (; i < Instruction.length; i++) {
                        Input.setText("");
                        for (int j = 0; j < Instruction.length; j++) {
                            if (j == i) {
                                appendToPane(Input, Instruction[j], Color.red);
                                StyleContext sc = StyleContext.getDefaultStyleContext();
                                AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.black);
                                aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Andale Mono");
                                aset = sc.addAttribute(aset, StyleConstants.FontSize, 12);
                                int len = Input.getDocument().getLength();
                                Input.setCaretPosition(len);
                                Input.setCharacterAttributes(aset, false);
                                Input.replaceSelection("");
                            } else {
                                appendToPane(Input, Instruction[j], Color.black);
                            }
                        }

                    String[] instructionArray = Instruction[i].split(" ");
                    boolean result = data.setInstruction(instructionArray);
                    if (result) {
                        refreshInterface();
                    } else {
                        break;
                    }
                    }
                    if (i == Instruction.length) {
                        Input.setText("");
                        JOptionPane.showMessageDialog(mainPanel, "Program Finished!", "Message", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "Line " + (i) + " Wrong!", "Message", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });



        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                data.MAR.value = 0;
                data.MBR.value = 0;
                data.IR.value = 0;
                data.PC.value = 0;
                data.IXR1.value = 0;
                data.IXR2.value = 0;
                data.IXR3.value = 0;
                data.GPR0.value = 0;
                data.GPR1.value = 0;
                data.GPR2.value = 0;
                data.GPR3.value = 0;
                for(int i=0;i<2048;i++){
                    data.simulator_memory.memoryArray[i][1] = "0000,0000,0000,0000";
                }
                refreshInterface();
            }
        });

        exitButton.addActionListener(new ActionListener() { //Exit button
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //load from file button action
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == loadButton) {
                    JFileChooser loadFile = new JFileChooser();
                    int resp = loadFile.showOpenDialog(null);
                    if (resp == JFileChooser.APPROVE_OPTION) {
                        //load hex data and turn it into binary then store it into memory
                        File file = loadFile.getSelectedFile();
                        BufferedReader readFile = null;
                        try {
                            readFile = new BufferedReader(new FileReader(file));
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        String inputHexData;
                            try {
                                while (((inputHexData = readFile.readLine()) != null)) {
                                    String[] binaryData = null;
                                    binaryData = inputHexData.split(" ");
                                    int address = Integer.valueOf(binaryData[0], 16);
                                    String value = Integer.toBinaryString(Integer.parseInt(binaryData[1],16));
                                    data.simulator_memory.putMem(address,value);
                                    setMemoryPanel();
                                    usedMem.setVisible(true);
                                    output.setText(output.getText()+"\n"+address + ": " + value );
                                }
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
            }
        });
    }

    //Main
    public static void main(String[] args) {

        JFrame frame = new CPUsimGUI("CPU Simulator");
        frame.setVisible(true);
    }

    // Code for components for GUI button text area
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private void addToInput(JTextPane textPane, String message, Color color){
        message = message+"\n";
        int length = textPane.getDocument().getLength();
        textPane.setCaretPosition(length);
        textPane.replaceSelection(message);
    }

    public void refreshInterface() {
        ixr1Val.setText(data.intToString(16, data.IXR1.value));
        ixr2Val.setText(data.intToString(16, data.IXR2.value));
        ixr3Val.setText(data.intToString(16, data.IXR3.value));
        marVal.setText(data.intToString(12, data.MAR.value));
        mbrVal.setText(data.intToString(16, data.MBR.value));
        irVal.setText(data.intToString(16, data.IR.value));
        pcVal.setText(data.intToString(16, data.PC.value));
        gpr0Val.setText(data.intToString(16, data.GPR0.value));
        gpr1Val.setText(data.intToString(16, data.GPR1.value));
        gpr2Val.setText(data.intToString(16, data.GPR2.value));
        gpr3Val.setText(data.intToString(16, data.GPR3.value));
        setMemoryPanel();
    }
    //set the content of memory panel
    public void setMemoryPanel() {
        final String[] columnNames = {"Address", "Value"};
        TableModel dataModel = new DefaultTableModel(data.simulator_memory.memoryArray, columnNames);
        usedMem.setModel(dataModel);
        System.out.println(data.simulator_memory.toString());
    }
    /*
Set text in JTextArea line by line.
 */
    private void appendToPane(JTextPane tp, String msg, Color c)
    {
        msg = msg+"\n";
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Andale Mono");
        aset = sc.addAttribute(aset,StyleConstants.FontSize,12);
        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }

}
