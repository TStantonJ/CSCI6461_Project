/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author trs
 */

public class Register {
    
   // Private vars representing the value and size in the Register
   private int register_value[];
   private int register_size = 0;
   
   // Constructor
   public Register(int size){
       initRegisterSize(size);
   }
   
    /* 
    Function to get the value of this register
    IN: N/A
    OUT: Int array representing the value stored in this array
    */
   public int[] getRegisterValue(){
       return register_value;
   }
   
    /* 
    Function to get the size of this register
    IN: N/A
    OUT: Int representing the size of this register
    */
   public int getRegisterSize(){
       return register_size;
   }
   
   
    /* 
    Function to set value of this register
    IN: Int array representing the value to change the register to
    OUT: N/A
    */
   public void setRegisterValue(int[] new_value){
       if (this.register_size == 0){
           System.out.print("Register size zero error");
       }
       else{
           this.register_value = new_value;
       }
      
   }
   
    /* 
    Function to set the size of this register
    IN: Int representing the size of the register
    OUT: N/A
    */
   public void initRegisterSize(int register_size){
       this.register_size = register_size;
       this.register_value = new int[register_size];
       
   }
}

