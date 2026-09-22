<script setup lang="ts">
import { onMounted } from 'vue'
import { useBookingsStore } from '@/stores/bookings'
import BookingItem from './BookingItem.vue'
import BookingForm from './BookingForm.vue'

const store = useBookingsStore()

// React: useEffect(() => { loadBookings() }, []) — runs once, after the component is mounted.
onMounted(() => {
  store.loadBookings()
})
</script>

<template>
  <div>
    <BookingForm />

    <input v-model="store.query" placeholder="Search client..." />

    <ul>
      <BookingItem
        v-for="booking in store.filteredBookings"
        :key="booking.id"
        :booking="booking"
        @cancel="store.cancelBooking"
      />
    </ul>
  </div>
</template>
