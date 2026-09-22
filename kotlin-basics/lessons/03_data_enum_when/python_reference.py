from dataclasses import dataclass, replace
from enum import Enum


class Status(Enum):
    PENDING = "pending"
    CONFIRMED = "confirmed"
    CANCELLED = "cancelled"


@dataclass(frozen=True)
class Booking:
    client: str
    hour: int
    status: Status = Status.PENDING


def confirm(b: Booking) -> Booking:
    return replace(b, status=Status.CONFIRMED)


def reschedule(b: Booking, new_hour: int) -> Booking:
    return replace(b, hour=new_hour)


def status_label(s: Status) -> str:
    match s:
        case Status.PENDING:
            return "Čeká na potvrzení"
        case Status.CONFIRMED:
            return "Potvrzeno"
        case Status.CANCELLED:
            return "Zrušeno"


def can_cancel(s: Status) -> bool:
    return s in (Status.PENDING, Status.CONFIRMED)


def hour_category(hour: int) -> str:
    if hour < 12:
        return "morning"
    elif hour < 18:
        return "afternoon"
    return "evening"
