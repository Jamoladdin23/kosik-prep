// Как Python TypedDict / dataclass — только описывает форму данных, без рантайм-класса.
export type Status = 'PENDING' | 'CONFIRMED' | 'CANCELLED'

export interface Booking {
  id: number
  client: string
  startsAt: string
  status: Status
}
