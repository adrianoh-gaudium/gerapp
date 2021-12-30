from subprocess import run
from tkinter import *
import tkinter as tk
from tkinter import ttk
from newproj import run_bash

root = Tk()
frm = ttk.Frame(root, padding=10)
frm.grid()

proj_num = tk.StringVar()

def gen_files():
    run_bash(proj_num.get())

ttk.Label(frm, text="Número do novo projeto").grid(column=0, row=0)
entry = ttk.Entry(frm, textvariable=proj_num).grid(column=1, row=0)
button = ttk.Button(frm, text="Gerar arquivos", command=gen_files).grid(column=2, row=1)
root.mainloop()

def get_initial_page_text_box():
    return ttk.Text()


def write_on_box(text_box, text):
    text_box.insert(ttk.END, "Put me at the end!")