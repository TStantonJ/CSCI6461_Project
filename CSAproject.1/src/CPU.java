/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author trs
 */
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
        }else{
            X3.setRegisterValue(value);
        }
    }
}
