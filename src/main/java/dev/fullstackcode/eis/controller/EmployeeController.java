package dev.fullstackcode.eis.controller;


import dev.fullstackcode.eis.entity.Department;
import dev.fullstackcode.eis.entity.Employee;
import dev.fullstackcode.eis.entity.Gender;
import dev.fullstackcode.eis.service.EmployeeService;
import dev.fullstackcode.eis.validation.OnCreate;
import dev.fullstackcode.eis.validation.OnUpdate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.groups.Default;
import javax.websocket.server.PathParam;
import java.util.List;


@Tag(name = "EmployeeController", description = "Manages operations on Employee")
@RestController
@RequestMapping("/employee")
@Validated
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


    @Operation(description = "List of all employees")
    @GetMapping()
    public List<Employee> getEmployees() {
        return employeeService.getAllEmployees();
    }


   @Operation( description = "Find employee by ID",
           parameters = { @Parameter(in = ParameterIn.PATH, name = "id", description = "Employee Id") },
           responses = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Employee.class))),
                   @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content(schema = @Schema(implementation = ResponseStatusException.class))) })
    @GetMapping("/{id}")
    public Employee getEmployee(   @PathVariable @Min(1) Integer id) {
        return employeeService.getEmployeeById(id).orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND,"Employee not found with id : "+ id));
    }



    @Operation( description = "createEmployee")
    @ResponseStatus(HttpStatus.CREATED) // send HTTP 201 instead of 200 as new object created
    @PostMapping
    @Validated(OnCreate.class)
    public Employee createEmployee( @RequestBody @Valid Employee employee) {
        return employeeService.createEmployee(employee);
    }


    @Operation(description = "updateEmployee details")
    @PutMapping()
    public Employee updateEmployee(@RequestBody  @Validated({OnUpdate.class, Default.class}) Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    @Operation(description = "Delete employee  based on the employee id")
    @DeleteMapping(value="/{id}")
    public  void deleteEmployee(  @Parameter(in = ParameterIn.PATH, name = "id", description = "Employee Id") @PathVariable("id") @Min(1) Integer id){
        employeeService.deleteEmployee(id);
    }

    @Operation(description = "Update employee department based on the employee id")
    @PatchMapping("/{id}/dept/{deptId}")
    public Employee updateEmpDepartment(@PathVariable("id") @Min(1) Integer emp_id , @PathVariable("deptId") @Min(1) Integer dept_id, @PathParam("id") @Min(1) String id) {
       return employeeService.updateEmpDepartment(emp_id,dept_id);
    }


    @Operation(description = "Update employee department based on the employee id")
    @PatchMapping("/{id}")
    public Employee updateEmpDepartmentById(@PathVariable("id") Integer emp_id , @RequestBody Department department) {
        return employeeService.updateEmpDepartment(emp_id,department.getId());
    }


    @Operation( description = "Get employees base on gender")
    @GetMapping(value="/gender/{gender}")
    public List<Employee> getEmployeesByGender(@PathVariable String gender) {

        return employeeService.findEmployeesByGender(Gender.valueOf(gender));
    }



}

