package edu.sliit.controller;

import edu.sliit.dto.ScheduleDto;
import edu.sliit.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ScheduleController.class)
public class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleService scheduleService;

    @Test
    public void testAddSchedule() throws Exception {
        ScheduleDto scheduleDto = new ScheduleDto("123", Collections.singletonList("bin1"), "driver1", "10:00", "Route A");

        when(scheduleService.addSchedule(any(ScheduleDto.class))).thenReturn(ResponseEntity.status(201).body("Schedule added successfully"));

        mockMvc.perform(post("/schedule/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"scheduleId\": \"123\", \"smartBins\": [\"bin1\"], \"driverId\": \"driver1\", \"time\": \"10:00\", \"route\": \"Route A\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Schedule added successfully"));

        verify(scheduleService, times(1)).addSchedule(any(ScheduleDto.class));
    }

    @Test
    public void testGetSchedules() throws Exception {
        ScheduleDto scheduleDto = new ScheduleDto("123", Collections.singletonList("bin1"), "driver1", "10:00", "Route A");

        when(scheduleService.getAllSchedules()).thenReturn(Collections.singletonList(scheduleDto));

        mockMvc.perform(get("/schedule/getSchedules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].scheduleId").value("123"))
                .andExpect(jsonPath("$[0].smartBins[0]").value("bin1"))
                .andExpect(jsonPath("$[0].driverId").value("driver1"))
                .andExpect(jsonPath("$[0].time").value("10:00"))
                .andExpect(jsonPath("$[0].route").value("Route A"));

        verify(scheduleService, times(1)).getAllSchedules();
    }

    @Test
    public void testGetScheduleById() throws Exception {
        ScheduleDto scheduleDto = new ScheduleDto("123", Collections.singletonList("bin1"), "driver1", "10:00", "Route A");

        when(scheduleService.getScheduleById("123")).thenReturn(scheduleDto);

        mockMvc.perform(get("/schedule/getSchedule")
                        .param("scheduleId", "123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.scheduleId").value("123"))
                .andExpect(jsonPath("$.smartBins[0]").value("bin1"))
                .andExpect(jsonPath("$.driverId").value("driver1"))
                .andExpect(jsonPath("$.time").value("10:00"))
                .andExpect(jsonPath("$.route").value("Route A"));

        verify(scheduleService, times(1)).getScheduleById("123");
    }

    @Test
    public void testUpdateSchedule() throws Exception {
        when(scheduleService.updateSchedule(any(ScheduleDto.class))).thenReturn(ResponseEntity.status(200).body("Schedule updated successfully"));

        mockMvc.perform(put("/schedule/update/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"scheduleId\": \"123\", \"smartBins\": [\"bin1\"], \"driverId\": \"driver1\", \"time\": \"10:00\", \"route\": \"Route A\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Schedule updated successfully"));

        verify(scheduleService, times(1)).updateSchedule(any(ScheduleDto.class));
    }

    @Test
    public void testDeleteSchedule() throws Exception {
        when(scheduleService.deleteSchedule("123")).thenReturn(ResponseEntity.status(200).body("Schedule deleted successfully"));

        mockMvc.perform(delete("/schedule/delete/123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Schedule deleted successfully"));

        verify(scheduleService, times(1)).deleteSchedule("123");
    }

    @Test
    public void testDeleteAllSchedules() throws Exception {
        when(scheduleService.deleteAllSchedules()).thenReturn(ResponseEntity.status(200).body("All schedules deleted successfully"));

        mockMvc.perform(delete("/schedule/deleteAll"))
                .andExpect(status().isOk())
                .andExpect(content().string("All schedules deleted successfully"));

        verify(scheduleService, times(1)).deleteAllSchedules();
    }
}
