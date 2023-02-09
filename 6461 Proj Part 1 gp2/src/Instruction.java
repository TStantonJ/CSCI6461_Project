import static com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary.stringToInt;

//class for instructions load and store
public class Instruction {
    public Register PC = new Register(12,0);
    public Register MAR = new Register(12,0);
    public Register MBR = new Register(12,0);
    public Register IR = new Register(12,0);
    public Register MFR = new Register(12,0);
    public Register GPR0 = new Register(12,0);
    public Register GPR1 = new Register(12,0);
    public Register GPR2 = new Register(12,0);
    public Register GPR3 = new Register(12,0);
    public Register IXR1 = new Register(12,0);
    public Register IXR2 = new Register(12,0);
    public Register IXR3 = new Register(12,0);
    public Memory simulator_memory = new Memory();

    /*
       Pass a register number and value. The function will set the register value automatically.
        */
    public void setGRegVal(int regNum, int value){
        if (regNum == 0){
            GPR0.value = value;
        }
        else if(regNum==1){
            GPR1.value = value;
        }
        else if(regNum==2){
            GPR2.value = value;
        }
        else{
            GPR3.value = value;
        }
    }

    /*
    Pass a register number and value. The function will return the register value automatically.
     */
    public int getGRegVal(int gRegNum){
        if (gRegNum == 0){
            return GPR0.value;
        }
        else if(gRegNum == 1){
            return GPR1.value;
        }
        else if(gRegNum == 2){
            return GPR2.value;
        }
        else{
            return GPR3.value;
        }
    }

    /*
    Pass an index register number and value. The function will set the index register value automatically.
     */
    public void setIRegVal(int indexRegNum, int value){
        if (indexRegNum == 0){
        }
        else if(indexRegNum == 1){
            IXR1.value = value;
        }
        else if(indexRegNum == 2){
            IXR2.value = value;
        }
        else if(indexRegNum == 3){
            IXR3.value = value;
        }
    }

    /*
    Pass a index register number and value. The function will set the index register value automatically.
     */
    public int getIRegVal(int indexRegNum){
        if (indexRegNum == 0){
            return 0;
        }
        else if(indexRegNum == 1){
            return IXR1.value;
        }
        else if(indexRegNum == 2){
            return IXR2.value;
        }
        else{
            return IXR3.value;
        }
    }

    /*
    Call the function for exact instruction based on the input
    */
    public boolean setInstruction(String[] instruction){
        if(instruction[0].equals("LDR") || instruction[0].equals("ldr")){
            return LDR(instruction[1]);
        }
        else if(instruction[0].equals("STR") || instruction[0].equals("str")){
            STR(instruction[1]);
            return true;
        } else{
            return false;
        }
    }

    /*
    The function to process LDR instruction.
     */
    public boolean LDR(String instructionData){
        String[] instruction = instructionData.split(",");
        if(instruction.length == 4){
            if(!checkInstruction(Integer.parseInt(instruction[0]),Integer.parseInt(instruction[1]),5,Integer.parseInt(instruction[2]))){
                return false;
            }
            int RealAddress = realAddress(instruction[1], instruction[2]);
            if(RealAddress < 6){
                return false;
            }
            derectLDR(Integer.parseInt(instruction[0]),0,RealAddress);
            return true;
        }
        else if(instruction.length == 3){
            if(!checkInstruction(Integer.parseInt(instruction[0]),Integer.parseInt(instruction[1]),5,Integer.parseInt(instruction[2]))){
                return false;
            }
            derectLDR(Integer.parseInt(instruction[0]),Integer.parseInt(instruction[1]),Integer.parseInt(instruction[2]));
            return true;
        }
        else{
            return false;
        }
    }

    /*
        The function to process STR instruction.
         */
    public boolean STR(String instructionData){
        String[] instruction = instructionData.split(",");
        if(instruction.length==4){
            if(!checkInstruction(Integer.parseInt(instruction[0]),Integer.parseInt(instruction[1]),5,Integer.parseInt(instruction[2]))){
                return false;
            }
            int RealAdress = realAddress(instruction[1],instruction[2]);
            if(RealAdress < 6){
                return false;
            }
            simulator_memory.memoryArray[RealAdress][1] = intToString(16,getGRegVal(Integer.parseInt(instruction[0])));
            return true;
        }
        else if(instruction.length==3){
            if(!checkInstruction(Integer.parseInt(instruction[0]),Integer.parseInt(instruction[1]),5,Integer.parseInt(instruction[2]))){
                return false;
            }
            simulator_memory.memoryArray[Integer.parseInt(instruction[2])][1] = intToString(16,getGRegVal(Integer.parseInt(instruction[0])));
            return true;
        }
        else{
            return false;
        }
    }

    /*
    This function will return false if the instruction is illegal.
     */
    public boolean checkInstruction(int rRegister,int iRegister,int addressBit,int address){
        if(rRegister > 3 || rRegister < 0){
            return false;
        }
        if(iRegister > 3 || iRegister<0){
            return false;
        }
        if(address < 6 || address >= Math.pow(2, addressBit)){
            return false;
        }
        return true;
    }

    /*
    This function can return real address based on the input instruction.
     */
    public int realAddress(String iRegister, String address){
        return parseInt((String)simulator_memory.memoryArray[Integer.parseInt(address) + getIRegVal(Integer.parseInt(iRegister))][1]);
    }

    public void derectLDR(int registerNumber, int iRegister, int address){
//        setGRegVal(registerNumber,stringToInt((String)simulator_memory.memoryArray[address + getIRegVal(iRegister)][1]));
        setGRegVal(registerNumber, (address + getIRegVal(iRegister)));
    }

    /*
    make data more user friendly.
     */
    public String intToString(int bit,int value){
        String string = Integer.toBinaryString(value);
        StringBuilder builder = new StringBuilder(string);
        if(string.length()<bit){
            for(int i=0; i < bit-string.length();i++){
                builder.insert(0,'0');
            }
        }
        else{
            return "Error";
        }
        for(int i = 0;i<(bit/4)-1;i++){
            builder.insert(4+i*4+i,',');
        }
        return builder.toString();
    }
}
