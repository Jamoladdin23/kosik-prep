from collections import Counter, defaultdict
from dataclasses import dataclass
from enum import Enum


class Status(Enum):
    PENDING = 1
    CONFIRMED = 2
    CANCELLED = 3


@dataclass(frozen=True)
class Booking:
    id: int
    client: str
    hour: int
    status: Status
    price: int


def confirmed_clients(bs):
    return [b.client for b in bs if b.status == Status.CONFIRMED]


def revenue(bs):
    return sum(b.price for b in bs if b.status == Status.CONFIRMED)


def group_by_status(bs):
    groups = defaultdict(list)
    for b in bs:
        groups[b.status].append(b)
    return dict(groups)


def count_by_status(bs):
    return dict(Counter(b.status for b in bs))


def sorted_by_hour(bs):
    return sorted(bs, key=lambda b: b.hour)


def by_id(bs):
    return {b.id: b for b in bs}


def busiest_hour(bs):
    if not bs:
        return None
    return Counter(b.hour for b in bs).most_common(1)[0][0]


def unique_clients_sorted(bs):
    return sorted({b.client for b in bs})
