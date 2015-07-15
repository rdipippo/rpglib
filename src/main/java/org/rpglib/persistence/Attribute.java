package org.rpglib.persistence;

public class Attribute {
   String name;
   String value;

   public Attribute(final String name, final String value){
       this.name = name;
       this.value = value;
   }
   
   public Attribute() {
       //
   }

   public String getValue() {
	   return this.value;
   }

   public void setValue(final String value) {
	   this.value = value;
   }

   public String getName() {
	   return this.name;
   }

   public void setName(final String name) {
	   this.name = name;
   }
}
