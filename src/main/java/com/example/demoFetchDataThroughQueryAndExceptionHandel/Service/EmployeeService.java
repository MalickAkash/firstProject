package com.example.demoFetchDataThroughQueryAndExceptionHandel.Service;

import com.example.demoFetchDataThroughQueryAndExceptionHandel.Entity.EmployeeEntity;
import com.example.demoFetchDataThroughQueryAndExceptionHandel.Exception.UserException;
import com.example.demoFetchDataThroughQueryAndExceptionHandel.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public void saveData(EmployeeEntity employee) {
       employeeRepository.save(employee);
    }


    public List<EmployeeEntity> findByFirstName(String empFirstName) {
        return employeeRepository.findByFirstName(empFirstName);
    }

    //here does not write to "throws UserException" because its custome(user define) define unchecked Exception
    // throws keyword used for user define checked Exception
    public List<EmployeeEntity> findByAge(Long empAge) throws UserException {
        return employeeRepository.findByAge(empAge);
    }

    //pagination concept
    public List<EmployeeEntity> findAllByPagination(Integer pageNumber, Integer pageSize) {
      Pageable p= PageRequest.of(pageNumber,pageSize);
        Page<EmployeeEntity> pageType= employeeRepository.findAll(p);
        List<EmployeeEntity> empList= pageType.toList(); //pageType.getContent();
        return empList;
    }

    public List<EmployeeEntity> findByPaginationAndSort(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
       Sort sort=null;
       if(sortDirection.equalsIgnoreCase("desc"))
       {
           sort=Sort.by(sortBy).descending();
       }
       else{
           sort=Sort.by(sortBy).ascending();
       }
       Pageable p=PageRequest.of(pageNumber,pageSize,sort);
       Page<EmployeeEntity> pageType=employeeRepository.findAll(p);
       List<EmployeeEntity> empList=pageType.getContent();
       return empList;
    }

    public List<EmployeeEntity> findAllByPaginationUsingQuery(String searchKey,PageRequest pageRequest) {
     //  List<EmployeeEntity> empSortList=employeeRepository.findBySortData(sortBy);

     //  Pageable p= PageRequest.of(pageNumber,pageSize,empSortList);
       // Page<EmployeeEntity> pageType= employeeRepository.findAll(p);
       // List<EmployeeEntity> empList= pageType.toList();//pageType.getContent();

        List<EmployeeEntity> empList=employeeRepository.getEmpList(searchKey,pageRequest);
        return empList;
    }
}
