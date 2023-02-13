/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.Arrays;

/**
 *
 * @author trs
 * 
 * Class Memory is the class that the memory objects implement.
 */
public class Memory {
    
    private int[][] memory_value;
    
    public Memory(){
        // Size of memory is 2048 words by 16 bits per word
        this.memory_value = new int[2048][16];
    }
    
     /* 
    Function to get a value stored in Memory    
    IN: Int representing the row the value is stored in
    OUT: Int array with the stored value
    */
    public int[] getMemoryValue(int row){
        // Return 16 bit value stored at given row
       return Arrays.copyOfRange(this.memory_value[row],0,16);
    }
    
     /* 
    Function to set a value in Memory
    IN: Int representing the row to store the value, Int array of the value
    OUT: N/A
    */
    public void setMemoryValue(int row, int[] value){
        System.arraycopy(value, 0, this.memory_value[row], 0, 16);
    }
    
}
