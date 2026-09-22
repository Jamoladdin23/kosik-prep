// Урок Vue, этап 4. Заменяем захардкоженные данные на настоящий API.
//
// Python/JS: requests.get(url).json()  ->  const r = await fetch(url); const data = await r.json()
// Два await: первый ждёт заголовки ответа, второй — разбор тела как JSON (тоже асинхронный).

import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import type { Booking } from '@/types/booking'

export const useBookingsStore = defineStore('bookings', () => {
  const bookings = ref<Booking[]>([])
  const query = ref('')

  const filteredBookings = computed(() =>
    bookings.value.filter((b) => b.client.toLowerCase().includes(query.value.toLowerCase())),
  )

  async function loadBookings() {
    const response = await fetch('/api/bookings')
    bookings.value = await response.json()
  }

  async function cancelBooking(id: number) {
    const response = await fetch(`/api/bookings/${id}/cancel`, { method: 'PATCH' })
    const updated: Booking = await response.json()
    const index = bookings.value.findIndex((b) => b.id === id)
    if (index !== -1) bookings.value[index] = updated
  }

  async function createBooking(client: string, startsAt: string) {
    const response = await fetch('/api/bookings', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ client, startsAt }),
    })
    const created: Booking = await response.json()
    bookings.value.push(created)
  }

  return { bookings, query, filteredBookings, loadBookings, cancelBooking, createBooking }
})
