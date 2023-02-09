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
   
   // Getter method for value of register
   public int[] getRegisterValue(){
       return register_value;
   }
   
   // Getter method for size of register
   public int getRegisterSize(){
       return register_size;
   }
   
   
   // Setter method for value of register
   public void setRegisterValue(int[] new_value){
       if (this.register_size == 0){
           System.out.print("Register size zero error");
       }
       else{
           this.register_value = new_value;
       }
      
   }
   
   // Initalizer/Setter method for size of register
   public void initRegisterSize(int register_size){
       this.register_size = register_size;
       this.register_value = new int[register_size];
       
   }
}

