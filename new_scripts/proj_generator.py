import requests 
import firebase_admin
from firebase_admin import credentials, project_management
import requests
from bundle_builder import BundleBuilder

def gen_info(cod_proj):
    r = requests.get("https://omegadev.taximachine.com.br/api/geracao/obterInformacaoProjeto", params={"cod_interno": cod_proj}, headers={"app-key": "chCoopTXMExemplo"})
    resp_json = r.json()['response']
    name = resp_json['nome']
    flag_type = resp_json['tipo_bandeira']

    return name, flag_type

#TODO: Use the project name to create new android_app - use the json to create the correct one
def gen_proj(cod_proj, app_os, app_type):
    print("Generating project...")
    cred = credentials.Certificate("/Users/Adrianoh/Desktop/mchapp-tools/new_scripts/machine-apps-1-firebase-adminsdk-p9g3p-77482d8a07.json")
    firebase_admin.initialize_app(cred)
    name, flag_type = gen_info(cod_proj)

    package_name = BundleBuilder(name, flag_type, app_type).get_bundle()
    display_name = name
    if (app_os == "Android"):
        project_management.create_android_app(package_name, display_name)
    else:
        project_management.create_ios_app(package_name, display_name)