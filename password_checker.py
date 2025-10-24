# password_checker.py
import re
import math

def check_strength(password):
    score = 0

    # Length check
    if len(password) >= 8:
        score += 1

    # Uppercase and lowercase
    if re.search(r"[A-Z]", password) and re.search(r"[a-z]", password):
        score += 1

    # Digits
    if re.search(r"[0-9]", password):
        score += 1

    # Special characters (common symbols)
    if re.search(r"[@$!%*?&#^()_\-+=[\]{};:'\",.<>\\/|`~]", password):
        score += 1

    # Return score and textual strength
    if score == 4:
        level = "Strong 💪"
    elif score == 3:
        level = "Medium ⚙️"
    else:
        level = "Weak ⚠️"

    return score, level

def estimate_crack_time(password):
    # Very simple entropy-ish estimate (approximation for demo)
    pool = 0
    if re.search(r"[a-z]", password): pool += 26
    if re.search(r"[A-Z]", password): pool += 26
    if re.search(r"[0-9]", password): pool += 10
    if re.search(r"[@$!%*?&#^()_\-+=[\]{};:'\",.<>\\/|`~]", password): pool += 32

    # entropy ~ log2(pool^len) = len * log2(pool)
    if pool == 0:
        return "Instant"
    entropy_bits = len(password) * math.log2(pool)
    # Assume attacker can try 1 billion (1e9) guesses per second (demo number)
    guesses_per_second = 1e9
    seconds = 2**entropy_bits / guesses_per_second
    # Format time
    units = [("years", 60*60*24*365), ("days", 60*60*24), ("hours", 60*60), ("minutes", 60), ("seconds", 1)]
    if seconds == float("inf") or seconds > 60*60*24*365*1000:
        return ">1000 years"
    for name, div in units:
        if seconds >= div:
            return f"{seconds/div:.2f} {name}"
    return f"{seconds:.2f} seconds"

def main():
    print("=== Password Strength Checker ===")
    pw = input("Enter password: ")
    score, level = check_strength(pw)
    crack = estimate_crack_time(pw)
    print(f"\nScore: {score}/4")
    print(f"Strength: {level}")
    print(f"Estimated crack time (demo approximation): {crack}")

if __name__ == "__main__":
    main()
