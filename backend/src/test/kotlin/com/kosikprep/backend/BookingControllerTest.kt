package com.kosikprep.backend

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.MockMvc

// Python: @pytest.mark.django_db + APIClient(), or Django's TestCase with self.client.
// @SpringBootTest boots the WHOLE Spring context (all beans, the repository, in-memory H2),
// not just a lightweight test client.
// @AutoConfigureMockMvc adds MockMvc: a way to send HTTP requests WITHOUT a real running server,
// like Django's test Client / DRF's APIClient — no port is actually opened.
@SpringBootTest
@AutoConfigureMockMvc
class BookingControllerTest {

    // lateinit = "I promise Spring will fill this field before it's used".
    // The alternative would be a nullable `var mockMvc: MockMvc?` with `!!`/`?.` everywhere.
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun fullFlow() {
        // Python: self.client.post("/api/bookings/", data={...}, content_type="application/json")
        mockMvc.perform(
            post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"client":"Petr Novak","startsAt":"2027-01-01T09:00:00"}""")
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.client").value("Petr Novak"))

        // Python: response = self.client.get("/api/bookings/"); assert response.data[0]["client"] == "Petr Novak"
        // jsonPath("$[0].client") reads the "client" field of the first element of the JSON array response.
        mockMvc.perform(get("/api/bookings"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].client").value("Petr Novak"))
    }
}
