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
    
    Memory main_Memory = new Memory();
    

    
    // Constructor
    public CPU(){
        // Initailize Registers in the CPU
        //int[] pc_var = {0,0,0,0,0,0,0,0,0,0,0,0};
        //PC.setRegisterValue(pc_var);
        //Register CC = new Register(4);
        //Register IR = new Register(16);
        //Register MAR = new Register(12);
        //Register MBR = new Register(16);
        //Register MFR = new Register(4);
        //Register X1 = new Register(16);
        //Register X2 = new Register(16);
        //Register X3 = new Register(16);
        
        //PC.initRegister();
        //CC.initRegister();
        //IR.initRegister();
        //MAR.initRegister();
        //MBR.initRegister();
        //MFR.initRegister();
        //X1.initRegister();
        //X2.initRegister();
        //X3.initRegister();
        
        // Initailize Memory in the CPU
        
        
        
    }
    
    // Getting method to access registers
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
    
    public int[] getMemoryValue(int row){
        return main_Memory.getMemoryValue(row);
    }
    
    public void setMemoryValue(int row, int[] value){
        main_Memory.setMemoryValue(row, value);
    }
    
    public void loadIntoMemory() throws FileNotFoundException, IOException{
        //File file = new File("src/IPL.txt");
        //Scanner sc = new Scanner(file);
        FileInputStream fstream = new FileInputStream("src/IPL.txt");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        while ((strLine = br.readLine()) != null)   {
           String[] tokens = strLine.split(" ");
           setMemoryValue(hexToInt(tokens[0]),hexToBinaryArray(tokens[1]));
           System.out.println(Arrays.toString(getMemoryValue(hexToInt(tokens[0]))));
        }
    }
    
    public int hexToInt(String hex){
        int ret_val = Integer.parseInt(hex,16);
        
        return ret_val;
    }
    
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
        
        for (int i = 0; i < hex.length(); i++) {
            ret_val[i] = hex.charAt(i);
        }
        return ret_val;
    }
}
