from typing import Optional


def display_name(name: Optional[str]) -> str:
    return name if name is not None else "Anonymous"      # NOT `name or ...`: "" stays ""


def name_length(name: Optional[str]) -> Optional[int]:
    return len(name) if name is not None else None


def initial(name: Optional[str]) -> Optional[str]:
    if name is None or name == "":
        return None
    return name[0].upper()


def email_domain(email: Optional[str]) -> str:
    if email is None or "@" not in email:
        return "unknown"
    return email.split("@")[1]


def require_phone(phone: Optional[str]) -> str:
    if phone is None:
        raise ValueError("phone is required")
    return phone


def send_reminder(phone: Optional[str], log: list) -> None:
    if phone is not None:
        log.append(f"SMS to {phone}")
