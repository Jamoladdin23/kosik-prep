package com.kosikprep.backend

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

// Python: class BookingViewSet(APIView): ... (or a ViewSet with queryset = Booking.objects.all())
// @RestController = @Controller + @ResponseBody: Spring serializes the return value to JSON itself,
// like DRF's Response(serializer.data).
@RestController
class BookingController(
    // Constructor injection: Spring finds a BookingRepository bean and passes it in automatically,
    // the same idea as passing a dependency as an explicit argument, but wired for you.
    private val repository: BookingRepository
) {
    // Python: def get(self, request): return Response(BookingSerializer(Booking.objects.all(), many=True).data)
    @GetMapping("/api/bookings")
    fun list(): List<BookingResponse> =
        repository.findAll().map { it.toResponse() }

    // Python: def post(self, request): serializer = CreateBookingSerializer(data=request.data);
    // serializer.is_valid(raise_exception=True); booking = Booking.objects.create(**serializer.validated_data)
    // @Valid triggers the @field:NotBlank/@field:Future checks from CreateBookingRequest automatically;
    // an invalid request never reaches the method body — Spring returns 400 by itself.
    @PostMapping("/api/bookings")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody request: CreateBookingRequest): BookingResponse {
        val booking = Booking(client = request.client, startsAt = request.startsAt)
        return repository.save(booking).toResponse()
    }

    // Python: def patch(self, request, pk): booking = get_object_or_404(Booking, pk=pk);
    // booking.status = Status.CANCELLED; booking.save()
    // findById returns an Optional<Booking> (Kotlin's null-safety equivalent from the Java world);
    // orElseThrow() is like get_object_or_404 — throws if nothing was found (a 404 handler comes later).
    @PatchMapping("/api/bookings/{id}/cancel")
    fun cancel(@PathVariable id: Long): BookingResponse {
        val booking = repository.findById(id).orElseThrow()
        booking.status = Status.CANCELLED
        return repository.save(booking).toResponse()
    }
}
