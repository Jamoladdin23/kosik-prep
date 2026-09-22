import re
from dataclasses import dataclass
from typing import Union


@dataclass(frozen=True)
class Booking:
    id: int
    client: str
    hour: int
    price: int


@dataclass(frozen=True)
class Success:
    booking: Booking


@dataclass(frozen=True)
class Conflict:
    existing: Booking


@dataclass(frozen=True)
class Invalid:
    reason: str


BookingResult = Union[Success, Conflict, Invalid]


def initials(s: str) -> str:
    return "".join(w[0].upper() for w in s.split() if w)


def is_valid_phone(s: str) -> bool:
    return re.fullmatch(r"\+\d{9,15}", s) is not None


def to_time_label(hour: int) -> str:
    return f"{hour:02d}:00"


def total_price(bs: list) -> int:
    return sum(b.price for b in bs)


def describe(r: BookingResult) -> str:
    match r:                                   # Python does NOT check that every case is covered
        case Success(booking=b):
            return f"Booked: {b.client} at {to_time_label(b.hour)}"
        case Conflict(existing=e):
            return f"Slot taken by {e.client}"
        case Invalid(reason=reason):
            return f"Invalid: {reason}"


def try_book(existing: list, client: str, hour: int, price: int) -> BookingResult:
    if not client.strip():
        return Invalid("client is blank")
    if hour not in range(8, 20):
        return Invalid(f"hour {hour} is outside 8..19")
    taken = next((b for b in existing if b.hour == hour), None)
    if taken is not None:
        return Conflict(taken)
    next_id = max((b.id for b in existing), default=0) + 1
    return Success(Booking(next_id, client, hour, price))
