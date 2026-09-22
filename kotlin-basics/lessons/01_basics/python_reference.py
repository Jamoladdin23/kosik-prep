def greet(name: str, greeting: str = "Ahoj") -> str:
    return f"{greeting}, {name}!"


def full_price(price: float, vat_percent: int = 21) -> float:
    return round(price * (1 + vat_percent / 100), 2)


def booking_label(client: str, hour: int, minute: int = 0) -> str:
    return f"{client} @ {hour:02d}:{minute:02d}"


def count_up(limit: int) -> int:
    total = 0
    for i in range(1, limit + 1):
        total += i
    return total
