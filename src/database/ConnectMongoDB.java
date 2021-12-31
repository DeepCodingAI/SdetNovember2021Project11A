package database;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ConnectMongoDB {

    public static MongoDatabase mongoDatabase = null;

    public static MongoDatabase connectToMongoDB(String dataBaseName){
        MongoClient mongoClient = new MongoClient();
        mongoDatabase = mongoClient.getDatabase(dataBaseName);
        System.out.println("MongoDB database is connected to "+ dataBaseName);
        return mongoDatabase;
    }

    public static String insertIntoMongoDB(List<Employee> list, String dataBaseName, String collectionName){
        MongoDatabase mongoDatabase = connectToMongoDB(dataBaseName);
        for(int i=0; i<list.size(); i++){
            MongoCollection<Document> collGiven = mongoDatabase.getCollection(collectionName);
            Document document = new Document().append("empName",list.get(i).getEmpName())
                    .append("empID",list.get(i).getEmpID())
                    .append("empDOB",list.get(i).getEmpDOB())
                    .append("empSalary",list.get(i).getEmpSalary());
            collGiven.insertOne(document);
        }
        return "Employee has been registered";

    }

    public static List<Employee> readEmployeeObject(String dataBasename,String collectionName){
        List<Employee> list = new ArrayList<>();
        Employee employee = new Employee();
        MongoDatabase mongoDatabase = connectToMongoDB(dataBasename);
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
        BasicDBObject basicDBObject = new BasicDBObject();
        FindIterable<Document> iterable = collection.find(basicDBObject);
        for(Document document:iterable){
            String empName = (String)document.get("empName");
            String empID = (String)document.get("empID");
            String empDOB = (String)document.get("empDOB");
            String empSalary = (String)document.get("empSalary");
            employee = new Employee(empName,empID,empDOB,empSalary);
            list.add(employee);
            employee = new Employee();
        }
        return list;
    }

    public static void main(String[] args) {
        List<Employee> listOfEmployee = new ArrayList<>();
        listOfEmployee.add(new Employee("Martin","2190","09-29-1999","95k"));
        listOfEmployee.add(new Employee("Jonson","4580","07-25-2099","110k"));
        insertIntoMongoDB(listOfEmployee,"employee_services","profile");
        List<Employee> list = readEmployeeObject("employee_services","profile");
        for(Employee employee:list){
            System.out.println(employee.getEmpName()+ " "+employee.getEmpID()+" "+employee.getEmpDOB()+" "+employee.getEmpSalary());
        }
    }

}


