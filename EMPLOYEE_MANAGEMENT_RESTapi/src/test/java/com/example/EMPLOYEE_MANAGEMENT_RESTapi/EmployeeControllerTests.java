package com.example.EMPLOYEE_MANAGEMENT_RESTapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    // Helper: adds an employee through the API and returns its new ID
    private long createEmployee(String name, String department, double salary) throws Exception {
        String json = "{\"name\":\"" + name + "\",\"department\":\"" + department + "\",\"salary\":" + salary + "}";
        String response = mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return ((Number) JsonPath.read(response, "$.id")).longValue();
    }

    @Test
    void addEmployee_returns201() throws Exception {
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Amit\",\"department\":\"Finance\",\"salary\":60000}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Amit"))
                .andExpect(jsonPath("$.department").value("Finance"))
                .andExpect(jsonPath("$.salary").value(60000.0));
    }

    @Test
    void getAllEmployees_returns200() throws Exception {
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getEmployeeById_returns200WhenFound() throws Exception {
        long id = createEmployee("Neha", "Sales", 45000);
        mockMvc.perform(get("/api/employees/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Neha"));
    }

    @Test
    void getEmployeeById_returns404WhenNotFound() throws Exception {
        mockMvc.perform(get("/api/employees/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Employee not found with id: 9999"));
    }

    @Test
    void updateEmployee_returns200() throws Exception {
        long id = createEmployee("Ravi", "Support", 30000);
        mockMvc.perform(put("/api/employees/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ravi Kumar\",\"department\":\"Engineering\",\"salary\":55000}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Ravi Kumar"))
                .andExpect(jsonPath("$.department").value("Engineering"))
                .andExpect(jsonPath("$.salary").value(55000.0));
    }

    @Test
    void updateEmployee_returns404WhenNotFound() throws Exception {
        mockMvc.perform(put("/api/employees/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"X\",\"department\":\"Y\",\"salary\":100}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteEmployee_returns200ThenEmployeeIsGone() throws Exception {
        long id = createEmployee("Temp", "Ops", 20000);
        mockMvc.perform(delete("/api/employees/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Employee with id " + id + " deleted successfully"));
        mockMvc.perform(get("/api/employees/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteEmployee_returns404WhenNotFound() throws Exception {
        mockMvc.perform(delete("/api/employees/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Employee not found with id: 9999"));
    }
}
