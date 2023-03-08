
import java.util.Arrays;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author shahin
 */
public class Cache extends Memory {
    
    private int[][] cache_value;
    
    public Cache(){
        // Size of Cache is 2048 words by 16 bits per word
        this.cache_value = new int[2][16];
    }
    
     /* 
    Function to get a value stored in Cache    
    IN: Int representing the row the value is stored in
    OUT: Int array with the stored value
    */
    public int[] getCacheValue(int row){
        // Return 16 bit value stored at given row
       return Arrays.copyOfRange(this.cache_value[row],0,16);
    }
    
     /* 
    Function to set a value in cache
    IN: Int representing the row to store the value, Int array of the value
    OUT: N/A
    */
    public void setCacheValue(int row, int[] value){
        System.arraycopy(value, 0, this.cache_value[row], 0, 16);
    }
    
}

