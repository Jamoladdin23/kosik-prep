<script setup lang="ts">
import type { Booking } from '@/types/booking'

// React: function BookingItem({ booking }: { booking: Booking }) { ... }
// defineProps is a compiler macro (no import needed) — it declares what this component receives.
// props is read-only: you never do props.booking = ... inside the component, same rule as React props.
const props = defineProps<{ booking: Booking }>()

// React: function BookingItem({ booking, onCancel }: Props) { ... oking.id)} }
// defineEmits declares which custom events this component can fire, with their payload types.
const emit = defineEmits<{ cancel: [id: number] }>()

// React: onCancel(booking.id)
// emit() fires the event; the parent listens for it with @cancel="...".
function handleCancel() {
  emit('cancel', props.booking.id)
}
</script>

<template>
  <li>
    {{ booking.client }} — {{ booking.startsAt }} — {{ booking.status }}
    <!-- React: {booking.status !== 'CANCELLED' && <button onClick=ton>} -->
    <button v-if="booking.status !== 'CANCELLED'" @click="handleCancel">Cancel</button>
  </li>
</template>
