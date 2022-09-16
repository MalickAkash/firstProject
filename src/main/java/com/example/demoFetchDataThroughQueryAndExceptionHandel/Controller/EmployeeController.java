package com.example.demoFetchDataThroughQueryAndExceptionHandel.Controller;

import com.example.demoFetchDataThroughQueryAndExceptionHandel.Entity.EmployeeEntity;
import com.example.demoFetchDataThroughQueryAndExceptionHandel.Entity.Response;
import com.example.demoFetchDataThroughQueryAndExceptionHandel.Exception.UserException;
import com.example.demoFetchDataThroughQueryAndExceptionHandel.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mainPath")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    //new security login part add
    @GetMapping("/login")
    private String home()
    {
        return ("<h1>WELCOME</h1>");

    }
    // Using Exception Handling
    @PostMapping("/storeData")
    public Response saveData(@RequestBody EmployeeEntity employee){
        Response response=new Response();
        try{
            if(employee.getEmpFirstName().isEmpty() || employee.getEmpLastName().isEmpty())
            {
                throw new UserException("Employee First and Last name should not be Null.");

            }
            if(employee.getEmpCity().isEmpty()||employee.getEmpCity().length()==0)
            {
                throw new UserException("Employee city should not be null.");
            }
            employeeService.saveData(employee);
            response.setStatus(HttpStatus.OK);
            response.setMessage("Employee details are saved into the DataBase.");
            response.setObject(employee);
            return response;
        }
        catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(e.toString());
            response.setObject(employee);
            return response;
        }
    }


//    @PostMapping("/storeData")
//    public Response saveData(@RequestBody EmployeeEntity employee){
//        Response response=new Response();
//        if(employee.getEmpFirstName().isEmpty() || employee.getEmpLastName().isEmpty())
//        {
//             response.setStatus(HttpStatus.BAD_REQUEST);
//            response.setMessage("Employee First and Last name should not be Null.");
//            response.setObject(employee);
//            return response;
//        }
//        employeeService.saveData(employee);
//        response.setStatus(HttpStatus.OK);
//        response.setMessage("Employee details are saved into the DataBase.");
//        response.setObject(employee);
//        return response;
//
//    }

        // Using Exception Handling

    @GetMapping("/fetchData/{empFirstName}")
    public Response show(@PathVariable("empFirstName") String empFirstName ){
        Response response=new Response();
        List<EmployeeEntity> emp=employeeService.findByFirstName(empFirstName);
    try{
        if(emp.isEmpty()){
            throw new UserException(empFirstName+" name is not exist inside DataBase.");
        }
        else {
            response.setStatus(HttpStatus.OK);
            response.setMessage(empFirstName+" name is found inside DataBase.");
            response.setObject(emp);
            return response;
        }
    }
    catch(Exception e){
            response.setStatus(HttpStatus.OK);
            response.setMessage(e.toString());
            response.setObject(emp);
            return response;
        }

    }

//    @GetMapping("/fetchData/firstName/{empFirstName}")
//    public Response show(@PathVariable("empFirstName") String empFirstName ){
//        Response response=new Response();
//        List<EmployeeEntity> emp=employeeService.findByFirstName(empFirstName);
//
//        if(emp.isEmpty()){
//            response.setStatus(HttpStatus.OK);
//
//            response.setMessage(empFirstName+" is not exist inside DataBase.");
//            response.setObject(emp);
//            return response;
//        }
//        response.setStatus(HttpStatus.OK);
//        response.setMessage(empFirstName+" This name is found inside DataBase.");
//        response.setObject(emp);
//        return response;
//    }


    // Using Exception Handling
    @GetMapping("/fetchData/age/{empAge}")
    public Response show(@PathVariable("empAge") Long empAge) throws HttpRequestMethodNotSupportedException {
        Response response=new Response();
        List<EmployeeEntity> emp=employeeService.findByAge(empAge);
        try{
            if(emp.isEmpty()){
                throw new UserException(empAge+" age is not exist inside DataBase.");
            }
            else {
                response.setStatus(HttpStatus.OK);
                response.setMessage(empAge+" age is found inside DataBase.");
                response.setObject(emp);
                return response;
            }
        }
        // here can write (Exception e) instad of (UserException) or any other unchecked Exception name
        catch(UserException e){
            response.setStatus(HttpStatus.OK);
            response.setMessage(e.toString());
            response.setObject(emp);
            return response;
        }

    }

    // Only page wise fetch data
    @GetMapping("/pagination")
    public Response findAllByPagination( @RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
                                         @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize){
        List<EmployeeEntity> empList=employeeService.findAllByPagination(pageNumber,pageSize);
        Response response=new Response();
        try{
            if(empList.isEmpty())
            {
                throw new NoSuchFieldException("No such data exist in this page.");
            }
            else
            {
                response.setStatus(HttpStatus.OK);
                response.setMessage("records are found");
                response.setObject(empList);
                return response;
            }
        }
        catch (NoSuchFieldException e)
        {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage(e.toString());
            response.setObject(empList);
            return response;
        }
    }


    //pagitation and sort useed by findAll() method
    @GetMapping("/paginationWithSort")
    public Response findByPaginationAndSort(@RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
                                         @RequestParam(value = "pageSize",defaultValue = "12",required = false) Integer pageSize,
                                         @RequestParam(value = "sortBy",defaultValue = "empId",required = false) String sortBy,
                                         @RequestParam(value = "sortDirection",defaultValue = "desc",required = false) String sortDirection){

        Response response=new Response();
        List<EmployeeEntity> empList= employeeService.findByPaginationAndSort(pageNumber,pageSize,sortBy,sortDirection);
        try{
            if(empList.isEmpty())
            {
                throw new NoSuchFieldException("No such data exist in this page.");
            }
            else {
                response.setStatus(HttpStatus.OK);
                response.setMessage("records are found");
                response.setObject(empList);
                return response;
            }
        }
        catch (NoSuchFieldException e1){
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage(e1.toString());
            response.setObject(empList);
            return response;
        }
    }
    //its very inportent logic
    @GetMapping("/paginationSortQuery")
    public Response findAllByPaginationUsingQuery(@RequestParam("limit") int limit, //page size
                                                  @RequestParam("page") int page, //page number
                                                  @RequestParam(name = "order_by_column", required = false, defaultValue = "emp_Id") String orderByColumn,
                                                  @RequestParam(name = "sort_desc", required = false, defaultValue = "false") boolean sortDesc,
                                                  @RequestParam(name = "searchKey", required = false, defaultValue = "") String searchKey){

        Map<String, String> map = new HashMap<String, String>();
        String s = map.getOrDefault(orderByColumn, orderByColumn); //for sort data asc or desc

        Sort sort = Sort.by(s);
        if (sortDesc) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        page = page <= 0 ? 1 : page;  //ternary operator
        limit = limit == 0 ? 5 : limit;  //ternary operator

        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        List<EmployeeEntity> empList=employeeService.findAllByPaginationUsingQuery(searchKey,pageRequest);
        Response response=new Response();
        try{
            if(empList.isEmpty())
            {
                throw new NoSuchFieldException("No such sorted and pageable data are exist in this page.");
            }
            else{
                response.setStatus(HttpStatus.OK);
                response.setMessage("sorted pageable emp list is found.");
                response.setObject(empList);
                return response;
            }
        }
        catch (NoSuchFieldException e)
        {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage(e.toString());
            response.setObject(empList);
            return response;
        }

    }


}