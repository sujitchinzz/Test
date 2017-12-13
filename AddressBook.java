/***********************************************
 * Purpose :This program add,delete,edit records in address book​
 *           
 * @author  Sujit Chincholkar
 * @version 1.0
 * @since   24/08/2017          
 ***********************************************/
package com.bridgelabz.programs;

import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.bridgelabz.utility.Utility;


public class AddressBook {
	static Scanner scanner=new Scanner(System.in);
	public static void main(String[] args) {
		AddressBook book=new AddressBook();
		String file="/home/bridgeit/Documents/AddressBook";
		book.display(Utility.readJSONArray(file));
		char ans='y';
		while(ans=='y'||ans=='Y'){
		System.out.println("Enter your choice \n1.Add \n2.Edit \n3.Delete \n4.Sort By Name "
				+ "\n5.Search By Name");
		int choice=scanner.nextInt();
		switch(choice){
		case 1:
			book.addRecord(file);
			break;
		case 2:
			book.editRecord(file);
			break;
		case 3:
			book.deleteRecord(file);
			break;
		case 4:
			book.sortByName(file);
			break;
		case 5:
			book.search(file);
			break;
		}
		System.out.println("Do you want to Continue[Y/N]");
		ans=scanner.next().charAt(0);
	}
	}
	/**This method search record from file by first name
	 * @param file
	 */
	public void search(String file) {
		System.out.println("Enter name to search");
		String valueToSearch=scanner.next();
		JSONArray addressList=Utility.readJSONArray(file);
		int presentAtIndex=Utility.isPresent(addressList,"firstname",valueToSearch);
		if((presentAtIndex)>=0){
			System.out.println("Record is present");
			JSONObject record=(JSONObject) addressList.get(presentAtIndex);
			Utility.displayJson(record);
		}else{
			System.out.println("Record is not present");
		}
		
	}
	/**This method prints data in sorted order read from file.
	 * @param file
	 */
	public  void sortByName(String file) {
		JSONArray bookRecords=Utility.readJSONArray(file);
		JSONObject temporary;
		int size=bookRecords.size()-1;
		for(int i=0;i<size;i++){
			JSONObject record=(JSONObject) bookRecords.get(i);
			JSONObject record2=(JSONObject) bookRecords.get(i+1);
			if(String.valueOf(record.get("firstname")).
					compareTo(String.valueOf(record2.get("firstname")))>0){
				temporary=(JSONObject) bookRecords.get(i);
				bookRecords.remove(i);
				bookRecords.add(i, record2);
				bookRecords.remove(i+1);
				bookRecords.add(i+1, temporary);
			}
		}
		display(bookRecords);
	}
	/**This method read data and delete specified record from file.
	 * @param file
	 */
	public void deleteRecord(String file) {
		JSONArray bookRecords=Utility.readJSONArray(file);
		System.out.println("Enter First name to delete record");
		String name=scanner.next();
		for(int i=0;i<bookRecords.size();i++){
			JSONObject record=(JSONObject) bookRecords.get(i);
			if(record.get("firstname").equals(name)){
				bookRecords.remove(i);
				System.out.println("Record Deleted");
				Utility.writeJSONFile(file, bookRecords);
				break;
			}
		}
	}
	public void display(JSONArray bookRecords) {
		for(int i=0;i<bookRecords.size();i++){
			JSONObject record=(JSONObject) bookRecords.get(i);
			System.out.println("First Name ="+record.get("firstname"));
			System.out.println("Last Name ="+record.get("laststname"));
			System.out.println("Address ="+record.get("address"));
			System.out.println("City ="+record.get("city"));
			System.out.println("City ="+record.get("state"));
			System.out.println("Zip ="+record.get("zip"));
			System.out.println("Phone ="+record.get("phone"));
			System.out.println("============================");
		}
	}
	public void editRecord(String file) {
		JSONArray bookRecords=Utility.readJSONArray(file);
		String choice[]={"firstname","laststname","address","city","state","zip","phone"};
		System.out.println("Enter first name of record which you wants to edit");
		String name=scanner.next();
		System.out.println("What do you want to edit \n1.FirstName\n2.LastName\n3.Address\n4.City"
				+ "\n5.state\n6.Zip\n7.Phone Number");
		int choiceIndex=scanner.nextInt();
		System.out.println("Enter "+choice[choiceIndex-1]);
		String value=scanner.next();
		for(int i=0;i<bookRecords.size();i++){
			JSONObject record=(JSONObject) bookRecords.get(i);
			if(String.valueOf(record.get("firstname")).equals(name)){
				((JSONObject) bookRecords.get(i)).put(choice[choiceIndex-1],value);
				Utility.writeJSONFile(file,bookRecords);
				System.out.println("Record updated");
				display(bookRecords);
				break;
			}
		}
	}
	public void addRecord(String file) {
		JSONObject record=new JSONObject();
		JSONArray bookRecords=Utility.readJSONArray(file);
		System.out.println("Enter First Name");
		String firstName=scanner.next();
		System.out.println("Enter Last Name");
		String lastName=scanner.next();
		System.out.println("Enter Address");
		scanner.nextLine();
		String address=scanner.nextLine();
		System.out.println("Enter  City,state and Zip");
		String city=scanner.nextLine();
		String State=scanner.next();
		String zip=scanner.next();
		System.out.println("Enter phone number");
		String phoneNo=scanner.next();
		record.put("firstname", firstName);
		record.put("laststname", lastName);
		record.put("address", address);
		record.put("city", city);
		record.put("state", State);
		record.put("zip", zip);
		record.put("phone",phoneNo);
		bookRecords.add(record);
		Utility.writeJSONFile(file,bookRecords);
		System.out.println("Record Added");
	}
	
}
