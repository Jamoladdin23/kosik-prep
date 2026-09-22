<script setup lang="ts">
import { ref } from 'vue'
import { useBookingsStore } from '@/stores/bookings'

const store = useBookingsStore()

const client = ref('')
const startsAt = ref('')

// async: createBooking now sends a real HTTP request and waits for the server's response.
async function handleSubmit() {
  if (!client.value.trim()) return

  await store.createBooking(client.value, startsAt.value)

  client.value = ''
  startsAt.value = ''
}
</script>

<template>
  <form @submit.prevent="handleSubmit">
    <input v-model="client" placeholder="Client name" />
    <input v-model="startsAt" type="datetime-local" />
    <button type="submit">Add booking</button>
  </form>
</template>
