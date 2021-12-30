#!/usr/bin/python3
import subprocess
import shlex
import os
import random
import string

COMMERCIAL_PATH = "/Users/Adrianoh/Google Drive/Meu Drive/Comercial/Projetos/"
OPERATION_PATH = "/Users/Adrianoh/Google Drive/Meu Drive/Operacao/Projetos Andamento/"

def run_bash(proj_num):
    subprocess.call(shlex.split(f"./newproj.sh {proj_num}"))

def x():
    proj_num = input("Qual o número do novo projeto a ser criado? \n")
    # prefix = "Proj" + proj_num + "-TXM-"
    # directory = prefix + get_app_dir_name()
    # parent_dir = "/Users/Adrianoh/Google Drive/Meu Drive/Comercial/Projetos/"
    # path = os.path.join(parent_dir, directory) 
    # os.mkdir(path) 
    # print("Directory '% s' created" % directory)
    subprocess.call(shlex.split(f"./newproj.sh {proj_num}"))
    # print("Baixando imagens do projeto \n")
    # gen_images(proj_num)

def get_hashes(cod_proj):
    proj_file, _ = get_proj_file_and_name(cod_proj)
    path = OPERATION_PATH + proj_file + "/Miscelâneas/bundle-sha1.txt"
    f = open(path, "r")
    file_content = f.readlines()
    google_hash = file_content[file_content.index("Google SHA1: \n") + 1][:-2]
    facebook_hash = file_content[file_content.index("Facebook Hash: \n") + 1][:-2]
    f.close()
    return google_hash, facebook_hash
        

def get_app_key(cod_proj):
    _, proj_name = get_proj_file_and_name(cod_proj)
    random_string = ''.join(random.choice(string.ascii_uppercase + string.ascii_lowercase + string.digits) for _ in range(7))
    return "ch" + proj_name + "-" + random_string

def get_proj_file_and_name(cod_proj):
    files = os.listdir(COMMERCIAL_PATH)

    for f in files:
        split_file = f.split("-")
        proj_num = split_file[0].replace("Proj", "")
        if str(cod_proj) == proj_num:
            return f, split_file[2]