# Ten-line Python script for git test
def greet(name: str) -> str:
    return f"Hello, {name}!"
def square(n: int) -> int:
    return n * n
def is_even(n: int) -> bool:
    return n % 2 == 0
def summarize(name: str, n: int) -> str:
    return f"{greet(name)} Your number {n} squared is {square(n)} and even={is_even(n)}."
print(summarize("Tester", 7))
