package com.example.demoFetchDataThroughQueryAndExceptionHandel.Repository;

import com.example.demoFetchDataThroughQueryAndExceptionHandel.Entity.EmployeeEntity;
import com.example.demoFetchDataThroughQueryAndExceptionHandel.Exception.UserException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long>{


     @Query(value="select * from employee_entity e where e.emp_first_name= :empFirstName",
               nativeQuery = true
     )
     List<EmployeeEntity> findByFirstName(@Param("empFirstName") String empFirstName);

     @Query(value="select * from employee_entity where emp_age= ?1",
             nativeQuery = true
     )
     //here does not write to "throws UserException" because its custome(user define) define unchecked Exception
     // throws keyword used for user define checked Exception
     List<EmployeeEntity> findByAge(Long empAge) throws UserException;


     //its very importent for pagination and sorting logic
     @Query(value = "select e.* from employee_entity e where LOWER(e.emp_first_name) LIKE CONCAT('%',:searchKey,'%')",
     nativeQuery = true
     )
          //in repository PageRequest name should be change by Pageable name
     List<EmployeeEntity> getEmpList(String searchKey, Pageable pageable);


}
