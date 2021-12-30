import requests
import os
import shutil
from proj_generator import get_app_dir_name

MAIN_FILE_PATH = "/Users/Adrianoh/Google Drive/Meu Drive/Comercial/Projetos/"

## TODO: Create new FileUtils file
## TODO: Figure out a way to hold the file value (using cod_proj and others)
def get_file(cod_proj):
    return MAIN_FILE_PATH + "Proj" + str(cod_proj) + "-TXM-" + get_app_dir_name() + "/Design/Imagens-base/"


def gen_images(cod_proj):
    r = requests.get("https://omegadev.taximachine.com.br/api/geracao/obterImagens", params={"cod_interno": cod_proj}, headers={"app-key": "chCoopTXMExemplo"})
    resp_json = r.json()['response']
    list_images = [file_name for file_name in resp_json.keys()]
    for image in list_images:
        download_images(resp_json, image, cod_proj)


def download_images(resp_json, img_name, cod_proj):
    print("Downloading image " + img_name)
    img_resp = requests.get(resp_json[img_name])
    file_name = img_name + ".jpg"
    file_path = get_file(cod_proj) + file_name
    file = open(file_path, "wb")
    file.write(img_resp.content)
    file.close()
    print("Image " + img_name + " downloaded.")