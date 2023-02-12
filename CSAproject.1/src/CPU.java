/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author trs
 */
import java.io.*;
import java.util.Scanner;
import java.util.Arrays;

public class CPU {
    
    Register PC = new Register(12);
    Register CC = new Register(4);
    Register IR = new Register(16);
    Register MAR = new Register(12);
    Register MBR = new Register(16);
    Register MFR = new Register(4);
    Register X1 = new Register(16);
    Register X2 = new Register(16);
    Register X3 = new Register(16);
    Register GPR0 = new Register(16);
    Register GPR1 = new Register(16);
    Register GPR2 = new Register(16);
    Register GPR3 = new Register(16);
    Register HLT = new Register(1);
    
    Memory main_Memory = new Memory();
    

    
     /* 
    Function to
    IN: 
    OUT: 
    */
    public CPU(){
        
    }
    
    /* 
    Function to execute a Single Step or Run
    IN: String reprenting a step or run
    OUT: N/A
    */
    public void execute(String type){
        
        if ("single".equals(type)){
            // Use PC to get instruction location and add it IR
            int[] instruction_address = getRegisterValue("PC");
            int converted_address = binaryToInt(instruction_address);
            setRegisterValue("IR",getMemoryValue(converted_address)); 
            
            // Increment PC 
            int[] cur_PC = getRegisterValue("PC");
            int trans_PC = binaryToInt(cur_PC);
            trans_PC = trans_PC+1;
            int[] new_PC = intToBinaryArray(Integer.toBinaryString(trans_PC));
            setRegisterValue("PC", new_PC);
            
            //System.out.println(Arrays.toString(instruction_address));
            //System.out.println(converted_address);
            
            
            // Read and decode instruction
            int[] instructionBinary = getMemoryValue(converted_address);
            int[] OPcode = Arrays.copyOfRange(instructionBinary, 0, 6);
            String instruction = decodeOPCode(OPcode);
            
            // Execute instruction
            if ("LDR".equals(instruction)){     //Load instruction
                
                int[] result = computeEffectiveAddr(instructionBinary);
                int EA = result[0];
                int I= result[1];
                int R= result[2];
                int IX= result[2];
                
                // Set MAR to location in memory to fetch
                int Addr= result[4];
                setRegisterValue("MAR", intToBinaryArray(Integer.toBinaryString(EA)));
                
                switch(R) {
                    case 0:
                        GPR0.setRegisterValue(getMemoryValue(EA));
                        break;
                    case 1:
                        GPR1.setRegisterValue(getMemoryValue(EA));
                        break;
                    case 2:
                        GPR2.setRegisterValue(getMemoryValue(EA));
                        break; 
                    default:
                        GPR3.setRegisterValue(getMemoryValue(EA));
                        System.out.println(converted_address);
                 }
                
                // Set MBR to value just fetched
                setRegisterValue("MBR", getMemoryValue(EA));
                
            }else if("STR".equals(instruction)){
                
                int[] result = computeEffectiveAddr(instructionBinary);
                int EA = result[0];
                int I= result[1];
                int R= result[2];
                int IX= result[2];
                int Addr= result[4];
                
                switch(R) {
                    case 0:
                        // Set MBR to the word to be stored in memory
                        setRegisterValue("MBR", GPR0.getRegisterValue());
                        // Store value from MBR
                        setMemoryValue(EA,MBR.getRegisterValue());
                        break;
                    case 1:
                        // Set MBR to the word to be stored in memory
                        setRegisterValue("MBR", GPR1.getRegisterValue());
                        // Store value from MBR
                        setMemoryValue(EA,MBR.getRegisterValue());
                        break;
                    case 2:
                        // Set MBR to the word to be stored in memory
                        setRegisterValue("MBR", GPR2.getRegisterValue());
                        // Store value from MBR
                        setMemoryValue(EA,MBR.getRegisterValue());
                        break; 
                    default:
                        // Set MBR to the word to be stored in memory
                        setRegisterValue("MBR", GPR3.getRegisterValue());
                        // Store value from MBR
                        setMemoryValue(EA,MBR.getRegisterValue());
                 }
            }else if("LDA".equals(instruction)){
                int[] result = computeEffectiveAddr(instructionBinary);
                int EA = result[0];
                int I= result[1];
                int R= result[2];
                int IX= result[2];
                int Addr= result[4];
                
                int[] converted_value = intToBinaryArray(Integer.toBinaryString(EA));
                switch(R) {
                    case 0:
                        GPR0.setRegisterValue(converted_value);
                        break;
                    case 1:
                        GPR1.setRegisterValue(converted_value);
                        break;
                    case 2:
                        GPR2.setRegisterValue(converted_value);
                        break; 
                    default:
                        GPR3.setRegisterValue(converted_value);
                        System.out.println(Arrays.toString(converted_value));
                        System.out.println(binaryToInt(converted_value));
                 }
            }else if("LDX".equals(instruction)){
                int[] result = computeEffectiveAddr(instructionBinary);
                int EA = result[0];
                int I= result[1];
                int R= result[2];
                int IX= result[2];
                int Addr= result[4];
                switch(IX) {
                    case 1:
                        X1.setRegisterValue(getMemoryValue(EA));
                        break;
                    case 2:
                        X2.setRegisterValue(getMemoryValue(EA));
                        break;
                    case 3:
                        X2.setRegisterValue(getMemoryValue(EA));
                        break;
                    default:
                        // raise issue
                 }
            }else if("STX".equals(instruction)){
                int[] result = computeEffectiveAddr(instructionBinary);
                int EA = result[0];
                int I= result[1];
                int R= result[2];
                int IX= result[2];
                int Addr= result[4];
                switch(IX) {
                    case 1:
                        // Set MBR to the word to be stored in memory
                        setRegisterValue("MBR", X1.getRegisterValue());
                        // Store value from MBR
                        setMemoryValue(EA,MBR.getRegisterValue());
                        break;
                    case 2:
                        // Set MBR to the word to be stored in memory
                        setRegisterValue("MBR", X2.getRegisterValue());
                        // Store value from MBR
                        setMemoryValue(EA,MBR.getRegisterValue());
                        break;
                    case 3:
                        // Set MBR to the word to be stored in memory
                        setRegisterValue("MBR", X3.getRegisterValue());
                        // Store value from MBR
                        setMemoryValue(EA,MBR.getRegisterValue());
                        break; 
                    default:
                        // Raise issue
                }
            }else if("HLT".equals(instruction)){
                int [] msg = new int[]{0};
                HLT.setRegisterValue(msg);
            
            }
        }
    }
    
    /* 
    Function to compute the EA of an instruction
    IN: Int array representing the instruction to be decoded
    OUT: Int array containing the EA, I, R, IX, and Address
    */
    public int[] computeEffectiveAddr(int[] instruction){
        // Format data
        String strInstruction = Arrays.toString(instruction);
        strInstruction = strInstruction.replace("[", "");
        strInstruction = strInstruction.replace("]", "");
        strInstruction = strInstruction.replace(",", "");
        strInstruction = strInstruction.replace(" ", "");
        
        // Calculate I
        int I;
        if (strInstruction.charAt(10) == '0'){
            I = 0;
        }else{
            I = 1;
        }
        System.out.print("I is");
        System.out.println(I);
        
        // Calculate R
        int R;
        if(strInstruction.charAt(6) == '0' && strInstruction.charAt(7) == '0'){
            R = 0;
        }else if(strInstruction.charAt(6) == '0' && strInstruction.charAt(7) == '1'){
            R = 1;
        }
        else if(strInstruction.charAt(6) == '1' && strInstruction.charAt(7) == '0'){
            R = 2;
        }else{
            R = 3;
        }
        System.out.print("R is");
        System.out.println(R);
        
        // Calculate IX
        int IX;
        if(strInstruction.charAt(8) == '0' && strInstruction.charAt(9) == '0'){ 
            IX = 0;
        }else if(strInstruction.charAt(8) == '0' && strInstruction.charAt(9) == '1'){
            IX = 1;
        }
        else if(strInstruction.charAt(8) == '1' && strInstruction.charAt(9) == '0'){
            IX = 2;
        }else{
            IX = 3;
        }
        System.out.print("IX is");
        System.out.println(IX);
        
        // Calculate Address Field
        int[] Addr_Field = Arrays.copyOfRange(instruction, 11, 16);
        System.out.print("ADDR is");
        System.out.println(binaryToInt(Addr_Field));
        
        // Calculate EA using I,R,IX, and Address Field
        int EA;
        int[] tmp_var;
        if (I == 0){
            if (IX == 0){   // c(Address Field)
                System.out.print("Direct load");
                // EA is just Address Field
                System.out.print("test is");
                EA = binaryToInt(Addr_Field);
                
            }else{          // c(Address Field) + c(IX)
                System.out.print("Register plus addr");
                // Get Value in Ix Register
                int IX_value;
                if (IX == 1){
                    IX_value = binaryToInt(getRegisterValue("X1"));
                }else if (IX == 2){
                    IX_value = binaryToInt(getRegisterValue("X2"));
                }else{
                    IX_value = binaryToInt(getRegisterValue("X2"));
                }
                EA = binaryToInt(Addr_Field) + IX_value;
                
            }
        }else{
            if (IX == 0){   // c(c(Address Field))
                System.out.print("pointer");
                // Get memory index from Address Field
                int tmp_var2 = binaryToInt(Addr_Field);
                
                tmp_var = getMemoryValue(tmp_var2);
                System.out.println(Arrays.toString(tmp_var));
                
                // Get address from earleir address
                EA = binaryToInt(tmp_var);
                
            }else{          // c(c(IX) + c(Address Field))
                System.out.print("Weird");
                // Get Value in Ix Register
                int IX_value;
                if (IX == 1){
                    IX_value = binaryToInt(getRegisterValue("X1"));
                }else if (IX == 2){
                    IX_value = binaryToInt(getRegisterValue("X2"));
                }else{
                    IX_value = binaryToInt(getRegisterValue("X2"));
                }
                
                // Get value of Address Field
                int tmp_var2 = binaryToInt(Addr_Field);
                
                // Add the two together to get memory location of EA
                tmp_var2 = tmp_var2 + IX_value;
                EA = binaryToInt(getMemoryValue(tmp_var2));
                
            }
        }
        System.out.print("EA is");
        System.out.println(EA);
        
        int ret[] = {EA,I,R,IX, binaryToInt(Addr_Field)};
        return ret;
        
    }
    
     /* 
    Function to decode a operation instruction
    IN: int array representing the opcode
    OUT: String value of the translated op code
    */
    public String decodeOPCode(int[] binary_OPCode){
        String ret_val;
        // Convert int array to string
        String opCode = Arrays.toString(binary_OPCode);
        opCode = opCode.replace("[", "");
        opCode = opCode.replace("]", "");
        opCode = opCode.replace(",", "");
        opCode = opCode.replace(" ", "");
        
        if ("000001".equals(opCode)){
            ret_val = "LDR";
            
        }else if("000010".equals(opCode)){
            ret_val = "STR";
        }else if("000011".equals(opCode)){
            ret_val = "LDA";
        }else if("101001".equals(opCode)){
            ret_val = "LDX";
        }else if("101010".equals(opCode)){
            ret_val = "STX";
        }else if("001010".equals(opCode)){
            ret_val = "JZ";
        }else if("001011".equals(opCode)){
            ret_val = "JNE";
        }else if("001100".equals(opCode)){
            ret_val = "JCC";
        }else if("001101".equals(opCode)){
            ret_val = "JMA";
        }else if("001110".equals(opCode)){
            ret_val = "JSR";
        }else if("001111".equals(opCode)){
            ret_val = "RFS";
        }else if("010000".equals(opCode)){
            ret_val = "SOB";
        }else if("010001".equals(opCode)){
            ret_val = "JGE";
        }else if("000100".equals(opCode)){
            ret_val = "AMR";
        }else if("000101".equals(opCode)){
            ret_val = "STR";
        }else if("000110".equals(opCode)){
            ret_val = "STR";
        }else if("000111".equals(opCode)){
            ret_val = "STR";
        }else if("010100".equals(opCode)){
            ret_val = "MLT";
        }else if("010101".equals(opCode)){
            ret_val = "DVD";
        }else if("010110".equals(opCode)){
            ret_val = "TRR";
        }else if("010111".equals(opCode)){
            ret_val = "AND";
        }else if("011000".equals(opCode)){
            ret_val = "ORR";
        }else if("000000".equals(opCode)){
            ret_val = "HLT";
        }else{
            ret_val = "NOT";
        }
        return ret_val;
    }
    
     /* 
    Function to get values from a register
    IN: String representing the register to get value from
    OUT: Int array representing the value in that register
    */
    public int[] getRegisterValue(String register){
        int[] ret_value;
        if ("PC".equals(register)){
            ret_value = PC.getRegisterValue();
        }else if("CC".equals(register)){
            ret_value = CC.getRegisterValue();
        }else if("IR".equals(register)){
            ret_value = IR.getRegisterValue();
        }else if("MAR".equals(register)){
            ret_value = MAR.getRegisterValue();
        }else if("MBR".equals(register)){
            ret_value = MBR.getRegisterValue();
        }else if("MFR".equals(register)){
            ret_value = MFR.getRegisterValue();
        }else if("X1".equals(register)){
            ret_value = X1.getRegisterValue();
        }else if("X2".equals(register)){
            ret_value = X2.getRegisterValue();
        }else if("GPR0".equals(register)){
            ret_value = GPR0.getRegisterValue();
        }else if("GPR1".equals(register)){
            ret_value = GPR1.getRegisterValue();
        }else if("GPR2".equals(register)){
            ret_value = GPR2.getRegisterValue();
        }else{
            ret_value = GPR3.getRegisterValue();
        }
        return ret_value;
    }
    
     /* 
    Function to set value in a register
    IN: String value representing the register to change and int[] representing the value
    OUT: N/A
    */
    public void setRegisterValue(String register, int[] value){
        if ("PC".equals(register)){
            PC.setRegisterValue(value);
        }else if("CC".equals(register)){
            CC.setRegisterValue(value);
        }else if("IR".equals(register)){
            IR.setRegisterValue(value);
        }else if("MAR".equals(register)){
            MAR.setRegisterValue(value);
        }else if("MBR".equals(register)){
            MBR.setRegisterValue(value);
        }else if("MFR".equals(register)){
            MFR.setRegisterValue(value);
        }else if("X1".equals(register)){
            X1.setRegisterValue(value);
        }else if("X2".equals(register)){
            X2.setRegisterValue(value);
        }else if("X3".equals(register)){
            X3.setRegisterValue(value);
        }else if("GPR0".equals(register)){
            GPR0.setRegisterValue(value);
        }else if("GPR1".equals(register)){
            GPR1.setRegisterValue(value);
        }else if("GPR2".equals(register)){
            GPR2.setRegisterValue(value);
        }else{
            GPR3.setRegisterValue(value);
        } 
    }
    
     /* 
    Function to get a value from memory
    IN: Int representing the row from which to get the value
    OUT: Int array with the contents of that row
    */
    public int[] getMemoryValue(int row){
        return main_Memory.getMemoryValue(row);
    }
    
     /* 
    Function to set a value in memory
    IN: Int with the row to set and Int array with the value to set
    OUT: N/A
    */
    public void setMemoryValue(int row, int[] value){
        if (row < 6){
            int[] fault_code = new int[]{0,0,0,1};
            MFR.setRegisterValue(fault_code);
        }else{
            main_Memory.setMemoryValue(row, value);
        }
        
    }
    
     /* 
    Function to load the IPL.txt file into memeory
    IN: N/A
    OUT: N/A
    */
    public void loadFileIntoMemory() throws FileNotFoundException, IOException{
        //File file = new File("src/IPL.txt");
        //Scanner sc = new Scanner(file);
        FileInputStream fstream = new FileInputStream("src/IPL.txt");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        
        
        String strLine;
        while ((strLine = br.readLine()) != null)   {
            String[] tokens = strLine.split(" ");
            // Get address and load into MAR
            int row = hexToInt(tokens[0]);
            int[] row_binary = hexToBinaryArray(tokens[0]);
            setRegisterValue("MAR",row_binary);
            
            // Get value and load into MBR
            int[] value = hexToBinaryArray(tokens[1]);
            setRegisterValue("MBR",value);

            if (row == 0){
                setMemoryValue(row,value);
            }else{
                setMemoryValue(row,value);
            }
            //System.out.println(Arrays.toString(hexToBinaryArray(tokens[1])));
            //System.out.println(hexToInt(tokens[0]));
        }
    }
    
     /* 
    Function to convert a hex value to int
    IN: String representing the hex value
    OUT: Int of the converted value
    */
    public int hexToInt(String hex){
        int ret_val = Integer.parseInt(hex,16);
        return ret_val;
    }
    
     /* 
    Function to convert a binary value to hex
    IN: Int array containing the binary value
    OUT: Int of the converted value
    */
    public int binaryToInt(int[] binary){
        int ret_val;
        String binary_holder = Arrays.toString(binary);
        binary_holder = binary_holder.replace("[", "");
        binary_holder = binary_holder.replace("]", "");
        binary_holder = binary_holder.replace(",", "");
        binary_holder = binary_holder.replace(" ", "");
        ret_val = Integer.parseInt(binary_holder, 2);
        return ret_val;
    }
    
     /* 
    Function to convert a hex value to binary value
    IN: String containing the hex value to be converted
    OUT: Int array containing the converted value
    */
    public int[] hexToBinaryArray(String hex){
        int[] ret_val = new int[16];
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("A", "1010");
        hex = hex.replaceAll("B", "1011");
        hex = hex.replaceAll("C", "1100");
        hex = hex.replaceAll("D", "1101");
        hex = hex.replaceAll("E", "1110");
        hex = hex.replaceAll("F", "1111");
        
        char[] arr = hex.toCharArray();
       
        
        for (int i = 0; i < hex.length(); i++) {
            ret_val[i] = Character.getNumericValue(hex.charAt(i));
        }
        return ret_val;
    }
    
     /* 
    Function to convert a binary int value to binary array value
    IN: String containing the int value to be converted
    OUT: Int array containing the converted value
    */
    public int[] intToBinaryArray(String int_value){
        int[] ret_val = new int[16];
        
        char[] arr = int_value.toCharArray();
       
        
        for (int i = 0; i < 16; i++) {
            if (i < 16 - arr.length){
                ret_val[i] = 0;
            }else{
                ret_val[i] = Character.getNumericValue(int_value.charAt(i-(16 - arr.length)));
            }
            
        }
        return ret_val;
    }
}
