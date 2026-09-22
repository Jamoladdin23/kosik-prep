import asyncio


async def fetch_price(id: int) -> int:
    await asyncio.sleep(1)          # imitation of a network call
    return id * 100


async def total_sequential(ids):
    total = 0
    for i in ids:
        total += await fetch_price(i)   # one after another: len(ids) seconds
    return total


async def fetch_all(ids):
    return list(await asyncio.gather(*(fetch_price(i) for i in ids)))   # in parallel: 1 second


async def total_parallel(ids):
    return sum(await fetch_all(ids))


async def run_all(names, log):
    async def one(n):
        await asyncio.sleep(0.1)
        log.append(n)
    await asyncio.gather(*(one(n) for n in names))     # returns when all are done


async def fetch_with_timeout(id, timeout_seconds):
    try:
        return await asyncio.wait_for(fetch_price(id), timeout_seconds)
    except asyncio.TimeoutError:
        return None


async def retry(times, block):
    if times <= 0:
        raise ValueError("times must be > 0")
    last = None
    for _ in range(times):
        try:
            return await block()
        except asyncio.CancelledError:
            raise                        # never swallow cancellation
        except Exception as e:
            last = e
    raise last
