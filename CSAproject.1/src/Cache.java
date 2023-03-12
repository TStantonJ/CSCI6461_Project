/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author trs
 * 
 * Class Cache impersonates main memory and stores the last 6 accessed items
 * to speed up memory operations.
 */

import java.util.Arrays;

public class Cache {

    private int[][] cache_value;
    public Memory main_Memory = new Memory(); 
    
    public Cache(){
        //
        this.cache_value = new int[6][32];
    }
    
    /* 
    Function to get a value from cache or memory. 
    Searches cache for value, if not found: then searches memory.
    IN: Int representing the row the value is stored in
    OUT: Int array with the stored value
    */
    public int[] getMemoryValue(int row_request){
        // Return 16 bit value stored at given row
        for(int i = 0; i<6 ; i++){
            int row_num = binaryToInt(Arrays.copyOfRange(this.cache_value[i],0,16));
            if(row_num == row_request){
                return Arrays.copyOfRange(this.cache_value[i],16,32);
            }
        }
        return main_Memory.getMemoryValue(row_request);
    }

    /* 
    Function to set a value in cache. Uses FIFO order to decide what gets dropped.
    IN: Int representing the row to store the value, Int array of the value
    OUT: N/A
    */
    public void setMemoryValue(int row, int[] value){
       // Since cache is write through, set value in memory firsrt
       main_Memory.setMemoryValue(row,value);
       
       int value_converted = binaryToInt(value); 
       boolean cache_hit = false;
       // Check if item is in cache already
       for(int i = 0; i<6 ; i++){
            int tmp_row_num = binaryToInt(Arrays.copyOfRange(this.cache_value[i],0,16));
            int tmp_value_num = binaryToInt(Arrays.copyOfRange(this.cache_value[i],16,32));
            
            if(tmp_row_num == row && tmp_value_num == value_converted){
                cache_hit = true;
                break;
            }
        }
       if(cache_hit != true){
           // Shuffle cache forward 
           for(int i = 5; i>0 ; i--){
                if(i!=5){
                    this.cache_value[i] = this.cache_value[i-1];
                }
            }
            // Put new item at from of cache
            int[] row_array = intToBinaryArray(row);
            int[] result = Arrays.copyOf(row_array, row_array.length + value.length);
            System.arraycopy(value, 0, result, row_array.length, value.length);
            this.cache_value[0] = result;
       }
    }
    
    /* 
    Function to convert a binary value to int
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
    Function to convert a int to binary value
    IN: Int array containing the int
    OUT: Int of the converted value
    */
    public int[] intToBinaryArray(int int_value){
        String int_value_string = Integer.toString(int_value);
        short int_int = Short.parseShort(int_value_string);
        int[] ret_val = new int[16];
        String result = Integer.toBinaryString(int_int);

        char[] arr = result.toCharArray();

        int j = arr.length-1;
        for (int i = 15; i > -1; i--) {
            if (i > 15 - arr.length){
                ret_val[i] = Character.getNumericValue(arr[j]);
                j-=1;
            }else{
                ret_val[i] = 0;
            }

        }
        return ret_val;
    }
}
