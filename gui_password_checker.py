# gui_password_checker.py
import re
import math
import tkinter as tk
from tkinter import ttk

def check_strength(password):
    score = 0
    if len(password) >= 8: score += 1
    if re.search(r"[A-Z]", password) and re.search(r"[a-z]", password): score += 1
    if re.search(r"[0-9]", password): score += 1
    if re.search(r"[@$!%*?&#^()_\-+=[\]{};:'\",.<>\\/|`~]", password): score += 1
    if score == 4: level = "Strong"
    elif score == 3: level = "Medium"
    else: level = "Weak"
    return score, level

def estimate_crack_time(password):
    pool = 0
    if re.search(r"[a-z]", password): pool += 26
    if re.search(r"[A-Z]", password): pool += 26
    if re.search(r"[0-9]", password): pool += 10
    if re.search(r"[@$!%*?&#^()_\-+=[\]{};:'\",.<>\\/|`~]", password): pool += 32
    if pool == 0: return "Instant"
    entropy_bits = len(password) * math.log2(pool)
    guesses_per_second = 1e9
    seconds = 2**entropy_bits / guesses_per_second
    if seconds > 60*60*24*365*1000:
        return ">1000 years"
    units = [("years", 60*60*24*365), ("days", 60*60*24), ("hours", 60*60), ("minutes", 60), ("seconds", 1)]
    for name, div in units:
        if seconds >= div:
            return f"{seconds/div:.2f} {name}"
    return f"{seconds:.2f} seconds"

def on_check():
    pw = entry.get()
    score, level = check_strength(pw)
    crack = estimate_crack_time(pw)
    result_var.set(f"Score: {score}/4   |   Strength: {level}")
    crack_var.set(f"Crack estimate: {crack}")
    # update color bar
    if score == 4:
        color = "green"
    elif score == 3:
        color = "orange"
    else:
        color = "red"
    canvas.itemconfig(bar, fill=color)
    canvas.coords(bar, 0, 0, 150 * (score/4), 20)

root = tk.Tk()
root.title("Password Strength Checker")
root.geometry("420x200")
root.resizable(False, False)

ttk.Label(root, text="Enter password:").pack(pady=(10,0))
entry = ttk.Entry(root, show="*", width=40)
entry.pack(pady=(5,10))

check_btn = ttk.Button(root, text="Check Strength", command=on_check)
check_btn.pack()

result_var = tk.StringVar(value="Score: -   |   Strength: -")
crack_var = tk.StringVar(value="Crack estimate: -")
ttk.Label(root, textvariable=result_var, font=("Arial", 11)).pack(pady=(10,0))
ttk.Label(root, textvariable=crack_var, font=("Arial", 9)).pack()

# color bar
canvas = tk.Canvas(root, width=150, height=20, bd=0, highlightthickness=0)
canvas.pack(pady=(8,0))
bar = canvas.create_rectangle(0,0,0,20, fill="red")

root.mainloop()
